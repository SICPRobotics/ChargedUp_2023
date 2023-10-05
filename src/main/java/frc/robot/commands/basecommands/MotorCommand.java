package frc.robot.commands.basecommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.other.rumble.Rumbler;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public class MotorCommand extends CommandBase {
    private final MotorSubsystem motorSubsystem;
    private final double velocity;
    private final boolean force;
    public MotorCommand(MotorSubsystem motorSubsystem, double velocity, boolean force){
        this.motorSubsystem = motorSubsystem;
        this.velocity = velocity;
        this.force = force;
        addRequirements(motorSubsystem);
    }
    public MotorCommand(MotorSubsystem motorSubsystem, double velocity) {
        this(motorSubsystem, velocity, false);
    }

    @Override
    public void initialize() {
        this.motorSubsystem.setMotor(velocity, force);
    }

    @Override
    public void end(boolean interrupted) {
        this.motorSubsystem.turnOff();

        if (!motorSubsystem.canTurn(velocity)) {
            Rumbler.rumble(0.1, 0.5);
        }
    }

    @Override
    public boolean isFinished() {
        return !motorSubsystem.canTurn(velocity);
    }
}
