package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	This is the Hanger subsystem. This is responsible for the 
 *	hang motor and any end manipulation done to the robot inn
 *	end game.
 */
public class Hanger extends Subsystem {

	//creating an instance of the hang motor
    public CANTalon hangMotor;
    //an instance of the motor max speed
    public double hangSpeed = 1.0;
    
    //creating an instance of the buttons on the hang
    //mechanism
    public DigitalInput hangButton;
    public DigitalInput hangButton2;
    
    public Hanger(){
    	//creating the hang motor at this port
    	hangMotor = new CANTalon(9);
    	
    	//cerating the buttons at these DIO ports
    	hangButton = new DigitalInput(5);
    	hangButton2 = new DigitalInput(6);
    }
    
    //applied a full force on the rope so the robot
    //can hang
    public void pullUp(){
    	if(hangButton.get() == false){
    		hangMotor.set(hangSpeed);
    	} else {
    		hangMotor.set(0);
    	}
    }
    
    //an idle function that keeps the motor 
    //from moving the entire match
    public void hangIdle(){
    	hangMotor.set(0);
    }
    
    //checks if any of the buttons on the hanger
    //are pressed, if any are pressed, then return true
    public boolean hangButtonHit(){
    	if(hangButton.get() == false || hangButton2.get() == false){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //this method sets the value of the hanging motors based 
  	//on UI during a match
    //MANUAL
    public void manualHanging(){
    	if(Robot.oi.secondaryStick.getRawButton(10)){
    		Robot.hanger.pullUp();
    	} else {
    		Robot.hanger.hangIdle();
    	}
    }
    
    //a standard log function that outputs data about the hanging mecahnism
    //to the SmarDashboard using .putInt() .putDouble() or .putData()
    public void log(){
    	SmartDashboard.putBoolean("hangButtonHit", hangButtonHit());
    }

    public void initDefaultCommand() {}
}

