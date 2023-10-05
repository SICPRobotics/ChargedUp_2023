package frc.robot.commands.motorsetting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.basecommands.MotorCommand;
import frc.robot.subsystems.basesubsytems.MotorSubsystem;

public class SmartDashboardMotorCommand extends MotorCommand{

    private final double defaultVelocity;

    public SmartDashboardMotorCommand(MotorSubsystem motorSubsystem, String key, double defaultVelocity) {
        super(motorSubsystem, SmartDashboard.getNumber(key, defaultVelocity));
        this.defaultVelocity = defaultVelocity;
    }
    public double getDefaultVelocity() {
        return defaultVelocity;
    }
    
}
