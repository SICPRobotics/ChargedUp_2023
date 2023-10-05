package frc.robot.commands.motorsetting;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.basesubsytems.EncoderSubsystem;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public class TurnUntilValue<TSubsystem extends MotorSubsystem & EncoderSubsystem> extends CommandBase {
    public final TSubsystem subsystem;
    public final double speed;
    public final int target;
    public TurnUntilValue(TSubsystem subsystem, double speed, int target) {
        this.subsystem = subsystem;
        this.speed = speed;
        this.target = target;
    }

    @Override
    public void initialize() {
        this.subsystem.setMotor(this.speed);
    }

    @Override
    public void end(boolean interrupted) {
        this.subsystem.turnOff();
    }

    @Override
    public boolean isFinished() {
        if (this.speed > 0) {
            return this.subsystem.getEncoderPosition() > target;
        } else {
            return this.subsystem.getEncoderPosition() < target;
        }
    }
}
