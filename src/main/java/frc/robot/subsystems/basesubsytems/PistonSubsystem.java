package frc.robot.subsystems.basesubsytems;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This is to be implemented by subsystems that essentially wrap around a piston.
 */
public interface PistonSubsystem extends Subsystem{
    public void pistonForward();
    public void pistonReverse();
}