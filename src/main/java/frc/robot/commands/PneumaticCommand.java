package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.rumble.Rumbler;
import frc.robot.subsystems.PneumaticSubsystem;

public class PneumaticCommand extends CommandBase {
    private final PneumaticSubsystem PneumaticSubsystem;
    private final double velocity;
    private final boolean force;
    public PneumaticCommand(PneumaticSubsystem PneumaticSubsystem, double velocity, boolean force){
        this.PneumaticSubsystem = PneumaticSubsystem;
        this.velocity = velocity;
        this.force = force;
        addRequirements(PneumaticSubsystem);
    }
    public PneumaticCommand(PneumaticSubsystem PneumaticSubsystem, double velocity) {
        this(PneumaticSubsystem, velocity, false);
    }

    @Override
    public void initialize() {
        this.PneumaticSubsystem.setMotor(velocity, force);
    }

    @Override
    public void end(boolean interrupted) {
        this.PneumaticSubsystem.turnOff();

        if (!PneumaticSubsystem.canTurn(velocity)) {
            Rumbler.rumble(0.1, 0.5);
        }
    }

    @Override
    public boolean isFinished() {
        return !PneumaticSubsystem.canTurn(velocity);
    }
}
