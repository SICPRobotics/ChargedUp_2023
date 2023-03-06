package frc.robot.commands.Crane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.TurnUntilStop;
import frc.robot.commands.TurnUntilValue;
import frc.robot.subsystems.CraneExtender;
import frc.robot.subsystems.CranePivot;
import frc.robot.subsystems.EncoderSubsystem;
import frc.robot.subsystems.Pidgey;

public class CraneCB extends CommandBase {
    //going to name the crane commands Crane + C for cone or B for ball + B for bottom or M for middle or T for top

    private final CranePivot cranePivot;
    private final Pigeon2 pidgey;
    //negitive pivot side for negitive pitch values
    private int pivotSide = 0;

    public CraneCB (CranePivot cranePivot, Pigeon2 pidgey) {
        this.cranePivot = cranePivot;
        this.pidgey = pidgey;

        addRequirements(cranePivot);
    }

    @Override
    public void initialize() {
        if(pidgey.getPitch() < 0){
            pivotSide = 1;
        }
        else{
            pivotSide = -1;
        }
    }

    @Override
    public void execute() {
        //example angles change to match with reality later

        if(pivotSide == 1){
            if(pidgey.getPitch() < 90){
                cranePivot.setMotor(.2);
            }
        }
        else{
            if(pidgey.getPitch() > -90){
                cranePivot.setMotor(-.2);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        //change this later
        return (pidgey.getPitch() == 0);
    }
}