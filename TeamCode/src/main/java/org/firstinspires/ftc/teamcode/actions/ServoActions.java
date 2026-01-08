package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoActions {
    final private Servo servo;

    public ServoActions(Servo servo) {
        this.servo = servo;
    }

    public class SetServoPos implements Action {
        private double position = 0d;

        public SetServoPos(double position) {
            this.position = position;
        }


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.setPosition(position);
            return false;
        }
    }

    public SetServoPos setServoPos(double position) {
        return new SetServoPos(position);
    }
}
