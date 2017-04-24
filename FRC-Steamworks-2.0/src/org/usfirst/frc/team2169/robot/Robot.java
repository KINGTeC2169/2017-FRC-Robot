
package org.usfirst.frc.team2169.robot;


import org.usfirst.frc.team2169.robot.commands.Auto_Master;
import org.usfirst.frc.team2169.robot.commands.CentralizeGearSlider;
import org.usfirst.frc.team2169.robot.commands.GearManip;
import org.usfirst.frc.team2169.robot.commands.Hanging;
import org.usfirst.frc.team2169.robot.commands.Intake;
import org.usfirst.frc.team2169.robot.commands.SetAlliance;
import org.usfirst.frc.team2169.robot.commands.SetPosition;
import org.usfirst.frc.team2169.robot.commands.TankDrive;
import org.usfirst.frc.team2169.robot.commands.TankDriveSolenoidFlip;
import org.usfirst.frc.team2169.robot.subsystems.ADIS16448_IMU;
import org.usfirst.frc.team2169.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2169.robot.subsystems.GearManipulator;
import org.usfirst.frc.team2169.robot.subsystems.Hanger;
import org.usfirst.frc.team2169.robot.subsystems.SliderPID;
import org.usfirst.frc.team2169.robot.subsystems.Intakes;
import org.usfirst.frc.team2169.robot.subsystems.KTMath;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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

	//creating an instance of the IMU accelerometers
	public ADIS16448_IMU imu;
	
	//creating an instance of the gearManipulator
	public static final GearManipulator gearManipulator = new GearManipulator();
	
	//creating an instance of the driveTrain
	public static final DriveTrain driveTrain = new DriveTrain();
	
	//creating an instance of the intakes
	public static final Intakes intakes = new Intakes();
	
	//creating an instance of the vision
	public static final KTMath ktMath = new KTMath();
	
	//creating an instance of the hanger
	public static final Hanger hanger = new Hanger();
	
	//creating an instance of the SliderPID
	public static final SliderPID SliderPID = new SliderPID();
	
	//creating an instance of the OI
	public static OI oi;
	
	//creating an instance of each command that runs continuously in teleOp
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
	public Command centralizeSlider;
	
	//vision setup variables
	public static NetworkTable table;
	
	public static double sliderVisionError;
	public static boolean centralize;
	
	public SendableChooser<Integer> allianceChooser;
	public SendableChooser<Integer> positionChooser;
	
	public static boolean ramming;
	public static int alliance;
	public static int position;

	public static boolean PIDvisionactive;
	public static boolean isSpringButtonPressed;
	public static boolean sliderCentralizing;
	public static boolean sliderAutomatic;
	
	public static boolean autoSliderCentralizing;
	
	public static boolean autoFailed;
	public static boolean autoRecovered;
	

	@Override
	public void robotInit() {
		
		//creating an instance of the OI class
		oi = new OI();
		
		sliderAutomatic = false;
		isSpringButtonPressed = false;
		sliderCentralizing = false;
		autoFailed = false;
		autoRecovered = false;
		autoSliderCentralizing = false;
		
		//creating an instance of the commands shown above
		tankDriveCom = new TankDrive();
		gearManipCom = new GearManip();
		intakeCom = new Intake();
		hangCom = new Hanging();
		tankDriveSol = new TankDriveSolenoidFlip();
		driveTrainShift = new TankDriveSolenoidFlip();
		centralizeSlider = new CentralizeGearSlider();
		
		try{
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("cam1", 1);
			camera.setResolution(160, 120);
			
			UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture("cam0", 0);
			camera2.setResolution(160, 120);
		} catch(Exception e){
			SmartDashboard.putString("CAMERA ERROR", e.getMessage());
		}

		table = NetworkTable.getTable("SmartDashboard");
		
		allianceChooser = new SendableChooser<Integer>();
		positionChooser = new SendableChooser<Integer>();
		
		allianceChooser.addDefault("Nothing", 0);
		allianceChooser.addObject("Self Test", -1);
		allianceChooser.addObject("Drive Forward", 3);
		allianceChooser.addObject("Blue Alliance", 1);
		allianceChooser.addObject("Red Alliance", 2);
		allianceChooser.addObject("Two Gear", 4);
		allianceChooser.addObject("Three Gear", 5);
		
		positionChooser.addDefault("Left", -1);
		positionChooser.addObject("Center", 0);
		positionChooser.addObject("Right", 1);
		
		SmartDashboard.putData("Alliance Chooser", allianceChooser);
		SmartDashboard.putData("Position Chooser", positionChooser);
		
		if(allianceChooser.getSelected() != null)
			Robot.alliance = allianceChooser.getSelected().intValue();
		
		if(positionChooser.getSelected() != null)
			Robot.position = positionChooser.getSelected().intValue();
		
		//robot restarting setup at startup
		Robot.driveTrain.imu.reset();
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.startCompressor();
		
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
		Robot.driveTrain.compressor.start();
		
		//sets the drive train to low gear for all autonomous options
		Robot.driveTrain.dogShift.set(Value.kReverse);
		Robot.gearManipulator.gearDoorSol.set(Value.kForward);
		Robot.intakes.intakeSol.set(Value.kReverse);
		
		Robot.gearManipulator.gearMotor.setEncPosition(0);
		
		if(allianceChooser.getSelected() != null)
			Robot.alliance = allianceChooser.getSelected().intValue();
		
		if(positionChooser.getSelected() != null)
			Robot.position = positionChooser.getSelected().intValue();
		
		//pulls the checked command on the SmartDashboard that the drivers want to use for that 
		autonomousCommand = new Auto_Master(Robot.alliance, Robot.position);
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		Robot.isSpringButtonPressed = Robot.gearManipulator.springButtonHit();
		
		//SmartDashboard.putBoolean("auto pressure plate", isSpringButtonPressed);
		//SmartDashboard.putDouble("Auto Enc Pos", Robot.driveTrain.getEncDistance());
		//SmartDashboard.putBoolean("Auto autoCentralizing", Robot.autoSliderCentralizing);
		
		sliderVisionError = table.getNumber("centx", 0);
		
		/*if(autoFailed && autoRecovered == false){
			autonomousCommand.cancel();
			autonomousCommand = new TimedDriveForward(2.5);
			autonomousCommand.start();
			
			autoRecovered = true;
		}*/
	}

	@Override
	public void teleopInit() {
		/* This makes sure that the autonomous stops running when
		 * teleop starts running. If you want the autonomous to
		 * continue until interrupted by another command, remove
		 * this line or comment it out. 
		 */
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		//this is the place to start all commands that run
		//continuously through the autonomous period
		tankDriveCom.start();
		gearManipCom.start();
		driveTrainShift.start();
		tankDriveSol.start();
		intakeCom.start();
		hangCom.start();
		
		//drop the intakes
		Robot.intakes.intakeSol.set(Value.kReverse);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		Robot.SliderPID.setSetpoint(sliderVisionError);
		sliderVisionError = table.getNumber("centx", 0);
		//SmartDashboard.putNumber("centx2", sliderVisionError);
		
		if(Robot.oi.secondaryStick.getRawButton(6)){
			sliderAutomatic = true;
		} else if(Robot.oi.secondaryStick.getRawButton(6) == false){
			sliderAutomatic = false;
		}
		
		if(Robot.oi.secondaryStick.getRawButton(1)){
			centralizeSlider.start();
		} else if(Robot.oi.secondaryStick.getRawButton(1) == false) {
			centralizeSlider.cancel();
		}
		
		if(Robot.oi.secondaryStick.getRawButton(4)){
			Robot.intakes.speed = .5;
		} else if(Robot.oi.secondaryStick.getRawButton(4) == false){
			Robot.intakes.speed = 1;
		}
		
		if(Robot.oi.secondaryStick.getRawButton(6)){
			Robot.PIDvisionactive = true;
			Robot.gearManipulator.gearManipBoth(-Robot.SliderPID.PIDvisionoutput);
			Robot.SliderPID.enable();
		} else if(Robot.oi.secondaryStick.getRawButton(6) == false){
			Robot.PIDvisionactive = false;
			Robot.SliderPID.disable();
		}
		
		Debug();
		DriverOutputs();
		//Robot.driveTrain.updatePosition();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void DriverOutputs(){
		SmartDashboard.putBoolean("Gear Door", Robot.gearManipulator.gearDoorSol.get() == Value.kReverse);
		SmartDashboard.putBoolean("Human Player", Robot.gearManipulator.playerSol.get() == Value.kReverse);
	}
	
	public void Debug(){
		//SmartDashboard.putNumber("gear enc", Robot.gearManipulator.gearMotor.getEncPosition());
		SmartDashboard.putNumber("Right Enc", Robot.driveTrain.rightEnc.getDistance());
		SmartDashboard.putNumber("Left Enc", Robot.driveTrain.leftEnc.getDistance());
		//SmartDashboard.putNumber("Robot Angle", (Robot.driveTrain.imu.getAngleZ() / 4) % 360);
		//SmartDashboard.putNumber("CentX Graph", Robot.sliderVisionError);
		//SmartDashboard.putBoolean("Pressure Plate", Robot.gearManipulator.springButtonHit());
		
		
		//visionPidcrap
		//SmartDashboard.putDouble("PID output", Robot.SliderPID.PIDvisionoutput);
		//SmartDashboard.putBoolean("PID Vision Active", Robot.PIDvisionactive);
		
	}
}
