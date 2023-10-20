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

    public void StatusCheck(Map<String, Integer> motorIDStorage){
        Map<String, Integer> motorIDList = new HashMap<>();
        motorIDList = Constants.getComponentIDList();

        ArrayList<String> keyList = new ArrayList<String>();
        ArrayList<Integer> idList = new ArrayList<Integer>();

        keyList.addAll(motorIDList.keySet());
        idList.addAll(motorIDList.values());

        update();
    }


    void update(){
        checkPower();
        checkCan();
    }

    void checkPowerList(ArrayList keyList, ArrayList idList){
        for (int i = 0; i < keyList.size(); i ++){
            if(checkPower() == false){
                SmartDashboard.putBoolean((String) keyList.get(i) , (Boolean) idList.get(i));
            }
        }
    }

    boolean checkPower(){
        //find a way to check power here
        return false;
    }

    void checkCanList(){
        
    }

    void checkCan(){
        
    }
}

//       autoDelay = new SmartDashBoardClass<Double>("autoDelay", 0.0);
