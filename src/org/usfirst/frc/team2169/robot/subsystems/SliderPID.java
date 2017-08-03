package org.usfirst.frc.team2169.robot.subsystems;
import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class SliderPID extends PIDSubsystem {
	double pot = Robot.sliderVisionError / 30;
	public double PIDvisionoutput;
	
	
	// Initialize your subsystem here
    public SliderPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	
    	
    	
    	//PID Controller
    	super("Slider", 0.032, 0.0006, 0.001);
    	setAbsoluteTolerance(5);
    	getPIDController().setContinuous(false);
    	setOutputRange(-1,1);
    	LiveWindow.addActuator("PID", "PIDSubsystem Controller", getPIDController());
    	
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
    	PIDvisionoutput = output;
    }
}
