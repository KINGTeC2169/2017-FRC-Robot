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
	// addSequential(new DriveBackward(double x)) - drives the robot backward x number of inches
	// addSequential(new DriveTrainTurn(double x)) - turns the robot x number of degrees 
	//												 x > 0 turns clockwise
	//												 x < 0 turns counter-clockwise
	// addSequential(new SetGearDoor(Value.kReverse)); - flip the gear slider door solenoids outward to the peg
	// addParallel(new Auto_ContinouslyUpdateSlider()) - updates slider to track the target
	
	
	
    public Auto_Master() {
    	
    	//if robot does not want to move
    	if(Robot.alliance == 0)
    		return;
    	
    	//blue side options
    	if(Robot.alliance == 1){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(60));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35));
        	    	addSequential(new SetGearDoor(Value.kForward));
        	    	addSequential(new DriveBackwards(47));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(-68));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(200));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(72));
        	    	addSequential(new DriveTrainTurn(60));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(50));
        	    	addSequential(new DriveForward(50, .25, .35));
        	    	addSequential(new SetGearDoor(Value.kForward));
        	    	addSequential(new DriveBackwards(47));
    			}
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(50));
    		    	addSequential(new DriveForward(32, .3, .4));
    		    	
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	
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
    		    	addSequential(new DriveForward(32, .3, .4));
    		    	
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	
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
    		    	addSequential(new DriveForward(30));
    		    	addSequential(new DriveForward(55, .2, .3));
    		    	addSequential(new SetGearDoor(Value.kForward));
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
    		    	addSequential(new DriveForward(30));
    		    	addSequential(new DriveForward(55, .2, .3));
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	addSequential(new DriveBackwards(38));
    		   	
    			}
    		}
    		
    		
    	//RED ALLIANCE
    			
    	} else if(Robot.alliance == 2){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(71));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	addSequential(new DriveBackwards(27));
    		   	
    		    	//turn away from the airship
    		    	addSequential(new DriveTrainTurn(-79));
    		   	
    		    	//drive across line
    		    	addSequential(new DriveForward(50));
    			} else {
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(71));
    		    	
    		    	addParallel(new Auto_ContinouslyUpdateSlider());
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	addSequential(new DriveBackwards(27));
    			}
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(85));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(52));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(-90));
    		    	//forward
    		    	addSequential(new DriveForward(70));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(90));
    		    	//forward Crossfield
    		    	addSequential(new DriveForward(120));
    			} else {
    				addParallel(new Auto_ContinouslyUpdateSlider());
    				//hang gear
    		    	addSequential(new DriveForward(85));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kForward));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(52));
    			}
    			
    		} else if(Robot.position == 1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(-68));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
        	    	addSequential(new SetGearDoor(Value.kForward));
        	    	
        	    	addSequential(new DriveBackwards(17));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(76));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(50));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(-68));
        	    	
        	    	addParallel(new Auto_ContinouslyUpdateSlider());
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
    		    	addSequential(new SetGearDoor(Value.kForward));
        	    	addSequential(new DriveBackwards(17));
    			}
    		}
    	}
    	
    	addSequential(new SetGearDoor(Value.kReverse, true));

    }
}
