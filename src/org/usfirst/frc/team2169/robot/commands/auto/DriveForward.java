package org.usfirst.frc.team2169.robot.commands.auto;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command drives the robot over a given distance with simple proportional
 * control and adjusts motor speed based on an error in the angle.
 * This command will drive a given distance limiting to a maximum speed.
 */
public class DriveForward extends Command {
	
	// What is this, AveryDog?
	public double refiningMotorSpeed = .15;
	public double distanceTolerance = .2;
	public double angleTolerance = 1;
	
	public double rateTolerance = 1;
	public double tolerance = 10;
	public double motorChange = .0018;
	public double rightSpeed = .65;
	public double leftSpeed = .65;
	public double minSpeed = .5;
	public double maxSpeed = .7;
	public double timer = 0;
	public double kP = .1;
	public double waitTime = .5;
	public double distance;
	public double errorDistance;
	public double errorAngle;
	public double currentAngle;
	public double refinedTolerance = .5;
	public double badEncTolerance = 1;
	public double currentMotorSpeed = 0;
	public double incSpeedStep = .02;
	
	public boolean checkForSpring;
	public static double meterToTickConversion = 128;
	public static double wheelCircumfrence = 4 * .0254 * 2 * Math.PI;
	
	public double actualTickDistance;
	
	public boolean incSpeed;
	public boolean refinedDistance;
	public boolean finished;
	public boolean timerOn;
	public boolean checkEnc;
	public int flip = 1;

	public DriveForward(double dist) {
		requires(Robot.driveTrain);
		distance = dist;
		refinedDistance = false;
		timerOn = false;
		
		//if the distance is negeative then the robot drives backwards
		if(dist < 0)
			flip = -1;
		
		leftSpeed = .6;
		rightSpeed = .6;
		
		finished = false;
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		refinedDistance = false;
		checkForSpring = false;
		incSpeed = true;
	}
	
	public DriveForward(double dist, double minSpeed2,double maxSpeed2) {
		requires(Robot.driveTrain);
		distance = dist;
		refinedDistance = false;
		timerOn = false;
		
		//if the distance is negeative then the robot drives backwards
		if(dist < 0)
			flip = -1;
		
		minSpeed = minSpeed2;
		maxSpeed = maxSpeed2;
		
		finished = false;
		
		leftSpeed = (minSpeed + maxSpeed) / 2;
		rightSpeed = (minSpeed + maxSpeed) / 2;
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		refinedDistance = false;
		checkForSpring = false;
		incSpeed = true;
	}
	
	public DriveForward(double dist, double minSpeed2,double maxSpeed2, boolean checkingForPeg) {
		requires(Robot.driveTrain);
		distance = dist;
		refinedDistance = false;
		timerOn = false;
		
		//if the distance is negeative then the robot drives backwards
		if(dist < 0)
			flip = -1;
		
		minSpeed = minSpeed2;
		maxSpeed = maxSpeed2;
		
		finished = false;
		
		leftSpeed = (minSpeed + maxSpeed) / 2;
		rightSpeed = (minSpeed + maxSpeed) / 2;
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		
		refinedDistance = false;
		checkForSpring = checkingForPeg;
		incSpeed = true;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
		
		
		currentAngle = Robot.driveTrain.imu.getAngleZ() / 4;
		finished = false;
		
		leftSpeed = .6;
		rightSpeed = .6;
		
		refinedDistance = false;
		
		timer = Timer.getFPGATimestamp();
		checkEnc = false;
		incSpeed = true;
		
		if(Robot.driveTrain.dogShift.get() == Value.kForward){
			tolerance = 20;
			refinedTolerance = 3;
		}
	}

