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
    	
    	if(Robot.ramming == false){
    	
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
	        	    	addSequential(new DriveForward(30));
	        	    	addSequential(new DriveForward(70, .25, .35, true));
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
	        	    	addSequential(new DriveForward(30));
	        	    	addSequential(new DriveForward(70, .25, .35, true));
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
	    		    	addSequential(new DriveForward(72));
	    		    	addSequential(new DriveTrainTurn(-62));
	    		    	
	    		    	addParallel(new Auto_ContinouslyUpdateSlider());
	    		   	
	    		    	//drive towards the target, hang the gear, and back up
	    		    	addSequential(new DriveForward(20));
	    		    	addSequential(new DriveForward(60, .1, .2, true));
	    		    	addSequential(new SetGearDoor(doorRelease));
	    		    	addSequential(new DriveBackwards(38));
	    		   	
	    		    	//turn away from the airship
	    		    	addSequential(new DriveTrainTurn(62));
	    		   	
	    		    	//drive across line
	    		    	addSequential(new DriveForward(200));
	    			} else {
	    				//drive forward and align with the target
	    		    	addSequential(new DriveForward(72));
	    		    	addSequential(new DriveTrainTurn(-62));
	    		    	
	    		    	addParallel(new Auto_ContinouslyUpdateSlider());
	    		   	
	    		    	//drive towards the target, hang the gear, and back up
	    		    	addSequential(new DriveForward(20));
	    		    	addSequential(new DriveForward(60, .1, .2, true));
	    		    	addSequential(new SetGearDoor(doorRelease));
	    		    	addSequential(new DriveBackwards(38));
	    		   	
	    			}
	    		}
	    		
	    		
	    	//RED ALLIANCE
	    			
	    	} else if(Robot.alliance == 2){
	    		if(Robot.position == -1){
	    				if(Robot.crossLine){
						//drive forward and align with the target
				    	addSequential(new DriveForward(72));
				    	addSequential(new DriveTrainTurn(62));
				    	
				    	addParallel(new Auto_ContinouslyUpdateSlider());
				   	
				    	//drive towards the target, hang the gear, and back up
				    	addSequential(new DriveForward(20));
				    	addSequential(new DriveForward(60, .1, .2, true));
				    	addSequential(new SetGearDoor(doorRelease));
				    	addSequential(new DriveBackwards(38));
				   	
				    	//turn away from the airship
				    	addSequential(new DriveTrainTurn(-62));
				   	
				    	//drive across line
				    	addSequential(new DriveForward(200));
						} else {
							//drive forward and align with the target
					    	addSequential(new DriveForward(72));
					    	addSequential(new DriveTrainTurn(62));
					    	
					    	addParallel(new Auto_ContinouslyUpdateSlider());
					   	
					    	//drive towards the target, hang the gear, and back up
					    	addSequential(new DriveForward(20));
					    	addSequential(new DriveForward(60, .1, .2, true));
					    	addSequential(new SetGearDoor(doorRelease));
					    	addSequential(new DriveBackwards(38));
					   	
						}
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
	            	    	
	            	    	//update slider
	            	    	addParallel(new Auto_ContinouslyUpdateSlider());
	            	    	
	            	    	//drive towards the target, hang the gear, and back up
	            	    	addSequential(new DriveForward(30));
	            	    	addSequential(new DriveForward(70, .25, .35, true));
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
	            	    	
	            	    	//update slider
	            	    	addParallel(new Auto_ContinouslyUpdateSlider());
	            	    	
	            	    	//drive towards the target, hang the gear, and back up
	            	    	addSequential(new DriveForward(30));
	            	    	addSequential(new DriveForward(70, .25, .35, true));
	            	    	addSequential(new SetGearDoor(doorRelease));
	            	    	addSequential(new DriveBackwards(47));
	        			}
	    		
	    	//DRIVE FORWARD
	    		
	    	} else if(Robot.alliance == 3){
	    		addSequential(new DriveForward(100));
	    		
	    	//2 GEAR AUTO
	    		
	    	} else if(Robot.alliance == 4){
	    		
	    		if(Robot.position == -1){
	    			addParallel(new Auto_ContinouslyUpdateSlider());
	    			
	    			addParallel(new ReleaseIntake());
	
	        		//hang first gear
	        		addSequential(new DriveForward(60, .8, .9));
	    	    	addSequential(new DriveForward(28, .2, .3, true));
	    	    	addSequential(new SetGearDoor(doorRelease));
	    	    	
	    	    	//setup for second gear
	    	    	addSequential(centralizer);
	    	    	addSequential(new DriveBackwards(25, -.8, -.9));
	    	    	addSequential(new SetGearDoor(doorClose, true));
	    	    	addSequential(new DriveTrainTurn(46));
	    	    	
	    	    	//drive forward and pickup second gear
	    	    	addParallel(new Auto_RunIntake(5));
	    	    	addSequential(new DriveBackwards(48, -.8, -.9));
	    	    	
	    	    	//setup for second gear hang
	    	    	addSequential(new DriveForward(48, .8, .9));
	    	    	addSequential(new DriveTrainTurn(-46));
	    	    	
	    	    	//hang the second gear
	    	    	addSequential(new DriveForward(30, .3, .4, true));
	    	    	addSequential(new SetGearDoor(doorRelease));
	    	    	
	    	    	//back up from second gear hanger
	    	    	addSequential(new DriveBackwards(30));
	    		} else if(Robot.position == 1){
	    			addParallel(new Auto_ContinouslyUpdateSlider());
	    			
	    			addParallel(new ReleaseIntake());
	
	        		//hang first gear
	        		addSequential(new DriveForward(60, .8, .9));
	    	    	addSequential(new DriveForward(28, .2, .3, true));
	    	    	addSequential(new SetGearDoor(doorRelease));
	    	    	
	    	    	//setup for second gear
	    	    	addSequential(centralizer);
	    	    	addSequential(new DriveBackwards(25, -.8, -.9));
	    	    	addSequential(new SetGearDoor(doorClose, true));
	    	    	addSequential(new DriveTrainTurn(-46));
	    	    	
	    	    	//drive forward and pickup second gear
	    	    	addParallel(new Auto_RunIntake(5));
	    	    	addSequential(new DriveBackwards(48, -.8, -.9));
	    	    	
	    	    	//setup for second gear hang
	    	    	addSequential(new DriveForward(48, .8, .9));
	    	    	addSequential(new DriveTrainTurn(46));
	    	    	
	    	    	//hang the second gear
	    	    	addSequential(new DriveForward(30, .3, .4, true));
	    	    	addSequential(new SetGearDoor(doorRelease));
	    	    	
	    	    	//back up from second gear hanger
	    	    	addSequential(new DriveBackwards(30));
	    		}
		    	
		    // 3 GEAR AUTO
	    	} else if(Robot.alliance == 5){
	    		
	    		addParallel(new Auto_ContinouslyUpdateSlider());
				
				addParallel(new ReleaseIntake());
	
	    		//hang first gear
	    		addSequential(new DriveForward(60, .8, .9));
		    	addSequential(new DriveForward(28, .2, .3, true));
		    	addSequential(new SetGearDoor(doorRelease));
		    	
		    	//setup for second gear
		    	addSequential(centralizer);
		    	addSequential(new DriveBackwards(25, -.8, -.9));
		    	addSequential(new SetGearDoor(doorClose, true));
		    	addSequential(new DriveTrainTurn(46));
		    	
		    	//drive forward and pickup second gear
		    	addParallel(new Auto_RunIntake(5));
		    	addSequential(new DriveBackwards(48, -.8, -.9));
		    	
		    	//setup for second gear hang
		    	addSequential(new DriveForward(48, .8, .9));
		    	addSequential(new DriveTrainTurn(-46));
		    	
		    	//hang the second gear
		    	addSequential(new DriveForward(30, .3, .4, true));
		    	addSequential(new SetGearDoor(doorRelease));
		    	
		    	//setup for third gear
		    	addSequential(centralizer);
		    	addSequential(new DriveBackwards(25, -.8, -.9));
		    	addSequential(new SetGearDoor(doorClose, true));
		    	addSequential(new DriveTrainTurn(-46));
		    	
		    	//drive forward and pickup third gear
		    	addParallel(new Auto_RunIntake(5));
		    	addSequential(new DriveBackwards(48, -.8, -.9));
		    	
		    	//setup for third gear hang
		    	addSequential(new DriveForward(48, .8, .9));
		    	addSequential(new DriveTrainTurn(46));
		    	
		    	//hang the third gear
		    	addSequential(new DriveForward(30, .3, .4, true));
		    	addSequential(new SetGearDoor(doorRelease));
		    	
		    	//back up from third gear hanger
		    	addSequential(new DriveBackwards(30));
	    	}
	    	
	    	addSequential(new SetGearDoor(doorClose, true)); //closes the doors at the end of autonomous
	    	
	    /* RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *  RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *   RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *    RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *     RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *      RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *       RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     *        RUNNING THE SAME AUTONOMOUS OPTIONS BUT WITH A RAMMING FUNCTION
	     */
	    
    	} else {
    		//BLUE ALLIANCE
        	// Pick Alliance
        	if(Robot.alliance == 1){
        		if(Robot.position == -1){
        			if(Robot.crossLine){
        				//drive to offset and turn towards target
        				addSequential(new DriveForward(72));
            	    	addSequential(new DriveTrainTurn(60));
            	    	
            	    	//drive towards the target, hang the gear, and back up
            	    	addSequential(new DriveForward(50));
            	    	addSequential(new Auto_Ramming(20, .25, .35));
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
            	    	
            	    	//drive towards the target, hang the gear, and back up
            	    	addSequential(new DriveForward(50));
            	    	addSequential(new Auto_Ramming(20, .25, .35));
            	    	addSequential(new SetGearDoor(doorRelease));
            	    	addSequential(new DriveBackwards(47));
        			}
        			//pick Position
        		} else if(Robot.position == 0){
        			if(Robot.crossLine){
        		    	addSequential(new DriveForward(45));
        		    	addSequential(new Auto_Ramming(42, .1, .25));
        		    	
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
        		    	addSequential(new DriveForward(45));
        		    	addSequential(new Auto_Ramming(42, .1, .25));
        		    	
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	
        		    	//Go back from the gear hanger
        		    	addSequential(new DriveBackwards(35));
        			}
        			
        		} else if(Robot.position == 1){
        			if(Robot.crossLine){
        				//drive forward and align with the target
        		    	addSequential(new DriveForward(72));
        		    	addSequential(new DriveTrainTurn(-62));
        		   	
        		    	//drive towards the target, hang the gear, and back up
        		    	addSequential(new DriveForward(40));
        		    	addSequential(new Auto_Ramming(40, .1, .2));
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	addSequential(new DriveBackwards(38));
        		   	
        		    	//turn away from the airship
        		    	addSequential(new DriveTrainTurn(62));
        		   	
        		    	//drive across line
        		    	addSequential(new DriveForward(200));
        			} else {
        				//drive forward and align with the target
        		    	addSequential(new DriveForward(72));
        		    	addSequential(new DriveTrainTurn(-62));
        		   	
        		    	//drive towards the target, hang the gear, and back up
        		    	addSequential(new DriveForward(40));
        		    	addSequential(new Auto_Ramming(40, .1, .2));
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	addSequential(new DriveBackwards(38));
        		   	
        			}
        		}
        		
        		
        	//RED ALLIANCE
        			
        	} else if(Robot.alliance == 2){
        		if(Robot.position == -1){
        			if(Robot.crossLine){
        				//drive forward and align with the target
        		    	addSequential(new DriveForward(72));
        		    	addSequential(new DriveTrainTurn(62));
        		   	
        		    	//drive towards the target, hang the gear, and back up
        		    	addSequential(new DriveForward(40));
        		    	addSequential(new Auto_Ramming(40, .1, .2));
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	addSequential(new DriveBackwards(38));
        		   	
        		    	//turn away from the airship
        		    	addSequential(new DriveTrainTurn(-62));
        		   	
        		    	//drive across line
        		    	addSequential(new DriveForward(200));
        			} else {
        				//drive forward and align with the target
        		    	addSequential(new DriveForward(72));
        		    	addSequential(new DriveTrainTurn(62));
        		   	
        		    	//drive towards the target, hang the gear, and back up
        		    	addSequential(new DriveForward(40));
        		    	addSequential(new Auto_Ramming(40, .1, .2));
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	addSequential(new DriveBackwards(38));
        		   	
        			}
        		}
        		} else if(Robot.position == 0){
        			if(Robot.crossLine){
        		    	addSequential(new DriveForward(45));
        		    	addSequential(new Auto_Ramming(42, .1, .25));
        		    	
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
        		    	addSequential(new DriveForward(45));
        		    	addSequential(new Auto_Ramming(42, .1, .25));
        		    	
        		    	addSequential(new SetGearDoor(doorRelease));
        		    	
        		    	//Go back from the gear hanger
        		    	addSequential(new DriveBackwards(35));
        			}
        			
        		} else if(Robot.position == 1){
        			if(Robot.crossLine){
        				//drive to offset and turn towards target
        				addSequential(new DriveForward(72));
            	    	addSequential(new DriveTrainTurn(-60));
            	    	
            	    	//drive towards the target, hang the gear, and back up
            	    	addSequential(new DriveForward(50));
            	    	addSequential(new Auto_Ramming(20, .25, .35));
            	    	addSequential(new SetGearDoor(doorRelease));
            	    	addSequential(new DriveBackwards(47));
            	    	
            	    	//turn away from the airship
            	    	addSequential(new DriveTrainTurn(60));
            	    	
            	    	//drive across line
            	    	addSequential(new DriveForward(200));
        			} else {
        				//drive to offset and turn towards target
        				addSequential(new DriveForward(72));
            	    	addSequential(new DriveTrainTurn(60));
            	    	
            	    	//drive towards the target, hang the gear, and back up
            	    	addSequential(new DriveForward(50));
            	    	addSequential(new Auto_Ramming(20, .25, .35));
            	    	addSequential(new SetGearDoor(doorRelease));
            	    	addSequential(new DriveBackwards(47));
        			}
        		
        	//DRIVE FORWARD
        		
        	} else if(Robot.alliance == 3){
        		addSequential(new DriveForward(100));
        		
        	//2 GEAR AUTO
        		
        	} else if(Robot.alliance == 4){
        		
        		if(Robot.position == -1){
        			addParallel(new Auto_ContinouslyUpdateSlider());
        			
        			addParallel(new ReleaseIntake());

            		//hang first gear
            		addSequential(new DriveForward(60, .8, .9));
        	    	addSequential(new DriveForward(28, .2, .3, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	
        	    	//setup for second gear
        	    	addSequential(centralizer);
        	    	addSequential(new DriveBackwards(25, -.8, -.9));
        	    	addSequential(new SetGearDoor(doorClose, true));
        	    	addSequential(new DriveTrainTurn(46));
        	    	
        	    	//drive forward and pickup second gear
        	    	addParallel(new Auto_RunIntake(5));
        	    	addSequential(new DriveBackwards(48, -.8, -.9));
        	    	
        	    	//setup for second gear hang
        	    	addSequential(new DriveForward(48, .8, .9));
        	    	addSequential(new DriveTrainTurn(-46));
        	    	
        	    	//hang the second gear
        	    	addSequential(new DriveForward(30, .3, .4, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	
        	    	//back up from second gear hanger
        	    	addSequential(new DriveBackwards(30));
        		} else if(Robot.position == 1){
        			addParallel(new Auto_ContinouslyUpdateSlider());
        			
        			addParallel(new ReleaseIntake());

            		//hang first gear
            		addSequential(new DriveForward(60, .8, .9));
        	    	addSequential(new DriveForward(28, .2, .3, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	
        	    	//setup for second gear
        	    	addSequential(centralizer);
        	    	addSequential(new DriveBackwards(25, -.8, -.9));
        	    	addSequential(new SetGearDoor(doorClose, true));
        	    	addSequential(new DriveTrainTurn(-46));
        	    	
        	    	//drive forward and pickup second gear
        	    	addParallel(new Auto_RunIntake(5));
        	    	addSequential(new DriveBackwards(48, -.8, -.9));
        	    	
        	    	//setup for second gear hang
        	    	addSequential(new DriveForward(48, .8, .9));
        	    	addSequential(new DriveTrainTurn(46));
        	    	
        	    	//hang the second gear
        	    	addSequential(new DriveForward(30, .3, .4, true));
        	    	addSequential(new SetGearDoor(doorRelease));
        	    	
        	    	//back up from second gear hanger
        	    	addSequential(new DriveBackwards(30));
        		}
    	    	
    	    // 3 GEAR AUTO
        	} else if(Robot.alliance == 5){
        		
        		addParallel(new Auto_ContinouslyUpdateSlider());
    			
    			addParallel(new ReleaseIntake());

        		//hang first gear
        		addSequential(new DriveForward(60, .8, .9));
    	    	addSequential(new DriveForward(28, .2, .3, true));
    	    	addSequential(new SetGearDoor(doorRelease));
    	    	
    	    	//setup for second gear
    	    	addSequential(centralizer);
    	    	addSequential(new DriveBackwards(25, -.8, -.9));
    	    	addSequential(new SetGearDoor(doorClose, true));
    	    	addSequential(new DriveTrainTurn(46));
    	    	
    	    	//drive forward and pickup second gear
    	    	addParallel(new Auto_RunIntake(5));
    	    	addSequential(new DriveBackwards(48, -.8, -.9));
    	    	
    	    	//setup for second gear hang
    	    	addSequential(new DriveForward(48, .8, .9));
    	    	addSequential(new DriveTrainTurn(-46));
    	    	
    	    	//hang the second gear
    	    	addSequential(new DriveForward(30, .3, .4, true));
    	    	addSequential(new SetGearDoor(doorRelease));
    	    	
    	    	//setup for third gear
    	    	addSequential(centralizer);
    	    	addSequential(new DriveBackwards(25, -.8, -.9));
    	    	addSequential(new SetGearDoor(doorClose, true));
    	    	addSequential(new DriveTrainTurn(-46));
    	    	
    	    	//drive forward and pickup third gear
    	    	addParallel(new Auto_RunIntake(5));
    	    	addSequential(new DriveBackwards(48, -.8, -.9));
    	    	
    	    	//setup for third gear hang
    	    	addSequential(new DriveForward(48, .8, .9));
    	    	addSequential(new DriveTrainTurn(46));
    	    	
    	    	//hang the third gear
    	    	addSequential(new DriveForward(30, .3, .4, true));
    	    	addSequential(new SetGearDoor(doorRelease));
    	    	
    	    	//back up from third gear hanger
    	    	addSequential(new DriveBackwards(30));
        	}
        	
        	addSequential(new SetGearDoor(doorClose, true)); //closes the doors at the end of autonomous
    	}

    }
}
