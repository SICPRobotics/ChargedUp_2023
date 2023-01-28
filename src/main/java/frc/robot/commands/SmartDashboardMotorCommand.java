package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.MotorSubsystem;

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
