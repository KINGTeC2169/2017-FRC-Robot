package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	A list of commands that run the pseudo code concepts
 *	for this side of the field
 */
public class Auto_CentralGoal extends CommandGroup {
	
	private double distToGoal = 72;
	private double crossField = 120;
	
    public Auto_CentralGoal() {
    	
    	//hang gear
    	addSequential(new DriveForward(distToGoal));
    	//addParallel(new DriveBackwards());
    	addSequential(new SetGearDoor(Value.kReverse));
    	
    	
    
    	//Go back from the gear hanger
    	addSequential(new DriveBackwards(distToGoal - 30));
    	//turn 90
    	addSequential(new DriveTrainTurn(-90));
    	//forward
    	addSequential(new DriveForward(70));
    	//turn 90
    	addSequential(new DriveTrainTurn(90));
    	//forward Crossfield
    	addSequential(new DriveForward(crossField));
    	
    	//addSequential(new GearSolenoidFlip());
    }
}
