package org.firstinspires.ftc.teamcode.utility;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class ActionWrapper {
    public static Task toTask(Action action, String name) {
        TelemetryPacket packet = new TelemetryPacket();

        return new Task() {
            @Override
            public Status run() {
                boolean status = action.run(packet);
                return status ? Status.RUNNING : Status.SUCCESS; // assume success
                                                                // so we don't attempt restart
            }
            final String identifier = name;
        };
    }

    public static Task toPerpetualTask(Action action, String name) {
        TelemetryPacket packet = new TelemetryPacket();

        return new Task() {
            @Override
            public Status run() {
                action.run(packet);
                return Status.RUNNING; // assume success
                // so we don't attempt restart
            }
            final String identifier = name;
        };
    }

    public static Task toTask(Action action, String name, Task telemetryTask) {
        TelemetryPacket packet = new TelemetryPacket();

        return new Task() {
            @Override
            public Status run() {
                boolean actionStatus = action.run(packet);
                telemetryTask.run();
                return actionStatus ? Status.RUNNING : Status.SUCCESS; // assume success
                // so we don't attempt restart
            }
            final String identifier = name;
        };
    }
    public static Task toPerpetualTask(Action action, String name, Task telemetryTask) {
        TelemetryPacket packet = new TelemetryPacket();

        return new Task() {
            @Override
            public Status run() {
                action.run(packet);
                telemetryTask.run();
                return Status.RUNNING; // assume success
                // so we don't attempt restart
            }
            final String identifier = name;
        };
    }
}
