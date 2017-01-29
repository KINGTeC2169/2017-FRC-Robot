package org.usfirst.frc.team2169.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	This is the DriveTrain subsystem that will control of all the
 *	basic functions from robot turning to encoder positions and
 *	accelerometer readins
 */
public class DriveTrain extends Subsystem {
	
    public CANTalon leftDrive;
    public CANTalon leftDrive2;
    public CANTalon rightDrive;
    public CANTalon rightDrive2;
    
    public ADIS16448_IMU imu;
    
    public Compressor compressor;
    public DoubleSolenoid dogShift;
    
    public Encoder leftEnc;
    public Encoder rightEnc;
	
	public DriveTrain(){
		//creating the left side of the drive train at these ports
		leftDrive = new CANTalon(5);
		leftDrive2 = new CANTalon(6);
		
		//creating the right side of the drive train at these ports
		rightDrive = new CANTalon(2);
		rightDrive2 = new CANTalon(3);
		
		//creating the IMU (Inertial Measurement Unit)
		//the highly accurate gyroscope that goes on the
		//roboRIO MXP Port
		imu = new ADIS16448_IMU();
		
		//creating the compressor that is attached to 
		//the rio
		compressor = new Compressor();
		
		//creating a solenoid at these ports
		dogShift = new DoubleSolenoid(0,3,4);
		dogShift.set(Value.kForward);
		
		//this is for wired encoders on the drive train
		//rails at these ports.
		//this also sets up the encoders based on distance and
		//NEEDS A DISTANCE PER PULSE FACTOR
		leftEnc = new Encoder(0,1,true);
		leftEnc.setDistancePerPulse(1);
		rightEnc = new Encoder(2,3,false);
		rightEnc.setDistancePerPulse(1);
		
		//these set the control mode of 2/3 motors on each side
		//of the drive train to the port of one of the drive motors
		//so it will look less messy to control all six motors
		//BASICALLY SETS OUTPUT EQUALLY TO ALL DRIVE MOTORS
		leftDrive2.changeControlMode(TalonControlMode.Follower);
		leftDrive2.set(5);
		rightDrive2.changeControlMode(TalonControlMode.Follower);
		rightDrive2.set(2);
	
	}
	
	//this controls the left and right side of the drivetrain by
	//setting each side of the DriveTrain to a specific value
	public void tankDrive(double leftSide, double rightSide){
		leftDrive.set(leftSide);
		rightDrive.set(-rightSide);
	}
	
	//this is a quick method that gets the average encoder distance
	public double getEncDistance(){
		return(leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
	}
	
	//this is a quick method that gets the average encoder rate
	public double getEncRate(){
		return(leftEnc.getRate() + rightEnc.getRate()) / 2.0;
	}
	
	//this is a quick method that resets the encoders
	public void resetEncoders(){
		leftEnc.reset();
		rightEnc.reset();
	}
	
	//this method switches the solenoid states of 
	//the drive train dog shifter for fast/slow
	//driving
	public void shiftDriveTrain(){
		if(dogShift.get() == Value.kOff){
    		dogShift.set(Value.kForward);
    	} else if(dogShift.get() == Value.kForward){
    		dogShift.set(Value.kReverse);
    	} else {
    		dogShift.set(Value.kForward);
    	}
	}
	
    //this function guarantees that the gyro will take the shortest 
    //possible route to get to a certain angle
    public double getTurnAngle(double wantAngle, double currentAngle){
    	double difference = wantAngle - currentAngle;
    	
    	if(difference > 180)
    		difference = difference - 360;
    	
    	return difference;
    }
	
    public void initDefaultCommand() {}

}

