package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import org.usfirst.frc.team2169.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Hanger extends Subsystem {

	CANTalon winch = new CANTalon(RobotMap.winch);
	
	public void hang(){
		winch.set(RobotMap.winchPower);
	}
	
	public void stop(){
		
		winch.set(0);
	}
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveWithJoysticks());
	}
}
