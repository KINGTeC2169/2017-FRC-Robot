package com.team2169.robot.commands.drive;

import com.team2169.robot.Robot;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveSolenoidFlipOnce extends Command {

	private Value val;
	
    public TankDriveSolenoidFlipOnce(Value val2) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	val = val2;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.dogShift.set(val);
    	
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
