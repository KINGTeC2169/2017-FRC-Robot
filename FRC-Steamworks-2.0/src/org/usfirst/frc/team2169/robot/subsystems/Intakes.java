package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	This is the Intakes Subsystem. It is responsible
 *	for the motor functions of the intakes at the
 *	front of the robot
 */
public class Intakes extends Subsystem {

	public CANTalon intakeMotor;				//Creating an instance of a intake motor
	public CANTalon topRollerMotor;				//Creating an instance of a intake motor
	public double speed = 1.0;		    		//Creating an instance of the intake speed
	
	//Creating an instance of the intake solenoid
	public DoubleSolenoid intakeSol;
	
	public Intakes(){
		
		intakeMotor = new CANTalon(8); 			//Creating the intake motor at this port
		topRollerMotor = new CANTalon(4);		//AVERY I CHANGED THIS PORT FROM 9 TO 10 FOR A TEST
		intakeSol = new DoubleSolenoid(12,2,5);
	}
	
	//sets the motor so objects can be lodged
	//into the robot
	public void intakeIn(){
		intakeMotor.set(speed);
		topRollerMotor.set(speed);
	}
	
	//an idle function that keeps the intakes
	//from constantly moving
	public void intakeIdle(){
		intakeMotor.set(0);
		topRollerMotor.set(0);
	}
	
	//sets the motor so the gear can be pushed
	//out in a rare case that they could not use a gear
	public void intakeOut(){
		intakeMotor.set(-speed);
		topRollerMotor.set(-speed);
	}
	
	//this method sets the value of the intakes based 
	//on UI during a match
    //MANUAL
	public void manualIntakes(){
		double axisTolerance = .3;
		
		if(Robot.oi.secondaryStick.getRawAxis(1) < -axisTolerance){
			intakeIn();
		} else if(Robot.oi.secondaryStick.getRawAxis(1) > axisTolerance) {
			intakeOut();
		} else {
			intakeIdle();
		}
	}
	
	public void intakeInit(){
		
	}
	
	//this shifts the intake solenoids
	//so the intake can apply pressure
	//to the gear it is trying to collect
	public void shiftIntakes(){
		if(intakeSol.get() == Value.kOff){
    		intakeSol.set(Value.kForward);
    	} else if(intakeSol.get() == Value.kForward){
    		intakeSol.set(Value.kReverse);
    	} else {
    		intakeSol.set(Value.kForward);
    	}
	}
	
    public void initDefaultCommand() {}
}

