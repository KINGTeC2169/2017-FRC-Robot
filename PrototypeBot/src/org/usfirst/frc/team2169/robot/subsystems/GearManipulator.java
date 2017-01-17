package org.usfirst.frc.team2169.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	This is the GearManipulator subsystem. This subsystem
 *	is responsible for the gear motor manipualtor along
 *	with cooperation between our vision control system
 *	and limit switches
 */
public class GearManipulator extends Subsystem {

    public CANTalon gearMotor;
    public double gearMotorSpeed = .2;
    
    public DigitalInput leftButton;
    public DigitalInput rightButton;
    
    public GearManipulator(){
    	//creating the gear manipulator at this port
    	gearMotor = new CANTalon(8);
    	
    	//creating the button on with the ports that 
    	//correspond to the ports on DIO section
    	//on the roboRIO
    	leftButton = new DigitalInput(0);
    	rightButton = new DigitalInput(1);
    }
    
    //this method checks if the leftButton is hit,
    //if not, it will send the gear manipulator
    //to the left until it hits the switch
    public void gearManipLeft(){
    	if(leftButton.get() == true){
    		gearMotor.set(gearMotorSpeed);
    	} else {
    		gearMotor.set(0);
    	}
    }
    
    //this is a quick method that stops the
    //gear manipulator if it gets too close
    //to its target
    public void gearMainpIdle(){
    	gearMotor.set(0);
    }

    //this method checks if the rightButton is hit,
    //if not, it will send the gear manipulator
    //to the right until it hits the switch
    public void gearManipRight(){
    	if(rightButton.get() == true){
    		gearMotor.set(-gearMotorSpeed);
    	} else {
    		gearMotor.set(0);
    	}
    }

    public void initDefaultCommand() {}
}

