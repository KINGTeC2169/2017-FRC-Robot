package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	A list of commands that run the pseudo code concepts
 *	for this side of the field
 */
public class Auto_RedLeft extends CommandGroup {

	private double driveOffset = 2.891256;
	private double distToGoal = 2.322063;
	
    public Auto_RedLeft() {
    	 
    	addSequential(new DriveForward(driveOffset));
    	addSequential(new DriveTrainTurn(60));
    	
    	addSequential(new GetVisonData());
    	addParallel(new GearManip());
    	
    	if(Robot.isVisionDataGood){
    		addSequential(new DriveForward(Robot.visionDistance - Robot.springLength));
    	} else {
    		addSequential(new DriveForward(distToGoal - Robot.springLength));
    	}
    	
    	addSequential(new GearSolenoidFlip());
    }
}
