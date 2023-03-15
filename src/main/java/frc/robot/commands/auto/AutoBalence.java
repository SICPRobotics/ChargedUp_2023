package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DoubleSolenoidSubsystem;
import frc.robot.subsystems.MDriveTrain;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class AutoBalence extends CommandBase {
    private MDriveTrain mDriveTrain;
    private ADIS16470_IMU adis16470_IMU;
    public static boolean broked = false;
    double initialPitch;
    double initialRoll;
    double initialYaw;
    static double initialX;
    public static boolean autoLeveling = false;

    private Pigeon2 pigeon = new Pigeon2(0);
    //private ADIS16470_IMU adis16470_IMU = new ADIS16470_IMU();

    XboxController xboxController = new XboxController(1);
    public AutoBalence(MDriveTrain mDriveTrain, ADIS16470_IMU adis16470_IMU) {
        this.mDriveTrain = mDriveTrain;
        this.adis16470_IMU = adis16470_IMU;
        initialX = adis16470_IMU.getXComplementaryAngle();
    }

    @Override
    public void initialize() {
        System.out.println("AutoLevel started");

        initialPitch = pigeon.getPitch();
        initialRoll = pigeon.getRoll();
        initialYaw = pigeon.getYaw();
        autoLeveling = true;
    }

    @Override
    public void execute(){
        double currentPitch = deltaX();
         
        if(currentPitch < 8 && currentPitch > -8){
            mDriveTrain.stop();
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
        return (adis16470_IMU.getXComplementaryAngle() - initialX);
    }
}