package frc.robot.controllers.operator;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.controllers.operator.buttons.DPadButton;
import frc.robot.controllers.operator.buttons.DPadButton.Direction;

public class DPad {
    public final Trigger up;
    public final Trigger right;
    public final Trigger down;
    public final Trigger left;
    public DPad(GenericHID controller) {
        up = new DPadButton(controller, Direction.UP);
        right = new DPadButton(controller, Direction.RIGHT);
        down = new DPadButton(controller, Direction.DOWN);
        left = new DPadButton(controller, Direction.LEFT);
    }
}