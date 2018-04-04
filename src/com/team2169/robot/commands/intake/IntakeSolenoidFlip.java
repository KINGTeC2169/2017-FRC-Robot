package org.usfirst.frc.team2169.robot.commands.intake;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeSolenoidFlip extends Command {

    public IntakeSolenoidFlip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakes.shiftIntakes();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//returns true right away because it is only flipping the 
    	//pistons once
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
