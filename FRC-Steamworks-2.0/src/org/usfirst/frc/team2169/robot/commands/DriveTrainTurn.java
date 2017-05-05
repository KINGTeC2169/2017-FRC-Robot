package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrainTurn extends Command {

	public double refiningMotorSpeed = .15;
	public double refinedTolerance = 1;
	public double motorSpeed = .45;
	public double tolerance = 3;
	public double waitTime = .2;
	public double timer = 0;
	public double want = 90;
	public double kP = .05;
	public double error;
	
	public boolean refinedAngle;
	public boolean finished;
	public boolean timerOn;
	
	public int flip = 1;
    
    public DriveTrainTurn(double angle){
    	requires(Robot.driveTrain);
    	want = angle;
    	refinedAngle = false;
    	finished = false;
    	timerOn = false;
    	
    	if(angle < 0)
    		flip = -1;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.driveTrain.imu.reset();
    	
    	refinedAngle = false;
    	finished = false;
    	timerOn = false;
    	
    	if(Robot.driveTrain.dogShift.get() == Value.kForward){
    		motorSpeed = .38;
    		refiningMotorSpeed = .25;
    		refinedTolerance = 6;
    		tolerance = 10;
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	error = Robot.driveTrain.getTurnAngle(want, Robot.driveTrain.imu.getAngleZ() / 4.0);
    	SmartDashboard.putDouble("Auto angle", Robot.driveTrain.imu.getAngleZ() / 4);
    	
    	//this is an example of the proportional component of the pid so it gets 
    	//closer and closer to the target as error decreases, speed decreases
    	if(!refinedAngle){
    		if(motorSpeed * kP * error >= motorSpeed){
    			//Robot.driveTrain.tankDrive(motorSpeed, motorSpeed);
	    		Robot.driveTrain.leftDrive.set(motorSpeed);
	    		Robot.driveTrain.leftDrive2.set(motorSpeed);
	            Robot.driveTrain.rightDrive.set(motorSpeed);
	            Robot.driveTrain.rightDrive2.set(motorSpeed);
    		} else {
    			//Robot.driveTrain.tankDrive(motorSpeed * kP * error, motorSpeed * kP * error);
    			Robot.driveTrain.leftDrive.set(motorSpeed * kP * error);
    			Robot.driveTrain.leftDrive2.set(motorSpeed * kP * error);
    			Robot.driveTrain.rightDrive.set(motorSpeed * kP * error);
    			Robot.driveTrain.rightDrive2.set(motorSpeed * kP * error);
    		}
    		
    		if(Math.abs(error) < tolerance){
	    		if (!timerOn) {
	    			timerOn = true;
	    			timer = Timer.getFPGATimestamp();
	    		}
	    	} 
    		
	    	if (timerOn && timer + waitTime < Timer.getFPGATimestamp()) {
	    		refinedAngle = true;
	    	}
	    	
	    //this part of the code runs when the robot is within a relative
	    //tolerance but momentum carries through the robot. This carries
	    //an extra delay so the robot can get to a refined angle
    	} else {
    		if(error > 0){
    			Robot.driveTrain.leftDrive.set(refiningMotorSpeed);
	    		Robot.driveTrain.leftDrive2.set(refiningMotorSpeed);
	            Robot.driveTrain.rightDrive.set(refiningMotorSpeed);
	            Robot.driveTrain.rightDrive2.set(refiningMotorSpeed);
    		} else {
    			Robot.driveTrain.leftDrive.set(-refiningMotorSpeed);
	    		Robot.driveTrain.leftDrive2.set(-refiningMotorSpeed);
	            Robot.driveTrain.rightDrive.set(-refiningMotorSpeed);
	            Robot.driveTrain.rightDrive2.set(-refiningMotorSpeed);
    		}
    		
    		if(Math.abs(error) < refinedTolerance){
    			Robot.driveTrain.leftDrive.set(0);
    			Robot.driveTrain.rightDrive.set(0);
    			finished = true;
    		}
    	}
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.leftDrive.set(0);
    	Robot.driveTrain.leftDrive2.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.rightDrive2.set(0);
    	Robot.driveTrain.imu.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
    
}
