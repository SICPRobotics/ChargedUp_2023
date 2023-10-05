package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.commands.components.arm.DownArmCommand;
import frc.robot.commands.components.arm.UpArmCommand;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.TurnRelative;
import frc.robot.commands.drive.TurnTo;

import static frc.robot.RobotContainer.*;

public class CustomAuto extends CommandBase {
    private List<Command> commands = new ArrayList<Command>();
    private final Timer timer = new Timer();
    private RobotContainer robot;
    private int index = 0;

    public CustomAuto(RobotContainer container) {
        this.robot = container;
        //addRequirements(robot.driveTrain, robot.cargoArm, robot.cargoIntake);
    }

    @Override
    public void initialize() {
        System.out.println("Initializing autonomous.");
        robot.pidgey.resetRobotHeading();
        timer.reset();
        timer.start();

        // Fetch and parse auto code on command start.
        NetworkTable autoTable = NetworkTableInstance.getDefault().getTable("Wolfbyte");
        String autoString = autoTable.getEntry("auto").getString("{instructions:[]}");
        System.out.println(autoString);
        JsonElement auto = gson.fromJson(autoString, JsonObject.class);
        JsonArray instructions = auto.getAsJsonObject().get("instructions").getAsJsonArray();
        
        for (int i = 0; i < instructions.size(); i++) {
            commands.add(parseCustomAutoStep(instructions.get(i).getAsJsonObject()));
        }

        index = 0;
        System.out.println("Added " + commands.size() + " commands.");
        if (commands.size() > 0) {
            System.out.println("Initialized first command.");
            commands.get(0).initialize();
        }
    }

    private Command parseCustomAutoStep(JsonObject instruction) {
        // Parse args:
        double _duration = 1;
        if (instruction.has("duration")) {
            _duration = instruction.get("duration").getAsDouble();
        }
        final double duration = _duration;

        double _speed = 0;
        if (instruction.has("speed")) {
            _speed = instruction.get("speed").getAsDouble();
        }
        final double speed = _speed;

        double _distance = 0;
        if (instruction.has("distance")) {
            _distance = instruction.get("distance").getAsDouble();
        }
        final double distance = _distance;

        double _rotation = 0;
        if(instruction.has("rotation")){
            _rotation = instruction.get("rotation").getAsDouble();
        }
        final double rotation = _rotation;
        
        String type = instruction.get("type").getAsString();
        Runnable start = () -> {};
        Runnable execute = () -> {};
        Runnable end = () -> {};
        BooleanSupplier isFinished = () -> this.timer.get() > duration;

        /* 
        if (type.equals("diffDrive")) {
            double left = instruction.get("left").getAsDouble();
            double right = instruction.get("right").getAsDouble();
            execute = () -> robot.driveTrain.diffDrive(left, right);
        } else if (type.equals("driveDistance")) {
            return new DriveDistance(robot.driveTrain, distance, speed);
        } else if (type.equals("driveDuration")) {
            execute = () -> robot.driveTrain.cheesyDrive(speed, 0);
        } else if (type.equals("wait")) {
            // Do nothing
        } else if (type.equals("relativeTurn")) {
            return new TurnRelative(robot.driveTrain, robot.pidgey, rotation);
        } else if (type.equals("turnToHeading")) {
            double heading = instruction.get("heading").getAsDouble();
            return new TurnTo(robot.driveTrain, robot.pidgey, heading);
        } else if (type.equals("armUp")) {
            return new UpArmCommand(robot.cargoArm, robot.pidgey);
        } else if (type.equals("armDown")) {
            return new DownArmCommand(robot.cargoArm, robot.pidgey);
        } else if (type.equals("intake")) {
            String direction = instruction.get("direction").getAsString();
            if (direction.equals("in")) {
                execute = () -> robot.cargoIntake.intakeIn();
            } else if (direction.equals("out")) {
                execute = () -> robot.cargoIntake.intakeOut();
            } else if (direction.equals("off")) {
                execute = () -> robot.cargoIntake.intakeOff();
            } else {
                System.out.println("Unknown direction " + direction);
            }
        }
        */


        final var exec = execute;

        return new FunctionalCommand(start, () -> {
            System.out.println("Here!");
            exec.run();
        }, b -> end.run(), isFinished);
    }

    @Override
    public void execute() {
        if (isFinished()) {
            return;
        }

        Command current = commands.get(index);

        if (current.isFinished()) {
            System.out.println("Next auto step");
            timer.reset();
            current.end(false);
            index++;
            if (index < commands.size()) {
                commands.get(index).initialize();
            }
        } else {
            System.out.println("Ran step " + index);
            current.execute();
        }
    }

    @Override
    public boolean isFinished() {
        return index >= commands.size();
    }
}