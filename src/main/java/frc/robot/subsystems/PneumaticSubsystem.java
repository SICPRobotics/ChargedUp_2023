package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This is to be implemented by subsystems that essentially wrap around a motor.
 */
public interface PneumaticSubsystem extends Subsystem {
    public void setMotor(double value, boolean force);
    default public void setMotor(double value) {
        setMotor(value, false);
    }
    public void turnOff();
    default public boolean canTurn(double direction) {
        return true;
    }
}