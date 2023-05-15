package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SDriveTrain;


public class SwerveWithJoystick extends CommandBase{
    private final SDriveTrain sDriveTrain;
    private final DoubleSupplier xValueGetter, yValueGetter, rotateValueGetter;
    private final DoubleSupplier adjustValueGetter;
    private final boolean isInverted;
    //private final int controlSystem;

    // public DriveWithJoystick(final DriveTrain driveTrain, final DoubleSupplier moveValueGetter, final DoubleSupplier rotateValueGetter, final DoubleSupplier adjustValueGetter) {
    //     this(driveTrain, moveValueGetter, rotateValueGetter, adjustValueGetter, false);    
    // }

    public SwerveWithJoystick(final SDriveTrain sDriveTrain, final DoubleSupplier xValueGetter, final DoubleSupplier yValueGetter, final DoubleSupplier rotateValueGetter,  final DoubleSupplier scaleValueGetter, final boolean isInverted) {
        this.sDriveTrain = sDriveTrain;
        this.xValueGetter = xValueGetter;
        this.yValueGetter = yValueGetter;
        this.rotateValueGetter = rotateValueGetter;
        // this.adjustValueGetter = scaleValueGetter;
        this.adjustValueGetter = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return 1;
            }
        };
        //this.isInverted = isInverted;
        this.isInverted = false;
        //this.controlSystem = controlSystem;
        addRequirements(sDriveTrain);
    }

    @Override
    public void execute() {
        this.sDriveTrain.wolfDrive(new Pose2d(this.xValueGetter.getAsDouble() * this.adjustValueGetter.getAsDouble(),
         this.yValueGetter.getAsDouble() * (isInverted ? -1 : 1) * this.adjustValueGetter.getAsDouble(),
          new Rotation2d(this.rotateValueGetter.getAsDouble() * (isInverted ? -1 : 1) * this.adjustValueGetter.getAsDouble())));
    }

}