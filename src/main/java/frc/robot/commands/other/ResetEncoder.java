package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.basesubsytems.EncoderSubsystem;

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
