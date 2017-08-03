package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TimedDriveForward extends Command {

	public double refiningMotorSpeed = .15;
	public double distanceTolerance = .2;
	public double angleTolerance = .5;
	public double rateTolerance = 1;
	public double motorChange = .001;
	public double tolerance = 3;
	public double rightSpeed = .65;
	public double leftSpeed = .65;
	public double minSpeed = .5;
	public double maxSpeed = .7;
	public double timer = 0;
	public double kP = .1;
	public double waitTime = .4;
	public double currentAngle;
	public double distance;
	public double errorAngle;
	public double refinedTolerance = 2;
	public double currentMotorSpeed = 0;
	public static double meterToTickConversion = 128;
	//in to meters to diamter to circumfrence
	public static double wheelCircumfrence = 4 * .0254 * 2 * Math.PI;
	
	public double actualTickDistance;
	
	public boolean refinedDistance;
	public boolean finished;
	public boolean timerOn;
	
	public int flip = 1;

	public TimedDriveForward(double time) {
		requires(Robot.driveTrain);
		refinedDistance = false;
		timerOn = true;
		
		waitTime = time;
		
		leftSpeed = .6;
		rightSpeed = .6;
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		refinedDistance = false;
	}
	
	public TimedDriveForward(double time, double minSpeed2,double maxSpeed2) {
		requires(Robot.driveTrain);
		refinedDistance = false;
		timerOn = true;
		
		minSpeed = minSpeed2;
		maxSpeed = maxSpeed2;
		
		waitTime = time;
		
		leftSpeed = (minSpeed + maxSpeed) / 2;
		rightSpeed = (minSpeed + maxSpeed) / 2;
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		refinedDistance = false;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		currentAngle = Robot.driveTrain.imu.getAngleZ() / 4;
		finished = false;
		
		leftSpeed = .6;
		rightSpeed = .6;
		
		refinedDistance = false;
		
		timer = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
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
				
		Robot.driveTrain.leftDrive.set(leftSpeed * flip);
		Robot.driveTrain.leftDrive2.set(leftSpeed * flip);
		Robot.driveTrain.rightDrive.set(-rightSpeed * flip);
		Robot.driveTrain.rightDrive2.set(-rightSpeed * flip);
		
    	if (timerOn && timer + waitTime < Timer.getFPGATimestamp()) {
    		finished = true;
    	}
	}

	@Override
	protected boolean isFinished() {
		//if the robot reaches its distance or the switches inside of the intakes are hit, it stops the command
		return finished || Robot.gearManipulator.springButtonHit();
		
	}

	@Override
	protected void end() {
		if(Robot.gearManipulator.springButtonHit())
			Robot.isSpringButtonPressed = true;
		
		Robot.driveTrain.leftDrive.set(0);
		Robot.driveTrain.leftDrive2.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.rightDrive2.set(0);
		Robot.driveTrain.resetEncoders();
	}
}
