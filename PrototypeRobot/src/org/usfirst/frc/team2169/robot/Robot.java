
package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.commands.Auto_DoNothing;
import org.usfirst.frc.team2169.robot.commands.FirstAutoTest;
import org.usfirst.frc.team2169.robot.commands.GearManipulator;
import org.usfirst.frc.team2169.robot.commands.TankDrive;
import org.usfirst.frc.team2169.robot.subsystems.ADIS16448_IMU;
import org.usfirst.frc.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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

	public static final DriveTrain driveTrain = new DriveTrain();
	//public static final Pneumatics pneumatics = new Pneumatics();
	public static OI oi;
	
	public static ADIS16448_IMU imu;

	public Command tankDriveCom;
	public Command gearManipCom2;
	
	public Command autonomousCommand;
	public SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		tankDriveCom = new TankDrive();
		gearManipCom2 = new GearManipulator();
		
		imu = new ADIS16448_IMU();
		imu.reset();
		
		
		oi = new OI();
		chooser.addDefault("Do Nothing", new Auto_DoNothing());
		chooser.addObject("Test Drive Forward Aut", new FirstAutoTest());
		SmartDashboard.putData("Auto mode", chooser);
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
		
		tankDriveCom.start();
		gearManipCom2.start();
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		Robot.driveTrain.log();
		
		SmartDashboard.putDouble("IMU X angle", imu.getAngleX());
		SmartDashboard.putDouble("IMU Y angle", imu.getAngleY());
		SmartDashboard.putDouble("IMU Z angle", imu.getAngleZ());
		SmartDashboard.putDouble("IMU angle", imu.getAngle());
		SmartDashboard.putDouble("IMU X Mag", imu.getMagX());
		SmartDashboard.putDouble("IMU Y Mag", imu.getMagY());
		SmartDashboard.putDouble("IMU Z Mag", imu.getMagZ());
		SmartDashboard.putDouble("IMU W Qua", imu.getQuaternionW());
		SmartDashboard.putDouble("IMU X Qua", imu.getQuaternionX());
		SmartDashboard.putDouble("IMU Y Qua", imu.getQuaternionY());
		SmartDashboard.putDouble("IMU Z Qua", imu.getQuaternionZ());
		SmartDashboard.putDouble("IMU Rate", imu.getRate());
		SmartDashboard.putDouble("IMU X Rate", imu.getRateX());
		SmartDashboard.putDouble("IMU Y Rate", imu.getRateY());
		SmartDashboard.putDouble("IMU Z Rate", imu.getRateZ());
		SmartDashboard.putDouble("Roll", imu.getRoll());
		SmartDashboard.putDouble("Yaw", imu.getYaw());
		SmartDashboard.putDouble("Pitch", imu.getPitch());
		SmartDashboard.putDouble("LeftEncoder distance", Robot.driveTrain.leftEnc.getDistance());
		SmartDashboard.putDouble("RightEncoder distance", Robot.driveTrain.rightEnc.getDistance());
		SmartDashboard.putNumber("Time Elasped", Timer.getFPGATimestamp());
    	SmartDashboard.putNumber("motor_Power", 0.5);
    	SmartDashboard.putBoolean("Is Refining", false);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
