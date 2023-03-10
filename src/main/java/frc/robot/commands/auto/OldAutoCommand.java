package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoArm;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.MDriveTrain;

public class OldAutoCommand extends CommandBase{
    private final MDriveTrain mDriveTrain;
    private final CraneExtender craneExtender; 
    private CranePivot cranePivot;
    private Timer timer;
    private final int version;
    private final double waitTime;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public OldAutoCommand(MDriveTrain mDriveTrain, CraneExtender craneExtender, CranePivot cranePivot, int verison, double waitTime){
    this.mDriveTrain = mDriveTrain;
    this.craneExtender = craneExtender;
    this.cranePivot = cranePivot;
    this.timer = new Timer();
    this.version = verison;
    this.waitTime = waitTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(mDriveTrain, craneExtender, cranePivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      this.timer.start();   
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        double time = this.timer.get();
        
        if(version == 0){
            if(time <2.3652434&& time > 1.044906){ 
                    this.mDriveTrain.driveLeft(); 
                 } 
                 if(time <6.75444136&& time > 3.6581893){ 
                 this.mDriveTrain.driveRight(); 
                 } 
                if(time >8.5444136&& time < 99.0 ){ 
                   this.mDriveTrain.stop(); 
                }                 
        }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      this.timer.reset();
      this.cranePivot.turnOff();
      this.mDriveTrain.stop();
      this.craneExtender.turnOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.timer.get() > 15;
  }
}
