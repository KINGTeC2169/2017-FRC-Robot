package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	A list of commands that run the pseudo code concepts
 *	for this side of the field
 */
public class Auto_BlueLeft extends CommandGroup {

	private double driveOffset = 90;
	private double distToGoal = 17;
	private double angleOffset = 8;
	
	private double distToCrossField = 50;
    public Auto_BlueLeft() {
    	 
    	//drive forward and align with the target
    	addSequential(new DriveForward(driveOffset));
    	addSequential(new DriveTrainTurn(68));
    	
    	//drive towards the target, hang the gear, and back up
    	addSequential(new DriveForward(distToGoal));
    	addSequential(new SetGearDoor(Value.kReverse));
    	addSequential(new DriveBackwards(distToGoal));
    	
    	//turn away from the airship
    	addSequential(new DriveTrainTurn(-68 - angleOffset));
    	
    	//drive across line
    	addSequential(new DriveForward(distToCrossField));
    	/*
    	if (driveBackAuto == true){
    		addSequential(new DriveBackwards(100));
    	}
    		else{
    		
    	}
    	*/
    	}
    }

