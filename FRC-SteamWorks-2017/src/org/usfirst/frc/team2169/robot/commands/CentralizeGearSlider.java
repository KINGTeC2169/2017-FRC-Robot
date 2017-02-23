package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CentralizeGearSlider extends Command {

	public double positionTolerance = 10;
	public double roughTolerance = 200;
	public double velocityTolerance = 250;
	public double gearMaxSpeed = .35;
	public double error;
	public double kP = .004;
	
	private boolean finished;
	
    public CentralizeGearSlider() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	gearMaxSpeed = .35;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	finished = false;
    	gearMaxSpeed = .35;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//no subtraction needed because the central value is a 
    	//reset value of 0
    	error = Robot.gearManipulator.gearMotor.getEncPosition();
    	SmartDashboard.putDouble("Centralize Gear Error", error);
    	
    	/*if(Robot.gearManipulator.gearMotor.getEncVelocity() < velocityTolerance && 
    	   Robot.gearManipulator.gearMotor.getEncVelocity() > -velocityTolerance && 
    	   Robot.gearManipulator.gearMotor.getEncPosition() < roughTolerance &&
    	   Robot.gearManipulator.gearMotor.getEncPosition() > -roughTolerance){
    		gearMaxSpeed /= 2;
    	}*/
    	
    		
    		
    	if(error > 0){
    		if(error * gearMaxSpeed * kP >= gearMaxSpeed){
    			Robot.gearManipulator.gearManipLeft(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipLeft(error * gearMaxSpeed * kP);
        	}
    	} else if(error < 0 ) {
    		if(error * gearMaxSpeed * kP <= -gearMaxSpeed){
    			Robot.gearManipulator.gearManipRight(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipRight(error * gearMaxSpeed * kP);
        	}
    	} else {
    		Robot.gearManipulator.gearManipIdle();
    	}
    	
    	//adjusts the position of the gear manip until it is slow enough and within tolerance to stop
    	if(Math.abs(error) < positionTolerance){
    		finished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished || Robot.oi.secondaryStick.getRawButton(2);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearManipulator.gearMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
