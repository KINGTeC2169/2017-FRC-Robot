package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_PickPosition extends Command {

	private int position;
	
    public Auto_PickPosition(int x) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	position = x;
    	Robot.savedPsosition = x;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	position = Robot.savedPsosition;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.position = position;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
