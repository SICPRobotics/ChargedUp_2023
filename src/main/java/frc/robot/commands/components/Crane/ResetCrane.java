package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.rumble.Rumbler;
import frc.robot.subsystems.CraneExtender;

public class ResetClimber extends CommandBase {
    private Timer timer = new Timer();
    private final CraneExtender climber;
    public ResetClimber(CraneExtender climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();

        climber.setMotor(-0.07, true);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > 0.1 && Math.abs(climber.getEncoderVelocity()) < 50;
    }

    @Override
    public void end(boolean b) {
        climber.setMotor(0);
        climber.resetEncoder();
        Rumbler.rumble(0.6, 0.2);
    }
}
