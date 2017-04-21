package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_Master extends CommandGroup {

	// AUTONOMOUS KEY
	// addSequential(new DriveForward(double x)) - drives the robot forward x number of inches
	// addSequential(new DriveForward(double x, double min, double max)) - drives the robot forward x number of inches
	//																	   with min and max speeds
	// addSequential(new DriveForward(double x, double min, double max, boolean x)) - drives the robot forward x number of inches
	//																	   		      with min and max speedsand checks for pressure plate
	// addSequential(new DriveBackward(double x)) - drives the robot backward x number of inches
	// addSequential(new DriveTrainTurn(double x)) - turns the robot x number of degrees 
	//												 x > 0 turns clockwise
	//												 x < 0 turns counter-clockwise
	// addSequential(new SetGearDoor(Value.kReverse)); - flip the gear slider door solenoids outward to the peg
	// addParallel(new Auto_ContinouslyUpdateSlider()) - updates slider to track the target
	
	public Value doorRelease = Value.kReverse;
	public Value doorClose = Value.kForward;
	
	public double waitTime = .3;
	
    public Auto_Master(double alliance, double position) {

    	//NOTHING AUTO
    	
    	if(alliance == 0)
    		return;
    	
    	//SELF TEST AUTO
    	
    	if(alliance == -1){
    		addSequential(new Auto_SelfTest());
    		return;
    	}
    	
    	//DRIVE FORWARD AUTO
    	
    	if(alliance == 3){
    		addSequential(new DriveForward(200));
    		return;
    	} 
    	
    	//2 GEAR AUTO
    	
    	if(alliance == 4){
    		addParallel(new Auto_ContinouslyUpdateSlider());
    		addParallel(new ReleaseIntake());
        		
    		//hang first gear
       		addSequential(new DriveForward(77, .9, .1, true));
   	    	addSequential(new SetGearDoor(doorRelease));
   	    	addSequential(new TimedStop(waitTime));
   	    	
   	    	//centralize slider
   	    	addParallel(new CentralizeGearSlider());
   	    	
   	    	//setup for second gear
   	    	addSequential(new DriveBackwards(25, -.9, -1));
   	    	addSequential(new SetGearDoor(doorClose, true));
   	    	
   	    	if(position == -1){
   	    		addSequential(new DriveTrainTurn(47));
   	    	} else {
   	    		addSequential(new DriveTrainTurn(-47));
   	    	}
   	    	
   	    	//drive forward and pickup second gear
   	    	addParallel(new Auto_RunIntake(6));
   	    	addSequential(new DriveBackwards(48, -.9, -1));
   	    	addSequential(new DriveForward(48, .9, 1));
   	    	
   	    	if(position == -1){
   	    		addSequential(new DriveTrainTurn(-47));
   	    	} else {
   	    		addSequential(new DriveTrainTurn(47));
   	    	}
       		
   	    	addParallel(new Auto_ContinouslyUpdateSlider());
   	    	
   	    	//hang the second gear
   	    	addSequential(new DriveForward(30, .5, .6, true));
   	    	addSequential(new SetGearDoor(doorRelease));
   	    	addSequential(new TimedStop(waitTime));
   	    	
   	    	//back up from second gear hanger
   	    	addSequential(new DriveBackwards(30));
   	    	addSequential(new SetGearDoor(doorClose, true));
    		return;
    	} 
    	
		//3 GEAR AUTO
    	
    	if(alliance == 5){
    		
    		addParallel(new Auto_ContinouslyUpdateSlider());
			
			addParallel(new ReleaseIntake());

    		//hang first gear
    		addSequential(new DriveForward(60, .9, 1));
	    	addSequential(new DriveForward(28, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	addSequential(new TimedStop(waitTime));
	    	
	    	//centralize slider
	    	addParallel(new CentralizeGearSlider());
	    	
	    	//setup for second gear
	    	addSequential(new DriveBackwards(25, -.9, -1));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(48));
	    	
	    	//drive forward and pickup second gear
	    	addParallel(new Auto_RunIntake(6));
	    	addSequential(new DriveBackwards(48, -.9, -1));
	    	
	    	
	    	//setup for second gear hang
	    	addSequential(new DriveForward(48, .9, 1));
	    	addParallel(new Auto_ContinouslyUpdateSlider());
	    	addSequential(new DriveTrainTurn(-48));
	    	
	    	
	    	//hang the second gear
	    	addSequential(new DriveForward(30, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	addSequential(new TimedStop(waitTime));
	    	
	    	//centralize slider
	    	addParallel(new CentralizeGearSlider());
	    	
	    	//setup for second gear
	    	addSequential(new DriveBackwards(25, -.9, -1));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(-48));
	    	
	    	//drive forward and pickup second gear
	    	addParallel(new Auto_RunIntake(6));
	    	addSequential(new DriveBackwards(48, -.9, -1));
	    	
	    	
	    	//setup for second gear hang
	    	addSequential(new DriveForward(48, .9, 1));
	    	addParallel(new Auto_ContinouslyUpdateSlider());
	    	addSequential(new DriveTrainTurn(48));
	    	
	    	
	    	//hang the second gear
	    	addSequential(new DriveForward(35, .5, .6, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	addSequential(new TimedStop(waitTime));
	    	
	    	addSequential(new DriveBackwards(30));
    		return;
    	} 
    	
    	//1 GEAR AUTO
    		//FEEDER STATION AUTOS
    		
    		if((alliance == 1 && position == 1) || (alliance == 2 && position == -1)){
    			double angleOffset;
    			if(alliance == 1 && position == 1)
    				angleOffset = 60;
    			else
    				angleOffset = -60;
    			
    			//drive forward and align with the target
    		   	addSequential(new DriveForward(73, .8, .9));
    		   	addSequential(new DriveTrainTurn(-angleOffset));
    		   	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		   	//drive towards the target, hang the gear, and back up
    		   	addSequential(new DriveForward(30,.7,.8));
    		   	addSequential(new DriveForward(50, .3, .4, true));
    		   	addSequential(new SetGearDoor(doorRelease));
    		   	addSequential(new TimedStop(waitTime));
    		   	addSequential(new DriveBackwards(38));
    		   	
    		   	addParallel(new CentralizeGearSlider());
    		  	
    		   	//turn away from the airship
    		   	addSequential(new DriveTrainTurn(angleOffset));
    		   	addSequential(new SetGearDoor(doorClose, true));
    		  	
    		   	//drive across line
    		   	addSequential(new DriveForward(200));
    			return;
    		} 
    		
    		//BOILER AUTOS
    		
    		if((alliance == 1 && position == -1) || (alliance == 2 && position == 1)){
    			double angleOffset;
    			if(alliance == 1 && position == -1)
    				angleOffset = 60;
    			else
    				angleOffset = -60;
    			
    			//drive to offset and turn towards target
    			addSequential(new DriveForward(83, .7, .8));
        	   	addSequential(new DriveTrainTurn(angleOffset));
        	   	addParallel(new Auto_ContinouslyUpdateSlider());
        		addSequential(new TimedStop(.5));
        	   	
        	   	//drive towards the target, hang the gear, and back up
        	   	addSequential(new DriveForward(40, .35, .45, true));
        	   	addSequential(new SetGearDoor(doorRelease));
        	   	addSequential(new TimedStop(waitTime));
        	   	
        	   	addSequential(new DriveBackwards(47));
        	   	addParallel(new CentralizeGearSlider());
        	   	
        	   	
        	   	//turn away from the airship
        	   	addSequential(new DriveTrainTurn(-angleOffset));
        	   	addSequential(new SetGearDoor(doorClose, true));
        	   	
        	   	//drive across line
        	   	addSequential(new DriveForward(200));
    			return;
    		}
    		
    		//CENTER GEAR AUTOS
    		
    		if((alliance == 1 && position == 0) || (alliance == 2 && position == 0)){
    			
    			addParallel(new Auto_ContinouslyUpdateSlider());
    			//hang gear
    	    	addSequential(new DriveForward(50));
    	    	addSequential(new DriveForward(37, .3, .4, true));
    	    	
    	    	addSequential(new SetGearDoor(doorRelease));
    	    	addSequential(new TimedStop(waitTime));
    	    	
    	    	//Go back from the gear hanger
    	    	addSequential(new DriveBackwards(35));
    	    	
    	    	addParallel(new CentralizeGearSlider());
    	    	
    	    	//turn 90
    	    	addSequential(new DriveTrainTurn(90));
    	    	addSequential(new SetGearDoor(doorClose, true));
    	    	//forward
    	    	addSequential(new DriveForward(74));
    	    	//turn 90
    	    	addSequential(new DriveTrainTurn(-90));
    	    	//forward across field
    	    	addSequential(new DriveForward(240));
    	    	addSequential(new SetGearDoor(doorClose, true));
    			return;
    		}
   }
}
