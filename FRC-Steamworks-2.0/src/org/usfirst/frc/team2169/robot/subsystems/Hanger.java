package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	This is the Hanger subsystem. This is responsible for the 
 *	hang motor and any end manipulation done to the robot inn
 *	end game.
 */
public class Hanger extends Subsystem {

    public CANTalon hangMotor; //creating an instance of the hang motor
    public double hangSpeed = 1.0; //an instance of the motor max speed
    
    //public double modifier = Robot.oi.secondaryStick.getRawAxis(4);
    
    public DigitalInput hangButton;  //creating an instance of the buttons on the hang mechanism
    //public DigitalInput hangButton2;
    
    public Hanger(){
    	hangMotor = new CANTalon(9); //creating the hang motor at this port
    	hangButton = new DigitalInput(8); //Cerating the buttons at these DIO ports
    }
    
    //applies a full force on the rope so the robot can hang
    public void pullUp(){
    	hangMotor.set(hangSpeed);
    }
    
    //applied a full force on the rope so the robot
    //can hang
    public void pullDown(){
    	hangMotor.set(-hangSpeed);
    }
    
    //an idle function that keeps the motor 
    //from moving the entire match
    public void hangIdle(){
    	hangMotor.set(0);
    }
    
    //checks if any of the buttons on the hanger
    //are pressed, if any are pressed, then return true
    public boolean hangButtonHit(){
    	if(hangButton.get() == false ){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //this method sets the value of the hanging motors based 
  	//on UI during a match
    //MANUAL
    public void manualHanging(){
    	//if button not pressed
    	if(Robot.oi.secondaryStick.getRawButton(8)){
    		hangMotor.set(-1);
    		//Robot.oi.secondaryStick.setRumble(RumbleType.kLeftRumble, 1);
    		//Robot.oi.secondaryStick.setRumble(RumbleType.kRightRumble, 1);
    	} else {
    		hangMotor.set(0);
    		//Robot.oi.secondaryStick.setRumble(RumbleType.kLeftRumble, 0);
    		//Robot.oi.secondaryStick.setRumble(RumbleType.kRightRumble, 0);
    	}
    }
    
    @SuppressWarnings("deprecation")
	public void log(){
		SmartDashboard.putNumber("Aamps:", hangMotor.getOutputCurrent());
		
		//SmartDashboard.p("Volts:", hangMotor.getOutputVoltage());
		}
    
    //a standard log function that outputs data about the hanging mechanism
    //to the SmarDashboard using .putInt() .putDouble() or .putData()
    /*
    @SuppressWarnings("deprecation")
	public void log(){
    	SmartDashboard.putBoolean("hangButtonHit", hangButtonHit());
    }
     */
    public void initDefaultCommand() {}
}

