package org.usfirst.frc.team2169.robot.commands;

import java.util.Set;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearManip extends Command {
	
	public double maxSpeed = .5;
	public double kP = .05;
	public double angleThreshold = 10;
	
    public GearManip() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearManipulator);
        
        //Robot.visionGearMotor = 0.0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.visionGearMotor = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    @SuppressWarnings("deprecation")
	protected void execute() {
    	
    	if(Robot.sliderAutomatic){
    		
    		if(Robot.gearManipulator.gearDoorSol.get() == Value.kReverse){
    			if (Robot.sliderVisionError < angleThreshold && Robot.sliderVisionError > -angleThreshold ){
            		Robot.gearManipulator.gearManipBoth(-Robot.sliderVisionError / 40);
            	}
            	else{
            		Robot.gearManipulator.gearManipBoth(-Robot.sliderVisionError / 30);
            	}
    		} else {
    			Robot.gearManipulator.gearMotor.set(0);
    		}
    	} else {
    		
    		if(Math.abs(Robot.oi.secondaryStick.getRawAxis(4)) > .4){
    			Robot.gearManipulator.gearManipBoth(Robot.oi.secondaryStick.getRawAxis(4));
    		} else {
    			Robot.gearManipulator.gearMotor.set(0);
    		}
    	}
    	
//    	if(Robot.oi.secondaryStick.getRawAxis(4) > .5){
//    		Robot.gearManipulator.gearManipLeft(1);
//    	} else if(Robot.oi.secondaryStick.getRawAxis(4) < -.5){
//    		Robot.gearManipulator.gearManipRight(1);
//    	} else {
//    		Robot.gearManipulator.gearManipIdle();
//    	}
    	
    	//if the sping button is hit, then the gear manipulator 
    	//stays closed or closes
    	//AUTOMATIC
    	if(!Robot.gearManipulator.springButton.get()){
    		Robot.gearManipulator.gearDoorSol.set(Value.kForward);
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
