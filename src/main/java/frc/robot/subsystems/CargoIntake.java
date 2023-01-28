package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CargoIntake extends SubsystemBase implements MotorSubsystem{

    WPI_TalonSRX intakeMotor;
    public CargoIntake(){
        this.intakeMotor = new WPI_TalonSRX(Constants.Arm.INTAKE_MOTOR_ID);
    }
    public void setMotor(double velocity, boolean force){
        this.intakeMotor.set(velocity);
    }
    public void turnOff(){
        this.intakeMotor.set(0);
    }
    public void intakeIn() {
        System.out.println("RAN INTAKE IN");
        setMotor(-0.8);
    }

    public void intakeOut() {
        setMotor(1);
        System.out.println("RAN INTAKE OUT");
    }

    public void intakeOff() {
        setMotor(0);
        System.out.println("RAN INTAKE OFF");
    }
}
