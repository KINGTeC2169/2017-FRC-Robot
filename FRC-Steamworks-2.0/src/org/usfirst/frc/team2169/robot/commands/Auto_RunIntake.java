package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RunIntake extends Command {

	public double timer;
	public double waitTime;
	
	public boolean finished;
	
    public Auto_RunIntake(double time) {
        waitTime = time;
    }

    protected void initialize() {
    	finished = false;
    	timer = Timer.getFPGATimestamp();
    }

    protected void execute() {
    	
    	Robot.intakes.intakeIn();
    	
    	if (timer + waitTime < Timer.getFPGATimestamp()) {
    		finished = true;
    	}
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    	Robot.intakes.intakeIdle();
    }

    protected void interrupted() {
    	Robot.intakes.intakeIdle();
    }
}
