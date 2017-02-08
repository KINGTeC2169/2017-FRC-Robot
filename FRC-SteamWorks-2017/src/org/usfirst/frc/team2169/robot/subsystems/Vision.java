package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {

	//creating an instance of the network table class
	public NetworkTable networkTable;
	public String name = "vtable";
	
	public Vision(){
		//creates an instance of the network table at the name
		//identifier
		networkTable = NetworkTable.getTable(name);
	}
	
	//this method gets the data frmo the pi stream over the
	//network table
	@SuppressWarnings("deprecation")
	public void getVisionData(){
		try{
			Robot.visionGearMotor = networkTable.getNumber("motor");
			Robot.visionDistance = networkTable.getNumber("TargetOnLeft");
			Robot.visionAngle = networkTable.getNumber("TargetOnLeft");
		
			Robot.isVisionDataGood = true;
		}catch(Exception e){
			Robot.visionGearMotor = null;
			Robot.visionDistance = null;
			Robot.visionAngle = null;
		
			Robot.isVisionDataGood = false;
		}
	}
	
	//this is a log function that prints out all of the 
	//values in the Vision class
	public void log(){
		SmartDashboard.putNumber("Vision GearMotor", Robot.visionGearMotor);
		SmartDashboard.putNumber("Vision Distance", Robot.visionDistance);
		SmartDashboard.putNumber("Vision Angle", Robot.visionAngle);
	}

    public void initDefaultCommand() {}
}

