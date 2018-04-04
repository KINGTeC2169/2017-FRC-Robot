package com.team2169.util;

import com.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {

	//creating an instance of the network table class
	//public NetworkTable networkTable;
	//public String name = "vTable";
	
	public Vision(){
		//creates an instance of the network table at the name
		//identifier
		//networkTable = NetworkTable.getTable(name);
		//NetworkTable.setClientMode();
    	//NetworkTable.setIPAddress("10.21.69.79");
    	//networkTable = NetworkTable.getTable("Root/vTable");
	}
	
	//this method gets the data from the pi stream over the
	//network table
	@SuppressWarnings("deprecation")
	public void getVisionData(){
		try{
			//Robot.visionGearMotor = networkTable.getNumber("centX") / 64;
			//Robot.visionDistance = networkTable.getNumber("TargetOnLeft");
			//Robot.visionAngle = networkTable.getNumber("TargetOnLeft");
		
			//Robot.isVisionDataGood = true;
		}catch(Exception e){
			//Robot.visionGearMotor = null;
			//Robot.visionDistance = null;
			//Robot.visionAngle = null;
		
			//Robot.isVisionDataGood = false;
		}
	}
	
	//this is a log function that prints out all of the 
	//values in the Vision class
	public void log(){
		//SmartDashboard.putNumber("Vision GearMotor", Robot.visionGearMotor);
		//SmartDashboard.putNumber("Vision Distance", Robot.visionDistance);
		//SmartDashboard.putNumber("Vision Angle", Robot.visionAngle);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
	}
	
	public void printCentX(){
		//SmartDashboard.putNumber("CentX", networkTable.getNumber("centX"));
	}

	//public void start() {
		// TODO Auto-generated method stub
		
	//}

	
}

