package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Auto_ContinouslyUpdateSlider extends Command {

	public double fullThreshold = 35;
	public double angleThreshold = 12;
	public double maxSpeed = 0.2;
	
	public boolean finished;
	
    public Auto_ContinouslyUpdateSlider() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	finished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    		Robot.sliderVisionError = Robot.table.getNumber("centx", 0);
    	
    		if(Math.abs(Robot.sliderVisionError) < fullThreshold){

    			if(Math.abs(Robot.sliderVisionError) > angleThreshold){
    				if(Robot.sliderVisionError > 0){
    					Robot.gearManipulator.gearManipLeft(maxSpeed);
    				} else {
    					Robot.gearManipulator.gearManipRight(maxSpeed);
    				}
//    				Robot.gearManipulator.gearManipBoth((-Robot.sliderVisionError / 30));
    			} else {
    				Robot.gearManipulator.gearManipBoth((-Robot.sliderVisionError / 22));
    			}
    		}
		
    		SmartDashboard.putDouble("centx auto", Robot.sliderVisionError);
    		
    		if(Robot.sliderVisionError > fullThreshold){
    			SmartDashboard.putString("Error", "SLIDER OUT OF RANGE");
    		}
    		
//    		if(Math.abs(Robot.sliderVisionError) < .5){
//    			finished = true;
//    		}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
