package org.usfirst.frc.team2169.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2169.robot.Robot;

/**
 * This command drives the robot over a given distance with simple proportional
 * control This command will drive a given distance limiting to a maximum speed.
 */
public class DriveForward extends Command {
	private double driveForwardSpeed;
	private double distance;
	private double error;
	private final double kTolerance = 0.1;
	private final double kP = -1.0 / 5.0;

	public DriveForward() {
		this(10, 0.5);
	}

	public DriveForward(double dist) {
		this(dist, 0.5);
	}

	public DriveForward(double dist, double maxSpeed) {
		requires(Robot.driveTrain);
		distance = dist;
		driveForwardSpeed = maxSpeed;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
	}

	@Override
	protected void execute() {
		error = (distance - Robot.driveTrain.getEncDistance());
		if (driveForwardSpeed * kP * error >= driveForwardSpeed) {
			Robot.driveTrain.tankDrive(-driveForwardSpeed, driveForwardSpeed);
		} else {
			Robot.driveTrain.tankDrive(-driveForwardSpeed * kP * error, driveForwardSpeed * kP * error);
		}
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(error) <= kTolerance) || isTimedOut();
	}

	@Override
	protected void end() {
		Robot.driveTrain.tankDrive(0, 0);
	}
}