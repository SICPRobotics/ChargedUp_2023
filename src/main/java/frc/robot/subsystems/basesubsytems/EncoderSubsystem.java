package frc.robot.subsystems;

public interface EncoderSubsystem {
    public int getEncoderPosition();
    public int getEncoderVelocity();
    public void resetEncoder();
}
