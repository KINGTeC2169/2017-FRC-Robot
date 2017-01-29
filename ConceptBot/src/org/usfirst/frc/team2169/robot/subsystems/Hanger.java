package org.usfirst.frc.team2169.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;

/**
 *	This is the Hanger subsystem. This is responsible for the 
 *	hang motor and any end manipulation done to the robot inn
 *	end game.
 */
public class Hanger extends Subsystem {

    public CANTalon hangMotor;
    
    public Hanger(){
    	//creating the hang motor at this port
    	hangMotor = new CANTalon(9);
    }
    
    //applied a full force on the rope so the robot
    //can hang
    public void pullUp(){
    	hangMotor.set(1);
    }
    
    //an idle function that keeps the motor 
    //from moving the entire match
    public void hangIdle(){
    	hangMotor.set(0);
    }

    public void initDefaultCommand() {}
}

