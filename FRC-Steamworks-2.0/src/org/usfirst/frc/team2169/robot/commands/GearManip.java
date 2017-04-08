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
	
	public double maxSpeed = .45;
	public double kP = .25;
	public double angleThreshold = 5;
	public double speed = 1;
	
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
   	/* Run the slider based off of vision input if sliderAutomatic is set to true,
   	 * sliderCentralizing is set to false, and if the gear door is closed, otherwise,
   	 * set the speed of the slider to the value from the X axis of the right stick on the
   	 * secondary controller.
   	 */
    	Robot.sliderVisionError = Robot.table.getNumber("centx", 0);
    	
    	if(Math.abs(Robot.oi.secondaryStick.getRawAxis(4)) > 0.3){
			//Robot.sliderVisionError = Robot.sliderVisionError + (Robot.oi.secondaryStick.getRawAxis(4)*10);
		}
    	if(Robot.sliderAutomatic == true && Robot.sliderCentralizing == false){	
    		if(Robot.gearManipulator.gearDoorSol.get() == Value.kForward){

    			if (Robot.sliderVisionError < angleThreshold && Robot.sliderVisionError > -angleThreshold ){
            		Robot.gearManipulator.gearManipBoth((-Robot.sliderVisionError / 40));
            	}
            	else{
            		Robot.gearManipulator.gearManipBoth((-Robot.sliderVisionError / 30));
            	}
    			
    		} else {
    			Robot.gearManipulator.gearMotor.set(0);
    		}
    	} else {
    		
    		if(Math.abs(Robot.oi.secondaryStick.getRawAxis(4)) > .4){
    			Robot.gearManipulator.gearManipBoth(Robot.oi.secondaryStick.getRawAxis(4)*speed);
    		} else {
    			Robot.gearManipulator.gearMotor.set(0);
    		}
    	}
    	
    	
    	if (Robot.oi.secondaryStick.getRawAxis(3) > .7){
    		speed = 0.5;
    	} else {
    		speed = 1;
    	}
    	
    	//If the spring button is hit, then the gear manipulator 
    	//stays closed or closes
    	//AUTOMATIC
    	if(!Robot.gearManipulator.springButton.get() && Robot.oi.secondaryStick.getRawButton(6)){
    		Robot.gearManipulator.gearDoorSol.set(Value.kReverse);
    	}else if(!Robot.gearManipulator.springButton.get() && (Robot.oi.secondaryStick.getRawAxis(3) > .7)){
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

    // Called when another command which requires one or more of the same subsystems is scheduled to run
    protected void interrupted() {
    }
}
