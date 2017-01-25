package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearManipulator extends Command {

    public GearManipulator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.leftStick.getRawButton(6) || Robot.oi.leftStick.getRawButton(7)){
    		if(Robot.oi.leftStick.getRawButton(6)){
    			Robot.driveTrain.gearManipLeft();
    		} else {
    			Robot.driveTrain.gearManipRight();
    		}
    	} else {
    		Robot.driveTrain.gearManip.set(0);
    	}
    	
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
