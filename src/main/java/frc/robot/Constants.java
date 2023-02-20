/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class CheesyDrive {
        public static final double X_AXIS_DEADZONE_RANGE = 0.005;
        public static final double Y_AXIS_DEADZONE_RANGE = 0.005;
        public static final double X_AXIS_DEADZONE_Y_MIN = 0.1;
    }

    public final class DriveTrain {
        public static final int FRONT_RIGHT_MOTOR_ID = 0;
        public static final int FRONT_LEFT_MOTOR_ID = 2;
        public static final int REAR_RIGHT_MOTOR_ID = 1;
        public static final int REAR_LEFT_MOTOR_ID = 3;
        public static final int WHEEL_Diameter_INCHES = 6;
        public static final double COUNTS_PER_ROTAION = 4096;
        public static final double X_AXIS_DEADZONE_RANGE = 0.005;
        public static final double Y_AXIS_DEADZONE_RANGE = 0.005;
        public static final double X_AXIS_DEADZONE_Y_MIN = 0.1; 

        public static final double ROTATION_DEADBAND = .2;
        public static final double STRAFING_DEADBAND = .2;
        public static final double SPEED_DEADBAND = .2;
        
        public final class ControlSystems{
            public static final int ARCADE_DRIVE_STANDARD = 0;
            public static final int TANK_DRIVE_TEST = 1;
            public static final int TANK_DRIVE_WITH_VOLTS = 2;
        }
        public final class kinematics{
            public static final double S = 0.85586; // static voltage constant ks THIS VALUE IS FOR 2022 Robot
            public static final double V = 3.0598; // velocity voltage constant kv THIS VALUE IS FOR 2022 Robot
            public static final double A = 0.86192; // acceloration voltage constant ka THIS VALUE IS FOR 2022 Robot
            public static final double P = 2.5115; // Proportionality gain for the drive train THIS VALUE IS FOR 2022 Robot
            public static final double I = 0; // Interal gain for the drive train 
            public static final double D = 0; // Derivative gain for the drive train 
        }
    }
    public final class Joystick{
        public static final int X_AXIS = 0;
        public static final int Y_AXIS = 1;
        public static final int Z_AXIS = 2;
        public static final int SCALE_AXIS = 3;
    }
    public final class Arm{
        public static final int INTAKE_MOTOR_ID = 8;
        public static final int ARM_MOTOR_ID = 5;
        public static final double ARM_DEAD_ZONE = 0.01;
    }
    public static class Climber{
        public static final int CLIMBER_MOTOR_ID = 4;
        public static final int CLIMBER_SERVO_ID = 4;
        public static final int PIVOT_MOTOR_ID = 6;
    }

    public static class Crane{
        public static final int CRANE_PIVOT_MOTOR_ID = 5;
        public static final int CRANE_CLIMBER_MOTOR_ID = 6;
    }

    public static class Auto{
        public static final double DIST_DRIVE_ACCEPTED_ERROR = 2000;
        public static final double TURN_ACCEPTED_ERROR = 1000;
    }

    public static class Gryo{
        public static final int PIDGEY_MOTOR_ID = 4;
    }

}
