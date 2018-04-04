package com.team2169.robot.commands.auto;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_TestSolenoids extends Command {

	public double timer;
	public double waitTime;
	
	public boolean finished;
	// Wait time for intake
    public Auto_TestSolenoids(double time) {
        waitTime = time;
    }
    //time stamp for the intake timer
    protected void initialize() {
    	finished = false;
    	timer = Timer.getFPGATimestamp();
    }
    //runs auto intake
    protected void execute() {
    	
    	Robot.gearManipulator.playerSol.set(Value.kForward);
    	Robot.gearManipulator.gearDoorSol.set(Value.kReverse);
    	
    	if (timer + waitTime < Timer.getFPGATimestamp()) {
    		finished = true;
    	}
    	
    	
    }
    //finishes
    protected boolean isFinished() {
        return finished && Robot.gearManipulator.springButtonHit();
    }
    // End program
    protected void end() {
    	Robot.gearManipulator.playerSol.set(Value.kReverse);
    	Robot.gearManipulator.gearDoorSol.set(Value.kForward);
    }
    // If interrupted, intake is set Idle
    protected void interrupted() {
    	Robot.intakes.intakeIdle();
    }
}
