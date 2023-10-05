package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrains.TankDrive;

public class DriveDistance extends CommandBase {
    private final TankDrive driveTrain;
    private final double distance, speed;
    private double startLeft, startRight;
    public DriveDistance(TankDrive driveTrain, double distance, double speed) {
        this.driveTrain = driveTrain;
        this.distance = distance;
        this.speed = speed;
    }

    private double getLeft() {
        return driveTrain.getLeftDistanceRaw() - startLeft;
    }

    private double getRight() {
        return driveTrain.getRightDistanceRaw() - startRight;
    }

    @Override
    public void initialize() {
        this.driveTrain.diffDrive(speed, speed);
        startLeft = driveTrain.getLeftDistanceRaw();
        startRight = driveTrain.getRightDistanceRaw();
    }
    @Override
    public void execute() {
        if(getRight() - getLeft() > Constants.Auto.DIST_DRIVE_ACCEPTED_ERROR){
            this.driveTrain.diffDrive(speed + 0.1, speed - 0.1);
        }
        else if(getRight() - getLeft() < -Constants.Auto.DIST_DRIVE_ACCEPTED_ERROR){
            this.driveTrain.diffDrive(speed - 0.1, speed + 0.1);
        }
        else{
            this.driveTrain.diffDrive(speed, speed);
        }
        System.out.println("Avg: " + ((getLeft() + getRight()) / 2) + " distance: " + distance);
    }
    @Override
    public boolean isFinished() {
        return Math.abs((getLeft() + getRight()) / 2) >= distance;
    }
    @Override
    public void end(boolean interrupted) {
        this.driveTrain.diffDrive(0, 0);
    }




}
