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
	
	public double maxSpeed = .8;
	public double kP = .01;
	
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
    	//if the gear manipualtor is desireed to be running
    	//automatically, then it will do so and vise versa for manual
    	//control
    	//if(Robot.gearManipulator.isSliderAutomatic){
    	//	Robot.gearManipulator.automaticGearManip();
    	//} else {
    	//	Robot.gearManipulator.manualGearManip();
    	//}
    	
    	if(!(Robot.oi.secondaryStick.getRawAxis(4) < 0.4 && Robot.oi.secondaryStick.getRawAxis(4) > -0.4)){
    		Robot.gearManipulator.manualGearManip();
    	} else {
    		
    		if(Robot.sliderVisionError == 0){
    			Robot.gearManipulator.gearManipIdle();
    		} else {
    			if(Robot.sliderVisionError < -0.01){
    				
    				if(Robot.sliderVisionError * kP < -maxSpeed){
    					Robot.gearManipulator.gearManipRight(-maxSpeed);
    				} else {
    					Robot.gearManipulator.gearManipRight(-Robot.sliderVisionError * kP);
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
    	if(Robot.gearManipulator.springButtonHit()){
    		Robot.gearManipulator.gearDoorSol.set(Value.kReverse);
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
