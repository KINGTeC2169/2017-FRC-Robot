package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RunHanger extends Command {

	public double timer;
	public double waitTime;
	
	public boolean finished;
	// Wait time for intake
    public Auto_RunHanger(double time) {
        waitTime = time;
    }
    //time stamp for the intake timer
    protected void initialize() {
    	finished = false;
    	timer = Timer.getFPGATimestamp();
    }
    //runs auto intake
    protected void execute() {
    	
    	Robot.hanger.hangMotor.set(-1);
    	
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
    	Robot.hanger.hangMotor.set(0);
    }
    // If interrupted, intake is set Idle
    protected void interrupted() {
    	Robot.hanger.hangMotor.set(0);
    }
}
