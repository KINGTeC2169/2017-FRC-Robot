package org.usfirst.frc.team2169.robot.commands;

import org.usfirst.frc.team2169.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_Master extends CommandGroup {

    public Auto_Master() {
    	
    	//if robot does not want to move
    	if(Robot.alliance == 0)
    		return;
    	
    	addParallel(new Auto_ContinouslyUpdateSlider());
    	
    	//blue side options
    	if(Robot.alliance == 1){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(68));
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
        	    	addSequential(new SetGearDoor(Value.kReverse));
        	    	addSequential(new DriveBackwards(17));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(-76));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(50));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(68));
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
        	    	addSequential(new SetGearDoor(Value.kReverse));
        	    	addSequential(new DriveBackwards(17));
    			}
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				//hang gear
    		    	addSequential(new DriveForward(72));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(42));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(-90));
    		    	//forward
    		    	addSequential(new DriveForward(70));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(90));
    		    	//forward Crossfield
    		    	addSequential(new DriveForward(120));
    			} else {
    				//hang gear
    		    	addSequential(new DriveForward(72));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(42));
    			}
    			
    		} else if(Robot.position == 1){
    			if(Robot.crossLine){
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(-71));
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	addSequential(new DriveBackwards(20));
    		   	
    		    	//turn away from the airship
    		    	addSequential(new DriveTrainTurn(79));
    		   	
    		    	//drive across line
    		    	addSequential(new DriveForward(50));
    			} else {
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(-71));
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	addSequential(new DriveBackwards(20));
    			}
    		}
    		
    		
    	//RED ALLIANCE
    			
    	} else if(Robot.alliance == 2){
    		if(Robot.position == -1){
    			if(Robot.crossLine){
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(71));
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	addSequential(new DriveBackwards(20));
    		   	
    		    	//turn away from the airship
    		    	addSequential(new DriveTrainTurn(-79));
    		   	
    		    	//drive across line
    		    	addSequential(new DriveForward(50));
    			} else {
    				//drive forward and align with the target
    		    	addSequential(new DriveForward(92));
    		    	addSequential(new DriveTrainTurn(71));
    		   	
    		    	//drive towards the target, hang the gear, and back up
    		    	addSequential(new DriveForward(20));
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	addSequential(new DriveBackwards(20));
    			}
    		} else if(Robot.position == 0){
    			if(Robot.crossLine){
    				//hang gear
    		    	addSequential(new DriveForward(72));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(42));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(-90));
    		    	//forward
    		    	addSequential(new DriveForward(70));
    		    	//turn 90
    		    	addSequential(new DriveTrainTurn(90));
    		    	//forward Crossfield
    		    	addSequential(new DriveForward(120));
    			} else {
    				//hang gear
    		    	addSequential(new DriveForward(72));
    		    	//addParallel(new DriveBackwards());
    		    	addSequential(new SetGearDoor(Value.kReverse));
    		    	//Go back from the gear hanger
    		    	addSequential(new DriveBackwards(42));
    			}
    			
    		} else if(Robot.position == 1){
    			if(Robot.crossLine){
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(-68));
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
        	    	addSequential(new SetGearDoor(Value.kReverse));
        	    	addSequential(new DriveBackwards(17));
        	    	
        	    	//turn away from the airship
        	    	addSequential(new DriveTrainTurn(76));
        	    	
        	    	//drive across line
        	    	addSequential(new DriveForward(50));
    			} else {
    				//drive to offset and turn towards target
    				addSequential(new DriveForward(90));
        	    	addSequential(new DriveTrainTurn(-68));
        	    	
        	    	//drive towards the target, hang the gear, and back up
        	    	addSequential(new DriveForward(17));
        	    	addSequential(new SetGearDoor(Value.kReverse));
        	    	addSequential(new DriveBackwards(17));
    			}
    		}
    	}

    }
}
