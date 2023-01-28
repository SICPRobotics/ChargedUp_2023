package frc.robot.commands.rumble;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.operator.OperatorController;

public class RumbleCommand extends CommandBase {
    private final OperatorController operator;
    private final double amount;
    private final double time;
    private final Timer timer = new Timer();
    
    public RumbleCommand(OperatorController operator, double amount, double time) {
        this.operator = operator;
        this.amount = amount;
        this.time = time;
        addRequirements(operator);
    }

    @Override
    public void initialize() {
        if (amount < 0) {
            operator.rumbleRight(amount);
        } else {
            operator.rumbleLeft(amount);
        }

        timer.start();
    }

    @Override
    public void end(boolean interrupted) {
        operator.endRumble();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > time;
    }
}
