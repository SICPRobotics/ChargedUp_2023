package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DoubleSolenoidSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSolenoidCommand extends CommandBase {
    private final DoubleSolenoidSubsystem pneumaticSubsystem;
    private final Value direction;

    public DoubleSolenoidCommand(DoubleSolenoidSubsystem pneumaticSubsystem,  Value direction) {
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