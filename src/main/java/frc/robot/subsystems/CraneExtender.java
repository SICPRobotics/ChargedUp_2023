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
    private static final int maxEncoderHeight = 392_000;

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
        if (velocity == 0 || force || canTurn(velocity)) {
            this.climberMotor.set(velocity);
        }
    }

    @Override
    public boolean canTurn(double direction) {
        
        //return true;
        if (override) {
            return true;
        }
        
        if (direction > 0) {
            return getEncoderPosition() < maxEncoderHeight;
        } else {
            return true;
            //return getEncoderPosition() > 0;
        }
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
