package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

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

    public CANTalon leftDrive;		// Creating an instance of a drive motor
    public CANTalon leftDrive2;		// Creating an instance of a drive motor
    public CANTalon rightDrive;		// Creating an instance of a drive motor
    public CANTalon rightDrive2;	// Creating an instance of a drive motor
    
    public ADIS16448_IMU imu;		// Creating an instance of the IMU Accel on the Robo-RIO MXP Port
    
    public Compressor compressor;	// Creating and instance of the compressor
    
    public DoubleSolenoid dogShift;	// Creating an instance of the dog shifter on the driver train
    
    //creating an instance of the two Encoders on each chain of the drive train
    public Encoder leftEnc;
    public Encoder rightEnc;
	
	public DriveTrain(){
		leftDrive = new CANTalon(1);	// Setting left drive to talon at port 1
		leftDrive2 = new CANTalon(2);	// Setting left drive to talon at port 2
		rightDrive = new CANTalon(6);	// Setting right drive to talon at port 6
		rightDrive2 = new CANTalon(7);	// Setting right drive to talon at port 7
		
		imu = new ADIS16448_IMU();	// Creating the IMU (Inertial Measurement Unit) at the MXP Bus port as labeled in its class
		
		compressor = new Compressor(0);	// Creating the compressor at port 0 on the PWM Module
		
		dogShift = new DoubleSolenoid(0,0,7);
		dogShift.set(Value.kForward);

		//creating the encoders at these DIO ports
		leftEnc = new Encoder(0,1,true);
		leftEnc.setDistancePerPulse((1 / 143.5) * 4.125 * Math.PI);		// (1 rev / number of ticks) * unit conversion for circumfrence
		leftEnc.reset();
		
		/* Here is an example of the an initialization
		 * and resetting of encoders. the initialization is at 0 and 1 on the DIO port.
		 * and set distance per pulse function sets ticks per distance applied.
		 */
		rightEnc = new Encoder(2,3,false);
		rightEnc.setDistancePerPulse((1 / 143.5) * 4.125 * Math.PI);	// (1 rev / number of ticks) * unit conversion  for circumfrence
		rightEnc.reset();
		

		
	
	}
	
	/* this controls the left and right side of the drivetrain by
	 * setting each side of the DriveTrain to a specific value
	 * clamped between -1 to 1	
	 */
	public void tankDrive(double leftSide, double rightSide){
		
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
	
	//this method switches the solenoid states of the drive train dog shifter for high/low driving
	public void shiftDriveTrainUp(){
		dogShift.set(Value.kForward);
	}
	
	public void shiftDriveTrainDown(){
		dogShift.set(Value.kReverse);
	}
	
    //this function guarantees that the gyro will take the shortest possible route to get to a certain angle
    public double getTurnAngle(double wantAngle, double currentAngle){
    	double difference = wantAngle - currentAngle;
    	
    	if(difference > 180)
    		difference = difference - 360;
    	
    	return difference;
    }
    
    //a standard log function that outputs data about the driver train to the SmarDashboard using .putInt() .putDouble() or .putData()
	@SuppressWarnings("deprecation")
	public void log(){
		//SmartDashboard.putDouble("left Enc", leftEnc.getDistance());
		//SmartDashboard.putDouble("right Enc", rightEnc.getDistance());
    	//SmartDashboard.putDouble("Robot Angle Z:", imu.getAngleZ() / (720 / 180));
    }
	
    public void initDefaultCommand() {}

}

