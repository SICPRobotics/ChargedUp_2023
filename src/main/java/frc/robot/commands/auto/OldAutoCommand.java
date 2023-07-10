package frc.robot.commands.auto;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoArm;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.MDriveTrain;

public class OldAutoCommand extends CommandBase{
    private Timer timer;
    private final int version;
    private final double waitTime;
    private final float starttime = System.nanoTime()/1000000000;
    private final Pigeon2 pigeon2 = new Pigeon2(0);
    private double initialYaw;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public OldAutoCommand(int verison, double waitTime){
    this.timer = new Timer();
    this.version = verison;
    this.waitTime = waitTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements();
    initialYaw = pigeon2.getYaw();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        float time = System.nanoTime()/1000000000 - starttime;
        /* 
        if(version == 0){
            if(time <2.3652434&& time > 1.044906){ 
                    this.mDriveTrain.driveBackwardsFast(); 
            } 
            if(time < 15 && time > 2.35){
              if(getDeltaYaw() > 7){
                this.mDriveTrain.turnLeft(.3);
              }
              else if(getDeltaYaw() < -7){
                this.mDriveTrain.turnRight(.3);
              }
              else if(getDeltaYaw() > 3){
                this.mDriveTrain.turnLeft(.05);
              }
              else if(getDeltaYaw() < -3){
                this.mDriveTrain.turnRight(.05);
              }
            }
            if(time <5.75444136&& time > 3.6581893){ 
              this.mDriveTrain.driveForwardsFast(); 
            } 
                 
            if(time >7.5444136&& time < 99.0 ){ 
              this.mDriveTrain.stop(); 
            }                 
        }


        
        if(version == 1){
          if(time < .75){
            initialYaw = pigeon2.getYaw();
          }
          if(time < 1.50 && time > 0.75){ 
              this.mDriveTrain.driveBackwardsFast(); 
            } 
          if(time > 1.5 && time < 3.22){ 
              this.mDriveTrain.driveForwardsFast(); 
            } 
          if(time < 999 && time > 3.22){ 
              this.mDriveTrain.autoLevel(initialYaw);
            } 

            
        }
        */
    
  }

  // Called once the command ends or is interrupted.

  @Override
  public void end(boolean interrupted) {
      this.timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.timer.get() > 15;
  }


  private double getDeltaYaw(){
    return(pigeon2.getYaw() - initialYaw);
  }
}
