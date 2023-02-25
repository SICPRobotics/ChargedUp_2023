package frc.robot.commands.Crane;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.Climber;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.MotorSubsystem;

public class CraneUpCommand extends CommandBase{
    CranePivot cranePivot;
    Climber climber;
    Pigeon2 pigeon2;
    CraneUpCommand(CranePivot cranePivot, Climber climber, Pigeon2 pigeon2){
        this.cranePivot = cranePivot;
        this.climber = climber;
        this.pigeon2 = pigeon2;
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
    
    }
    @Override
    public void end(boolean interrupted){
        
    }
}