package frc.robot.commands.auto;

import frc.robot.Constants;
import frc.robot.subsystems.drivetrains.SwerveDrive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;


//choose a corner to use as 0,0 later, probally blue corner because I prefer blue
//try reflective tape/game peices as a backup if the system cant find apriltags to use
public class MoveToPosition extends CommandBase {    
    private SwerveDrive s_Swerve;
    private Pigeon2 pigeon = new Pigeon2(Constants.Swerve.pigeonID);   
    private BooleanSupplier robotCentricSup;
    private double xTarget;
    private double yTarget;
    private double turnTarget;

    public MoveToPosition(SwerveDrive s_Swerve, BooleanSupplier robotCentricSup, double xTarget, double yTarget, double turnTarget) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
        this.robotCentricSup = robotCentricSup;    
    }

    @Override
    public void execute() {
        double aTags[] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);
        //change these to use cordinates starting from one of the corners
        double xCord = aTags[0];
        double yCord = aTags[1];
        double turn = pigeon.getYaw();
        System.out.println("current position is: x=" + xCord + ", y=" + yCord + ", turn=" + turn);
        System.out.println("moveing to position: x=" + xTarget + ", y=" + yTarget + ", turn=" + turnTarget);
        
        double moveX;
        double moveY;
        double moveTurn;
        // do something like xTarget + .5 or something close to that to prevent ossolating
        if(xCord > xTarget && xCord != 0){
            moveX = .3;
        }
        else if(xCord < xTarget && xCord != 0){
            moveX = -.3;
        }
        else{
            moveX = 0;
        }

        if(yCord > yTarget && yCord != 0){
            moveY = .3;
        }
        else if(yCord < yTarget && yCord != 0){
            moveY = -.3;
        }
        else{
            moveY = 0;
        }

        if(turn > turnTarget){
            moveTurn = .3;
        }
        else if(turn < turnTarget){
            moveTurn = -.3;
        }
        else{
            moveTurn = 0;
        }


       //will probally have to switch out x and y cords here
        s_Swerve.drive(
                new Translation2d(moveX, moveY).times(Constants.Swerve.maxSpeed), 
                moveTurn * Constants.Swerve.maxAngularVelocity, 
                !robotCentricSup.getAsBoolean(), 
                true
        );

    }

    @Override
    public void end(boolean interrupted) {
       
    }

    @Override
    public boolean isFinished() {
        //change this later
        return(false);
        
    }
}

/*
    s_Swerve.drive(
        new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
        0 * Constants.Swerve.maxAngularVelocity, 
        !robotCentricSup.getAsBoolean(), 
        true
    );
 */