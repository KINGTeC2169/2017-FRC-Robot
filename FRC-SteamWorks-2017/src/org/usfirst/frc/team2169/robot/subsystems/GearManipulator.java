package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	This is the GearManipulator subsystem. This subsystem
 *	is responsible for the gear motor manipualtor along
 *	with cooperation between our vision control system
 *	and limit switches
 */
public class GearManipulator extends Subsystem {

	//creating an instance of the gear motor
    public CANTalon gearMotor;
    //manual speed of the gear manipulator
    public double gearMotorSpeed = 1.0;
    
    //creating an instance of a deciding boolean
    public boolean isSliderAutomatic;
    
    //creating an instance of the left and right
    //button bounds on the slider
    public DigitalInput leftButton;
    public DigitalInput rightButton;
    
    //creating an instance of the pressure plate 
    //buttons on the slider
    public DigitalInput springButton;
    
    //creating an instance of the gear solenoid
    //and the human player door solenoid
    public DoubleSolenoid playerSol;
    public DoubleSolenoid gearDoorSol;
    
    public GearManipulator(){
    	//creating the gear manipulator at this port
    	gearMotor = new CANTalon(4);
    	//this resets the position of the slider
    	//to 0. This should be at the center of 
    	//every match to ensure the slider can return to 
    	//the middle during a match
    	gearMotor.setEncPosition(0);
    	
    	isSliderAutomatic = true;
    	
    	//creating the buttons at these DIO ports 
    	leftButton = new DigitalInput(5);
    	rightButton = new DigitalInput(6);
    	
    	//creating the buttons at these DIO ports 
    	springButton = new DigitalInput(4);
    	
    	//creating two solenoids that flip pistons
    	playerSol = new DoubleSolenoid(12,3,4);
    	gearDoorSol = new DoubleSolenoid(12,0,7);
    }
    
    //this method sets the value of the gear 
    //manipulator based on the reflective tapes position
    //relative to the slider. Motor values are already
    //calculated on the Pi side
    //AUTOMATIC
    public void automaticGearManip(){
    	if(Robot.sliderVisionError > 0){
    		//Robot.gearManipulator.gearManipLeft(Robot.visionGearMotor);
    	} else if(Robot.sliderVisionError < 0){
    		//Robot.gearManipulator.gearManipRight(Robot.visionGearMotor);
    	} else {
    		Robot.gearManipulator.gearManipIdle();
    	}
    }
    
    //this method sets the value of the gear 
    //manipulator based on UI if the slider and vision
    //do not cooperate well in a match
    //MANUAL
    public void manualGearManip(){
    	//this statement tests if the gear manipulator should move at all, 
    	//otherwise the gear manipulator should not move at all
    	if (Robot.oi.secondaryStick.getRawAxis(4) > 0.7) {
    		gearManipRight(Robot.oi.secondaryStick.getRawAxis(4) * 0.5);
    	} else if (Robot.oi.secondaryStick.getRawAxis(4) < -0.7) {
    		gearManipLeft(-Robot.oi.secondaryStick.getRawAxis(4) * 0.5);
    	} else {
    		gearManipIdle();
    	}
    		
    }
    
    //when this method is called, it flips the boolean that decides
    //whether or not the slider is moving based on user input
    //or vision calculations
    public void flipSliderManipulation(){
    	isSliderAutomatic = !isSliderAutomatic;
    }
    
    //this method checks if the leftButton is hit,
    //if not, it will send the gear manipulator
    //to the left until it hits the switch
    //MANUAL
    public void gearManipLeft(double speed){
    	if(leftButton.get() == true){
    		gearMotor.set(-speed);
    	} else {
    		gearMotor.set(0);
    	}
    }
    
    //this is a quick method that stops the
    //gear manipulator if it gets too close
    //to its target
    //MANUAL
    public void gearManipIdle(){
    	gearMotor.set(0);
    }

    //this method checks if the rightButton is hit,
    //if not, it will send the gear manipulator
    //to the right until it hits the switch
    //MANUAL
    public void gearManipRight(double speed){
    	if(rightButton.get() == true){
    		gearMotor.set(speed/60);
    	} else {
    		gearMotor.set(0);
    	}
    }
    
    public void gearManipBoth(double speed){
    	//Moving Left
    	if (speed < 0 && leftButton.get() == true){
    		gearMotor.set(speed);
    	}
    	//moving right
    	else if (speed > 0 && rightButton.get() == true){
    		gearMotor.set(speed);    		
    	}
    	else{
    		gearMotor.set(0);
    	}
    }
    //flips the state of the solenoids
    //AKA flips the pneumatic pistons
    //on the human player door
    public void flipHumanPlayerSolenoids(){
    	if(playerSol.get() == Value.kOff){
    		playerSol.set(Value.kForward);
    	} else if(playerSol.get() == Value.kForward){
    		playerSol.set(Value.kReverse);
    	} else {
    		playerSol.set(Value.kForward);
    	}
    	
    	//if we want a gear and are loading one in,
    	//we make sure the gear doors are closed
    	if(playerSol.get() == Value.kReverse){
    		Robot.gearManipulator.gearDoorSol.set(Value.kReverse);
    	}
    	
    }
    
    //flips the state of the solenoids
    //AKA flips the pneumatic pistons
    //on both sides of the door
    public void flipDoorSolenoids(){
    	if(gearDoorSol.get() == Value.kOff){
    		gearDoorSol.set(Value.kForward);
    	} else if(gearDoorSol.get() == Value.kForward){
    		gearDoorSol.set(Value.kReverse);
    	} else {
    		gearDoorSol.set(Value.kForward);
    	}
    }
    
    public void closeDoor(){
    	if(Robot.oi.secondaryStick.getRawButton(5)){
    	}
    	//gearDoorSol.set(Value.kReverse);
    }
    
    //checks if any of the buttons on the slider
    //are pressed, if any are pressed, then return true
    public boolean springButtonHit(){
    	if(springButton.get() == false){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //a standard log function that outputs data about the gear mechanism
    //to the SmarDashboard using .putInt() .putNumber() or .putData()
    public void log(){
    	//SmartDashboard.putBoolean("Spring Button", springButtonHit());
    	//SmartDashboard.putBoolean("Left Slider Button", leftButton.get());
    	//SmartDashboard.putBoolean("Right Slider Button", rightButton.get());
    	//SmartDashboard.putDouble("Slider Enc Velocity", gearMotor.getEncVelocity());
    	//SmartDashboard.putDouble("Right Stick", Robot.oi.secondaryStick.getRawAxis(4));
    }

    public void initDefaultCommand() {}
}

