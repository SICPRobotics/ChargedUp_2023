package frc.robot;

import java.util.ArrayList;

public class AutoConverter {
    ArrayList<String> Inputs = new ArrayList<String>();
    ArrayList<Long> InputDurations = new ArrayList<Long>();
    ArrayList<Long> TimeOInput = new ArrayList<Long>();
    int cycle = 0;
    public AutoConverter(ArrayList Inputs, ArrayList InputDurations, ArrayList TimeOInput){
        this.Inputs = Inputs;
        this.InputDurations = InputDurations;
        this.TimeOInput = TimeOInput;
    }
    public void convert(ArrayList Inputs, ArrayList InputDurations, ArrayList TimeOInput){
        while(cycle < TimeOInput.size())
        System.out.println("if(time <" + TimeOInput.get(cycle) + "){"); 
        System.out.println("    this." + "temp" + ".setMotor(.5);");
        System.out.println("}");
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