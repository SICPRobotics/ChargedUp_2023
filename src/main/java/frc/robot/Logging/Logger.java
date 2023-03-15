package frc.robot.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Logger {
    private XboxController xboxController;
    private boolean pressing;
    private Float start;
    private Float pressstart;
    private List<String> Inputs;
    private List<Float> TimeOInput;
    private List<Float> InputDurations;
    public float currentTime;
    public Timer timer = new Timer();
    private Map<String, Float> startTimes = new HashMap<>();
    
    public Logger(XboxController xboxController) {
        this.xboxController = xboxController;
        this.pressing = false;
        this.start = (float) (System.nanoTime());
        this.Inputs = new ArrayList<String>();
        this.TimeOInput = new ArrayList<Float>();
        this.InputDurations = new ArrayList<Float>();
    }

    //called constanlty to check for button inputs
    public void CheckInputs() {
        checkbutton("A");
        checkbutton("B");
        checkbutton("X");
        checkbutton("Y");
        checkbutton("DUp");
        checkbutton("DRight");
        checkbutton("DLeft");
        checkbutton("DDown");
        checkbutton("LT");
        checkbutton("LB");
        checkbutton("RT");
        checkbutton("RB");
        checkbutton("DUp");
        checkbutton("DRight");
        checkbutton("DLeft");
        checkbutton("DDown");
        checkbutton("Start");
      }
      
    private void checkbutton(String buttonName) {
        boolean currentPress = false;
        currentTime = (float) System.nanoTime();
      
        if (buttonName.equals("A")) {
            currentPress = xboxController.getAButton();
        } 
        else if (buttonName.equals("B")) {
            currentPress = xboxController.getBButton();
        } 
        else if (buttonName.equals("X")) {
            currentPress = xboxController.getXButton();
        } 
        else if (buttonName.equals("Y")) {
            currentPress = xboxController.getYButton();
        }
        else if (buttonName.equals("LT")) {
            currentPress = xboxController.getLeftTriggerAxis() > .3;
        }
        else if (buttonName.equals("LB")) {
            currentPress = xboxController.getLeftBumper();
        }
        else if (buttonName.equals("RT")) {
            currentPress = xboxController.getRightTriggerAxis() > .3;
        }
        else if (buttonName.equals("RB")) {
            currentPress = xboxController.getRightBumper();
        }
        else if (buttonName.equals("RB")) {
            currentPress = xboxController.getRightBumper();
        }
        else if (buttonName.equals("DUp")) {
            currentPress = xboxController.getPOV() == 0;
        }
        else if (buttonName.equals("DRight")) {
            currentPress = xboxController.getPOV() == 90;
        }
        else if (buttonName.equals("DDown")) {
            currentPress = xboxController.getPOV() == 180;
        }
        else if (buttonName.equals("DLeft")) {
            currentPress = xboxController.getPOV() == 270;
        }
        else if (buttonName.equals("Start")) {
            currentPress = xboxController.getStartButton();
        }

        //beutiful logic checks if button if being press/held logs which button is pressed when it is pressed and for how long
        if (currentPress != pressing) {
            if (currentPress) {
                Inputs.add(buttonName);
                TimeOInput.add((currentTime - start)/1000000000);
                startTimes.put(buttonName, currentTime);
                pressing = true;
            } 
            else {
                Float pressStart = startTimes.get(buttonName);
                if (pressStart != null) {
                    InputDurations.add((currentTime - pressStart)/1000000000);
                    startTimes.remove(buttonName);
                    pressing = false;
                }
            }
          }
      }
    
    //can be called to access logs
    public List<String> getInputs() {
        return Inputs;
    }
    
    public List<Float> getTimeOInput() {
        return TimeOInput;
    }
    
    public List<Float> getInputDurations() {
        return InputDurations;
    }

    //called after creating the auto will clear logs to be used again
    public void emptyLog(){
        Inputs.removeAll(Inputs);
        TimeOInput.removeAll(TimeOInput);
        InputDurations.removeAll(InputDurations);
        start = (float) System.nanoTime();
    }
  }