	@Override
	protected void execute() {
		
		//after waitime has passed, run a check to see if 
		//the encoders are working properly, otherwise run off of just one encoder
    	if (timer + waitTime < Timer.getFPGATimestamp()) {
    		checkEnc = true;
    		SmartDashboard.putString("WARNING", "checking");
    	}
    	
    	if(checkEnc){
    		if(Math.abs(Robot.driveTrain.leftEnc.getDistance()) < badEncTolerance){
    			errorDistance = Math.abs((distance - Robot.driveTrain.rightEnc.getDistance()));
    		} else if(Math.abs(Robot.driveTrain.rightEnc.getDistance()) < badEncTolerance){
    			errorDistance = Math.abs((distance - Robot.driveTrain.leftEnc.getDistance()));
    		} else {
    			errorDistance = Math.abs(distance - Robot.driveTrain.getEncDistance());
    		}
    	} else {
    		errorDistance = Math.abs((distance - Robot.driveTrain.getEncDistance()));
    	}
		
		errorAngle = Robot.driveTrain.getTurnAngle(currentAngle, Robot.driveTrain.imu.getAngleZ() / 4);
		
		if(incSpeed){
			
			//increase the speed applied to the motors to prevent initial jolting of robot
			incSpeedStep += .035;
			
			//if the speeding up speed applied to the motors is close to what we want
			//jump out ofthe loop
			if(incSpeedStep > minSpeed)
				incSpeed = false;
			
			//applies speed to motor
			Robot.driveTrain.leftDrive.set(incSpeedStep);
    		Robot.driveTrain.leftDrive2.set(incSpeedStep);
            Robot.driveTrain.rightDrive.set(-incSpeedStep);
            Robot.driveTrain.rightDrive2.set(-incSpeedStep);
		}
		
		//if the robot drives out of the angle tolerance to drive 
		//forward, a motor speed up or cool down is applied
		if(Math.abs(errorAngle) < angleTolerance){
			//apply a motor change based upon how far off angle
			//motorChange = Math.abs(errorAngle) * .001;
			//turning too far right
			
			double average = (leftSpeed + rightSpeed) / 2;
			leftSpeed = average;
			rightSpeed = average;
			
			if(errorAngle < 0){
				if(rightSpeed < maxSpeed){
					rightSpeed += (motorChange);
				} else {
					leftSpeed -= (motorChange / 3);
				}
			//turning too far left
			} else if(errorAngle > 0){
				if(leftSpeed < maxSpeed){
					leftSpeed += (motorChange);
				} else {
					rightSpeed -= (motorChange / 3);
				}
			}
		
		} else {
			
			if(errorAngle < 0){
				if(rightSpeed < maxSpeed){
					rightSpeed += (motorChange * 1.4 * Math.abs(errorAngle));
				} else {
					leftSpeed -= (motorChange * 1.4 * Math.abs(errorAngle));
				}
			//turning too far left
			} else if(errorAngle > 0){
				if(leftSpeed < maxSpeed){
					leftSpeed += (motorChange * 1.4 * Math.abs(errorAngle));
				} else {
					rightSpeed -= (motorChange * 1.4 * Math.abs(errorAngle));
				}
			}
		}
		
		SmartDashboard.putDouble("Auto Angle Error", errorAngle);
		SmartDashboard.putNumber("Auto Distance Error", errorDistance);

				
		//clamp set speeds so they can be applied to the 
		//motors without errors or exceptions
		leftSpeed = Robot.ktMath.clamp(leftSpeed, minSpeed, maxSpeed);
		rightSpeed = Robot.ktMath.clamp(rightSpeed, minSpeed, maxSpeed);
		
		if(refinedDistance == false && incSpeed == false){
			//Update driving speeds on both sides of the driveTrain
			if (leftSpeed * kP * errorDistance * flip >= leftSpeed * flip) {
				Robot.driveTrain.leftDrive.set(leftSpeed * flip);
				Robot.driveTrain.leftDrive2.set(leftSpeed * flip);
			} else {
				Robot.driveTrain.leftDrive.set(leftSpeed * kP * errorDistance * flip);
				Robot.driveTrain.leftDrive2.set(leftSpeed * kP * errorDistance * flip);
			}
			
			if (rightSpeed * kP * errorDistance * flip >= rightSpeed * flip) {
				Robot.driveTrain.rightDrive.set(-rightSpeed * flip);
				Robot.driveTrain.rightDrive2.set(-rightSpeed * flip);
			} else {
				Robot.driveTrain.rightDrive.set(-rightSpeed * kP * errorDistance * flip);
				Robot.driveTrain.rightDrive2.set(-rightSpeed * kP * errorDistance * flip);
			}
			
			if(Math.abs(errorDistance) < tolerance){
				refinedDistance = true;
			}
		} else if(incSpeed == false){
			if(errorDistance > 0){
    			Robot.driveTrain.leftDrive.set(refiningMotorSpeed * flip);
	    		Robot.driveTrain.leftDrive2.set(refiningMotorSpeed * flip);
	            Robot.driveTrain.rightDrive.set(-refiningMotorSpeed * flip);
	            Robot.driveTrain.rightDrive2.set(-refiningMotorSpeed * flip);
    		} else {
    			Robot.driveTrain.leftDrive.set(-refiningMotorSpeed * flip);
	    		Robot.driveTrain.leftDrive2.set(-refiningMotorSpeed * flip);
	            Robot.driveTrain.rightDrive.set(refiningMotorSpeed * flip);
	            Robot.driveTrain.rightDrive2.set(refiningMotorSpeed * flip);
    		}
    		
    		/*if(Math.abs(errorDistance) < refinedTolerance){
    			Robot.driveTrain.leftDrive.set(0);
    			Robot.driveTrain.rightDrive.set(0);
    			finished = true;
    		}*/
		}
		
		if(Math.abs(errorDistance) < refinedTolerance){
			Robot.driveTrain.leftDrive.set(0);
			Robot.driveTrain.rightDrive.set(0);
			finished = true;
		}
		
		SmartDashboard.putNumber("Auto Left Enc", Robot.driveTrain.leftEnc.getDistance());
		SmartDashboard.putNumber("Auto Right Enc", Robot.driveTrain.rightEnc.getDistance());
		SmartDashboard.putNumber("Auto Enc Dist Combined", errorDistance);
	}

	@Override
	protected boolean isFinished() {
		//if the robot reaches its distance or the switches inside of the intakes
		//are hit, it stops the command
		if(checkForSpring == false){
			return finished;
		} else {
			return finished || (Robot.isSpringButtonPressed && checkForSpring);
		}
		
	}

	@Override
	protected void end() {
		if(Robot.gearManipulator.springButtonHit()){
			SmartDashboard.putDouble("auto dist left", errorDistance);
		}
		
		Robot.driveTrain.leftDrive.set(0);
		Robot.driveTrain.leftDrive2.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.rightDrive2.set(0);
		Robot.driveTrain.resetEncoders();
		
	}
}
