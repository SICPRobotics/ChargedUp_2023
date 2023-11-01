package frc.robot.commands.auto;

import frc.robot.Constants;
import frc.robot.subsystems.drivetrains.SwerveDrive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenixpro.hardware.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class LimeLightPositioning extends CommandBase {    
    private SwerveDrive s_Swerve;
    private Pigeon2 pigeon = new Pigeon2(Constants.Swerve.pigeonID);   
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;

    public LimeLightPositioning(SwerveDrive s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;

    
    }

    @Override
    public void execute() {
        double aTags[] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);
        System.out.println("test here v2");
        System.out.println(aTags[0]);
        System.out.println(aTags[1]);
        /* Get Values, Deadband*/
        double valueX = aTags[0];
        double valueY = aTags[1];
        
        if(valueX > -6 && valueX != 0){
            double translationVal = .3;
            double strafeVal = 0;
            double rotationVal = 0;

            /* Drive */
            s_Swerve.drive(
                new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
                rotationVal * Constants.Swerve.maxAngularVelocity, 
                !robotCentricSup.getAsBoolean(), 
                true
            );
        }
        else if(valueX < -6 || valueX == 0){
            double translationVal = 0;
            double strafeVal = 0;
            double rotationVal = .3;

            /* Drive */
            s_Swerve.drive(
                new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
                rotationVal * Constants.Swerve.maxAngularVelocity, 
                !robotCentricSup.getAsBoolean(), 
                true
            );
        }
        else{
        s_Swerve.drive(
                new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
                0 * Constants.Swerve.maxAngularVelocity, 
                !robotCentricSup.getAsBoolean(), 
                true
        );
        }

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