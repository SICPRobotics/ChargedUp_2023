package frc.robot.subsystems;

import static frc.robot.Constants.*;

import javax.lang.model.util.ElementScanner14;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.controllers.joystick.Joystick;

public class MDriveTrain extends SubsystemBase {
    public double adjustedScale;
    public WPI_TalonFX frontRightMotor, rearRightMotor;
    public WPI_TalonFX rearLeftMotor, frontLeftMotor;
    frc.robot.controllers.joystick.Joystick joystick = new Joystick(0);
    private Pigeon2 pigeon2 = new Pigeon2(0);
    private MecanumDrive mDrive;
    boolean lastMoveForwards = true;
    int TimeInDeadZone = 0;
    boolean inDeadZoneLastTime = true;

  
    public MDriveTrain() {
      //Mecanum Drive motors
      frontLeftMotor = new WPI_TalonFX(Constants.DriveTrain.FRONT_RIGHT_MOTOR_ID);
      rearLeftMotor = new WPI_TalonFX(Constants.DriveTrain.REAR_RIGHT_MOTOR_ID);
      frontRightMotor = new WPI_TalonFX(Constants.DriveTrain.FRONT_LEFT_MOTOR_ID);
      rearRightMotor = new WPI_TalonFX(Constants.DriveTrain.REAR_LEFT_MOTOR_ID);

      frontLeftMotor.setNeutralMode(NeutralMode.Brake);
      rearLeftMotor.setNeutralMode(NeutralMode.Brake);
      frontRightMotor.setNeutralMode(NeutralMode.Brake);
      rearRightMotor.setNeutralMode(NeutralMode.Brake);
  
      frontLeftMotor.setInverted(true);
      rearLeftMotor.setInverted(true);
      frontRightMotor.setInverted(false);
      rearRightMotor.setInverted(false);
  
      mDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
  
      configureShuffleboardData();
    }
  
    private void configureShuffleboardData() {
  
    }
    /* 
    @Override
    public void periodic() {
      mDrive.feed();
    }
    */
  
    public void resetEncoderPositions() {
      frontLeftMotor.setSelectedSensorPosition(0);
      frontRightMotor.setSelectedSensorPosition(0);
      rearLeftMotor.setSelectedSensorPosition(0);
      rearRightMotor.setSelectedSensorPosition(0);
    }
  
    public double getFrontLeftEncoderPosition() { return frontLeftMotor.getSelectedSensorPosition(); }
    public double getRearLeftEncoderPosition() { return rearLeftMotor.getSelectedSensorPosition(); }
    public double getFrontRightEncoderPosition() { return frontRightMotor.getSelectedSensorPosition(); }
    public double getRearRightEncoderPosition() { return rearRightMotor.getSelectedSensorPosition(); }
    public double getFrontLeftEncoderVelocity() { return frontLeftMotor.getSelectedSensorVelocity(); }
    public double getFrontRightEncoderVelocity() { return frontRightMotor.getSelectedSensorVelocity(); }
    public double getRearLeftEncoderVelocity() { return rearLeftMotor.getSelectedSensorVelocity(); }
    public double getRearRightEncoderVelocity() { return rearRightMotor.getSelectedSensorVelocity(); }
  
    
    public void driveCartesian(double xSpeed, double ySpeed, double zRotation){
      
      xSpeed = MathUtil.applyDeadband(xSpeed, Constants.DriveTrain.SPEED_DEADBAND);
      ySpeed = MathUtil.applyDeadband(ySpeed, Constants.DriveTrain.SPEED_DEADBAND);
      zRotation = MathUtil.applyDeadband(zRotation, Constants.DriveTrain.ROTATION_DEADBAND);

      xSpeed = xSpeed * scaleToModifier(joystick.getScale());
      xSpeed = xSpeed * Math.abs(xSpeed);
      ySpeed = ySpeed * scaleToModifier(joystick.getScale());
      ySpeed = ySpeed *Math.abs(ySpeed);
      zRotation = zRotation * scaleToModifier(joystick.getScale());
      zRotation = zRotation *Math.abs(zRotation) * .5;

  
      mDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }

    public double scaleToModifier(double scale){
      adjustedScale = scale;
      if(adjustedScale == 0){
        adjustedScale = .40;
      }
      return(adjustedScale);
    }
    double initalX;
    double initalYaw;

    private double deltaX(double startX){
      return (pigeon2.getPitch());
    }

    boolean once = false;
    public void autoLevel(double startX, double initalYaw){
      this.initalYaw = initalYaw;
      if(once = false){
        initalX = startX;
        once = true;
      }
        double currentPitch = deltaX(startX);

        System.out.println("initalx" + initalX);
        System.out.println("actaul pitch" + pigeon2.getPitch());
        //System.out.println("DeltaX" + deltaX(startX) + "DeltaYaw " + getDeltaYaw(initalYaw));
        double turn = 0;
        

        /* 
        if(getDeltaYaw(initalYaw) > 7){
          turn = -.1;
        }
        else if(getDeltaYaw(initalYaw) < -7){
          turn = .1;
        }
        else if(getDeltaYaw(initalYaw) > 3){
          turn = -.05;
        }
        else if(getDeltaYaw(initalYaw) < -3){
          turn = .05;
        }
        */
         
        if(currentPitch < -4.5){
          System.out.println("correcting one way");
          mDrive.driveCartesian(-.12, 0, turn);
          inDeadZoneLastTime = false;
          lastMoveForwards = false;

          //mDrive.driveCartesian(-.17, 0, turn);
          //System.out.print("leveling forwards turn:"+turn);
          
        }
        else if(currentPitch > 4.5){
          System.out.println("correcting other way");
          mDrive.driveCartesian(.12, 0, turn);
          inDeadZoneLastTime = false;
          lastMoveForwards = true;
          //mDrive.driveCartesian(.17, 0, turn);
          //System.out.print("leveling backwards turn:"+turn);
        }
        
        else{
          //System.out.println("this is the else");
          if (inDeadZoneLastTime == false){
            TimeInDeadZone = 0;
          }
          inDeadZoneLastTime = true;
          if(TimeInDeadZone < 3){
            if(lastMoveForwards == true){
              mDrive.driveCartesian(-.05, 0, 0);
            }
            else{
              mDrive.driveCartesian(.05, 0, 0);
            }

          }
          TimeInDeadZone += 1;
          
        }
        
    }

    public void driveBackwardsFast(){
      mDrive.driveCartesian(-.4, 0, 0);
    }
    public void driveForwardsFast(){
      mDrive.driveCartesian(.4, 0, 0);
    }
    public void driveRightFast(){
      mDrive.driveCartesian(0, -.4, 0);
    }
    public void driveLeftFast(){
      mDrive.driveCartesian(0, .4, 0);
    }

    public void driveBackwards(){
      mDrive.driveCartesian(-.15, 0, 0);
    }
    public void driveForwards(){
      mDrive.driveCartesian(.15, 0, 0);
    }
    public void driveRight(){
      mDrive.driveCartesian(0, -.13, 0);
    }
    public void driveLeft(){
      mDrive.driveCartesian(0, .13, 0);
    }
    public void turnLeft(double speed){
      mDrive.driveCartesian(0, 0, -speed);
    }
    public void turnRight(double speed){
      mDrive.driveCartesian(0, 0, speed);
    }
    public void stop(){
      mDrive.driveCartesian(0,0,0);
    }
    private double getDeltaYaw(double startingyaw){
      return(pigeon2.getYaw() - initalYaw);
    }
  }

