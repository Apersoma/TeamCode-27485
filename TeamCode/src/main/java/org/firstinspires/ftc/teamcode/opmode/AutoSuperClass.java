package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.pipeline.HardwarePipeline;
import org.firstinspires.ftc.teamcode.pipeline.TelemetryPipeline;
import org.firstinspires.ftc.teamcode.utility.HardwareConstants;
import org.firstinspires.ftc.teamcode.utility.Supervisor;

public abstract class AutoSuperClass extends LinearOpMode {

    protected TelemetryPipeline telemetryPipeline;
    protected HardwarePipeline drive;
    protected final Supervisor supervisor = new Supervisor();

    ElapsedTime timer;

    DcMotorEx cocker;
    VoltageSensor controlHub;

    public void loadAndShoot() {
        
    }

    public void initialize(boolean startParallelTelemetry) {
        telemetryPipeline = new TelemetryPipeline(telemetry);
        drive = new HardwarePipeline(hardwareMap);

        timer = new ElapsedTime();

        cocker = hardwareMap.get(DcMotorEx.class, "cocker");
        cocker.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        cocker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        controlHub = hardwareMap.voltageSensor.get("Control Hub");

        floor.setPosition(0);

        if (startParallelTelemetry) {
            this.startParallelTelemetry();
        }
    }

    public void shoot1() {
        cocker.setTargetPosition(HardwareConstants.COCKER_POS_A);
    }

    public void leak() {
        //just in case
        kicker.setPosition(0);

        // ball 1
        intake.setPower(-1);
        sleep(2000);

        // ball 2
        this.setMiniFlyWheels(-1);
        sleep(2000);

        // ball 3
        floor.setPosition(HardwareConstants.FLOOR_POS);
        sleep(2000);

        // reset
        intake.setPower(0);
        this.setMiniFlyWheels(0);
        floor.setPosition(0);
    }

    /// angle is the angle to the line extending out the front of the robot
    public void move(double speed, double angleRad, long timeMillis) {
        double forwardSpeed = Math.cos(angleRad) * speed;
        double strafeSpeed = Math.sin(angleRad) * speed;
        drive.driveRobotCentric(-strafeSpeed, -forwardSpeed, 0.1);
        sleep(timeMillis);
        drive.driveRobotCentric(0,0,0);
    }

    public void startParallelTelemetry() {
        LinearOpMode auto = this;
        Thread telemetryThread = new Thread() {
            @Override
            public void run(){
                while (!auto.isStopRequested()) {
                    telemetryPipeline.addDataPointPerpetual("real cocker pos", cocker.getCurrentPosition());
                    telemetryPipeline.addDataPointPerpetual("real cocker target", cocker.getTargetPosition());
                    telemetryPipeline.addDataPointPerpetual("set cocker target", HardwareConstants.COCKER_POS_A);
                    telemetryPipeline.refresh();
                }
            }
        };
        telemetryThread.start();
    }
}