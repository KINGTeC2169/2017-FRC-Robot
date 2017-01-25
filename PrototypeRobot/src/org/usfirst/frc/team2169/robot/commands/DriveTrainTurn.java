package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrainTurn extends Command {

	//public PIDController pidLeft = new PIDController(0.00,0.01,0,Robot.imu, Robot.driveTrain.leftDrive1);
	//public PIDController pidRight = new PIDController(0.00,0.01,0,Robot.imu, Robot.driveTrain.rightDrive2);
	
	/*public double want = 180;
	public double speed = 0.5;
	public double tolerance = 3;
	public double kP = 0.05;
	public double error;*/
	public double turnBy = 0;
	public static double want = 0;
	public double speed = 0.5;
	public double tolerance = 6;
	public double kP = 0.05;
	public double error;
	public double lastActiveSpeed = speed;
	public double timer = 0;
    public boolean timerOn = false;
    public boolean refineAngle = false;
    public double estimatedAngle = want;
	public DriveTrainTurn(double e) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		error = e;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	refineAngle = false;
    	//Robot.imu.reset();
    	want = want + turnBy;
    	//pidLeft.setSetpoint(angle);
    	//pidRight.setSetpoint(angle);
    	speed = 0.5;
    	//pidLeft.enable();
    	//pidRight.enable();
    }
    
    //this method returns the closest angle from
    //the desired angle to the current angle
    public double getAngle(double want, double at){
    	double x = want - at;
    	
    	if(x > 180)
    		x = x - 360;
    	
    	return x;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    @SuppressWarnings("deprecation")
	protected void execute() {
    	error = getAngle(want, Robot.imu.getAngleZ());
    	if (!refineAngle) {
	    	
	    	if(speed * kP * error >= speed){
	    		Robot.driveTrain.leftDrive1.set(speed);
	            Robot.driveTrain.rightDrive2.set(-speed);
	            lastActiveSpeed = speed;
	    	} else {
	    		Robot.driveTrain.leftDrive1.set(speed * kP * error);
	            Robot.driveTrain.rightDrive2.set(-speed * kP * error);
	            lastActiveSpeed = speed * kP * error;
	    	}
    	} else {
    		if (error > 0) {
    			Robot.driveTrain.leftDrive1.set(0.25);
    			Robot.driveTrain.rightDrive2.set(-0.25);
    		} else if (error < 0) {
    			Robot.driveTrain.leftDrive1.set(-0.25);
    			Robot.driveTrain.rightDrive2.set(0.25);
    			lastActiveSpeed = 911;
    		}
    	}
    	SmartDashboard.putDouble("IMU X Auto", Robot.imu.getAngleX());
    	SmartDashboard.putDouble("IMU Y Auto", Robot.imu.getAngleY());
    	SmartDashboard.putDouble("IMU Z Auto", Robot.imu.getAngleZ());
    	SmartDashboard.putDouble("IMU Auto", Robot.imu.getAngle());
    	SmartDashboard.putDouble("pid error", error);
    	SmartDashboard.putDouble("IMU Z angle", Robot.imu.getAngleZ());
    	SmartDashboard.putNumber("Time Elasped", Timer.getFPGATimestamp());
    	SmartDashboard.putNumber("motor_Power", lastActiveSpeed);
    	SmartDashboard.putBoolean("Is Refining", refineAngle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (!refineAngle) {
	    	if(Math.abs(error) < tolerance){
	    		if (!timerOn) {
	    			timerOn = true;
	    			timer = Timer.getFPGATimestamp();
	    		}
	    	} else {
	    		timerOn = false;
	    	}
	    	if (Math.abs(error) < tolerance && timerOn && timer + 1 < Timer.getFPGATimestamp()) {
	    		refineAngle = true;
	    	}
	    } else {
	    	if (Math.abs(error) < 0.1) {
	    		Robot.driveTrain.leftDrive1.set(0);
    			Robot.driveTrain.rightDrive2.set(0);
	    		return true;
	    	}
	    }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//pidLeft.disable();
    	//pidRight.disable();
    	
    //	Robot.imu.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

