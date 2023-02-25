package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.Climber;
import frc.robot.subsystems.MDriveTrain;

public class AutoConverter {
    ArrayList<String> Inputs = new ArrayList<String>();
    ArrayList<Long> InputDurations = new ArrayList<Long>();
    ArrayList<Long> TimeOInput = new ArrayList<Long>();
    Joystick joystick = new Joystick(0);
    public int cycle = 0;
    MDriveTrain mDriveTrain = new MDriveTrain();
    public AutoConverter(ArrayList Inputs, ArrayList InputDurations, ArrayList TimeOInput){
        this.Inputs = Inputs;
        this.InputDurations = InputDurations;
        this.TimeOInput = TimeOInput;
    }
    public void convert(ArrayList Inputs, ArrayList InputDurations, ArrayList TimeOInput){
        while(cycle < TimeOInput.size()){
            inputToCommand(Inputs.get(cycle).toString());
            System.out.println("if(time <" + TimeOInput.get(cycle) + "){"); 
            System.out.println("    this." + inputToCommand(Inputs.get(cycle).toString()));
            System.out.println("}");

        }
    }
    public String inputToCommand(String value){
        if(value == "A"){
            return("Climber.setmotor(.5);");
        }
        if(value == "Y"){
            return("Climber.setMotor(-.5);");
        }
        if(value == "X"){
            return("CranePivot.setMotor(.5);");
        }
        if(value == "B"){
            return("CranePivot.setMotor(-.5);");
        }
        if(value == "DUp"){
            return("mDriveTrain.driveUp();");
        }
        if(value == "DDown"){
            return("mDriveTrain.driveDown();");
        }
        if(value == "DRight"){
            return("mDriveTrain.driveRight();");
        }
        if(value == "DLeft"){
            return("mDriveTrain.driveLeft();");
        }
        return ("temp return");
    }
}

/*
if(time < 2){
    this.intake.setMotor(1);
}
else if(time > 2 && time < 3){
    this.intake.setMotor(-1);
}
else if(time > 3 && time < 5){
    this.intake.setMotor(1);
}
else if(time > 5 && time < 9){
    this.intake.setMotor(0);
    this.driveTrain.cheesyDrive(-0.5, 0);
}
else{
    this.driveTrain.cheesyDrive(0, 0);
} 
*/