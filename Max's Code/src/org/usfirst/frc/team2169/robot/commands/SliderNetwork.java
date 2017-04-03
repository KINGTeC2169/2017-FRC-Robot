package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team2169.robot.Robot;

/**
 *
 */
public class SliderNetwork extends Command {
	
	NetworkTable vTable;
	public static double motorValue = 0;
	
	public SliderNetwork() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearSlider);
		vTable = NetworkTable.getTable("vTable");
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//Put nothing here for now
		
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("deprecation")
	@Override
	protected void execute() {
		motorValue = vTable.getNumber("centX");
		//Robot.gearSlider.slide(motorValue);
		
		
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
