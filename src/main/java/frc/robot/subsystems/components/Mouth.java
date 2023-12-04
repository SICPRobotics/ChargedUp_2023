package frc.robot.subsystems.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.subsystems.basesubsytems.EncoderSubsystem;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public class Mouth extends SubsystemBaseWrapper implements MotorSubsystem, EncoderSubsystem {

    private VictorSPX pivotMotor;

    public Mouth(){
        // change out value of pivot id once plugged in
        this.pivotMotor = new VictorSPX(16);
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