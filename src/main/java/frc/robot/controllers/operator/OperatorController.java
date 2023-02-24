package frc.robot.controllers.operator;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.SubsystemBaseWrapper;

import java.util.concurrent.ScheduledExecutorService;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class OperatorController extends SubsystemBaseWrapper {
    public final Buttons buttons;
    public final Sticks sticks;
    public final Triggers triggers;

    private final XboxController controller;

    public OperatorController(int port) {
        controller = new XboxController(port);
        
        buttons = new Buttons(controller);
        sticks = new Sticks(controller);
        triggers = new Triggers(controller);
    }

    public void rumbleRight(double value) {
        controller.setRumble(RumbleType.kRightRumble, value);
    }

    public void rumbleLeft(double value) {
        controller.setRumble(RumbleType.kLeftRumble, value);
    }

    public void endRumble() {
        rumbleLeft(0);
        rumbleRight(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("LX", sticks.left.getX());
        SmartDashboard.putNumber("LY", sticks.left.getY());
        SmartDashboard.putNumber("RX", sticks.right.getX());
        SmartDashboard.putNumber("RY", sticks.right.getY());
    }

}