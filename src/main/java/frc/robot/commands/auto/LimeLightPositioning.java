package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrains.MechDrive;
import frc.robot.subsystems.drivetrains.SwerveDrive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class LimeLightPositioning extends CommandBase{
    XboxController xboxController = new XboxController(1);
    SwerveDrive swerve;

    public LimeLightPositioning(SwerveDrive swerve) {
        this.swerve = swerve;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute(){
        

    }
}