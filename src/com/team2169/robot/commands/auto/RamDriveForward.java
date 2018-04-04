package com.team2169.robot.commands.auto;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RamDriveForward extends Command {

	// What is this, AveryDog?
		public double refiningMotorSpeed = .15;
		public double distanceTolerance = .2;
		public double angleTolerance = .5;
		public double rateTolerance = 1;
		public double motorChange = .001;
		public double tolerance = 10;
		public double rightSpeed = .65;
		public double leftSpeed = .65;
		public double minSpeed = .5;
		public double maxSpeed = .7;
		public double timer = 0;
		public double kP = .1;
		public double waitTime = .2;
		public double currentAngle;
		public double distance;
		public double errorDistance;
		public double errorAngle;
		public double refinedTolerance = 2;
		public double currentMotorSpeed = 0;
		public static double meterToTickConversion = 128;
		public double badEncTolerance = 1;
		public double velTolerance = 12;
		
		public boolean checkForSpring;
		//in to meters to diamter to circumfrence
		public static double wheelCircumfrence = 4 * .0254 * 2 * Math.PI;
		
		
		public double actualTickDistance;
		
		public boolean refinedDistance;
		public boolean finished;
		public boolean timerOn;
		public boolean checkEnc;
		public int flip = 1;

		public RamDriveForward(double dist) {
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
		}
		
		public RamDriveForward(double dist, double minSpeed2,double maxSpeed2) {
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
		}
		
		public RamDriveForward(double dist, double minSpeed2,double maxSpeed2, boolean checkingForPeg) {
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
			checkEnc = false;
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
	    		if(Robot.driveTrain.leftEnc.getDistance() < badEncTolerance){
	    			errorDistance = Math.abs((distance - Robot.driveTrain.rightEnc.getDistance()));
	    		} else if(Robot.driveTrain.rightEnc.getDistance() < badEncTolerance){
	    			errorDistance = Math.abs((distance - Robot.driveTrain.leftEnc.getDistance()));
	    		} else {
	    			errorDistance = Math.abs(distance - Robot.driveTrain.getEncDistance());
	    		}
	    		
	    		if(Robot.driveTrain.getEncRate() < velTolerance){
	    			finished = true;
	    		}
	    		
	    		/*if(Robot.driveTrain.leftEnc.getDistance() == 0 && Robot.driveTrain.rightEnc.getDistance() == 0){
	    			finished = true;
	    			Robot.autoFailed = true;
	    		}*/
	    		
	    	} else {
	    		errorDistance = Math.abs((distance - Robot.driveTrain.getEncDistance()));
	    	}
			
			
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
				if (leftSpeed * kP * errorDistance * flip >= leftSpeed * flip) {
					Robot.driveTrain.leftDrive.set(leftSpeed * flip);
					Robot.driveTrain.leftDrive2.set(leftSpeed * flip);
					currentMotorSpeed = leftSpeed * flip;
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
			} else {
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
	    		
	    		if(Math.abs(errorDistance) < refinedTolerance){
	    			Robot.driveTrain.leftDrive.set(0);
	    			Robot.driveTrain.rightDrive.set(0);
	    			finished = true;
	    		}
			}
			
			SmartDashboard.putDouble("aut driveforward dist", errorDistance);
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
