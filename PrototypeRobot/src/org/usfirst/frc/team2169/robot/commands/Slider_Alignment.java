package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Slider_Alignment extends Command {

    public Slider_Alignment() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	// we made need commands for the datatable or retreaving data from the PI
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		/*  slider motor declarations/init (possibly declarations for encoders if we need them)
    		 * 
    		 */
 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
   
    	// inside loop:
    	/* set a variable to the value of the yaw angle that is recived from a datatable
    	 * 
    	 * start the motor
    	 * 
    	 *if the value of x is greater than or equal to 1 then set the speed of the slider motor so that the slider moves to the left
    	 *
    	 *else/if the vale of x is less than or equal to -1 then set the speed of the slider motor so that the slider moves to the right
    	 * 
    	 * else stop the motor and make isFinished return true
    	 *
    	 * 
    	 * 
    	 */
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
