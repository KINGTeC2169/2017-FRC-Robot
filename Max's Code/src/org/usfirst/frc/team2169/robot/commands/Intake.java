package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2169.robot.Robot;

/**
 *
 */
public class Intake extends Command {
	public Intake() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeRoller);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//Put nothing here for now
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.intakeRoller.intake();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.hanger.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
