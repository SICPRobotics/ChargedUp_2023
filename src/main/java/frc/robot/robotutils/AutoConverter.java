package frc.robot.robotutils;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.Climber;
import frc.robot.subsystems.drivetrains.MechDrive;

public class AutoConverter {
    List<String> inputs = new ArrayList<String>();
    List<Float> inputDurations = new ArrayList<Float>();
    List<Float> timeOInput = new ArrayList<Float>();
    public int cycle = 0;
    public int cycle2 = 0;
    public int runs = 0;
    public String checked = "z";
  
    int lasttime;
    boolean TwoInput = false;
   
    //takes in inputs obtained from logs
    public AutoConverter(List<String> inputs, List<Float> inputDurations, List<Float> timeOInput){
        this.inputs = inputs;
        this.inputDurations = inputDurations;
        this.timeOInput = timeOInput;
    }

    //called after disableing the bot before clearing logs
    public void convert(List<String> inputs, List<Float> inputDurations, List<Float> timeOInput){
        this.inputs = inputs;
        this.inputDurations = inputDurations;
        this.timeOInput = timeOInput;
        if(inputs.size() == 0){
            return;
        }
        while(cycle < timeOInput.size()){
            //will turn on motor after correct amount of time has elapsed
            System.out.println("if(time <" + stopTime() + "&& time > " + timeOInput.get(cycle) + "){"); 
            System.out.println("    this." + inputToCommand(inputs.get(cycle).toString()));
            System.out.println("}");

            //this turns of the motor after the input duration is up until they are used again
            System.out.println("if(time >" + stopTime() + "&& time < " + endStop() + " ){" );
            System.out.println("    this." + inputToStop(inputs.get(cycle).toString()));
            System.out.println("}");
            
            
            cycle = cycle + 1;
      }
      //resets class to be reused after disabling
      inputs.clear();
      inputDurations.clear();
      timeOInput.clear();
      this.inputs.clear();
      this.inputDurations.clear();
      this.timeOInput.clear();
      cycle = 0;
      cycle2 = 0;
      runs = 0;
      checked = "z";

    }

    //finds when an input should stop being made
    public float stopTime(){
      return(timeOInput.get(cycle) + inputDurations.get(cycle));
    }

    //figures out when a motor will (if ever) be needed again
    public float endStop(){
      cycle2 = runs;
      runs = runs + 1;
      checked = inputs.get(cycle);
      cycle2 = cycle2 + 1;
      //checking to see if the motor is used again
      while(cycle2 < inputDurations.size()){
        if(inputs.get(cycle2).equals(checked)){
          //will stop turning off the motor before it needs to be turn on again 
          return(timeOInput.get(cycle2));
        }
        cycle2 = cycle2 + 1;
      }
      // will the turn the motor off indefinitly if its not needed again
      return(99);
    }
    
    public String inputToStop(String value){
      //turns off correcting motor change this to match button bindings
      if(value.equals("A")){
            return("Climber.setmotor(0);");
        }
        if(value.equals("Y")){
            return("Climber.setMotor(0);");
        }
        if(value.equals("X")){
            return("CranePivot.setMotor(0);");
        }
        if(value.equals("B")){
            return("CranePivot.setMotor(0);");
        }
        if(value.equals("DUp")){
            return("mDriveTrain.stop();");
        }
        if(value.equals("DDown")){
            return("mDriveTrain.stop();");
        }
        if(value.equals("DRight")){
            return("mDriveTrain.stop();");
        }
        if(value.equals("DLeft")){
            return("mDriveTrain.stop();");
        }
        if(value.equals("RB")){
            return("pinchy.out()");
        }
        if(value.equals("LB")){
            return("pinchy.out()");
        }
        if(value.equals("Start")){
            return("temp autolevel stop");
        }
      return("invalid input");
    }

    public String inputToCommand(String value){
      // returns the coralating command to the button input change this to match button bindings
        if(value.equals("A")){
            return("Climber.setmotor(.5);");
        }
        if(value.equals("Y")){
            return("Climber.setMotor(-.5);");
        }
        if(value.equals("X")){
            return("CranePivot.setMotor(.5);");
        }
        if(value.equals("B")){
            return("CranePivot.setMotor(-.5);");
        }
        if(value.equals("DUp")){
            return("mDriveTrain.driveUp();");
        } 
        if(value.equals("DDown")){
            return("mDriveTrain.driveDown();");
        }
        if(value.equals("DRight")){
            return("mDriveTrain.driveRight();");
        }
        if(value.equals("DLeft")){
            return("mDriveTrain.driveLeft();");
        }
        if(value.equals("RB")){
            return("pinchy.out()");
        }
        if(value.equals("LB")){
            return("pinchy.in()");
        }
        if(value.equals("Start")){
            return("temp autolevel start");
        }

        //returns if the input value hasn't been set up on controler
        return ("invalid input");
    }
}