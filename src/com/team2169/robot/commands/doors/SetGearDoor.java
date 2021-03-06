package com.team2169.robot.commands.doors;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetGearDoor extends Command {

	private Value val;
	private boolean go;
	
    public SetGearDoor(Value val2) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	val = val2;
    	go = false;
    }
    
    public SetGearDoor(Value val2, boolean x) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	val = val2;
    	go = x;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.gearManipulator.springButtonHit() || go)
    		Robot.gearManipulator.gearDoorSol.set(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
