package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearSolenoidFlip extends Command {

    public GearSolenoidFlip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if(Robot.pneumatics.gearSol.get() == Value.kOff){
    		Robot.pneumatics.gearSol.set(Value.kForward);
    	} else if(Robot.pneumatics.gearSol.get() == Value.kForward){
    		Robot.pneumatics.gearSol.set(Value.kReverse);
    	} else {
    		Robot.pneumatics.gearSol.set(Value.kForward);
    	}*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//return true so it only runs once
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
