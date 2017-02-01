package org.usfirst.frc.team2169.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
    public boolean gearAutomatic;
    
    public DigitalInput leftButton;
    public DigitalInput rightButton;
    
    public DigitalInput springButton1;
    public DigitalInput springButton2;
    public DigitalInput springButton3;
    public DigitalInput springButton4;
    
    public DoubleSolenoid gearSolLeft;
    public DoubleSolenoid gearSolRight;
    public DoubleSolenoid sliderSol;
    
    public GearManipulator(){
    	//creating the gear manipulator at this port
    	gearMotor = new CANTalon(8);
    	//this resets the position of the slider
    	//to 0. This should be at the center of 
    	//every match
    	gearMotor.setEncPosition(0);
    	gearAutomatic = true;
    	
    	//creating the button on with the ports that 
    	//correspond to the ports on DIO section
    	//on the roboRIO
    	leftButton = new DigitalInput(4);
    	rightButton = new DigitalInput(5);
    	
    	//creating the button on with the ports that 
    	//correspond to the ports on DIO section
    	//on the roboRIO
    	springButton1 = new DigitalInput(6);
    	springButton2 = new DigitalInput(7);
    	springButton3 = new DigitalInput(8);
    	springButton4 = new DigitalInput(9);
    	
    	//creating two solenoids that flip pistons
    	//on these modules and ports
    	gearSolLeft = new DoubleSolenoid(0,0,1);
    	gearSolRight = new DoubleSolenoid(0,2,3);
    	sliderSol = new DoubleSolenoid(0,4,5);
    	
    	//setting the initial values of the pistons
    	gearSolLeft.set(Value.kForward);
    	gearSolRight.set(Value.kForward);
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
    
    //flips the state of the solenoids
    //AKA flips the pneumatic pistons
    //on both sides of the door
    public void flipDoorSolenoids(){
    	if(gearSolLeft.get() == Value.kOff){
    		gearSolLeft.set(Value.kForward);
    	} else if(gearSolLeft.get() == Value.kForward){
    		gearSolLeft.set(Value.kReverse);
    	} else {
    		gearSolLeft.set(Value.kForward);
    	}
    	
    	if(gearSolLeft.get() == Value.kOff){
    		gearSolLeft.set(Value.kForward);
    	} else if(gearSolLeft.get() == Value.kForward){
    		gearSolLeft.set(Value.kReverse);
    	} else {
    		gearSolLeft.set(Value.kForward);
    	}
    }
    
  //flips the state of the solenoids
    //AKA flips the pneumatic pistons
    //on both sides of the door
    public void flipSlideSolenoids(){
    	if(sliderSol.get() == Value.kOff){
    		sliderSol.set(Value.kForward);
    	} else if(sliderSol.get() == Value.kForward){
    		sliderSol.set(Value.kReverse);
    	} else {
    		sliderSol.set(Value.kForward);
    	}
    }
    
    //checks if any of the buttons inside of the 
    //manipulkator are checked, if any are pressed,
    //then return true
    public boolean springButtonHit(){
    	if(springButton1.get() == false || springButton2.get() == false || springButton3.get() == false || springButton4.get() == false){
    		return true;
    	} else {
    		return false;
    	}
    }

    public void initDefaultCommand() {}
}

