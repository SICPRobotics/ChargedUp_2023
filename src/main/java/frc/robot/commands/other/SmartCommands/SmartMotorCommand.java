package frc.robot.commands.other.SmartCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public abstract class SmartMotorCommand extends CommandBase {
    public final DoubleSupplier input;
    public final MotorSubsystem subsystem;
    public final double target;
    public double error = 0;
    public final double maxError;
    
    public SmartMotorCommand(MotorSubsystem subsystem, DoubleSupplier input, double target, double maxError) {
        this.subsystem = subsystem;
        this.input = input;
        this.target = target;
        this.maxError = maxError;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        error = (target - input.getAsDouble()) / maxError;
        SmartDashboard.putNumber("SmartCommand", error);
        subsystem.setMotor(getOutput());
    }

    public abstract double getOutput();
}
