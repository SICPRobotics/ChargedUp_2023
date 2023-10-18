package frc.robot.robotutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    Map<String, Boolean> motorPower = new HashMap<>();
    Map<String, Boolean> motorCan = new HashMap<>();

    public void StatusCheck(Map<String, Integer> motorIDList){
        this.motorIDList = Constants.getComponentIDList();

    }


    void update(){
        checkPower();
        checkCan();
    }

    void checkPower(){
        for (int i = 0; i < motorIDList.size(); i ++){
            SmartDashboard.putBoolean("test 1", false);
        }
    }
    void checkCan(){
        
    }
}

//       autoDelay = new SmartDashBoardClass<Double>("autoDelay", 0.0);
