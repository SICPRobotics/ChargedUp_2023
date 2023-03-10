package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DoubleSolenoidSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class BrakeMode extends CommandBase {
    public static boolean broked = false;
    public BrakeMode() {

    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}