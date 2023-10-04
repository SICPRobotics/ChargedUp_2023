package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DoubleSolenoidSubsystem;
import frc.robot.subsystems.MDriveTrain;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class BrakeMode extends CommandBase {
    private WPI_TalonFX frontRightMotor, rearRightMotor;
    private WPI_TalonFX rearLeftMotor, frontLeftMotor;
    public static boolean broked = true;
    public BrakeMode(MDriveTrain mDriveTrain) {
        System.out.println("brakemode contstructed");
        frontLeftMotor = mDriveTrain.frontLeftMotor;
        rearLeftMotor = mDriveTrain.rearLeftMotor;
        frontRightMotor = mDriveTrain.frontRightMotor;
        rearRightMotor = mDriveTrain.rearRightMotor;
    }

    @Override
    public void initialize() {
        if(broked == false){
            System.out.println("brakemode on");
            frontLeftMotor.setNeutralMode(NeutralMode.Brake);
            rearLeftMotor.setNeutralMode(NeutralMode.Brake);
            frontRightMotor.setNeutralMode(NeutralMode.Brake);
            rearRightMotor.setNeutralMode(NeutralMode.Brake);
            broked = true;
        }
        else if(broked == true){
            System.out.println("brakemode off");
            frontLeftMotor.setNeutralMode(NeutralMode.Coast);
            rearLeftMotor.setNeutralMode(NeutralMode.Coast);
            frontRightMotor.setNeutralMode(NeutralMode.Coast);
            rearRightMotor.setNeutralMode(NeutralMode.Coast);
            broked = false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("brakemode stoped");
    }
}