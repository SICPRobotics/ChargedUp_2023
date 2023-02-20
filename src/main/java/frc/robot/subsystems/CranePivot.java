package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class CranePivot extends SubsystemBaseWrapper implements MotorSubsystem, EncoderSubsystem {

    private TalonFX pivotMotor;

    public CranePivot(){
        // change out value of pivot id once plugged in
        this.pivotMotor = new TalonFX(Constants.Climber.PIVOT_MOTOR_ID);
        this.pivotMotor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void setMotor(double value, boolean force) {
        this.pivotMotor.set(ControlMode.PercentOutput, value);
    }

    @Override
    public void turnOff() {
        this.pivotMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public int getEncoderPosition() {
        return (int) pivotMotor.getSelectedSensorPosition();
    }

    @Override
    public int getEncoderVelocity() {
        return (int) pivotMotor.getSelectedSensorVelocity();
    }

    @Override
    public void resetEncoder() {
        pivotMotor.setSelectedSensorPosition(0);
    }
    
    @Override
    public void periodic() {

    }
}