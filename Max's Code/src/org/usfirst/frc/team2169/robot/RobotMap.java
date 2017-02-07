package org.usfirst.frc.team2169.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	public static int left1 = 6;
	public static int left2 = 7;
	public static int right1 = 1;
	public static int right2 = 2;
	public static int intakeRoller = 4;
	public static int gearSlider = 3;
	public static int winch = 5;
	public static double winchPower = .75;
	public static double rollerSpeed = 1;
	public static int intakeButton = 1;
	public static int reverseIntakeButton = 2;
	public static int hangButton = 3;
	
	public static int driveForwardChannel = 1;
	public static int driveReverseChannel = 2;
	public static int groundIntakeForward = 3;
	public static int groundIntakeReverse = 4;
	public static int topIntakeForward = 5;
	public static int topIntakeReverse = 6;
	public static int gearDoorsForward = 7;
	public static int gearDoorsReverse = 8;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
