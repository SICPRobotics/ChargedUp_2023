/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.google.gson.Gson;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.simulation.JoystickSim;
//simport edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.drive.BrakeMode;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.commands.drive.MechinumDrive;
import frc.robot.commands.drive.TeleopSwerve;
import frc.robot.commands.other.rumble.Rumbler;
import frc.robot.commands.auto.AutoBalence;
import frc.robot.commands.auto.CustomAuto;
import frc.robot.commands.auto.OldAutoCommand;
import frc.robot.commands.components.Crane.CraneBM;
import frc.robot.commands.components.Crane.CraneBT;
import frc.robot.commands.components.Crane.CraneCM;
import frc.robot.commands.components.Crane.CraneUp;
import frc.robot.commands.components.Crane.SubStation;
import frc.robot.commands.components.arm.DownArmCommand;
import frc.robot.commands.components.arm.SimpleArmCommand;
import frc.robot.commands.components.arm.UpArmCommand;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.controllers.operator.OperatorController;

import com.ctre.phoenix.sensors.Pigeon2;
import frc.robot.Constants;
import frc.robot.Constants.Crane;
import frc.robot.Constants.Gryo;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.basesubsytems.Pidgey;
import frc.robot.subsystems.drivetrains.TankDrive;
import frc.robot.subsystems.drivetrains.SwerveDrive;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public final class RobotContainer {
    public static final Gson gson = new Gson();
    
    //public final DriveTrain driveTrain;
    //public final TrajectoryGeneration trajectoryGeneration = new TrajectoryGeneration();
    public final GsonSaver gsonSaver;
    public final OperatorController operator = new OperatorController(1);
    public SmartDashBoardClass<Double> autoVersion, autoDelay;
    public final Pidgey pidgey;
    public final Pigeon2 pigeon2;
    public final ADIS16470_IMU adis16470_IMU;

    private final Joystick joystick  = new Joystick(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(joystick, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(joystick, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final SwerveDrive s_Swerve = new SwerveDrive();
 
    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                
                () -> joystick.getRawAxis(translationAxis), 
                () -> joystick.getRawAxis(strafeAxis), 
                () -> joystick.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()

                /* 
                () -> joystick.getRawAxis(translationAxis), 
                () -> joystick.getRawAxis(strafeAxis), 
                () -> joystick.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
                */
            )
        );
        
        CameraServer.startAutomaticCapture();
        Rumbler.setOperator(operator);
//        driveTrain = new DriveTrain();
        //driveTrain =null;
        gsonSaver = new GsonSaver();
        autoVersion = new SmartDashBoardClass<Double>("autoVersion", 0.0);
        autoDelay = new SmartDashBoardClass<Double>("autoDelay", 0.0);
        //trajectoryGeneration.addGson(gsonSaver);
        pidgey = new Pidgey();
        pigeon2 = new Pigeon2(Gryo.PIDGEY_ID);
        adis16470_IMU = new ADIS16470_IMU();


        //final MechinumDrive mechdrive = new MechinumDrive(mDriveTrain, () -> getX(), () -> getY(), () -> joystick.getZ());
        
            
        //driveTrain.setDefaultCommand(
        //    new DriveWithJoystick(driveTrain, this::getY, this::getX, joystick::getScale, false));

        //check for autolveeling being true in mechdrive commadn

        


        

        // Configure the button bindings
        configureButtonBindings();
        //SmartDashboard.putNumber("Auton Chooser", 0);
        // trajectoryGeneration.generate(new Pose2d(new Translation2d(0,0), new Rotation2d(0)), List.of(
        //     new Translation2d(2,1),
        //     new Translation2d(5, -1)
        // ), 
        // new Pose2d(new Translation2d(7,0), new Rotation2d(0)), 
        // new TrajectoryConfig(4, 2), "test");
        // trajectoryGeneration.generate(new Pose2d(new Translation2d(0,0), new Rotation2d(0)), List.of(
        //     new Translation2d(1,2),
        //     new Translation2d(3, 3)
        // ), 
        // new Pose2d(new Translation2d(7,0), new Rotation2d(0)),
        //  new TrajectoryConfig(4, 2), "nottest");
        //trajectoryGeneration.printTrajectory("test");
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
   private void configureButtonBindings() {
        //joystick.thumb.toggleWhenPressed(
        //    new DriveWithJoystick(driveTrain, this::getY, this::getX, joystick::getScale, true));
    

        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));


        //a botton cone
        //y substation (top cone to unreliable)
        // x middle cude
        // b top cube

        /* 
        operator.buttons.Y.whileTrue(new MotorCommand(craneExtender, 1));
        operator.buttons.A.whileTrue(new MotorCommand(craneExtender, -1));
        operator.buttons.X.whileTrue(new MotorCommand(cranePivot, .2));
        operator.buttons.B.whileTrue(new MotorCommand(cranePivot, -.2));
        */

        //operator.buttons.start.whileTrue(new BrakeMode(mDriveTrain));
    }

    public double getJX(){
        return joystick.getX();
    }

    public double getJY(){
        return joystick.getY();
    }

    public double getJZ(){
        return joystick.getZ();
    }


    public double getY() {
        double joystickY = joystick.getY();
        double operatorY = -operator.sticks.right.getY();
        System.out.println("Joystick: " + joystickY + " Operator: " + operatorY);

        if (Math.abs(joystickY) > Math.abs(operatorY)) {
            return joystickY;
        } else {
            return operatorY;
        }
    }

    public double getX() {
        double joystickX = joystick.getX();
        double operatorX = operator.sticks.right.getX();

        if (Math.abs(joystickX) > Math.abs(operatorX)) {
            return joystickX;
        } else {
            return operatorX;
        }
    }

    // public void trajectory(TrajectoryGeneration trajectoryGeneration, DriveTrain driveTrain, Pose2d ){
    //   trajectoryGeneration = new TrajectoryGeneration(driveTrain.getPose(),
    //         new Pose2d(new Translation2d(0, 2), new Rotation2d(Math.PI/2)), 
    //         List.of(new Translation2d(0,1)), driveTrain);
    // }
    // * @return the command to run in autonomous
    public Command getAutonomousCommand() {
        return new OldAutoCommand(this.autoVersion.getValue().intValue(), this.autoDelay.getValue().doubleValue());
    }
    
}