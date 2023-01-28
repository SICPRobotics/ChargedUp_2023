package frc.robot.commands;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class NudgeServo extends CommandBase {
    private final Servo servo;
    private final double amount;
    public NudgeServo(Subsystem subsystem, Servo servo, double amount) {
        this.servo = servo;
        this.amount = amount;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        servo.set(servo.get() + amount);
    }
}
