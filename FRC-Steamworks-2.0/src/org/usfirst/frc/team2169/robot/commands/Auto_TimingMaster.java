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
    	//default value doing nothing
        if(Robot.timingOption == -1)
        	return;
        
        //doing nothing selected as 0
        if(Robot.timingOption == 0){
        	
        } else if(Robot.timingOption == 1){ //drive forward timing
        	addSequential(new TimedDriveForward(3));
        } else if(Robot.timingOption == 2){ //center gear hang timing
        	addSequential(new TimedDriveForward(3)); //approach the peg
        	
        	//addParallel(new Auto_ContinouslyUpdateSlider()); //update vision to track the target
        	
        	addSequential(new SetGearDoor(doorRelease)); //hang the gear
        	
        	addSequential(new TimedDriveBackwards(2)); //back up from the target
        	
        } else { //nothing in case any other numbers were selected
        	
        }
        
        
        
        
        
        
    }
}
