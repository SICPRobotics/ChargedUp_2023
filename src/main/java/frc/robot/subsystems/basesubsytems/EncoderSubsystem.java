package frc.robot.subsystems.basesubsytems;

public interface EncoderSubsystem {
    public int getEncoderPosition();
    public int getEncoderVelocity();
    public void resetEncoder();
}
