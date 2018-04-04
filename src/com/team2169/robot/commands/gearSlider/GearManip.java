package org.usfirst.frc.team2169.robot.commands.gearSlider;

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
	
	public double maxSpeed = .3;
	public double kP = .25;
	public double angleThreshold = 6;
	public double sliderSpeed = 1;
	
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
    	
    	if(Robot.sliderAutomatic == true && Robot.sliderCentralizing == false && Robot.PIDvisionactive == false){	
    		if(Robot.gearManipulator.gearDoorSol.get() == Value.kForward){

    			Robot.gearManipulator.gearManipBoth((-Robot.sliderVisionError / 30));
    		}
    			
    			
    	} else {
    		
        	if (Robot.oi.secondaryStick.getRawAxis(3) > .7){
        		sliderSpeed = 0.5;
        	} else {
        		sliderSpeed = .75;
        	}
        	
    		if(Math.abs(Robot.oi.secondaryStick.getRawAxis(4)) > .4){
    			Robot.gearManipulator.gearManipBoth(Robot.oi.secondaryStick.getRawAxis(4) * sliderSpeed);
    		} else {
    			Robot.gearManipulator.gearMotor.set(0);
    		}
    	}
    	
    	//If the spring button is hit, then the gear manipulator 
    	//stays closed or closes
    	if(Robot.gearManipulator.springButtonHit() && (Robot.oi.secondaryStick.getRawAxis(3) > .7)){
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
