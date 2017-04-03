package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import org.usfirst.frc.team2169.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class IntakeRoller extends Subsystem {

	CANTalon intakeRoller = new CANTalon(RobotMap.intakeRoller);
	
	public void intake(){
		intakeRoller.set(RobotMap.rollerSpeed);
	}
	
	public void reverseIntake(){
		intakeRoller.set(-RobotMap.rollerSpeed);
	}
	
	public void stop(){
		
		intakeRoller.set(0);
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveWithJoysticks());
	}
}
