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
    public static boolean broked = false;
    double initialPitch;
    double initialRoll;
    double initialYaw;
    double initialX;
    public static boolean autoLeveling = false;

    private Pigeon2 pigeon = new Pigeon2(0);
    private ADIS16470_IMU adis16470_IMU = new ADIS16470_IMU();

    XboxController xboxController = new XboxController(1);
    public AutoBalence(MDriveTrain mDriveTrain) {
        this.mDriveTrain = mDriveTrain;
    }

    @Override
    public void initialize() {
        System.out.println("AutoLevel started");

        initialPitch = pigeon.getPitch();
        initialRoll = pigeon.getRoll();
        initialYaw = pigeon.getYaw();
        initialX = adis16470_IMU.getXComplementaryAngle();
        autoLeveling = true;
    }

    @Override
    public void execute(){
        double currentPitch = deltaX();
        System.out.println("AutoLevel testing");
        System.out.println("Current Pitch" + currentPitch);
        System.out.println("Yaw:"+deltaYaw()); // prints the yaw of the Pigeon
        System.out.println("Pitch:"+deltaPitch()); // prints the pitch of the Pigeon
        System.out.println("PitchX:"+deltaX());
        System.out.println("Roll:"+deltaRoll()); // prints the roll of the Pigeon


        if(currentPitch <4.5 && currentPitch >-4.5){
            mDriveTrain.stop();
        }
        else if(currentPitch < -4.5){
            mDriveTrain.driveForwards();
        }
        else if(currentPitch > 4.5){
            mDriveTrain.driveBackwards();
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoLevel stoped");
    }

    private double deltaX(){
        return (adis16470_IMU.getXComplementaryAngle() - initialX);
      }

    private double deltaPitch(){
        return (pigeon.getPitch() - initialPitch);
      }
      
      private double deltaRoll(){
        return (pigeon.getRoll() - initialRoll);
      }
      
      private double deltaYaw(){
        return (pigeon.getYaw() - initialYaw);
      }

    
      
}