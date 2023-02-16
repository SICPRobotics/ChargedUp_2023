package frc.robot.controllers.joystick;

import frc.robot.Constants;

/**
 * Custom wrapper class over WPI joystick
 */
public class Joystick {
    private final edu.wpi.first.wpilibj.Joystick joystick;
    private final Button[] buttons = new Button[13];
    public final Button trigger;
    public final Button thumb;
    public Joystick(int port) {
        this.joystick = new edu.wpi.first.wpilibj.Joystick(port);
        trigger = button(1);
        thumb = button(2);
    }

    public Button button(int port) {
        if (buttons[port] != null) {
            return buttons[port];
        } else {
            buttons[port] = new Button(joystick, port);
            return buttons[port];
        }
    }

    public double getX() {
        if(this.joystick.getRawAxis(Constants.Joystick.X_AXIS) < 0.1 && this.joystick.getRawAxis(Constants.Joystick.X_AXIS) > -0.1){
            return 0;
        }
        return this.joystick.getRawAxis(Constants.Joystick.X_AXIS);
        
    }

    public double getY() {
        if(this.joystick.getRawAxis(Constants.Joystick.Y_AXIS) < 0.1 && this.joystick.getRawAxis(Constants.Joystick.Y_AXIS) > -0.1){
            return 0;
        }
        return this.joystick.getRawAxis(Constants.Joystick.Y_AXIS);
        
    }
    
    public double getZ() {
        if(this.joystick.getRawAxis(Constants.Joystick.Z_AXIS) < 0.1 && this.joystick.getRawAxis(Constants.Joystick.Z_AXIS) > -0.1){
            return 0;
        }
        return this.joystick.getRawAxis(2);
        
    }

    //we created a range for the joystick 

    public double getScale() {
        return ((-this.joystick.getRawAxis(Constants.Joystick.SCALE_AXIS) + 1) / 2);
    }

}
