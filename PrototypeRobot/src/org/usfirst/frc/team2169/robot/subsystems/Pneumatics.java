package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotMap;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {

	public Compressor compressor;
	
	public DoubleSolenoid driveSol;
	public DoubleSolenoid gearSol;
	
	public Pneumatics(){
		//compressor = new Compressor();
		
		//driveSol = new DoubleSolenoid(RobotMap.S_DRIVE_Left, RobotMap.S_DRIVE_RIGHT);
		//gearSol = new DoubleSolenoid(RobotMap.S_GEAR_Left, RobotMap.S_GEAR_RIGHT);
		
	}
	
	public void startCompressor(){
		compressor.start();
	}
	
    public void initDefaultCommand() {
    }
}

