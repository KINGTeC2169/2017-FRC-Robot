package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DrivingStraightPID extends PIDSubsystem {
	public double setAnglePID;
	double pot = Robot.driveTrain.getTurnAngle(setAnglePID, Robot.driveTrain.imu.getAngleZ() / 4);
	public double DrivingStraightOutput;
    // Initialize your subsystem here
    public DrivingStraightPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super("DrivingStraightPID", 0.06, 0.0, 0.0);
    	setAbsoluteTolerance(0.5);
    	getPIDController().setContinuous(false);
    	setOutputRange(-0.75,0.75);
    	LiveWindow.addActuator("PID", "DrivingStraightPID", getPIDController());
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
    	DrivingStraightOutput = output;
    }
}
