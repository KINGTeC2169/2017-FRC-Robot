package org.usfirst.frc.team2169.robot.commands.auto;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimedStop extends Command {

	public double time;
	public double waitTime;
	
	private boolean finished;
	
    public TimedStop(double wait) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	waitTime = wait;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = Timer.getFPGATimestamp();
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.tankDrive(0, 0);
    	
    	if(time + waitTime < Timer.getFPGATimestamp()){
    		finished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
