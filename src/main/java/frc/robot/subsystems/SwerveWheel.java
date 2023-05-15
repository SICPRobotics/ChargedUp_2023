package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;

public class SwerveWheel {

    private TalonFX driveMotor;
    private TalonFX steerMotor;
    private double steerSetpoint = 0;
    private double driveSetpoint = 0;
    private double steerVelocity = 0;
    private double steerTime = 0;
    private double driveTime = 0;
    private final double MAX_STEER_ERROR = Math.PI * 2;
    private final double MAX_DRIVE_ERROR = 10 * 12; // 10ft in inches
    private double sP = 1;
    private double sI = 0.25;
    private double dP = 1;
    private double dI = 0.5;
    private Timer timer = new Timer();
    private final double WHEEL_Diameter_INCHES = 6;

    public SwerveWheel(int driveMotorId, int steerMotorId){
        this.driveMotor = new TalonFX(driveMotorId);
        this.steerMotor = new TalonFX(steerMotorId);
        motorInit(this.driveMotor);
        motorInit(this.steerMotor);
        this.timer.reset();
        this.timer.start();
    }

    private void motorInit(TalonFX motor){
        motor.setNeutralMode(NeutralMode.Brake);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        motor.setSelectedSensorPosition(0);
    }

    public void setDriveMotor(double velocity){
        this.driveMotor.set(TalonFXControlMode.PercentOutput, velocity);
    }
    public void setSteerMotor(double steerSetpoint){
        this.steerSetpoint = steerSetpoint;
        this.steerTime = this.timer.get();
    }
    public void setDriveSetpoint(double driveSetpoint){
        this.driveSetpoint = driveSetpoint;
        this.driveTime = this.timer.get();
    }
    public double getDriveEncoder(){
        return this.driveMotor.getSelectedSensorPosition() * 4096 * WHEEL_Diameter_INCHES;
    }
    public double getSteerEncoder(){
        return this.steerMotor.getSelectedSensorPosition() * (4096/2) * Math.PI;
    }
    public double getsteerSetpoint() {
        return steerSetpoint;
    }
    public double getDriveSetpoint() {
        return driveSetpoint;
    }
    public void resetEncoders(){
        this.driveMotor.setSelectedSensorPosition(0);
        this.steerMotor.setSelectedSensorPosition(0);
    }


    public void updateSteer(){
        double error = (this.steerSetpoint - getSteerEncoder()) / this.MAX_STEER_ERROR; 
        double iError = (this.timer.get() - this.steerTime) * error;
        this.steerMotor.set(TalonFXControlMode.PercentOutput, (error * sP) + (iError * sI)); // PI Controller 
    }
    public void updateDrive(){
        double error = (this.driveSetpoint - getDriveEncoder()) / this.MAX_DRIVE_ERROR; 
        double iError = (this.timer.get() - this.driveTime) * error;
        this.steerMotor.set(TalonFXControlMode.PercentOutput, (error * dP) + (iError * dI)); // PI Controller
    }


}