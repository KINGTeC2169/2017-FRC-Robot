package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearManip extends Command {
	
    public GearManip() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearManipulator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if the gear manipualtor is desireed to be running
    	//automatically, then it will do so and vise versa for manual
    	//control
    	/*if(Robot.gearManipulator.isSliderAutomatic){
    		Robot.gearManipulator.automaticGearManip();
    	} else {
    		Robot.gearManipulator.manualGearManip();
    	}*/
    	
    	Robot.gearManipulator.manualGearManip();
    	
    	//this statement flips the door open when it is in
    	//a closed door state and when the springButton is hit
    	//if(Robot.gearManipulator.springButtonHit() && Robot.gearManipulator.gearDoorSol.get() == Value.kForward)
    		//Robot.gearManipulator.flipDoorSolenoids();
    	
    	//if the sping button is hit, then the gear manipulator 
    	//stays closed or closes
    	//if(!Robot.gearManipulator.springButtonHit())
    		//Robot.gearManipulator.gearDoorSol.set(Value.kForward);
    	
    	
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
