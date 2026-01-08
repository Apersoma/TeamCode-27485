package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class CRServoActions {
    final private CRServo servo;

    public CRServoActions(CRServo servo) {
        this.servo = servo;
    }

    public class SpinServo implements Action {
        private double power = 0d;
        private double spinTime = 0d;
        private boolean firstRun = true;

        private final ElapsedTime timer;

        public SpinServo (double power, double spinTime) {//spin time in millis
            this.power = power;
            this.spinTime = spinTime;
            timer = new ElapsedTime();
        }

        public SpinServo (double power) {//spin time in millis
            this.power = power;
            this.spinTime = -1;
            timer = new ElapsedTime();
        }


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(spinTime == -1) {
                servo.setPower(power);
                return false;
            }

            if(firstRun) {
                firstRun = false;
                timer.reset();
            }

            if(timer.milliseconds() < spinTime) {
                servo.setPower(power);
                return true;
            } else {
                servo.setPower(0);
                return false;
            }
        }
    }

    public SpinServo spinServo(double power, double spinTime) {//spin time in millis
        return new SpinServo(power, spinTime);
    }

    public SpinServo spinServo(double power) {//spin time in millis
        return new SpinServo(power);
    }
}
