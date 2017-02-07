package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import org.usfirst.frc.team2169.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class DriveTrain extends Subsystem {

	CANTalon left1 = new CANTalon(RobotMap.left1);
	CANTalon left2 = new CANTalon(RobotMap.left2);
	CANTalon right1 = new CANTalon(RobotMap.right1);
	CANTalon right2 = new CANTalon(RobotMap.right2);
	
	public void drive(double left, double right){
		left = -left;
		left1.set(left);
		left2.set(left);
		right1.set(right);
		right2.set(right);
	}
	
	public void stop(){
		left1.set(0);
		left2.set(0);
		right1.set(0);
		right2.set(0);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}
}
