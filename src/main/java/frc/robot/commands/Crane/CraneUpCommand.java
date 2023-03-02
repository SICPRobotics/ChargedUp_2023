package frc.robot.commands.Crane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.TurnUntilStop;
import frc.robot.commands.TurnUntilValue;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.EncoderSubsystem;
import frc.robot.subsystems.Pidgey;

public class CraneUpCommand extends CommandBase {
    private final CraneExtender craneExtender;
    private final CranePivot cranePivot;
    private final Pigeon2 pidgey;
    private final List<CommandBase> steps;
    private int stepIndex;

    CommandBase turnUntilCrane(double speed, int target) {
        return new TurnUntilValue<CraneExtender>(craneExtender, speed, target);
    }

    CommandBase turnUntilPivot(double speed, int target) {
        return new TurnUntilValue<CranePivot>(cranePivot, speed, target);
    }

    CommandBase turnUntilStopPivot(double velocity) {
        return new TurnUntilStop<>(cranePivot, velocity, 300);
    }

    public CraneUpCommand (CraneExtender craneExtender, CranePivot cranePivot, Pigeon2 pidgey) {
        this.craneExtender = craneExtender;
        this.cranePivot = cranePivot;
        this.pidgey = pidgey;

        steps = Arrays.asList(


            turnUntilCrane(-1, 0),
            turnUntilCrane(1, 0)

            
        );

        addRequirements(craneExtender, cranePivot);
    }

    @Override
    public void initialize() {
        stepIndex = 0;

        steps.get(0).initialize();

        System.out.println("Started CraneUp");
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

        System.out.println("Ended CraneUp");
    }

    @Override
    public boolean isFinished() {
        return stepIndex >= steps.size();
    }
}