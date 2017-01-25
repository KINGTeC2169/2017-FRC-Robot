package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;
import org.usfirst.frc.team2169.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

    private RobotDrive robotDrive;
    
    public CANTalon leftDrive1;
    public CANTalon leftDrive2;
    public CANTalon leftDrive3;
    public CANTalon rightDrive1;
    public CANTalon rightDrive2;
    public CANTalon rightDrive3;
    
    public CANTalon gearManip;
    
    public Compressor comp;

    public DigitalInput leftButton;
    public DigitalInput rightButton;
    
    public Encoder leftEnc;
    public Encoder rightEnc;
    
    public PIDController pid;
    
    public DriveTrain(){
    	
    	comp = new Compressor();
    	
    	leftDrive1 = new CANTalon(RobotMap.M_LEFT_DRIVE1);
    	leftDrive2 = new CANTalon(RobotMap.M_LEFT_DRIVE2);
    	leftDrive3 = new CANTalon(RobotMap.M_LEFT_DRIVE3);
    	rightDrive1 = new CANTalon(RobotMap.M_RIGHT_DRIVE1);
    	rightDrive2 = new CANTalon(RobotMap.M_RIGHT_DRIVE2);
    	rightDrive3 = new CANTalon(RobotMap.M_RIGHT_DRIVE3);
    	
    	gearManip = new CANTalon(8);
    	
    	//robotDrive = new RobotDrive(leftDrive1, leftDrive2, rightDrive1, rightDrive2);
    	
    	leftEnc = new Encoder(0,1,true);
    	rightEnc = new Encoder(2,3,false);
    	//leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
    	//rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);
    	
    	
    	
    	/*
    	 * NEED to do distance per pulse calculations in order to use 
    	 * enc.getDistance() method accurately
    	 */
    	leftEnc.setDistancePerPulse(1);
    	rightEnc.setDistancePerPulse(1);
    	leftEnc.reset();
    	rightEnc.reset();
    	
    	//pid = new PIDController(0, 0.1, 0, leftEnc, leftDrive1);
    	
    	leftDrive2.changeControlMode(TalonControlMode.Follower);
    	//leftDrive3.changeControlMode(TalonControlMode.Follower);
    	//rightDrive2.changeControlMode(TalonControlMode.Follower);
    	rightDrive3.changeControlMode(TalonControlMode.Follower);
    	leftDrive2.set(RobotMap.M_LEFT_DRIVE1);
    	rightDrive3.set(RobotMap.M_RIGHT_DRIVE2);
    	
    	rightDrive2.setInverted(true);
    	
    }
    
    public void tankDrive(double leftY, double rightY){
    	robotDrive.tankDrive(leftY, leftY);
    }
    
    public void tankDrive(){
    	leftDrive1.set(Robot.oi.leftStick.getY());
    	rightDrive2.set(Robot.oi.rightStick.getY());
    }
    
    public void arcadeDrive(Joystick joy){
    	robotDrive.arcadeDrive(joy);
    }
    
    public void resetEncoders(){
    	leftEnc.reset();
    	rightEnc.reset();
    	
    }
    
    public void gearManipLeft(){
    	if(leftButton.get() == true){
    		gearManip.set(.2);
    	} else {
    		gearManip.set(0);
    	}
    }
    
    public void gearManipRight(){
    	if(rightButton.get() == true){
    		gearManip.set(-.2);
    	} else {
    		gearManip.set(0);
    	}
    }
    
    public double getEncDistance(){
    	return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
    	
    }
    public double getLeftEncDistance() {
    	return (leftEnc.getDistance());
    }
    public double getRightEncDistance() {
    	return (rightEnc.getDistance());
    }
    
    @SuppressWarnings("deprecation")
	public void log(){
    	//SmartDashboard.putDouble("Encoder Left Distance", leftEnc.getDistance());
    	//SmartDashboard.putDouble("Encoder Right Distance", rightEnc.getDistance());
    	//SmartDashboard.putBoolean("Left Button", leftButton.get());
    	//SmartDashboard.putBoolean("Right Button", rightButton.get());
    }

    public void initDefaultCommand() {
    }
}

