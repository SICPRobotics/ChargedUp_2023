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
        return this.joystick.getRawAxis(Constants.Joystick.X_AXIS);
    }

    public double getY() {
        return this.joystick.getRawAxis(Constants.Joystick.Y_AXIS);
    }

    public double getZ() {
        return this.joystick.getRawAxis(2);
    }

    public double getScale() {
        return ((-this.joystick.getRawAxis(Constants.Joystick.SCALE_AXIS) + 1) / 2);
    }
}
