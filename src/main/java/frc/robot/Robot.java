/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Logging.Logger;
import frc.robot.commands.auto.AutoBalence;
import frc.robot.controllers.operator.OperatorController;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.MDriveTrain;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;






public class Robot extends TimedRobot {
  private Command autonomousCommand;
  private RobotContainer robotContainer;
  double initialPitch;
  double initialRoll;
  double initialYaw;
  double initialX;

  Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  XboxController xboxController = new XboxController(1);
  Logger logger = new Logger(xboxController);

  

  List<String> inputs = new ArrayList<String>();
  List<Float> inputDurations = new ArrayList<Float>();
  List<Float> timeOInput = new ArrayList<Float>();

  AutoConverter converter = new AutoConverter(inputs, inputDurations, timeOInput);

  CraneExtender craneExtender = new CraneExtender();
  CranePivot cranePivot = new CranePivot();

  Pigeon2 pigeon = new Pigeon2(0);


  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    System.out.print("initailizzing");
    robotContainer = new RobotContainer();
    System.out.print("intialized");
    //SmartDashboardValues.clear();
    // UsbCamera cam1 = CameraServer.startAutomaticCapture(0);
    // UsbCamera cam2 = CameraServer.startAutomaticCapture(1);
    // cam2.setResolution(320, 320);
    // cam1.setResolution(320, 320);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    //robotContainer.generateTrajectory(SmartDashboard.getBoolean("Generate Trajectory", false));
    SmartDashboardValues.postAllValues();
    //System.out.println(SmartDashboardValues.getValues()); 
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {

    
    System.out.print("Inputs:");
    System.out.println(logger.getInputs());
    inputs = logger.getInputs();
    System.out.print("Input Durations:");
    System.out.println(logger.getInputDurations());
    inputDurations = logger.getInputDurations();
    System.out.print("Input times:");
    System.out.println(logger.getTimeOInput());
    timeOInput = logger.getTimeOInput();
    System.out.println("");
    System.out.println("");
    System.out.println("");

    converter.convert(inputs, inputDurations, timeOInput);
    
    inputs.clear();
    inputDurations.clear();
    timeOInput.clear();
    logger.emptyLog();
    
  }

  @Override
  public void disabledPeriodic() {
    
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    //robotContainer.generateTrajectory(true);
    autonomousCommand = robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public final void teleopInit() {

    pcmCompressor.enableDigital();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    logger.emptyLog();
  }
  //425975

  /**
   * This function is called periodically durting operator control.
   */
  @Override
  public void teleopPeriodic() {

    //System.out.println("Encoder position " + craneExtender.getEncoderPosition());
    //System.out.println("pitch " + pigeon.getPitch());

    System.out.println("Crane Pivot = " + (cranePivot.getEncoderPosition() + 15000));
    System.out.println("Crane Extender = " + craneExtender.getEncoderPosition());

    logger.CheckInputs();

    /* 
    if(xboxController.getPOV() == -1){
      mDriveTrain.stop();
    }
    if(xboxController.getPOV() == 0){
      mDriveTrain.driveForwardsFast();
    }
    if(xboxController.getPOV() == 90){
      mDriveTrain.driveRightFast();
    }
    if(xboxController.getPOV() == 180){
      mDriveTrain.driveBackwardsFast();
    }
    if(xboxController.getPOV() == 270){
      mDriveTrain.driveLeftFast();
    }
    */
    
}


    

    
    
    

  

  @Override
  public final void testInit() {
    // Cancels all running commands at the start of test mode.
    cranePivot.resetEncoder();
    craneExtender.resetEncoder();
    initialPitch = pigeon.getPitch();
    initialRoll = pigeon.getRoll();
    initialYaw = pigeon.getYaw();
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
    System.out.println(deltaPitch());



    /* 
    double currentPitch = deltaPitch();
    if(currentPitch <4.5 && currentPitch >-4.5){
      mDriveTrain.driveCartesian(0,0,0);
    }
    else if(currentPitch > -4.5){
      mDriveTrain.driveCartesian(0,.15,0);
    }
    else if(currentPitch < 4.5){
      mDriveTrain.driveCartesian(0,-.15,0);
    }
    // may have to use value other than pitch based on how pidgey is mounted
    */
  }
  private double deltaPitch(){
    return (pigeon.getPitch() - initialPitch);
  }
  
  private double deltaRoll(){
    return (pigeon.getRoll() - initialRoll);
  }
  
  private double deltaYaw(){
    return (pigeon.getYaw() - initialYaw);
  }
}
