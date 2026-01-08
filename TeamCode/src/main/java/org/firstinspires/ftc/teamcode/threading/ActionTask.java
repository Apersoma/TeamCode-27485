package org.firstinspires.ftc.teamcode.threading;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

/**
 * Wraps a RoadRunner Action into the custom Task interface.
 */
public class ActionTask implements Task {
    private final String name;
    private final int id;
    private final Action rrAction;
    private final TelemetryPacket packet;

    public ActionTask(int id, String name, Action rrAction) {
        this.id = id;
        this.name = name;
        this.rrAction = rrAction;
        this.packet = new TelemetryPacket(); // Reused for RR dashboard updates
    }

    @Override
    public Signal execute() {
        // RR Action returns true if it needs to run again, false if finished.
        // We map true -> REPEAT and false -> CONTINUE (move to next task).
        boolean running = rrAction.run(packet);

        return running ? Signal.REPEAT : Signal.CONTINUE;
    }

    @Override public String getName() { return name; }
    @Override public int getId() { return id; }
}