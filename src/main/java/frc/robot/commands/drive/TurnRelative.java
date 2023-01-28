package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pidgey;

public class TurnRelative extends CommandBase{
    
    private final DriveTrain driveTrain;
    private final Pidgey pidgey;
    private final double relativeTarget, speed, direction;
    private double target;
    private final PIDController pidController = new PIDController(0.05, 0, 0.006);
    private final Timer timer = new Timer();

    public TurnRelative(DriveTrain driveTrain, Pidgey pidgey, double target){
        this.driveTrain = driveTrain;
        this.pidgey = pidgey;
        this.relativeTarget = target;
        this.direction = Math.signum(target);
        this.speed = 0.5; // add constatnt for deafult auto speed
        pidController.enableContinuousInput(0, 360);

    }
    @Override
    public void initialize() {
        //this.driveTrain.diffDrive(speed * this.direction , -speed * this.direction);
        target = pidgey.getRobotHeading() + relativeTarget;
        timer.reset();
        timer.start();

    }
    @Override
    public void execute() {
        System.out.println("Ran TurnRelative");
        //this.driveTrain.diffDrive(speed * this.direction , -speed * this.direction);
        double amount = pidController.calculate(pidgey.getRobotHeading(), target);
        driveTrain.diffDrive(amount, -amount);
    }
    @Override
    public boolean isFinished() {
        return timer.get() > 5 || Math.abs(this.target - pidgey.getRobotHeading()) < 4 && pidgey.getRobotAngularVelocity() < 4;
    }
    @Override
    public void end(boolean interrupted) {
        this.driveTrain.diffDrive(0, 0);
    }


}
