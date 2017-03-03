
package org.usfirst.frc.team2169.robot;


import org.usfirst.frc.team2169.robot.commands.Auto_CrossLine;
import org.usfirst.frc.team2169.robot.commands.Auto_Master;
import org.usfirst.frc.team2169.robot.commands.Auto_PickAlliance;
import org.usfirst.frc.team2169.robot.commands.Auto_PickPosition;
import org.usfirst.frc.team2169.robot.commands.GearManip;
import org.usfirst.frc.team2169.robot.commands.Hanging;
import org.usfirst.frc.team2169.robot.commands.Intake;
import org.usfirst.frc.team2169.robot.commands.TankDrive;
import org.usfirst.frc.team2169.robot.commands.TankDriveSolenoidFlip;
import org.usfirst.frc.team2169.robot.subsystems.ADIS16448_IMU;
import org.usfirst.frc.team2169.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2169.robot.subsystems.GearManipulator;
import org.usfirst.frc.team2169.robot.subsystems.Hanger;
import org.usfirst.frc.team2169.robot.subsystems.Intakes;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
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

	//defining the imu so log values can be pushed later on
	public ADIS16448_IMU imu;
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
	public NetworkTable table;
	public static double sliderVisionError;
	
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
	public Command autonomousCommand1;
	public Command autonomousCommand2;
	public Command autonomousCommand3;
	public Command driveTrainShift;
	public static boolean centralize;
	//public Command visionCommand;
	//creating an instance of the sendable object that
	//displays the commands to the SmartDashboard in a match
	public SendableChooser<Command> chooser = new SendableChooser<>();
	public SendableChooser<Command> allianceChooser;// = new SendableChooser<>();
	public SendableChooser<Command> positionChooser;// = new SendableChooser<>();
	public SendableChooser<Command> lineCrossChooser;//= new SendableChooser<>();
	
	public static boolean crossLine;
	public static int alliance;
	public static int position;
	
	public static boolean savedCrossLine;
	public static int savedAlliance;
	public static int savedPosition;
	

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
		
		//Alliance Chooser
		allianceChooser = new SendableChooser<>();
		allianceChooser.addObject("None", new Auto_PickAlliance(0));
		allianceChooser.addObject("Blue", new Auto_PickAlliance(1));
		allianceChooser.addObject("Red", new Auto_PickAlliance(2));
		SmartDashboard.putData("Alliance Selection", allianceChooser);
		
		//Position Chooser
		positionChooser = new SendableChooser<>();
		positionChooser.addObject("Left", new Auto_PickPosition(-1));
		positionChooser.addObject("Center", new Auto_PickPosition(0));
		positionChooser.addObject("Right", new Auto_PickPosition(1));
		SmartDashboard.putData("Position Selection", positionChooser);
		
		//CrossLine Chooser
		lineCrossChooser = new SendableChooser<>();
		lineCrossChooser.addObject("Yes", new Auto_CrossLine(true));
		lineCrossChooser.addObject("No", new Auto_CrossLine(false));
		SmartDashboard.putData("Cross Line Selection", lineCrossChooser);

		
		
		table = NetworkTable.getTable("SmartDashboard");
		
		
		
		//robot restarting setup at startup
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
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
		
		//sets the drive train to low gear for all autonomous options
		Robot.driveTrain.dogShift.set(Value.kReverse);
		
		//pulls the checked command on the SmartDashboard
		//that the drivers want to use for that 
		autonomousCommand = new Auto_Master();
		

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		sliderVisionError = table.getNumber("centX");
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
		hangCom.start();
		centralize = false;
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
		Robot.gearManipulator.log();
		//SmartDashboard.putDouble("Left Stick", Robot.oi.secondaryStick.getRawAxis(1));
		//SmartDashboard.putDouble("Robot Acceleration X:", imu.getAccelX());
		//SmartDashboard.putDouble("Amps", Robot.hanger.hangMotor.getOutputCurrent());
		//Robot.gearManipulator.log();\
		SmartDashboard.putBoolean("Hang Buttons:", Robot.hanger.hangButtonHit());
		SmartDashboard.putDouble("gear enc", Robot.gearManipulator.gearMotor.getEncPosition());
		
		sliderVisionError = table.getNumber("centX", -1);
		SmartDashboard.putDouble("bb", sliderVisionError);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
