package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_TimingMaster extends CommandGroup {

	private Value doorRelease = Value.kReverse;
	
    public Auto_TimingMaster() {
    	// Default value doing nothing
        if(Robot.timingOption == -1)
        	return;
        
        // Doing nothing selected as 0
        if(Robot.timingOption == 0){
        	
        } else if(Robot.timingOption == 1){ // Drive forward timing
        	addSequential(new TimedDriveForward(3));
        } else if(Robot.timingOption == 2){ // Center gear hang timing
        	addSequential(new TimedDriveForward(3)); // Approach the peg
        	
        	// AddParallel(new Auto_ContinouslyUpdateSlider()); // Update vision to track the target
        	
        	addSequential(new SetGearDoor(doorRelease)); // Hang the gear
        	
        	addSequential(new TimedDriveBackwards(2)); // Back up from the target
        	
        } else { // Nothing in case any other numbers were selected
        	
        }
        
        
        
        
        
        
    }
}
