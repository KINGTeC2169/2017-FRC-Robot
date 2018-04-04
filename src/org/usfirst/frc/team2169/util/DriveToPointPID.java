package org.usfirst.frc.team2169.util;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveToPointPID extends PIDSubsystem {
	double pot = Robot.driveTrain.getEncDistance();
	public double DriveToPointPIDOutput;
	// Initialize your subsystem here
    public DriveToPointPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super("DriveToPointPID", 0.03, 0.00, 0.00);
    	setAbsoluteTolerance(0.5);
    	getPIDController().setContinuous(false);
    	setOutputRange(-0.75,0.75);
    	LiveWindow.addActuator("PID", "DriveToPointPID", getPIDController());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return pot;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	DriveToPointPIDOutput = output;
    }
}
