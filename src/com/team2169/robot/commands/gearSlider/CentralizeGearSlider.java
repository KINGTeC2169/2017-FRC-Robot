package com.team2169.robot.commands.gearSlider;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * This command adjusts the gear slider using 
 * position of the encoder to manipulate 
 * its position along the slider
 */
public class CentralizeGearSlider extends Command {

	public double positionTolerance = 400;
	public double roughTolerance = 1000;
	public double velocityTolerance = 250;
	public double gearMaxSpeed = .45;
	public double error;
	public double kP = .004;
	public double center;
	
	private boolean finished;
	
    public CentralizeGearSlider() {
    	gearMaxSpeed = .5;   	
    	finished = false;
    	center = 0;
    }
    
    public CentralizeGearSlider(double cent) {
    	gearMaxSpeed = .5;   	
    	finished = false;
    	center = cent;
    }
    
    // Initialize gear slider
    protected void initialize() {
    	gearMaxSpeed = .5;
    	finished = false;
    }

    protected void execute() {
    	// Central value is set to 0
    	error = center - Robot.gearManipulator.gearMotor.getEncPosition();
    	Robot.autoSliderCentralizing = true;
    	
    	// Update the global variable to keep the locomotion of the slider as "centralizing"
    	Robot.sliderCentralizing = true;
    	
    	// Set the speed of the motor based on the encoder position of the gear slider
    	if(error > 0){
    		if(error * gearMaxSpeed * kP >= gearMaxSpeed){
    			Robot.gearManipulator.gearManipRight(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipRight(Math.abs(error) * gearMaxSpeed * kP);
        	}
    	} else if(error < 0 ) {
    		if(error * gearMaxSpeed * kP <= -gearMaxSpeed){
    			Robot.gearManipulator.gearManipLeft(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipLeft(Math.abs(error) * gearMaxSpeed * kP);
        	}
    	} else {
    		Robot.gearManipulator.gearManipIdle();
    	}
    	
    	//SmartDashboard.putDouble("slider enc speed", Robot.gearManipulator.gearMotor.getEncVelocity());
    	//SmartDashboard.putDouble("slider enc pos", Robot.gearManipulator.gearMotor.getEncPosition());
    	
    	// Adjusts the position of the gear manip until it is slow enough and within tolerance to stop
    	if(error < positionTolerance && error > -positionTolerance){
    		finished = true;
    	}
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    	Robot.sliderCentralizing = false;
    	Robot.gearManipulator.gearMotor.set(0);
    	Robot.autoSliderCentralizing = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run.
    protected void interrupted() {
    	finished = false;
    	Robot.sliderCentralizing = false;
    	Robot.gearManipulator.gearMotor.set(0);
    	Robot.autoSliderCentralizing = false;
    }
}
