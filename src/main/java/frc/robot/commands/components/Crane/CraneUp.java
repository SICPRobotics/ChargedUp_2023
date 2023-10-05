package frc.robot.commands.components.Crane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.basecommands.MotorCommand;
import frc.robot.commands.motorsetting.TurnUntilStop;
import frc.robot.commands.motorsetting.TurnUntilValue;
import frc.robot.subsystems.basesubsytems.EncoderSubsystem;
import frc.robot.subsystems.basesubsytems.Pidgey;
import frc.robot.subsystems.components.CraneExtender;
import frc.robot.subsystems.components.CranePivot;

import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class CraneUp extends CommandBase {
    //going to name the crane commands "Crane + C for cone or B for ball + B for bottom or M for middle or T for top"

    private final CranePivot cranePivot;
    private final CraneExtender craneExtender;
    private Pigeon2 pidgey = new Pigeon2(0);
    //negitive pivot side for negitive pitch values
    private int pivotSide = 0;
    private double encoderZero;


   public CraneUp (CranePivot cranePivot, CraneExtender craneExtender) {
        encoderZero = cranePivot.getEncoderPosition() + 22000;
        System.out.println("encoder starting position = " + encoderZero);
        this.cranePivot = cranePivot;
        this.craneExtender = craneExtender;
        // fin the anlge the gryo gives when staright up
        

        addRequirements(cranePivot);
    }

    @Override
    public void initialize() {
        if(currentPivotPosition() > 0){
            pivotSide = 1;
        }
        else{
            pivotSide = -1;
        }
    }

    @Override
    public void execute() {
        if(currentPivotPosition() > 0){
            //System.out.println("pitch  = " + pidgey.getRoll());
            cranePivot.setMotor(-.15);
        }
        if(currentPivotPosition() < 0 ){
           // System.out.println("pitch  = " + pidgey.getRoll());
            cranePivot.setMotor(0.15);
        }
            
        if(currentExtenderPosition() > -10000){
            craneExtender.setMotor(.01);
        }
        else if(currentExtenderPosition() < -10000){
            craneExtender.setMotor(.45);
        }
    }

    @Override
    public void end(boolean interrupted) {
       
    }

    @Override
    public boolean isFinished() {
        //change this later
        return(false);
        
    }

    public double currentPivotPosition(){
        return(cranePivot.getEncoderPosition() + Constants.Crane.POSITION_FROM_ZERO);
    }
    public double currentExtenderPosition(){
        return(craneExtender.getEncoderPosition());
    }

}