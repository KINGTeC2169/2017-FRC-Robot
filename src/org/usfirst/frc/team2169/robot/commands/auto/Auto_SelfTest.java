package org.usfirst.frc.team2169.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_SelfTest extends CommandGroup {

    public Auto_SelfTest() {
    	//test vision
        addParallel(new Auto_ContinouslyUpdateSlider());
        //test intakes for x seconds
        addParallel(new Auto_RunIntake(3));
        //test encoders
        addSequential(new DriveForward(50));
        //test hanger for x seconds
        addParallel(new Auto_RunHanger(6));
        //test encoders
        addSequential(new DriveBackwards(50));
        //test solenoids
        addSequential(new Auto_TestSolenoids(6));
        //test gyro
        addSequential(new DriveTrainTurn(90));
        addSequential(new DriveTrainTurn(-90));
    }
}
