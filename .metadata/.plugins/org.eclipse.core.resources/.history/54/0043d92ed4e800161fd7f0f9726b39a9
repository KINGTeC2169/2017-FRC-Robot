
package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.commands.Auto_BlueLeft;
import org.usfirst.frc.team2169.robot.commands.Auto_BlueRight;
import org.usfirst.frc.team2169.robot.commands.Auto_CentralGoal;
import org.usfirst.frc.team2169.robot.commands.Auto_DoNothing;
import org.usfirst.frc.team2169.robot.commands.Auto_RedLeft;
import org.usfirst.frc.team2169.robot.commands.Auto_RedRight;
import org.usfirst.frc.team2169.robot.commands.GearManip;
import org.usfirst.frc.team2169.robot.commands.Hanging;
import org.usfirst.frc.team2169.robot.commands.Intake;
import org.usfirst.frc.team2169.robot.commands.TankDrive;
import org.usfirst.frc.team2169.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2169.robot.subsystems.GearManipulator;
import org.usfirst.frc.team2169.robot.subsystems.Hanger;
import org.usfirst.frc.team2169.robot.subsystems.Intakes;

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

	public static final GearManipulator gearManipulator = new GearManipulator();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final Intakes intakes = new Intakes();
	public static final Hanger hanger = new Hanger();
	public static OI oi;

	public static boolean isVisionDataGood;
	public static Boolean visionTargetOnLeft;
	public static Double visionDistance;
	public static Double visionAngle;
	
	public static double springLength = 35.255;
	
	public Command tankDriveCom;
	public Command gearManipCom;
	public Command intakeCom;
	public Command hangCom;
	public Command autonomousCommand;
	
	public SendableChooser<Command> chooser = new SendableChooser<>();
	

	@Override
	public void robotInit() {
		oi = new OI();
		
		tankDriveCom = new TankDrive();
		gearManipCom = new GearManip();
		intakeCom = new Intake();
		hangCom = new Hanging();
		
		chooser.addDefault("Do Nothing", new Auto_DoNothing());
		chooser.addObject("Blue Left", new Auto_BlueLeft());
		chooser.addObject("Blue Center", new Auto_CentralGoal());
		chooser.addObject("Blue Right", new Auto_BlueRight());
		chooser.addObject("Red Left", new Auto_RedLeft());
		chooser.addObject("Red Center", new Auto_CentralGoal());
		chooser.addObject("Red Right", new Auto_RedRight());
		SmartDashboard.putData("Auto mode", chooser);
		
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.compressor.start();
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
		
		//this is the place to start all commands that run
		//continuously through the autonomous period
		tankDriveCom.start();
		gearManipCom.start();
		intakeCom.start();
		hangCom.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
