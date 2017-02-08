package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CentralizeGearSlider extends Command {

	public double positionTolerance = .2;
	public double velocityTolerance = 1;
	public double gearMaxSpeed = .2;
	public double error;
	
	private boolean finished;
	
    public CentralizeGearSlider() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//no subtraction needed because the central value is a 
    	//reset value of 0
    	error = Robot.gearManipulator.gearMotor.getEncPosition();
    	
    	if(error > 0 && gearMaxSpeed * error <= 1){
    		Robot.gearManipulator.gearManipLeft(gearMaxSpeed * error);
    	} else if(error < 0 && gearMaxSpeed * error >= -1) {
    		Robot.gearManipulator.gearManipRight(gearMaxSpeed * error);
    	} else {
    		Robot.gearManipulator.gearManipIdle();
    	}
    	
    	//adjusts the position of the gear manip until it is slow enough and within tolerance to stop
    	if(Math.abs(error) < positionTolerance && Robot.gearManipulator.gearMotor.getEncVelocity() < velocityTolerance){
    		finished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
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
