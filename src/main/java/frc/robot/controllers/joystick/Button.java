package frc.robot.controllers.joystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.MotorSubsystem;

public class Button extends edu.wpi.first.wpilibj2.command.button.Button {
    private final JoystickButton button;
    public Button(Joystick joystick, int port) {
        super();
        this.button = new JoystickButton(joystick, port);
    }


}
