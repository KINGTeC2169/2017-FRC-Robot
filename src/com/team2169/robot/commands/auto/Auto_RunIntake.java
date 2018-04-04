package org.usfirst.frc.team2169.robot.commands.auto;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Auto_RunIntake extends Command {

	public double timer;
	public double waitTime;
	
	public boolean finished;
	// Wait time for intake
    public Auto_RunIntake(double time) {
        waitTime = time;
    }
    //time stamp for the intake timer
    protected void initialize() {
    	finished = false;
    	timer = Timer.getFPGATimestamp();
    }
    //runs auto intake
    protected void execute() {
    	
    	Robot.intakes.intakeIn();
    	
    	if (timer + waitTime < Timer.getFPGATimestamp()) {
    		finished = true;
    	}
    }
    //finishes
    protected boolean isFinished() {
        return finished;
    }
    // End program
    protected void end() {
    	Robot.intakes.intakeIdle();
    }
    // If interrupted, intake is set Idle
    protected void interrupted() {
    	Robot.intakes.intakeIdle();
    }
}
