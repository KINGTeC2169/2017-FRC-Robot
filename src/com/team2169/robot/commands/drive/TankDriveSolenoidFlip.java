package com.team2169.robot.commands.drive;

import com.team2169.robot.Robot;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveSolenoidFlip extends Command {

	
	
    public TankDriveSolenoidFlip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.rightStick.getRawButton(2)){
    		Robot.driveTrain.dogShift.set(Value.kReverse);
    	}
    	else if (Robot.oi.rightStick.getRawButton(3)){
    		Robot.driveTrain.dogShift.set(Value.kForward);
    	}
    	
    	if (Robot.oi.leftStick.getRawButton(2)){
    		Robot.driveTrain.dogShift.set(Value.kReverse);
    	}
    	else if (Robot.oi.leftStick.getRawButton(3)){
    		Robot.driveTrain.dogShift.set(Value.kForward);
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
