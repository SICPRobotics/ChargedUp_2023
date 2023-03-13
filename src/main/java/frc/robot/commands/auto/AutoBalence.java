package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
    private Pigeon2 pigeon = new Pigeon2(0);
    public AutoBalence(MDriveTrain mDriveTrain) {
        this.mDriveTrain = mDriveTrain;
    }

    @Override
    public void initialize() {
        System.out.println("AutoLevel started");
        initialPitch = pigeon.getPitch();
        initialRoll = pigeon.getRoll();
        initialYaw = pigeon.getYaw();
    }

    @Override
    public void execute(){
        double currentPitch = deltaPitch();
        if(currentPitch <4.5 && currentPitch >-4.5){
            mDriveTrain.driveCartesian(0,0,0);
        }
        else if(currentPitch > -4.5){
            mDriveTrain.driveCartesian(0,.15,0);
        }
        else if(currentPitch < 4.5){
            mDriveTrain.driveCartesian(0,-.15,0);
        }
        }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoLevel stoped");
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