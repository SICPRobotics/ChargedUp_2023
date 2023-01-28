package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class ClimberPivot extends SubsystemBaseWrapper implements MotorSubsystem, EncoderSubsystem {

    private TalonFX pivotMotor;
    private final double maxTorque = 1000;
    private long timeLastTonePlayed = 0;
    private boolean hasResetSound = true;
    private long lastAtMax = Long.MAX_VALUE;
    private double laggingAngularVelocity = 0;

    public ClimberPivot(){
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
        /*double power = 12 * pivotMotor.getStatorCurrent();
        double angularVelocity = (double) getEncoderVelocity() * 10 / (2048 * 10 * 5 * 5) * 2 * Math.PI;
        double currentTorque = power / angularVelocity;

        laggingAngularVelocity = laggingAngularVelocity * 0.5 + angularVelocity * 0.5;

        System.out.println("Current torque: " + currentTorque + " Power: " + power + " AngV: " + angularVelocity + " V: ");

        if (Math.abs(currentTorque) > maxTorque && Math.abs(laggingAngularVelocity) > Math.abs(angularVelocity) + 0.05) {
            if (lastAtMax > System.currentTimeMillis()) {
                lastAtMax = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - lastAtMax > 100) {
                System.out.println("Exceeded max torque! (" + currentTorque + " N*m)");
                turnOff();
                CommandScheduler.getInstance().cancel(this.getCurrentCommand());
                timeLastTonePlayed = System.currentTimeMillis();
            }
        } else {
            lastAtMax = Long.MAX_VALUE;
        }*/

        System.out.println(pivotMotor.getStatorCurrent());
        if (Math.abs(pivotMotor.getStatorCurrent()) > 30 && pivotMotor.getMotorOutputPercent() > 0.1) {
            turnOff();
            System.out.println("Canceled pivot!");
            CommandScheduler.getInstance().cancel(this.getCurrentCommand());
            timeLastTonePlayed = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() - timeLastTonePlayed < 1000) {
            playTone();
        } else if (!hasResetSound) {
            this.pivotMotor.set(ControlMode.MusicTone, 0);
            hasResetSound = true;
        }
    }

    private boolean lastToneWasHigh = false;
    public void playTone() {
        hasResetSound = false;
        if (lastToneWasHigh) {
            this.pivotMotor.set(ControlMode.MusicTone, 493.88);
        } else {
            this.pivotMotor.set(ControlMode.MusicTone, 523.25);
        }
        lastToneWasHigh = !lastToneWasHigh;
    }
}