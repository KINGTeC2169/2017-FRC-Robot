package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	A list of commands that run the pseudo code concepts
 *	for this side of the field
 */
public class Auto_BlueRight extends CommandGroup {

	private double driveOffset = 92;
	private double distToGoal = 20;
	
    public Auto_BlueRight() {
    	 
    	addSequential(new DriveForward(driveOffset));
    	addSequential(new DriveTrainTurn(-71));
    	
    	addSequential(new DriveForward(distToGoal));
    	addSequential(new SetGearDoor(Value.kReverse));
    	addSequential(new DriveForward(-1.5));
    	
    }
}
