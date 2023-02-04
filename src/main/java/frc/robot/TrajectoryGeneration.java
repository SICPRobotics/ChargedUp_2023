package frc.robot;

import java.io.ObjectInputFilter.Config;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.geometry.Translation2d;

public class TrajectoryGeneration extends SubsystemBase{

    private GsonSaver gsonSaver;

    public TrajectoryGeneration(){
        super();
    }
    @Override
    public void periodic() {
       for(String name : this.gsonSaver.getNames()){
            SmartDashboard.putString(name,":" +name + ":" +  this.getTrajectory(name).toString());
        }
    }
    // public TrajectoryConfig generateConfig(){
    //     Trajectory
    // }
    public void generate(Pose2d start, List<Translation2d> waypoints, Pose2d end, TrajectoryConfig config, String name){
        gsonSaver.saveObject(TrajectoryGenerator.generateTrajectory(start, waypoints, end, config), name);
    }
    public void addGson(GsonSaver gsonSaver){
        this.gsonSaver = gsonSaver;
    }
    public Trajectory getTrajectory(String name){
        return gsonSaver.getObject(Trajectory.class, name);
    } 
    public void printTrajectory(String trajectoryName){
        System.out.println(trajectoryName);
        System.out.println(this.getTrajectory(trajectoryName).toString());
    }
    public Command getTrajectoryCommand(DriveTrain driveTrain, String trajectoryName){
        System.out.println(trajectoryName);
        System.out.println(this.getTrajectory(trajectoryName).getStates());
        return new RamseteCommand(this.getTrajectory(trajectoryName), 
            driveTrain::getPose,
             new RamseteController(),
              new SimpleMotorFeedforward(Constants.DriveTrain.kinematics.S, Constants.DriveTrain.kinematics.V, Constants.DriveTrain.kinematics.A),
               driveTrain.kinematics,
                driveTrain::getWheelSpeeds,
                 new PIDController(Constants.DriveTrain.kinematics.P, Constants.DriveTrain.kinematics.I, Constants.DriveTrain.kinematics.D),
                  new PIDController(Constants.DriveTrain.kinematics.P, Constants.DriveTrain.kinematics.I, Constants.DriveTrain.kinematics.D),
                   driveTrain::voltDrive,
                    driveTrain);
    }

}


