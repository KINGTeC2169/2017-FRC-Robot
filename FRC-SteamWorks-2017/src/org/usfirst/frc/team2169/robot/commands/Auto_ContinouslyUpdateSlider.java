package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_ContinouslyUpdateSlider extends Command {

	private double maxSpeed = .6;
	public double kP = .05;
	
    public Auto_ContinouslyUpdateSlider() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.sliderVisionError == 0){
			Robot.gearManipulator.gearManipIdle();
		} else {
			if(Robot.sliderVisionError < -0.01){
				
				if(Robot.sliderVisionError * kP < -maxSpeed){
					Robot.gearManipulator.gearManipRight(maxSpeed);
				} else {
					Robot.gearManipulator.gearManipRight(Robot.sliderVisionError * kP);
				}
        	} else if (Robot.sliderVisionError > 0.01){
        		
        		if(Robot.sliderVisionError * kP > maxSpeed){
					Robot.gearManipulator.gearManipLeft(maxSpeed);
				} else {
					Robot.gearManipulator.gearManipLeft(Robot.sliderVisionError * kP);
				}
        		
        	}
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
