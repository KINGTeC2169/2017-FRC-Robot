package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForward extends Command {
	private double rightSpeedMultiplier = 1;
	private double leftSpeedMultiplier = 1;
	private double driveForwardSpeed = .8;
	private double distance = 1500;
	private double speedMultiplierIncreaseRate = 0.03;
	private double error;
	private final double kTolerance = 0.01;
	private final double kP = .01;
	private boolean isBackwards = false;
	
	public void calculateSpeedMultipliers() {
		double expectedGyroLocation = DriveTrainTurn.want;
		double gyro = Robot.imu.getAngleZ();
		rightSpeedMultiplier = 1 - ((expectedGyroLocation - gyro) * speedMultiplierIncreaseRate);
		leftSpeedMultiplier = 1 + ((expectedGyroLocation - gyro) * speedMultiplierIncreaseRate);
		if (rightSpeedMultiplier > 1) {
			rightSpeedMultiplier = 1;
		}
		if (leftSpeedMultiplier > 1) {
			leftSpeedMultiplier = 1;
		}
	}

	public DriveForward(){
		distance = 100;
	}
	
	public DriveForward(double d) {
		//requires(Robot.driveTrain);
		distance = d;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.imu.reset();
	}

	@Override
	protected void execute() {
		calculateSpeedMultipliers();
		if (distance < 0) {
			distance = distance * -1;
			isBackwards = true;
		}
		
		error = (distance - (reverseIfTrue(isBackwards) * Robot.driveTrain.getLeftEncDistance()));
		if (driveForwardSpeed * kP * error >= driveForwardSpeed) {
			Robot.driveTrain.leftDrive1.set(-driveForwardSpeed * reverseIfTrue(isBackwards) * leftSpeedMultiplier);
		} else {
			Robot.driveTrain.leftDrive1.set(-driveForwardSpeed * kP * error * reverseIfTrue(isBackwards) * leftSpeedMultiplier);
		}
		
		error = (distance - (reverseIfTrue(isBackwards) * Robot.driveTrain.getRightEncDistance()));
		if (driveForwardSpeed * kP * error >= driveForwardSpeed) {
			Robot.driveTrain.rightDrive1.set(-driveForwardSpeed * reverseIfTrue(isBackwards) * rightSpeedMultiplier);
		} else {
			Robot.driveTrain.rightDrive1.set(-driveForwardSpeed * kP * error * reverseIfTrue(isBackwards) * rightSpeedMultiplier);
		}
		
		SmartDashboard.putDouble("Left Enc Auto Distance", Robot.driveTrain.getEncDistance());
	}

	@Override
	protected boolean isFinished() {
		return ((Math.abs(distance - Math.abs(Robot.driveTrain.getRightEncDistance())) <= kTolerance) &&
				(Math.abs(distance - Math.abs(Robot.driveTrain.getLeftEncDistance())) <= kTolerance));
	}

	@Override
	protected void end() {

	}
	
	public int reverseIfTrue(boolean in) {
		if (in) {
			return -1;
		}
		return 1;
	}
}
