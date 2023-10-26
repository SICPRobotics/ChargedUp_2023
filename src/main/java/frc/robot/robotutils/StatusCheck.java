package frc.robot.robotutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SmartDashBoardClass;
import frc.robot.Constants;

/*
 * display status of motors and CAN signal from here
 */

public class StatusCheck {
    // add a name for each motor in constatnts
    Map<String, Integer> motorIDList = new HashMap<>();
    ArrayList<String> keyList = new ArrayList<String>();
    ArrayList<Integer> idList = new ArrayList<Integer>();
    ArrayList<SmartDashBoardClass<Boolean>> smartDashBoardClasses = new ArrayList<SmartDashBoardClass<Boolean>>();

    public void StatusCheck(){
    }

    public void getConstants(){
        motorIDList.putAll(Constants.getComponentIDList());
        keyList.addAll(motorIDList.keySet());
        idList.addAll(motorIDList.values());
    }

    public void update(){
        checkPowerList();
        checkCanList();
    }

    public void checkPowerList(){
        for (int i = 0; i < keyList.size(); i ++){
            if(checkPower() == true){
                SmartDashboard.putBoolean(keyList.get(i) + " ID:" + motorIDList.get(keyList.get(i)), true);
            }
            else{
                SmartDashboard.putBoolean(keyList.get(i) + " ID:" + motorIDList.get(keyList.get(i)), false);
            }
        }
    }

    public boolean checkPower(){
        //find a way to tell if motors are on
        return true;
    }

    public void checkCanList(){
        
    }

    public boolean checkCan(){
        //check to see if can frames are being received
        return true;
    }
}

//       autoDelay = new SmartDashBoardClass<Double>("autoDelay", 0.0);
