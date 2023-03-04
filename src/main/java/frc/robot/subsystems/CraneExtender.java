package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class CraneExtender extends SubsystemBaseWrapper implements MotorSubsystem, EncoderSubsystem {
    
    private final WPI_TalonFX climberMotor;

    public boolean override = false;

    public CraneExtender(){
        this.climberMotor = new WPI_TalonFX(Constants.Crane.CRANE_EXTENDER_MOTOR_ID);
        climberMotor.setNeutralMode(NeutralMode.Brake);
        resetEncoder();
    }

    @Override
    public void setMotor(double velocity) {
        setMotor(velocity, false);
    }

    public void setMotor(double velocity, boolean force){
        this.climberMotor.set(ControlMode.PercentOutput, velocity);
    }

    @Override
    public void turnOff(){
        this.climberMotor.set(0);
    }

    @Override
    public int getEncoderPosition() {
        return (int) climberMotor.getSelectedSensorPosition();
    }

    @Override
    public int getEncoderVelocity() {
        return (int) climberMotor.getSelectedSensorVelocity();
    }

    @Override
    public void resetEncoder() {
        climberMotor.setSelectedSensorPosition(0);
    }
}
