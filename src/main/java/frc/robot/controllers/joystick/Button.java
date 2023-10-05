package frc.robot.controllers.joystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public class Button extends edu.wpi.first.wpilibj2.command.button.Trigger {
    private final JoystickButton button;
    public Button(Joystick joystick, int port) {
        super();
        this.button = new JoystickButton(joystick, port);
    }
    public void toggleWhenPressed(DriveWithJoystick driveWithJoystick) {
    }

}
