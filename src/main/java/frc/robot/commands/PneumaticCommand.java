package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class PneumaticCommand extends CommandBase {
    private final PneumaticSubsystem pneumaticSubsystem;
    private final Value direction;

    public PneumaticCommand(PneumaticSubsystem pneumaticSubsystem,  Value direction) {
        this.direction = direction;
        this.pneumaticSubsystem = pneumaticSubsystem;

    }

    @Override
    public void execute() {
        pneumaticSubsystem.set(direction);
    }

    @Override
    public void end(boolean interrupted) {
        pneumaticSubsystem.set(Value.kOff);
    }
}