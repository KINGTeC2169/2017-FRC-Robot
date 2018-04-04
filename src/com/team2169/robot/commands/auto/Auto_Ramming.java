package com.team2169.robot.commands.auto;

import com.team2169.robot.Robot;
import com.team2169.robot.commands.gearSlider.CentralizeGearSlider;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_Ramming extends CommandGroup {

	public double sliderIterationChange;
	public double distToPeg = 20;
	public double newCenter;
	public int count;
	
    public Auto_Ramming(double dist, double min, double max) {
        count = 0;
        
        //bring in that extra drive forward that was carried from
        //approaching at a far distance
        addSequential(new RamDriveForward(dist, min, max, true));
        
        
        //Repeat the sequence of backing up and realigning the slider
        while(Robot.isSpringButtonPressed == false){
        	//number of iterations for slider math
        	count++;
        	sliderIterationChange = 1500;
        	
        	//calculate alternating slider change
        	//Ex. if sliderIterationChange = 200
        	//	200, -400, 600, -800
        	//This allows for back and forth checking of peg on all slider configurations
        	newCenter = (Math.pow(-1, count - 1)) * (count * sliderIterationChange);
        	
        	addSequential(new DriveBackwards(distToPeg));
        	addSequential(new CentralizeGearSlider(newCenter));
        	addSequential(new RamDriveForward(distToPeg, min, max, true));
        	
        	
        }
    	
    	
    }
}
