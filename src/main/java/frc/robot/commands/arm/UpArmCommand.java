package frc.robot.commands.arm;

import frc.robot.subsystems.CargoArm;
import frc.robot.subsystems.Pidgey;

public class UpArmCommand extends SmartArmCommand {

    public UpArmCommand(CargoArm arm, Pidgey pidgey) {
        super(arm, pidgey, 60, 60);
    }

    @Override
    public double getOutput() {
        if (error > 0.8) {
            return 1;
        } else if (error > 0.6) {
            return 0.8;
        } else {
            return 0.4;
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.arm.setMotor(0.3);
    }
    
}
