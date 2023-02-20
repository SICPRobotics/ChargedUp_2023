package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface PneumaticSubsystem {
  void set(DoubleSolenoid.Value value);
}