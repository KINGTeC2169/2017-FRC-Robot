package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	This is the Intakes Subsystem. It is responsible
 *	for the motor functions of the intakes at the
 *	front of the robot
 */
public class Intakes extends Subsystem {

	//creating an instance of the intake motor
	public CANTalon intakeMotor;
	
	//setting the intake motor speed
	public double speed = 1.0;
	
	//creating an instance of the intake solenoid
	public DoubleSolenoid intakeSol;
	
	public Intakes(){
		//creating the intake motor at this port
		/*intakeMotor = new CANTalon(5);
		
		//creats the solenoid module at these ports
		intakeSol = new DoubleSolenoid(0,6,7);*/
	}
	
	//sets the motor so objects can be lodged
	//into the robot
	public void intakeIn(){
		intakeMotor.set(speed);
	}
	
	//an idle function that keeps the intakes
	//from constantly moving
	public void intakeIdle(){
		intakeMotor.set(0);
	}
	
	//sets the motor so the gear can be pushed
	//out in a rare case that they could not use a gear
	public void intakeOut(){
		intakeMotor.set(-speed);
	}
	
	//this method sets the value of the intakes based 
	//on UI during a match
    //MANUAL
	public void manualIntakes(){
		if(Robot.oi.secondaryStick.getRawButton(3) || Robot.oi.secondaryStick.getRawButton(4)){
    		if(Robot.oi.secondaryStick.getRawButton(3)){
    			Robot.intakes.intakeIn();
    		} 
    		else {
    			Robot.intakes.intakeOut();
    		}
    	} 
		else {
    		Robot.intakes.intakeIdle();
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

