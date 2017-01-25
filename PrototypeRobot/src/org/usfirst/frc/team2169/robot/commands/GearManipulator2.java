package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearManipulator2 extends Command {
	boolean breaking = true;
    public GearManipulator2() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Used to move back and forth
    	if(Robot.driveTrain.leftButton.get()== true){
    		while(Robot.oi.leftStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(.2);
        	}
        	if(Robot.oi.leftStick.getTrigger()== false){
        		Robot.driveTrain.gearManip.set(0);
    	}
        	
        if(Robot.driveTrain.rightButton.get()== true){
        	while(Robot.oi.rightStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(-.2);
        	}
        	if(Robot.oi.rightStick.getTrigger()== false){
        		Robot.driveTrain.gearManip.set(0);
        	}
        }
        
        // Used to disable movement of one stick on on button triggers
        if (Robot.driveTrain.rightButton.get()== false){
        	if(Robot.oi.rightStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(0);
        	}
        	if(Robot.oi.leftStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(.2);
        	}
        }
        
        if (Robot.driveTrain.leftButton.get()== false){
        	if(Robot.oi.leftStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(0);
        	}
        	if(Robot.oi.rightStick.getTrigger()== true){
        		Robot.driveTrain.gearManip.set(.2);
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
