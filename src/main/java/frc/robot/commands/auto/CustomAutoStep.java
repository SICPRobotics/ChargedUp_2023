package frc.robot.commands.auto;

import java.util.function.DoubleConsumer;
import java.util.function.Function;

public class CustomAutoStep {
    public final double startTime;
    public final Function<Double, Boolean> isFinished;
    public final Runnable start;
    public final DoubleConsumer execute;
    public final Runnable end;

    public CustomAutoStep(double startTime, Runnable start, DoubleConsumer execute, Function<Double, Boolean> isFinished, Runnable end) {
        this.startTime = startTime;
        this.isFinished = isFinished;
        this.start = start;
        this.execute = execute;
        this.end = end;
    }
}
