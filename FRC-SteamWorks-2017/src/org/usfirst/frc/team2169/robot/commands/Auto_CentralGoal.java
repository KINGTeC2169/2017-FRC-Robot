package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	A list of commands that run the pseudo code concepts
 *	for this side of the field
 */
public class Auto_CentralGoal extends CommandGroup {
	
	private double distToGoal = 3.85;
	
    public Auto_CentralGoal() {
    	
    	addSequential(new DriveForward(distToGoal));
    	addSequential(new SetGearDoor(Value.kReverse));
    	addSequential(new DriveForward(-1.5));
    	
    	//addSequential(new GearSolenoidFlip());
    }
}
