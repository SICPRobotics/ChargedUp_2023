package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.basesubsytems.DoubleSolenoidSubsystem;
import frc.robot.subsystems.drivetrains.MechDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class AutoBalence extends CommandBase {
    private MechDrive mDriveTrain;
    private ADIS16470_IMU adis16470_IMU;
    public static boolean broked = false;
    double initialPitch;
    double initialRoll;
    double initialYaw;
    static double initialX;
    public static boolean autoLeveling = false;

    private Pigeon2 pigeon = new Pigeon2(0);

    XboxController xboxController = new XboxController(1);
    public AutoBalence(MechDrive mDriveTrain) {
        this.mDriveTrain = mDriveTrain;
        initialX = pigeon.getPitch();
    }

    @Override
    public void initialize() {
        System.out.println("AutoLevel started");

        initialPitch = pigeon.getPitch();
        initialRoll = pigeon.getRoll();
        initialYaw = pigeon.getYaw();
        initialX = pigeon.getPitch();
        autoLeveling = true;
    }

    @Override
    public void execute(){
        double currentPitch = deltaX();
        System.out.println("DeltaX" + deltaX());
         
        if(currentPitch < 8 && currentPitch > -8){
            //mDriveTrain.stop();
        }
        else if(currentPitch < -8){
            mDriveTrain.driveBackwards();
        }
        else if(currentPitch > 8){
            mDriveTrain.driveForwards();
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoLevel stoped");
    }

    private double deltaX(){
        return (pigeon.getPitch() - initialX);
    }
}