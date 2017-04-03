package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import org.usfirst.frc.team2169.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Solenoids extends Subsystem {

	DoubleSolenoid shifter = new DoubleSolenoid(RobotMap.driveForwardChannel, RobotMap.driveReverseChannel);
	DoubleSolenoid groundIntake = new DoubleSolenoid(RobotMap.groundIntakeForward, RobotMap.groundIntakeReverse);
	DoubleSolenoid topIntake = new DoubleSolenoid(RobotMap.topIntakeForward, RobotMap.topIntakeReverse);
	DoubleSolenoid gearDoors = new DoubleSolenoid(RobotMap.gearDoorsForward, RobotMap.gearDoorsReverse);
	
	public void highGearDrive(){
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public void lowGearDrive(){
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void groundIntakeOpen(){
		groundIntake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void groundIntakeRetract(){
		groundIntake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void topIntakeOpen(){
		topIntake.set(DoubleSolenoid.Value.kForward);
	}
	
	public void topIntakeRetract(){
		topIntake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void gearDoorsOpen(){
		gearDoors.set(DoubleSolenoid.Value.kForward);
	}
	
	public void gearDoorsClose(){
		gearDoors.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop(){
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveWithJoysticks());
	}
}
