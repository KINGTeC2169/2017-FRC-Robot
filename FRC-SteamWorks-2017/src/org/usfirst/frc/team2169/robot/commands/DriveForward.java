package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2169.robot.Robot;

/**
 * This command drives the robot over a given distance with simple proportional
 * control and adjusts motor speed based on an error in the angle.
 * This command will drive a given distance limiting to a maximum speed.
 */
public class DriveForward extends Command {
	
	public double refiningMotorSpeed = .15;
	public double distanceTolerance = 1;
	public double angleTolerance = .5;
	public double rateTolerance = 1;
	public double motorChange = .0005;
	public double tolerance = 5;
	public double rightSpeed = .25;
	public double leftSpeed = .25;
	public double minSpeed = .2;
	public double maxSpeed = .4;
	public double timer = 0;
	public double kP = .01;
	public double waitTime = 3;
	public double currentAngle;
	public double distance;
	public double errorDistance;
	public double errorAngle;
	public double refinedTolerance = 2;
	
	public boolean refinedDistance;
	public boolean finished;
	public boolean timerOn;

	public DriveForward(double dist) {
		requires(Robot.driveTrain);
		distance = dist;
		refinedDistance = false;
		timerOn = false;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		currentAngle = Robot.driveTrain.imu.getAngleZ() / 4;
		finished = false;
		
	}

	@Override
	protected void execute() {
		errorDistance = (distance - Robot.driveTrain.getEncDistance());
		errorAngle = Robot.driveTrain.getTurnAngle(currentAngle, Robot.driveTrain.imu.getAngleZ() / 4);
		
		//if the robot drives out of the angle tolerance to drive 
		//forward, a motor speed up or cool down is applied
		if(!(Math.abs(errorAngle) < angleTolerance)){
			//turning too far right
			if(errorAngle < 0){
				if(rightSpeed < maxSpeed){
					rightSpeed += motorChange;
				} else {
					leftSpeed -= motorChange;
				}
			//turning too far left
			} else if(errorAngle > 0){
				if(leftSpeed < maxSpeed){
					leftSpeed += motorChange;
				} else {
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
		
		if(refinedDistance == false){
			//Update driving speeds on both sides of the driveTrain
			if (leftSpeed * kP * errorDistance >= leftSpeed) {
				Robot.driveTrain.leftDrive.set(leftSpeed);
				Robot.driveTrain.leftDrive2.set(leftSpeed);
			} else {
				Robot.driveTrain.leftDrive.set(leftSpeed * kP * errorDistance);
				Robot.driveTrain.leftDrive2.set(leftSpeed * kP * errorDistance);
			}
			
			if (rightSpeed * kP * errorDistance >= rightSpeed) {
				Robot.driveTrain.rightDrive.set(-rightSpeed);
				Robot.driveTrain.rightDrive2.set(-rightSpeed);
			} else {
				Robot.driveTrain.rightDrive.set(-rightSpeed * kP * errorDistance);
				Robot.driveTrain.rightDrive2.set(-rightSpeed * kP * errorDistance);
			}
			
			if(Math.abs(errorDistance) < tolerance){
	    		if (!timerOn) {
	    			timerOn = true;
	    			timer = Timer.getFPGATimestamp();
	    		}
	    		
	    		refinedDistance = true;
	    	} 
    		
	    	if (timerOn && timer + waitTime < Timer.getFPGATimestamp()) {
	    		refinedDistance = true;
	    	}
		} else {
			if(errorDistance > 0){
    			Robot.driveTrain.leftDrive.set(refiningMotorSpeed);
	    		Robot.driveTrain.leftDrive2.set(refiningMotorSpeed);
	            Robot.driveTrain.rightDrive.set(-refiningMotorSpeed);
	            Robot.driveTrain.rightDrive2.set(-refiningMotorSpeed);
    		} else {
    			Robot.driveTrain.leftDrive.set(-refiningMotorSpeed);
	    		Robot.driveTrain.leftDrive2.set(-refiningMotorSpeed);
	            Robot.driveTrain.rightDrive.set(refiningMotorSpeed);
	            Robot.driveTrain.rightDrive2.set(refiningMotorSpeed);
    		}
    		
    		if(Math.abs(errorDistance) < refinedTolerance){
    			Robot.driveTrain.leftDrive.set(0);
    			Robot.driveTrain.rightDrive.set(0);
    			finished = true;
    		}
		}
		
		SmartDashboard.putDouble("Auto enc average", errorDistance);
		SmartDashboard.putDouble("Auto drive forward angle error", errorAngle);
	}

	@Override
	protected boolean isFinished() {
		//if the robot reaches its distance or the switches inside of the intakes
		//are hit, it stops the command
		//return finished || Robot.gearManipulator.springButtonHit();
		return finished;
	}

	@Override
	protected void end() {
		Robot.driveTrain.leftDrive.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.resetEncoders();
	}
}
