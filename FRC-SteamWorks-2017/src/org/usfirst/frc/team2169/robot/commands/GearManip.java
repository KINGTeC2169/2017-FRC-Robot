package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearManip extends Command {

	public double velocityTolerance = 1;
	
    public GearManip() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearManipulator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//updateManualSliderPosition();
    	
    	//this function runs whenever the drivers want the 
    	//slider to run based on vision data
    	if(Robot.gearManipulator.gearAutomatic){
    		updateAutomaticSliderPosition();
    	}
    	
    }
    
    //automatic controlling of the gear slider and stops
    //it if the slider jolts back and forth repeatedly
    private void updateAutomaticSliderPosition(){
    	if(Robot.visionTargetOnLeft){
    		Robot.gearManipulator.gearManipLeft();
    	} else {
    		Robot.gearManipulator.gearManipRight();
    	}
    	
    	if(Robot.gearManipulator.gearMotor.getEncVelocity() < velocityTolerance){
    		Robot.gearManipulator.gearMotor.set(0);
    	}
    }
    
    //if the vision data does not work, then the drivers
    //can control the slider with this method
    private void updateManualSliderPosition(){
    	//this statement tests if the gear manipulator should move at all, 
    	//otherwise the gear manipulator should not move at all
    	if(Robot.oi.secondaryStick.getRawButton(0) || Robot.oi.secondaryStick.getRawButton(1)){
    		if(Robot.oi.secondaryStick.getRawButton(0)){
    			Robot.gearManipulator.gearManipLeft();
    		} else {
    			Robot.gearManipulator.gearManipRight();
    		}
    	} else {
    		Robot.gearManipulator.gearMainpIdle();
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
