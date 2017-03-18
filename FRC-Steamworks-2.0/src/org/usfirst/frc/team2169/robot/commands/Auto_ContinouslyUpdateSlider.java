package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Auto_ContinouslyUpdateSlider extends Command {

	public double angleThreshold = 10;
	
    public Auto_ContinouslyUpdateSlider() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    		Robot.sliderVisionError = Robot.table.getNumber("centX", -1);
    	
			if (Robot.sliderVisionError < angleThreshold && Robot.sliderVisionError > -angleThreshold ){
        		Robot.gearManipulator.gearManipBoth(-Robot.sliderVisionError / 40);
        	}
        	else{
        		Robot.gearManipulator.gearManipBoth(-Robot.sliderVisionError / 30);
        	}
			
			SmartDashboard.putDouble("Auto visionSliderError", Robot.sliderVisionError);
		
    	
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