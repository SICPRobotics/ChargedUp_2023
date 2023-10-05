package frc.robot.subsystems.drivetrains;

import java.util.Arrays;
import java.util.function.BiConsumer;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.WillowMath;
import frc.robot.commands.other.rumble.Rumbler;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

/**
 * the DriveTrain, aka the thing that moves the robot
 */
public final class TankDrive extends SubsystemBaseWrapper implements MotorSubsystem{
    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
    
    public final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(21.5));
    private final Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0); //SPI.Port.kMXP ?
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.DriveTrain.FRONT_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX rearRight = new WPI_TalonSRX(Constants.DriveTrain.REAR_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.DriveTrain.FRONT_LEFT_MOTOR_ID);
    private final WPI_TalonSRX rearLeft = new WPI_TalonSRX(Constants.DriveTrain.REAR_LEFT_MOTOR_ID);
    private final MotorControllerGroup left = new MotorControllerGroup(frontLeft, rearLeft);
    private final MotorControllerGroup right = new MotorControllerGroup(frontRight, rearRight);
    private final DifferentialDrive robotDrive = new DifferentialDrive(left, right);
    private Pose2d savedPose = new Pose2d();
    
    public TankDrive() {
        super();
        right.setInverted(true); 
        // Motors
        gyro.calibrate();

        frontRight.configFactoryDefault();

        rearRight.configFactoryDefault();
        rearRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        rearRight.setSelectedSensorPosition(0);

        //right = new SpeedControllerGroup(frontRight, rearRight);
        frontLeft.configFactoryDefault();

        rearLeft.configFactoryDefault();
        rearLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        rearLeft.setSelectedSensorPosition(0); // LEFT IS WRONG DIRECTION BY DEFAULT

        /**
         * Encoder values:
         * 3m -25273 / 24981
         * 10ft -25759 / 25415
         */

        //left = new SpeedControllerGroup(frontLeft, rearLeft);
        //this.robotDrive = new DifferentialDrive(left, right);
        // odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(gyro.getAngle())), new Pose2d(0, 0, new Rotation2d()));
        // chassisSpeeds = new ChassisSpeeds(0,0,0);
        //reset();
    }
    public void setMotor(double value, boolean force) {
        this.robotDrive.arcadeDrive(value, 0);
    }
    public void turnOff() {
        this.robotDrive.arcadeDrive(0, 0);
    }
    public void turnOn(double velocity) {
        this.robotDrive.arcadeDrive(velocity, 0);
    }
    //Mostly taken from last year's robot
    /**
     * The method to drive the robot.
     * @param moveValue the amount that the robot should move (Y axis, Joystick rawAxis 1)
     * @param rotateValue the amount that the robot should rotate (X axis, Joystick rawAxis 0)
     * @param scaleValue the amount that everything should be scaled by (usually given by the
     * little flap thing on the bottom of the joystick, Joystick rawAxis 3)
     */
    public void cheesyDrive(final double moveValue, final double rotateValue) {
        
        final double movevalue = Math.abs(moveValue) < Constants.CheesyDrive.Y_AXIS_DEADZONE_RANGE
                ? 0
                : moveValue;

        //Deadzone on x axis only if y value is small
        final double turnvalue = Math.abs(rotateValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_RANGE
                && Math.abs(moveValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_Y_MIN
                ? 0
                : rotateValue;

        //if(ControlSystem == Constants.DriveTrain.ControlSystems.ARCADE_DRIVE_STANDARD){
            this.robotDrive.arcadeDrive(movevalue, turnvalue, true);
        // }
        // else if(ControlSystem == Constants.DriveTrain.ControlSystems.TANK_DRIVE_TEST){
        //     this.robotDrive.tankDrive(moveValue, rotateValue);
        // }
        // else if (ControlSystem == Constants.DriveTrain.ControlSystems.TANK_DRIVE_WITH_VOLTS){
        //     this.voltDrive(12 * ((moveValue + rotateValue) * adjustValue), 12 * ((moveValue - rotateValue) * adjustValue));
        // }
        
        
        
        
        //this.robotDrive.tankDrive((moveValue + rotateValue) * adjustValue, (moveValue - rotateValue) * adjustValue);
    }
    public BiConsumer<Double, Double> tankDriveVolts = (leftVolts, rightVolts) -> {
        left.setVoltage(leftVolts);
        right.setVoltage(-rightVolts);
        this.robotDrive.feed();
    };
    public void voltDrive(double leftVolts, double rightVolts){
        left.setVoltage(leftVolts);
        right.setVoltage(-rightVolts);
        this.robotDrive.feed();
    }
    // public void tankDriveVolts(Double leftVolts, Double rightVolts){
    //     left.setVoltage(leftVolts);
    //     right.setVoltage(-rightVolts);
    //     this.robotDrive.feed();
    // }

    public void diffDrive(double leftVelocity, double rightVelocity) {
        left.set(leftVelocity);
        right.set(rightVelocity);
    }
    
    public void periodic() {
        updatePose();
        SmartDashboard.putNumber("TalonSRX 0 (front right) Temperature", frontRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 1 (rear right) Temperature", rearRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 2 (rear left) Temperature", rearLeft.getTemperature());
        SmartDashboard.putNumber("TalonSRX 3 (front left) Temperature", frontLeft.getTemperature());
        //SmartDashboard.putNumberArray("test Array", new double[2]);
        SmartDashboard.putNumber("Linear Velocity", getLinearVelocity());
        SmartDashboard.putNumber("Angular Velocity", getAngularVelocity());
        SmartDashboard.putNumber("Front Left Motor Volts", getLeftVolts());
        SmartDashboard.putNumber("Front Right Motor Volts", getRightVolts());
        SmartDashboard.putString("Pose", getPose().toString());
        SmartDashboard.putNumber("Front Right Motor Position", getRightDistanceMeters());
        SmartDashboard.putNumber("Front Left Motor Position", getLeftDistanceMeters());
        SmartDashboard.putNumber("Front Right Motor Velocity", getRightVelocityMeters());
        SmartDashboard.putNumber("Front Left Motor Velocity", getLeftVelocityMeters());
        SmartDashboard.putNumber("Front Right Motor Position Raw", frontRight.getSelectedSensorPosition());
        SmartDashboard.putNumber("Front Left Motor Position Raw", frontLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Front Right Motor Velocity Raw", frontRight.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Front Left Motor Velocity Raw", frontLeft.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Encoder Difference: Right - Left", getRightDistanceMeters() - getLeftDistanceMeters());
        SmartDashboard.putNumber("Encoder Difference Raw: Right - Left", frontRight.getSelectedSensorPosition() - frontLeft.getSelectedSensorPosition());
        //SmartDashboard.putNumber("Right Side Volts", 0);
        SmartDashboard.putNumber("Front Left Side Volts", this.frontLeft.getMotorOutputVoltage());
        SmartDashboard.putNumber("Front Right Side Volts", this.frontRight.getMotorOutputVoltage());
        SmartDashboard.putNumber("Rear Left Side Volts", this.rearLeft.getMotorOutputVoltage());
        SmartDashboard.putNumber("Rear Right Side Volts", this.rearRight.getMotorOutputVoltage());
        SmartDashboard.putNumber("Left Side Volt Difference", this.frontLeft.getMotorOutputVoltage() - this.rearLeft.getMotorOutputVoltage());
        SmartDashboard.putNumber("Right Side Volt Difference", this.frontRight.getMotorOutputVoltage() - this.rearRight.getMotorOutputVoltage());
        SmartDashboard.putString("Saved Pose", this.savedPose.toString());
        SmartDashboard.putNumber("leftEncoder", rearLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("rightEncoder", rearRight.getSelectedSensorPosition());
        //System.out.println(this.getLeftDistanceMeters());

        //System.out.println(odometry.getPoseMeters().getTranslation().getX());
        //System.out.println(getRadians());
        //System.out.println(this.getPose().toString());

        if (RobotController.getBatteryVoltage() < 7.5) {
            Rumbler.rumble(0.5, 0.1);
        }
    }
    public double getRightDistanceRaw(){
        return -rearRight.getSelectedSensorPosition();
    }
    public double getLeftDistanceRaw(){
        return rearLeft.getSelectedSensorPosition();
    }
    
    public double getRightDistanceMeters(){
        return ((double)(frontRight.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * WillowMath.diameterToCircumfrance(Units.inchesToMeters(Constants.DriveTrain.WHEEL_Diameter_INCHES));
    }
    public double getLeftDistanceMeters(){
        return ((double)(-frontLeft.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * WillowMath.diameterToCircumfrance(Units.inchesToMeters(Constants.DriveTrain.WHEEL_Diameter_INCHES));
    }
    public double getRightVelocityMeters(){
        return ((double)(frontRight.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * WillowMath.diameterToCircumfrance(Units.inchesToMeters(Constants.DriveTrain.WHEEL_Diameter_INCHES));
    }
    public double getLeftVelocityMeters(){
        return ((double)(-frontLeft.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * WillowMath.diameterToCircumfrance(Units.inchesToMeters(Constants.DriveTrain.WHEEL_Diameter_INCHES));
    }
    public double getRightVolts(){
        return frontRight.getMotorOutputVoltage();
    }
    public double getLeftVolts(){
        return frontLeft.getMotorOutputVoltage();
    }
    public double getRadians(){
        return Math.toRadians(-gyro.getAngle());
    }
    private ChassisSpeeds updateVelocity(){
        return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(getLeftVelocityMeters(),getRightVelocityMeters()));
    }
    public double getLinearVelocity(){
        return updateVelocity().vxMetersPerSecond;
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelocityMeters(),getRightVelocityMeters());
    }
    public double getAngularVelocity(){
        return updateVelocity().omegaRadiansPerSecond;
    }
    public Pose2d getPose(){
        return odometry.getPoseMeters();
    }
    public void setSavedPose(){
        this.savedPose = this.getPose();
    }
    private void updatePose(){
        odometry.update(new Rotation2d(getRadians()), getLeftDistanceMeters(), getRightDistanceMeters());
    }
    public void reset(){
        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);
        gyro.calibrate();
    }
    
}