package org.usfirst.frc.team2169.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	This is the DriveTrain subsystem that will control of all the
 *	basic functions from robot turning to encoder positions and
 *	accelerometer readins
 */
public class DriveTrain extends Subsystem {

	//creating an instance of the four drive motors
    public CANTalon leftDrive;
    public CANTalon leftDrive2;
    public CANTalon rightDrive;
    public CANTalon rightDrive2;
    
    //creating an instance of the IMU Accel on the 
    //Robo-RIO MXP Port
    public ADIS16448_IMU imu;
    
    //creating and instance of the compressor and the 
    //dog shifter taht shifts the gear boxes between
    //high and low gear
    public Compressor compressor;
    public DoubleSolenoid dogShift;
    
    //creating an instance of the two Encoders on
    //each chain of the drive train
    public Encoder leftEnc;
    public Encoder rightEnc;
	
	public DriveTrain(){
		//creating the left side of the drive train at these ports
		leftDrive = new CANTalon(1);
		leftDrive2 = new CANTalon(2);
		
		//creating the right side of the drive train at these ports
		rightDrive = new CANTalon(6);
		rightDrive2 = new CANTalon(7);
		
		//creating the IMU (Inertial Measurement Unit)
		//at the MXP Bus port as labeled in its class
		imu = new ADIS16448_IMU();
		
		//creating the compressor at port 0
		compressor = new Compressor(12);
		
		//creating a solenoid at these ports
		dogShift = new DoubleSolenoid(12,3,4);
		dogShift.set(Value.kForward);

		//creating the encoders at these DIO ports 
		//NEEDS A DISTANCE PER PULSE FACTOR
		leftEnc = new Encoder(0,1);
		leftEnc.setDistancePerPulse(1);
		leftEnc.reset();
		rightEnc = new Encoder(2,3);
		rightEnc.setDistancePerPulse(1);
		
		//these set the control mode of one of the motors on each side
		//of the drive train to the port of one of the drive motors
		//so it will look less messy and ensures the motors are moving
		//in together 100% of the time
		//leftDrive2.changeControlMode(TalonControlMode.Follower);
		//leftDrive2.set(6);
		//rightDrive2.changeControlMode(TalonControlMode.Follower);
		//rightDrive2.set(1);
	
	}
	
	//this controls the left and right side of the drivetrain by
	//setting each side of the DriveTrain to a specific value
	//clamped between -1 to 1
	public void tankDrive(double leftSide, double rightSide){
		//flips the values of one side because the 
		//gear box is flipped 180 degrees
		leftSide = -leftSide;
		leftDrive.set(leftSide);
		leftDrive2.set(leftSide);
		rightDrive.set(rightSide);
		rightDrive2.set(rightSide);
	}
	
	//this starts the compressor at the start of the match
	public void startCompressor(){
		compressor.start();
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
	//the drive train dog shifter for high/low
	//driving
	public void shiftDriveTrainUp(){
		dogShift.set(Value.kForward);
	}
	
	public void shiftDriveTrainDown(){
		dogShift.set(Value.kReverse);
	}
	
    //this function guarantees that the gyro will take the shortest 
    //possible route to get to a certain angle
    public double getTurnAngle(double wantAngle, double currentAngle){
    	double difference = wantAngle - currentAngle;
    	
    	if(difference > 180)
    		difference = difference - 360;
    	
    	return difference;
    }
    
    //a standard log function that outputs data about the driver train
    //to the SmarDashboard using .putInt() .putDouble() or .putData()
	@SuppressWarnings("deprecation")
	public void log(){
		SmartDashboard.putDouble("left Enc", leftEnc.getDistance());
		SmartDashboard.putDouble("right Enc", rightEnc.getDistance());
    	//.getAngleZ() is the robots Z rotation or its top down rotation
    	//relative to the field
		SmartDashboard.putDouble("Robot Angle X:", imu.getAngleX());
		SmartDashboard.putDouble("Robot Angle Y:", imu.getAngleY());
    	SmartDashboard.putDouble("Robot Angle Z:", imu.getAngleZ() / (720 / 180));
    	
    }
	
    public void initDefaultCommand() {}

}

