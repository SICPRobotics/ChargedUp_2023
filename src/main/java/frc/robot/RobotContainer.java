/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.google.gson.Gson;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.commands.drive.MechinumDrive;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.BrakeMode;
import frc.robot.commands.DoubleSolenoidCommand;
import frc.robot.commands.ResetClimber;
import frc.robot.commands.ResetEncoder;
import frc.robot.commands.TurnUntilStop;
import frc.robot.commands.Crane.CraneCB;
import frc.robot.commands.arm.DownArmCommand;
import frc.robot.commands.arm.SimpleArmCommand;
import frc.robot.commands.auto.AutoBalence;
import frc.robot.commands.auto.CustomAuto;
import frc.robot.commands.auto.OldAutoCommand;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.commands.arm.UpArmCommand;
import frc.robot.commands.rumble.Rumbler;
import frc.robot.controllers.joystick.Joystick;
import frc.robot.controllers.operator.OperatorController;
import frc.robot.subsystems.CargoArm;
import frc.robot.commands.MotorCommand;
import frc.robot.controllers.joystick.Joystick;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.Pinchy;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.MDriveTrain;
import frc.robot.subsystems.DoubleSolenoidSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.Pidgey;
import com.ctre.phoenix.sensors.Pigeon2;
import frc.robot.Constants;
import frc.robot.Constants.Crane;
import frc.robot.Constants.Gryo;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public final class RobotContainer {
    public static final Gson gson = new Gson();
    public final Joystick joystick;
    //public final DriveTrain driveTrain;
    public final MDriveTrain mDriveTrain;
    private final DoubleSolenoid doubleSolenoid;
    private final DoubleSolenoid doubleSolenoid2;
    //public final TrajectoryGeneration trajectoryGeneration = new TrajectoryGeneration();
    public final GsonSaver gsonSaver;
    public final OperatorController operator = new OperatorController(1);
    public final CargoArm cargoArm;
    public final CargoIntake cargoIntake;
    public SmartDashBoardClass<Double> autoVersion, autoDelay;
    public final CraneExtender craneExtender;
    public final Pinchy pinchy;
    public final Pinchy pinchy2;
    public final Pidgey pidgey;
    public final Pigeon2 pigeon2;
    public final CranePivot cranePivot;
    public final BrakeMode brakeMode;
    public final AutoBalence autoBalence;
    
 
    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        CameraServer.startAutomaticCapture();
        Rumbler.setOperator(operator);
//        driveTrain = new DriveTrain();
        //driveTrain =null;
        mDriveTrain = new MDriveTrain();
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        doubleSolenoid2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
        joystick = new Joystick(0);
        gsonSaver = new GsonSaver();
        cargoArm = new CargoArm();
        cargoIntake = new CargoIntake();
        autoVersion = new SmartDashBoardClass<Double>("autoVersion", 0.0);
        autoDelay = new SmartDashBoardClass<Double>("autoDelay", 0.0);
        //trajectoryGeneration.addGson(gsonSaver);
        craneExtender = new CraneExtender();
        cranePivot = new CranePivot();
        pinchy = new Pinchy(doubleSolenoid);
        pinchy2 = new Pinchy(doubleSolenoid2);
        pidgey = new Pidgey();
        pigeon2 = new Pigeon2(Gryo.PIDGEY_ID);
        brakeMode = new BrakeMode(mDriveTrain);
        autoBalence = new AutoBalence(mDriveTrain);


        //final MechinumDrive mechdrive = new MechinumDrive(mDriveTrain, () -> getX(), () -> getY(), () -> joystick.getZ());
        
            
        //driveTrain.setDefaultCommand(
        //    new DriveWithJoystick(driveTrain, this::getY, this::getX, joystick::getScale, false));

        mDriveTrain.setDefaultCommand(new MechinumDrive(mDriveTrain, () -> getJY(), () -> getJX(), () -> getJZ()));


        

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
       


        operator.buttons.RB.whileTrue(new DoubleSolenoidCommand(pinchy, Value.kForward));
        operator.buttons.LB.whileTrue(new DoubleSolenoidCommand(pinchy, Value.kReverse));
        operator.buttons.RB.whileTrue(new DoubleSolenoidCommand(pinchy2, Value.kForward));
        operator.buttons.LB.whileTrue(new DoubleSolenoidCommand(pinchy2, Value.kReverse));
        operator.buttons.Y.whileTrue(new MotorCommand(craneExtender, 1));
        operator.buttons.A.whileTrue(new MotorCommand(craneExtender, -1));
        operator.buttons.X.whileTrue(new MotorCommand(cranePivot, .2));
        operator.buttons.B.whileTrue(new MotorCommand(cranePivot, -.2));
        operator.buttons.start.whileTrue(new BrakeMode(mDriveTrain));
        operator.buttons.LS.toggleOnTrue(new AutoBalence(mDriveTrain));
        cranePivot.setDefaultCommand(new RunCommand(() -> cranePivot.setMotor(operator.sticks.left.getY() * -0.2), cranePivot));
        craneExtender.setDefaultCommand(new RunCommand(() -> craneExtender.setMotor(operator.sticks.right.getY()), craneExtender));
    }

    public double getJX(){
        if(autoBalence.autoLeveling == true){

        }
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
        return new OldAutoCommand(mDriveTrain, craneExtender, cranePivot, this.autoVersion.getValue().intValue(), this.autoDelay.getValue().doubleValue());
    }
    
}