package frc.robot.commands.motorsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.other.rumble.Rumbler;
import frc.robot.subsystems.basesubsytems.EncoderSubsystem;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;
import frc.robot.subsystems.components.CraneExtender;

public class TurnUntilStop<TSubsystem extends MotorSubsystem & EncoderSubsystem & Subsystem> extends CommandBase {
    private Timer timer = new Timer();
    private final TSubsystem subsystem;
    private final List<Runnable> callbacks = new ArrayList<>();
    private final double velocity;
    private final int tolerance;
    private int peak;
    private int currentVelocity;
    public TurnUntilStop(TSubsystem subsystem, double velocity, int tolerance) {
        this.subsystem = subsystem;
        this.velocity = velocity;
        this.tolerance = tolerance;
        addRequirements( subsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();

        subsystem.setMotor(velocity, true);
        peak = Integer.MIN_VALUE;
    }

    @Override
    public void execute() {
        currentVelocity = subsystem.getEncoderVelocity() * (int)Math.signum(velocity);
        peak = Math.max(peak, currentVelocity);
        System.out.println("CV: " + currentVelocity + " Peak: " + peak);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > 0.05 && peak - currentVelocity > tolerance;
    }

    @Override
    public void end(boolean b) {
        subsystem.setMotor(0);

        for (Runnable cb : callbacks) {
            cb.run();
        }
    }

    public TurnUntilStop<TSubsystem> onEnd(Runnable cb) {
        callbacks.add(cb);
        return this;
    }
}
