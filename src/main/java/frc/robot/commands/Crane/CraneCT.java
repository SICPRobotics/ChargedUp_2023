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

import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class CraneCT extends CommandBase {
    //going to name the crane commands "Crane + C for cone or B for ball + B for bottom or M for middle or T for top"

    private final CranePivot cranePivot;
    private final CraneExtender craneExtender;
    private Pigeon2 pidgey = new Pigeon2(0);
    //negitive pivot side for negitive pitch values
    private int pivotSide = 0;
    private double encoderZero;


   public CraneCT (CranePivot cranePivot, CraneExtender craneExtender) {
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

        System.out.println("Pivot position" + currentPivotPosition());
        System.out.println("Extender position" + currentExtenderPosition());

        // pivot side = 1 is front o f robot 
        if(pivotSide == 1){
            System.out.println("front of Robot");
                
            if(currentPivotPosition() < 21000){
               
                cranePivot.setMotor(.1);
            }
            if(currentPivotPosition() > 22000){
               // System.out.println("pitch  = " + pidgey.getRoll());
                cranePivot.setMotor(-.1);
            }
        }
        else if(pivotSide == -1){
            System.out.println("back of Robot");
                
            if(currentPivotPosition() > -21000){
                //System.out.println("pitch  = " + pidgey.getRoll());
                cranePivot.setMotor(-.1);
            }
            if(currentPivotPosition() < -22000 ){
               // System.out.println("pitch  = " + pidgey.getRoll());
                cranePivot.setMotor(0.1);
            }
        }

        if(currentExtenderPosition() > -400000){
            craneExtender.setMotor(-.75);
        }
        else if(currentExtenderPosition() < -400000){
            craneExtender.setMotor(.75);
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

    public double currentPivotPosition(){
        return(cranePivot.getEncoderPosition() + 22000);
    }
    public double currentExtenderPosition(){
        return(craneExtender.getEncoderPosition());
    }

}