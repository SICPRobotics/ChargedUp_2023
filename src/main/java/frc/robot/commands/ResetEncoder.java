package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EncoderSubsystem;

public class ResetEncoder extends CommandBase {
    EncoderSubsystem subsystem;
    public ResetEncoder(EncoderSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initialize() {
        this.subsystem.resetEncoder();
    }
}
