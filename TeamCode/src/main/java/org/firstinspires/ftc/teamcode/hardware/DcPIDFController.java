package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.ArrayList;

public class DcPIDFController {
    private DcMotorEx dcMotor = null;
    private ElapsedTime timer = null;

    private ArrayList<ArrayList<Double>> positionData = new ArrayList<>();

    private double position = 0d;
    private double velocity = 0d;
    private double acceleration = 0d;

    public DcPIDFController(DcMotorEx motor) {
        this.dcMotor = motor;
        this.timer = new ElapsedTime();
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void start() {
        timer.reset();
    }

    public void loop() {
        dcMotor.getCurrent(CurrentUnit.AMPS); // speed is the duty cycle
    }
}
