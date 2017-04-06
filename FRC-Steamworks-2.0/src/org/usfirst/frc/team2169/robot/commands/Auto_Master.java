package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
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
	public Command centralizer;
	
	
    public Auto_Master() {
    	
    	centralizer = new CentralizeGearSlider();
    	
    	//if robot does not want to move
    	if(Robot.alliance == 0)
    		return;
    	
    	if(Robot.alliance == -1){
    		addSequential(new Auto_SelfTest());
    		return;
    	}
    	
    	//BLUE ALLIANCE
    	// Pick Alliance
    	if(Robot.alliance == 1){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(60));
        	    	
        	    	//update slider
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	addSequential(new DriveBackwards(47));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(-60));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(200));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(60));
        	    	
        	    	//update slider
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	addSequential(new DriveBackwards(47));
    			}
    			//pick Position
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(45));
    		    	addSequential(new DriveForward(42, .1, .25, true));
    		    	
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(35));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(90));
    		    	//forward
    		    	addSequential(new DriveForward(74));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(-90));
    		    	//forward Crossfield
    		    	addSequential(new DriveForward(240));
    			} else {
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(50));
    		    	addSequential(new DriveForward(37, .1, .25, true));
    		    	
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(35));
    			}
    			
    		} else if(Robot.position == 1){
    			if(Robot.crossLine){
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(75));
    		    	addSequential(new DriveTrainTurn(-62));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(40));
    		    	addSequential(new DriveForward(40, .1, .2, true));
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	addSequential(new DriveBackwards(38));
    		   	
    		    	//turn away from the airship
    		    	addSequential(new DriveTrainTurn(62));
    		   	
    		    	//drive across line
    		    	addSequential(new DriveForward(200));
    			} else {
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(75));
    		    	addSequential(new DriveTrainTurn(-62));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(40));
    		    	addSequential(new DriveForward(40, .1, .2, true));
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	addSequential(new DriveBackwards(38));
    		   	
    			}
    		}
    		
    		
    	//RED ALLIANCE
    			
    	} else if(Robot.alliance == 2){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(75));
    		    	addSequential(new DriveTrainTurn(62));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(40));
    		    	addSequential(new DriveForward(40, .1, .2, true));
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	addSequential(new DriveBackwards(38));
    		   	
    		    	//turn away from the airship
    		    	addSequential(new DriveTrainTurn(-62));
    		   	
    		    	//drive across line
    		    	addSequential(new DriveForward(200));
    			} else {
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(75));
    		    	addSequential(new DriveTrainTurn(62));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(40));
    		    	addSequential(new DriveForward(40, .1, .2, true));
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	addSequential(new DriveBackwards(38));
    		   	
    			}
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(45));
    		    	addSequential(new DriveForward(42, .1, .25, true));
    		    	
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(35));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(90));
    		    	//forward
    		    	addSequential(new DriveForward(74));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(-90));
    		    	//forward Crossfield
    		    	addSequential(new DriveForward(240));
    			} else {
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(45));
    		    	addSequential(new DriveForward(42, .1, .25, true));
    		    	
    		    	addSequential(new SetGearDoor(doorRelease));
    		    	
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(35));
    			}
    			
    		} else if(Robot.position == 1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(-60));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	addSequential(new DriveBackwards(47));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(60));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(200));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(-60));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	addSequential(new DriveBackwards(47));
    			}
    		}
    		
    	//DRIVE FORWARD
    		
    	} else if(Robot.alliance == 3){
    		addSequential(new DriveForward(100));
    		
    	//2 GEAR AUTO
    		
    	} else if(Robot.alliance == 4){
    		
    		/*addParallel(new Auto_ContinouslyUpdateSlider());
    		
    		//hang first gear
    		addSequential(new DriveForward(54, .7, .8));
	    	addSequential(new DriveForward(34, .2, .3, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//setup for second gear
	    	addParallel(new CentralizeGearSlider());
	    	addSequential(new DriveBackwards(75, -.8, -.9));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(-90));
	    	
	    	//drive forward and pickup second gear
	    	addParallel(new Auto_RunIntake(5));
	    	addSequential(new DriveBackwards(55, -.7, -.8));
	    	
	    	//setup for second gear hang
	    	addSequential(new DriveForward(55, .4, 5));
	    	addSequential(new DriveTrainTurn(90));
	    	
	    	//hang the second gear
	    	addSequential(new DriveForward(50, .8, .9));
	    	addSequential(new DriveForward(32, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//back up from second gear hanger
	    	addSequential(new DriveBackwards(30));*/
    		
    		addParallel(new Auto_ContinouslyUpdateSlider());

    		//hang first gear
    		addSequential(new DriveForward(54, .8, .9));
	    	addSequential(new DriveForward(34, .2, .3, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//setup for second gear
	    	addSequential(centralizer);
	    	addSequential(new DriveBackwards(25, -.8, -.9));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(-35));
	    	
	    	//drive forward and pickup second gear
	    	addParallel(new Auto_RunIntake(5));
	    	addSequential(new DriveBackwards(65, -.8, -.9));
	    	
	    	//setup for second gear hang
	    	addSequential(new DriveForward(65, .8, .9));
	    	addSequential(new DriveTrainTurn(35));
	    	
	    	//hang the second gear
	    	addSequential(new DriveForward(30, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//back up from second gear hanger
	    	addSequential(new DriveBackwards(30));
	    	
	    // 3 GEAR AUTO
    	} else if(Robot.alliance == 5){
    		
    		addParallel(new Auto_ContinouslyUpdateSlider());
    		
    		//hang first gear
    		addSequential(new DriveForward(54, .9, 1));
	    	addSequential(new DriveForward(34, .2, .3, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//setup for second gear
	    	addSequential(new DriveBackwards(75, -.8, -.9));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(-90));
	    	
	    	//drive forward and pickup second gear
	    	addParallel(new Auto_RunIntake(5));
	    	addSequential(new DriveBackwards(55, -.9, -1));
	    	
	    	//setup for second gear hang
	    	addSequential(new DriveForward(55, .5, .6));
	    	addSequential(new DriveTrainTurn(90));
	    	
	    	//hang the second gear
	    	addSequential(new DriveForward(50, .8, .9));
	    	addSequential(new DriveForward(25, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
	    	
	    	//setup for third gear
	    	addSequential(new DriveBackwards(75, -.8, -.9));
	    	addSequential(new SetGearDoor(doorClose, true));
	    	addSequential(new DriveTrainTurn(90));
	    	
	    	//drive forward and pickup third gear
	    	addParallel(new Auto_RunIntake(5));
	    	addSequential(new DriveBackwards(55, -.9, -1));
	    	
	    	//setup for third gear hang
	    	addSequential(new DriveForward(55, .6, 7));
	    	addSequential(new DriveTrainTurn(-90));
	    	
	    	//hang the  gear
	    	addSequential(new DriveForward(50, .8, .9));
	    	addSequential(new DriveForward(25, .3, .4, true));
	    	addSequential(new SetGearDoor(doorRelease));
    	}
    	
    	addSequential(new SetGearDoor(doorClose, true)); //closes the doors at the end of autonomous

    }
}
