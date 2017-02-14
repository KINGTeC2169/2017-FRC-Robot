
package org.usfirst.frc.team2169.robot;


import org.usfirst.frc.team2169.robot.commands.Auto_BlueLeft;
import org.usfirst.frc.team2169.robot.commands.Auto_BlueRight;
import org.usfirst.frc.team2169.robot.commands.Auto_CentralGoal;
import org.usfirst.frc.team2169.robot.commands.Auto_DoNothing;
import org.usfirst.frc.team2169.robot.commands.Auto_RedLeft;
import org.usfirst.frc.team2169.robot.commands.Auto_RedRight;
import org.usfirst.frc.team2169.robot.commands.Auto_Tester;
import org.usfirst.frc.team2169.robot.commands.GearManip;
import org.usfirst.frc.team2169.robot.commands.Hanging;
import org.usfirst.frc.team2169.robot.commands.Intake;
import org.usfirst.frc.team2169.robot.commands.TankDrive;
import org.usfirst.frc.team2169.robot.commands.TankDriveSolenoidFlip;
import org.usfirst.frc.team2169.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2169.robot.subsystems.GearManipulator;
import org.usfirst.frc.team2169.robot.subsystems.Hanger;
import org.usfirst.frc.team2169.robot.subsystems.Intakes;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//creating an instance of the gearManipulator
	public static final GearManipulator gearManipulator = new GearManipulator();
	
	//creating an instance of the driveTrain
	public static final DriveTrain driveTrain = new DriveTrain();
	
	//creating an instance of the intakes
	public static final Intakes intakes = new Intakes();
	
	//creating an instance of the vision
	//public static final Vision vision = new Vision();
	
	//creating an instance of the hanger
	public static final Hanger hanger = new Hanger();
	
	//creating an instance of the OI
	public static OI oi;

	//vision setup variables
	public static boolean isVisionDataGood;
	public static Double visionGearMotor;
	public static Double visionDistance;
	public static Double visionAngle;
	
	//standard spring length in m to calculate 
	//the offset distance from the edge of the spring
	//to the reflective tape
	public static double springLength = .035255;
	
	//creating an instance of each command that
	//runs continuously in teleOp
	public Command tankDriveCom;
	public Command gearManipCom;
	public Command intakeCom;
	public Command hangCom;
	public Command tankDriveSol;
	public Command autonomousCommand;
	public Command driveTrainShift;
	//public Command visionCommand;
	//creating an instance of the sendable object that
	//displays the commands to the SmartDashboard in a match
	public SendableChooser<Command> chooser = new SendableChooser<>();
	

	@Override
	public void robotInit() {
		//creating an instance of the OI class
		oi = new OI();
		
		//creating an instance of the commands shown above
		tankDriveCom = new TankDrive();
		gearManipCom = new GearManip();
		intakeCom = new Intake();
		hangCom = new Hanging();
		tankDriveSol = new TankDriveSolenoidFlip();
		driveTrainShift = new TankDriveSolenoidFlip();
		//visionCommand = new VisionCommand();
		
		//adding all of the commands that go to the 
		//SmartDashbaord at the beginning of the match
		chooser.addDefault("Do Nothing", new Auto_DoNothing());
		chooser.addObject("Blue Left", new Auto_BlueLeft());
		chooser.addObject("Blue Center", new Auto_CentralGoal());
		chooser.addObject("Blue Right", new Auto_BlueRight());
		chooser.addObject("Red Left", new Auto_RedLeft());
		chooser.addObject("Red Center", new Auto_CentralGoal());
		chooser.addObject("Red Right", new Auto_RedRight());
		chooser.addObject("Test Auto", new Auto_Tester());
		SmartDashboard.putData("Auto Infoz", chooser);
		
		//robot restarting setup at startup
		Robot.driveTrain.imu.reset();
		//Robot.driveTrain.resetEncoders();
		//Robot.driveTrain.startCompressor();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//pulls the checked command on the SmartDashboard
		//that the drivers want to use for that match
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		//Robot.driveTrain.compressor.start();
		
		//this is the place to start all commands that run
		//continuously through the autonomous period
		//Robot.driveTrain.startCompressor();
		tankDriveCom.start();
		gearManipCom.start();
		driveTrainShift.start();
		tankDriveSol.start();
		//visionCommand.start();
		intakeCom.start();
		//hangCom.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//any continously updated SmartDashboard data goes here
		Robot.driveTrain.log();
		//Robot.gearManipulator.log();\
		
		Robot.oi.secondaryStick.setRumble(RumbleType.kLeftRumble, 1);
		Robot.oi.secondaryStick.setRumble(RumbleType.kRightRumble, 1);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
