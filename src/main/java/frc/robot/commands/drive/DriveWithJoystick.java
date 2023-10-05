package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrains.TankDrive;

public final class DriveWithJoystick extends CommandBase {
    private final TankDrive driveTrain;
    private final DoubleSupplier moveValueGetter;
    private final DoubleSupplier rotateValueGetter;
    private final DoubleSupplier adjustValueGetter;
    private final boolean isInverted;
    //private final int controlSystem;

    // public DriveWithJoystick(final DriveTrain driveTrain, final DoubleSupplier moveValueGetter, final DoubleSupplier rotateValueGetter, final DoubleSupplier adjustValueGetter) {
    //     this(driveTrain, moveValueGetter, rotateValueGetter, adjustValueGetter, false);    
    // }

    public DriveWithJoystick(final TankDrive driveTrain, final DoubleSupplier moveValueGetter, final DoubleSupplier rotateValueGetter, final DoubleSupplier scaleValueGetter, final boolean isInverted) {
        this.driveTrain = driveTrain;
        this.moveValueGetter = moveValueGetter;
        this.rotateValueGetter = rotateValueGetter;
        this.adjustValueGetter = scaleValueGetter;
        this.isInverted = isInverted;
        //this.controlSystem = controlSystem;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        this.driveTrain.cheesyDrive(-this.moveValueGetter.getAsDouble() * (isInverted ? -1 : 1) * this.adjustValueGetter.getAsDouble(), this.rotateValueGetter.getAsDouble() * this.adjustValueGetter.getAsDouble());
    }

}