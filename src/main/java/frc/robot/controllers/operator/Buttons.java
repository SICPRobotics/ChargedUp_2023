package frc.robot.controllers.operator;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Buttons {
    public final Trigger A;         
    public final Trigger B;         
    public final Trigger X;         
    public final Trigger Y;         
    public final Trigger LB; 
    public final Trigger RB;
    public final Trigger back;
    public final Trigger start;
    public final Trigger LS;
    public final Trigger RS;
    public final DPad dPad;
    
    public Buttons(GenericHID controller) {
        A               = new JoystickButton(controller, 1); 
        B               = new JoystickButton(controller, 2); 
        X               = new JoystickButton(controller, 3);
        Y               = new JoystickButton(controller, 4); 
        LB              = new JoystickButton(controller, 5); 
        RB              = new JoystickButton(controller, 6); 
        back            = new JoystickButton(controller, 7); 
        start           = new JoystickButton(controller, 8);
        LS              = new JoystickButton(controller, 9); 
        RS              = new JoystickButton(controller, 10);
        
        dPad = new DPad(controller);
    }
}