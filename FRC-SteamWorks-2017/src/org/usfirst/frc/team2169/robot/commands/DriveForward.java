package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2169.robot.Robot;

/**
 * This command drives the robot over a given distance with simple proportional
 * control and adjusts motor speed based on an error in the angle.
 * This command will drive a given distance limiting to a maximum speed.
 */
public class DriveForward extends Command {
	
	public double distanceTolerance = .1;
	public double angleTolerance = .5;
	public double rateTolerance = 1;
	public double motorChange = .025;
	public double tolerance = .1;
	public double rightSpeed = .9;
	public double leftSpeed = .9;
	public double minSpeed = .8;
	public double maxSpeed = 1;
	public double kP = .01;
	public double currentAngle;
	public double distance;
	public double errorDistance;
	public double errorAngle;

	public boolean finished;

	public DriveForward(double dist) {
		requires(Robot.driveTrain);
		distance = dist;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		currentAngle = Robot.driveTrain.imu.getAngleZ();
		finished = false;
		
	}

	@Override
	protected void execute() {
		errorDistance = (distance - Robot.driveTrain.getEncDistance());
		errorAngle = Robot.driveTrain.getTurnAngle(currentAngle, Robot.driveTrain.imu.getAngleZ());
		
		//if the robot drives out of the angle tolerance to drive 
		//forward, a motor speed up or cool down is applied
		if(!(Math.abs(errorAngle) < angleTolerance)){
			if(errorAngle > 0){
				if(leftSpeed > maxSpeed - .01){
					rightSpeed += motorChange;
				} else if(leftSpeed <= minSpeed){
					leftSpeed -= motorChange;
				}
			} else if(errorAngle < 0){
				if(rightSpeed > maxSpeed - .01){
					leftSpeed += motorChange;
				} else if(rightSpeed > minSpeed){
					rightSpeed -= motorChange;
				}
			}
		} 
		
		//clamp set speeds so they can be applied to the 
		//motors without errors or exceptions
		if(leftSpeed < minSpeed){
			leftSpeed = minSpeed;
		} else if(leftSpeed > maxSpeed){
			leftSpeed = maxSpeed;
		}
		
		if(rightSpeed < minSpeed){
			rightSpeed = minSpeed;
		} else if(rightSpeed > maxSpeed){
			rightSpeed = maxSpeed;
		}
		
		
		//Update driving speeds on both sides of the driveTrain
		if (leftSpeed * kP * errorDistance >= leftSpeed) {
			Robot.driveTrain.leftDrive.set(leftSpeed);
		} else {
			Robot.driveTrain.leftDrive.set(leftSpeed * kP * errorDistance);
		}
		
		if (rightSpeed * kP * errorDistance >= rightSpeed) {
			Robot.driveTrain.leftDrive.set(-rightSpeed);
		} else {
			Robot.driveTrain.leftDrive.set(-rightSpeed * kP * errorDistance);
		}
		
		if(Math.abs(errorDistance) <= distanceTolerance && Robot.driveTrain.getEncRate() < rateTolerance){
			finished = true;
		}
	}

	@Override
	protected boolean isFinished() {
		//if the robot reaches its distance or the switches inside of the intakes
		//are hit, it stops the command
		return finished || Robot.gearManipulator.springButtonHit();
	}

	@Override
	protected void end() {
		Robot.driveTrain.leftDrive.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.resetEncoders();
	}
}
