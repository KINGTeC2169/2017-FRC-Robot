package com.team2169.robot.commands.drive;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDrive extends Command {

    public TankDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//this function runs all six of the drive train motors
    	//based on the Y position of the left joystick and
    	//the y stick continuously throughout this command
    	Robot.driveTrain.tankDrive(Robot.oi.leftStick.getY(), Robot.oi.rightStick.getY());
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
