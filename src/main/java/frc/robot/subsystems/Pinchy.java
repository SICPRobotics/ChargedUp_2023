package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pinchy extends SubsystemBase implements PneumaticSubsystem{
  private final DoubleSolenoid doubleSolenoid;

  public Pinchy(DoubleSolenoid doubleSolenoid) {
    this.doubleSolenoid = doubleSolenoid;
  }

  public void clamp() {
    doubleSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void release() {
    doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void stop() {
    doubleSolenoid.set(DoubleSolenoid.Value.kOff);
  }

  @Override
  public void set(DoubleSolenoid.Value value) {
    doubleSolenoid.set(value);
  }
}