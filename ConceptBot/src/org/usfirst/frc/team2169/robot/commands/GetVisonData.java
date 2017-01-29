package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *	This command does one iteration and ensures our
 *	angle and position on the field is up to data
 */
public class GetVisonData extends Command {

	private NetworkTable networkTable;
	private String name = "datatable";
	
    public GetVisonData() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	networkTable = NetworkTable.getTable(name);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	networkTable = NetworkTable.getTable(name);
    }

    // Called repeatedly when this Command is scheduled to run
    @SuppressWarnings("deprecation")
	protected void execute() {
    	
    	//some networktables implementation
    	//if the networks table is working properly,
    	//then assign real values otherwise
    	//say they do not exist because of an error
    	try{
    		Robot.visionTargetOnLeft = networkTable.getBoolean("TargetOnLeft");
    		Robot.visionDistance = networkTable.getDouble("TargetOnLeft");
    		Robot.visionAngle = networkTable.getDouble("TargetOnLeft");
    		
    		Robot.isVisionDataGood = true;
    	}catch(Exception e){
    		Robot.visionTargetOnLeft = null;
    		Robot.visionDistance = null;
    		Robot.visionAngle = null;
    		
    		Robot.isVisionDataGood = false;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
