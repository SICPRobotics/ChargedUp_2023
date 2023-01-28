package frc.robot.commands.autoclimb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.TurnUntilStop;
import frc.robot.commands.TurnUntilValue;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberPivot;
import frc.robot.subsystems.EncoderSubsystem;
import frc.robot.subsystems.Pidgey;

public class AutoClimb extends CommandBase {
    private final Climber climber;
    private final ClimberPivot pivot;
    private final Pidgey pidgey;
    private final List<CommandBase> steps;
    private int stepIndex;

    CommandBase turnUntilClimber(double speed, int target) {
        return new TurnUntilValue<Climber>(climber, speed, target);
    }

    CommandBase turnUntilPivot(double speed, int target) {
        return new TurnUntilValue<ClimberPivot>(pivot, speed, target);
    }

    CommandBase turnUntilStopPivot(double velocity) {
        return new TurnUntilStop<>(pivot, velocity, 300);
    }

    public AutoClimb (Climber climber, ClimberPivot pivot, Pidgey pidgey) {
        this.climber = climber;
        this.pivot = pivot;
        this.pidgey = pidgey;

        steps = Arrays.asList(
            turnUntilClimber(-1, 0),
            new TurnUntilStop<Climber>(climber, -0.2, 100),
            //turnUntilPivot(0.3, 120_000),
            turnUntilStopPivot(0.3),
            turnUntilClimber(1, 70_000),
            // on mid rung

            turnUntilPivot(-0.3, 120_000),
            turnUntilClimber(-1, 0),
            turnUntilPivot(0.3, 10_000),
            turnUntilClimber(1, 300_000)

            
        );

        addRequirements(climber, pivot);
    }

    /**
     * Climber up
     * drive back
     * 
     * -- start --
     * climber down
     * hooks forwards
     * climber up
     * on mid rung
     * climber down x
     * hooks forwards x
     * climber down 
     * hooks backwards
     * climber up
     * hooks backwards
     * on high rung 
     * climber down
     * hooks forwards
     * climber up
     * climber down
     * hooks forwards 
     * climber up
     * hooks forwards
     * climber down
     * hooks backwards
     */

    @Override
    public void initialize() {
        stepIndex = 0;

        steps.get(0).initialize();

        System.out.println("Started AutoClimb");
    }

    @Override
    public void execute() {
        //System.out.println("Executed -- " + stepIndex);
        if (isFinished()) {
            return;
        }

        CommandBase step = steps.get(stepIndex);

        step.execute();

        if (step.isFinished()) {
            step.end(false);

            stepIndex++;

            if (stepIndex < steps.size()) {
                steps.get(stepIndex).initialize();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (stepIndex < steps.size()) {
            steps.get(stepIndex).end(interrupted);
        }

        System.out.println("Ended AutoClimb");
    }

    @Override
    public boolean isFinished() {
        return stepIndex >= steps.size();
    }
}