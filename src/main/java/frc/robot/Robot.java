/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.Joystick;
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
import frc.robot.controllers.operator.OperatorController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;





public class Robot extends TimedRobot {
  private Command autonomousCommand;
  private RobotContainer robotContainer;
  double initialPitch;
  double initialRoll;
  double initialYaw;
  private final WPI_TalonFX m_leftFront = new WPI_TalonFX(0);
  private final WPI_TalonFX m_leftBack = new WPI_TalonFX(2);
  private final WPI_TalonFX m_rightFront = new WPI_TalonFX(1);
  private final WPI_TalonFX m_rightBack = new WPI_TalonFX(3);
  public final OperatorController operator = new OperatorController(1);
  XboxController xboxController = new XboxController(0);
  Logger logger = new Logger(xboxController);
  Joystick joystick = new Joystick(1);
  

  ArrayList<String> Inputs = new ArrayList<String>();
  ArrayList<Long> InputDurations = new ArrayList<Long>();
  ArrayList<Long> TimeOInput = new ArrayList<Long>();

  Timer totalTime = new Timer();
  boolean pressing = false;
  long pressstart = 0;
  long start = System.nanoTime();

  private final MecanumDrive   m_robotDrive = new MecanumDrive(m_leftFront, m_leftBack, m_rightFront, m_rightBack); 

Pigeon2 pigeon = new Pigeon2(0);
private double deltaPitch(){
  return (pigeon.getPitch() - initialPitch);
}

private double deltaRoll(){
  return (pigeon.getRoll() - initialRoll);
}

private double deltaYaw(){
  return (pigeon.getYaw() - initialYaw);
}

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
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically durting operator control.
   */
  @Override
  public void teleopPeriodic() {
    logger.CheckInputs();
  }

  @Override
  public final void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    System.out.println("Yaw:"+pigeon.getYaw()); // prints the yaw of the Pigeon
    System.out.println("Pitch:"+deltaPitch()); // prints the pitch of the Pigeon
    System.out.println("Roll:"+deltaRoll()); // prints the roll of the Pigeon
    //m_robotDrive.arcadeDrive(m_stick.getY(), -m_stick.getX());

    
    double currentPitch = deltaPitch();
    if(currentPitch <4.5 && currentPitch >-4.5){
      m_robotDrive.driveCartesian(0,0,0);
    }
    else if(currentPitch > -4.5){
      m_robotDrive.driveCartesian(0,.15,0);
    }
    else if(currentPitch < 4.5){
      m_robotDrive.driveCartesian(0,-.15,0);
    }
    // may have to use value other than pitch based on how pidgey is mounted
    
  }
}
