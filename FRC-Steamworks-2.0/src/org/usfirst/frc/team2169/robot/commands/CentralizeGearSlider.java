package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CentralizeGearSlider extends Command {

	public double positionTolerance = 200;
	public double roughTolerance = 500;
	public double velocityTolerance = 250;
	public double gearMaxSpeed = .45;
	public double error;
	public double kP = .004;
	
	private boolean finished;
	
    public CentralizeGearSlider() {
    	gearMaxSpeed = .45;   	
    	finished = false;
    }

    protected void initialize() {
    	gearMaxSpeed = .45;
    	finished = false;
    }

    protected void execute() {
    	//no subtraction needed because the central value is a reset value of 0
    	error = Robot.gearManipulator.gearMotor.getEncPosition();
    	
    	//update the global variable to keep the locomotion of the slider as "centralizing"
    	Robot.sliderCentralizing = true;
    	
    	//Set the speed of the motor based on the encoder position of the gear slider
    	if(error > 0){
    		if(error * gearMaxSpeed * kP >= gearMaxSpeed){
    			Robot.gearManipulator.gearManipLeft(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipLeft(error * gearMaxSpeed * kP);
        	}
    	} else if(error < 0 ) {
    		if(error * gearMaxSpeed * kP <= -gearMaxSpeed){
    			Robot.gearManipulator.gearManipRight(gearMaxSpeed);
        	} else {
        		Robot.gearManipulator.gearManipRight(-error * gearMaxSpeed * kP);
        	}
    	} else {
    		Robot.gearManipulator.gearManipIdle();
    	}
    	
    	SmartDashboard.putDouble("slider enc speed", Robot.gearManipulator.gearMotor.getEncVelocity());
    	
    	//adjusts the position of the gear manip until it is slow enough and within tolerance to stop
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	finished = false;
    	Robot.gearManipulator.gearMotor.set(0);
    }
}
