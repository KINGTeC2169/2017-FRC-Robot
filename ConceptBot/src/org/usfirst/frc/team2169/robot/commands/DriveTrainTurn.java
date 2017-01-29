package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTrainTurn extends Command {

	public double refiningMotorSpeed = .25;
	public double refinedTolerance = .25;
	public double motorSpeed = .5;
	public double tolerance = 6;
	public double waitTime = .4;
	public double timer = 0;
	public double want = 90;
	public double kP = .05;
	public double error;
	
	public boolean refinedAngle;
	public boolean finished;
	public boolean timerOn;
    
    public DriveTrainTurn(double angle){
    	requires(Robot.driveTrain);
    	want = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.imu.reset();
    	
    	refinedAngle = false;
    	finished = false;
    	timerOn = false;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	error = Robot.driveTrain.getTurnAngle(want, Robot.driveTrain.imu.getAngleZ());
    	
    	//this is the proportional component of the pid so it gets closer and closer
    	//to the target as error decreases, speed decreases
    	if(!refinedAngle){
    		if(motorSpeed * kP * error >= motorSpeed){
	    		Robot.driveTrain.leftDrive.set(motorSpeed);
	            Robot.driveTrain.rightDrive.set(-motorSpeed);
    		} else {
    			Robot.driveTrain.leftDrive.set(motorSpeed * kP * error);
    			Robot.driveTrain.rightDrive.set(-motorSpeed * kP * error);
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
	            Robot.driveTrain.rightDrive.set(-refiningMotorSpeed);
    		} else {
    			Robot.driveTrain.leftDrive.set(refiningMotorSpeed);
	            Robot.driveTrain.rightDrive.set(-refiningMotorSpeed);
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
    	Robot.driveTrain.imu.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
    
}
