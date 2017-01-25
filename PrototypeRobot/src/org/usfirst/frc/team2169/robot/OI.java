package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.commands.DriveSolenoidFlip;
import org.usfirst.frc.team2169.robot.commands.GearManipulator2;
import org.usfirst.frc.team2169.robot.commands.GearSolenoidFlip;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public Joystick leftStick;
	public Joystick rightStick;
	public Joystick secondaryStick;
	
	public OI(){
		leftStick = new Joystick(RobotMap.J_LEFT);
		rightStick = new Joystick(RobotMap.J_RIGHT);
		secondaryStick = new Joystick(RobotMap.J_SECONDARY);
		
		JoystickButton driveSolButton = new JoystickButton(secondaryStick, 1);
		JoystickButton leftTrigger = new JoystickButton(leftStick, 1);
		JoystickButton rightTrigger = new JoystickButton(rightStick, 1);
		
		driveSolButton.whenPressed(new DriveSolenoidFlip());
		leftTrigger.whenPressed(new GearManipulator2());
		rightTrigger.whenPressed(new GearManipulator2());
	}
}
