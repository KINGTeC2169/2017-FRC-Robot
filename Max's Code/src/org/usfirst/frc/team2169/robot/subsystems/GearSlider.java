package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import org.usfirst.frc.team2169.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team2169.robot.commands.SliderNetwork;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class GearSlider extends Subsystem {

	CANTalon slider = new CANTalon(RobotMap.gearSlider);
	
	public void slide(double power){
		slider.set(power);
	}
	
	public void stop(){
		
		slider.set(0);
	}
	public void initDefaultCommand() {
		setDefaultCommand(new SliderNetwork());
	}
}
