package org.usfirst.frc.team2169.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	This is the DriveTrain subsystem that will control of all the
 *	basic functions from robot turning to encoder positions and
 *	accelerometer readins
 */
public class DriveTrain extends Subsystem {
	
    public CANTalon leftDrive;
    public CANTalon leftDrive2;
    public CANTalon leftDrive3;
    public CANTalon rightDrive;
    public CANTalon rightDrive2;
    public CANTalon rightDrive3;
    
    public ADIS16448_IMU imu;
    
    public PIDController pidLeftSide;
    public PIDController pidRightSide;
    
    public Encoder leftEnc;
    public Encoder rightEnc;
	
	public DriveTrain(){
		//creating the left side of the drive train at these ports
		leftDrive = new CANTalon(5);
		leftDrive2 = new CANTalon(6);
		leftDrive3 = new CANTalon(7);
		
		//creating the right side of the drive train at these ports
		rightDrive = new CANTalon(2);
		rightDrive2 = new CANTalon(3);
		rightDrive3 = new CANTalon(4);
		
		//creating the IMU (Inertial MEasurement Unit)
		//the highly accurate gyroscope that goes on the
		//roboRIO MXP Port
		imu = new ADIS16448_IMU();
		
		//this is setting up the p,i,d,source, and output
		//for a pid that can be used with the driveTrain
		//VALUES NEED TO BE TWEEKED A LOT
		pidLeftSide = new PIDController(0, 0, 0, imu, leftDrive);
		pidRightSide = new PIDController(0, 0, 0, imu, rightDrive);
		
		//this is for wired encoders on the drive train
		//rails at these ports.
		//this also sets up the encoders based on distance and
		//NEEDS A DISTANCE PER PULSE FACTOR
		leftEnc = new Encoder(0,1);
		leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		leftEnc.setDistancePerPulse(1);
		rightEnc = new Encoder(2,3);
		rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEnc.setDistancePerPulse(1);
		
		//these set the control mode of 2/3 motors on each side
		//of the drive train to the port of one of the drive motors
		//so it will look less messy to control all six motors
		//BASICALLY SETS OUTPUT EQUALLY TO ALL DRIVE MOTORS
		leftDrive2.changeControlMode(TalonControlMode.Follower);
		leftDrive2.set(5);
		leftDrive3.changeControlMode(TalonControlMode.Follower);
		leftDrive3.set(5);
		rightDrive2.changeControlMode(TalonControlMode.Follower);
		rightDrive2.set(2);
		rightDrive3.changeControlMode(TalonControlMode.Follower);
		rightDrive3.set(2);
	}
	
	//this controls the left and right side of the drivetrain by
	//setting each side of the DriveTrain to a specific value
	public void tankDrive(double leftSide, double rightSide){
		leftDrive.set(leftSide);
		rightDrive.set(rightSide);
	}
	
	//this is a quick method that enables the two drive train pids
	public void enablePIDs(){
		pidLeftSide.enable();
		pidRightSide.enable();
	}
	
	//this is a quick method that disables the two drive train pids
	public void disablePIDs(){
		pidLeftSide.enable();
		pidRightSide.enable();
	}
	
	//this is a quick method that sets an angle setpoint for the two drive train pids
	public void setPIDs(double angle){
		pidLeftSide.setSetpoint(angle);
		pidRightSide.setSetpoint(angle);
	}
	
	//this is a quick method that gets the average encoder distance
	public double getEncDistance(){
		return(leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
	}
	
	//this is a quick method that resets the encoders
	public void resetEncoders(){
		leftEnc.reset();
		rightEnc.reset();
	}
	
    public void initDefaultCommand() {}
}

