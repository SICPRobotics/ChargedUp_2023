package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CargoArm;

public class SimpleArmCommand extends CommandBase {
    private final CargoArm arm;
    private final double amount;
    private final Timer timer;
    public SimpleArmCommand(CargoArm arm, double amount) {
        this.arm = arm;
        addRequirements(arm);
        this.amount = amount;
        this.timer = new Timer();
    }
    public void initialize () {
        timer.reset();
        timer.start();
        arm.setMotor(amount);
    }
    @Override 
    public void end(boolean b){
        arm.turnOff();
    }
    @Override
    public boolean isFinished(){
        return timer.get() > 5;
    }
}
