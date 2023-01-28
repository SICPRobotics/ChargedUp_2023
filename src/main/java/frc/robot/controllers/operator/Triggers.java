package frc.robot.controllers.operator;

import edu.wpi.first.wpilibj.XboxController;

public class Triggers extends Mirrored<Trigger> {
    public Triggers(XboxController controller) {
        left = new Trigger(() -> controller.getLeftTriggerAxis());
        right = new Trigger(() -> controller.getRightTriggerAxis());
    }
}