package frc.robot.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;

public class Logger {
    private XboxController xboxController;
    private boolean pressing;
    private long start;
    private long pressstart;
    private List<String> Inputs;
    private List<Long> TimeOInput;
    private List<Long> InputDurations;
    private Map<String, Long> startTimes = new HashMap<>();
    
    public Logger(XboxController xboxController) {
        this.xboxController = xboxController;
        this.pressing = false;
        this.start = System.nanoTime();
        this.Inputs = new ArrayList<String>();
        this.TimeOInput = new ArrayList<Long>();
        this.InputDurations = new ArrayList<Long>();
    }
    
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
      }
      
    private void checkbutton(String buttonName) {
        boolean currentPress = false;
        long currentTime = System.nanoTime();
      
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


        if (currentPress != pressing) {
            if (currentPress) {
                Inputs.add(buttonName);
                TimeOInput.add(currentTime - start);
                startTimes.put(buttonName, currentTime);
                pressing = true;
            } 
            else {
                Long pressStart = startTimes.get(buttonName);
                if (pressStart != null) {
                    InputDurations.add(currentTime - pressStart);
                    startTimes.remove(buttonName);
                }
                pressing = false;
            }
          }
      }
    
    public List<String> getInputs() {
        return Inputs;
    }
    
    public List<Long> getTimeOInput() {
        return TimeOInput;
    }
    
    public List<Long> getInputDurations() {
        return InputDurations;
    }
  }