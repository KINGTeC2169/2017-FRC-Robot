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
	
    public Auto_BlueLeft() {
    	 
    	addSequential(new DriveForward(driveOffset));
    	addSequential(new DriveTrainTurn(68));
    	
    	addSequential(new DriveForward(distToGoal));
    	addSequential(new SetGearDoor(Value.kReverse));
    	addSequential(new DriveForward(-1.5));
    	
    }
}
