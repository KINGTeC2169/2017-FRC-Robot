package org.usfirst.frc.team2169.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/* This is a subsystem that stores a lot of math functions that are useful 
 * for when programming a robot
 */
public class KTMath extends Subsystem {

	// This function clamps any value X between a min/max variable.
	// This is good for controller motor output and encoder position 
	// limits or Accelerometer angle limits
    public double clamp(double x, double min, double max){
    	double minTemp = min;
    	double maxTemp = max;
    	
    	// If the min is great than max, then they flip
    	if(min > max){
    		max = minTemp;
    		min = maxTemp;
    	}
    	
    	// Return the X value clamped between min and max
    	if(x < min){
    		return min;
    	} else if(x > max){
    		return max;
    	} else {
    		return x;
    	}
    }

    public void initDefaultCommand() {}
}

