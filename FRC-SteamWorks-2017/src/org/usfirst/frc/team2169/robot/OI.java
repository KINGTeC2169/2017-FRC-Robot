package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.commands.AnotherGearFlipCom;
import org.usfirst.frc.team2169.robot.commands.HumanPlayerSolenoidFlip;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//creating an instance of the three joysticks
	//that are controlling the robot
	public Joystick leftStick;
	public Joystick rightStick;
	public Joystick secondaryStick;
	
	public OI(){
		//creating the three joysticks that are needed to
		//run all of the functions on the robot at the 
		//correct ports (generally from 0-2)
		leftStick = new Joystick(0);
		rightStick = new Joystick(1);
		secondaryStick = new Joystick(2);
		
		//creates and declares buttons for a specific joystick and button id
		JoystickButton dogShiftButtonLow = new JoystickButton(leftStick, 2);
		JoystickButton dogShiftButtonHigh = new JoystickButton(leftStick, 3);
		JoystickButton intakeShiftButton = new JoystickButton(secondaryStick, 5);
		JoystickButton humanPlayerShiftButton = new JoystickButton(secondaryStick, 5);
		JoystickButton gearDoorFlipButton = new JoystickButton(secondaryStick,7);
		JoystickButton gearManipChangeButton = new JoystickButton(secondaryStick, 6);
		JoystickButton manualCloseDoors = new JoystickButton(leftStick,1);
		
		
		//runs an instance of a command until its end() function is called
		//when the button is pressed during teleOp
		//intakeShiftButton.whenPressed(new IntakeSolenoidFlip());
		humanPlayerShiftButton.whenPressed(new HumanPlayerSolenoidFlip());
		gearDoorFlipButton.whenPressed(new AnotherGearFlipCom());
		//gearManipChangeButton.whenPressed(new GearManipulationChange());
	
		
	}
}