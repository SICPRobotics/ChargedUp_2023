/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrains.MechDrive;


public class MechinumDrive extends CommandBase {
  /*
   * Creates a new DriveMecanum.
   */

  private MechDrive driveTrain;
  private Supplier<Double>  x, y, z;
  private XboxController xboxController = new XboxController(1);

  public MechinumDrive(MechDrive drivetrain, Supplier<Double> forward, Supplier<Double> strafe, Supplier<Double> zRotation) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.driveTrain = drivetrain;
    this.x = forward;
    this.y = strafe;
    this.z = zRotation;
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("mechdrive started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -x.get();
    double ySpeed = -y.get();
    double zRotation = -z.get();
    
    if(xboxController.getStartButton() == false){
      driveTrain.driveCartesian(xSpeed, ySpeed, zRotation);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    //driveTvfsrain.driveCartesian(0.0, 0.0, 0.0);	
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}