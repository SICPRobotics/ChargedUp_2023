package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoArm;
import frc.robot.subsystems.Pidgey;

public abstract class SmartArmCommand extends CommandBase {
    public final Pidgey pidgey;
    public final CargoArm arm;
    public final double target;
    public double error = 0;
    public final double maxError;
    public SmartArmCommand(CargoArm arm, Pidgey pidgey, double target, double maxError) {
        this.arm = arm;
        this.pidgey = pidgey;
        this.target = target;
        this.maxError = maxError;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        error = (target - pidgey.getArmRotation()) / maxError;
        SmartDashboard.putNumber("Arm Progress", error);
        arm.setMotor(getOutput());
    }

    @Override
    public boolean isFinished() {
        return Math.abs(target - pidgey.getArmRotation()) < 5;
    }

    public abstract double getOutput();
}
