package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToPoint extends Command {
	double distance;
	public double currentSpeed;
	private boolean isDone;
	double angleFix;
    public DriveToPoint(double dist) {
    	requires(Robot.driveTrain);
    	Robot.DriveToPointPID.enable();
    	Robot.DrivingStraightPID.enable();
        Robot.DriveToPointPID.setSetpoint(dist);
        
        distance = dist;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.DriveToPointPID.enable();
    	Robot.DrivingStraightPID.enable();
    	Robot.driveTrain.imu.reset();
    	Robot.PIDdrivingactive = true;
    	Robot.driveTrain.resetEncoders();
    	isDone = false;
    	Robot.DrivingStraightPID.setAnglePID = Robot.driveTrain.imu.getAngleZ() / 4;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.DriveToPointPID.setSetpoint(distance - Robot.driveTrain.getEncDistance());
    	Robot.DrivingStraightPID.setSetpoint(Robot.driveTrain.getTurnAngle(Robot.DrivingStraightPID.setAnglePID, Robot.driveTrain.imu.getAngleZ() / 4));
    	currentSpeed = Robot.DriveToPointPID.DriveToPointPIDOutput;
    	angleFix = Robot.DrivingStraightPID.DrivingStraightOutput;
    	if(Robot.driveTrain.getEncDistance() <= distance){
    		Robot.driveTrain.leftDrive.set(currentSpeed + -angleFix);
    		Robot.driveTrain.leftDrive2.set(currentSpeed + -angleFix);
            Robot.driveTrain.rightDrive.set(-currentSpeed + angleFix);
            Robot.driveTrain.rightDrive2.set(-currentSpeed + angleFix);
    	} else {
    		isDone = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
		Robot.driveTrain.leftDrive.set(0);
		Robot.driveTrain.leftDrive2.set(0);
		Robot.driveTrain.rightDrive.set(0);
		Robot.driveTrain.rightDrive2.set(0);
		Robot.driveTrain.resetEncoders();
		Robot.PIDdrivingactive = false;
		Robot.driveTrain.imu.reset();
		
		
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
