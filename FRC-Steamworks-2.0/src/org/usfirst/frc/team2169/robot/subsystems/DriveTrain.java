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

    public CANTalon leftDrive;		//creating an instance of a drive motor
    public CANTalon leftDrive2;		//creating an instance of a drive motor
    public CANTalon rightDrive;		//creating an instance of a drive motor
    public CANTalon rightDrive2;	//creating an instance of a drive motor
    
    //creating an instance of the IMU Accel on the Robo-RIO MXP Port
    public ADIS16448_IMU imu;
    
    //creating and instance of the compressor and the dog shifter that shifts the gear boxes between high and low gear
    public Compressor compressor;
    public DoubleSolenoid dogShift;
    
    //creating an instance of the two Encoders on each chain of the drive train
    public Encoder leftEnc;
    public Encoder rightEnc;
	
	public DriveTrain(){
		leftDrive = new CANTalon(1);	//creating a talon at this port
		leftDrive2 = new CANTalon(2);	//creating a talon at this port
		rightDrive = new CANTalon(6);	//creating a talon at this port
		rightDrive2 = new CANTalon(7);	//creating a talon at this port
		
		imu = new ADIS16448_IMU();	//creating the IMU (Inertial Measurement Unit) at the MXP Bus port as labeled in its class
		
		//creating the compressor at port 0
		compressor = new Compressor(0);
		
		//creating a solenoid at these ports
		dogShift = new DoubleSolenoid(0,0,7);
		dogShift.set(Value.kForward);

		//creating the encoders at these DIO ports
		leftEnc = new Encoder(0,1,false);
		leftEnc.setDistancePerPulse((1 / 143.5) * 4.125 * Math.PI);		// (1 rev / number of ticks) * unit conversion for circumfrence
		leftEnc.reset();
		rightEnc = new Encoder(2,3,true);
		rightEnc.setDistancePerPulse((1 / 143.5) * 4.125 * Math.PI);	// (1 rev / number of ticks) * unit conversion  for circumfrence
		rightEnc.reset();
		

		
	
	}
	
	/* this controls the left and right side of the drivetrain by
	 * setting each side of the DriveTrain to a specific value
	 * clamped between -1 to 1	
	 */
	public void tankDrive(double leftSide, double rightSide){
		//flips the values of one side because the gear box is flipped 180 degrees
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
    
    public void setAlliance(int alliance){
    	
    	Robot.alliance = alliance;
    	
    }
    
    public void setPosition(int position){
    	
    	Robot.position = position;
    	
    }
    
    public void setCrossLine(boolean crossLine){
    	
    	Robot.crossLine = crossLine;
    	
    }
    
    public void saveAutoVariables(){
    	Robot.savedCrossLine = Robot.crossLine;
    	Robot.savedPosition = Robot.position;
    	Robot.savedAlliance = Robot.alliance;
    }
    
    //a standard log function that outputs data about the driver train to the SmarDashboard using .putInt() .putDouble() or .putData()
	@SuppressWarnings("deprecation")
	public void log(){
		SmartDashboard.putDouble("left Enc", leftEnc.getDistance());
		SmartDashboard.putDouble("right Enc", rightEnc.getDistance());
    	SmartDashboard.putDouble("Robot Angle Z:", imu.getAngleZ() / (720 / 180));
    	}
	
    public void initDefaultCommand() {}

}

