package frc.robot.commands.rumble;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.controllers.operator.OperatorController;

public final class Rumbler {
    private static OperatorController operator;
    public static void setOperator(OperatorController operator) {
        Rumbler.operator = operator;
    }

    public static void rumble(double amount, double time) {
        if (operator == null) {
            System.out.println("You forgot to call Rumbler.setOperator");
            return;
        }

        CommandScheduler.getInstance().schedule(new RumbleCommand(operator, amount, time));
    }
}
