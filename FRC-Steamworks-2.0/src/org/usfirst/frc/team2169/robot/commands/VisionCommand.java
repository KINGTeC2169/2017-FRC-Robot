package org.usfirst.frc.team2169.robot.commands;

import java.beans.VetoableChangeListener;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionCommand extends Command {
	
	//creating an instance of the network table class
		public NetworkTable networkTable;
		public String name = "vtable";
		
		public VisionCommand(){
			//creates an instance of the network table at the name
			//identifier
			//networkTable = NetworkTable.getTable(name);
			//NetworkTable.setClientMode();
	    	//NetworkTable.setIPAddress("10.21.69.79");
	    	//networkTable = NetworkTable.getTable("vTable");
		}

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//SmartDashboard.putNumber("CentX", networkTable.getNumber("centX"));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
