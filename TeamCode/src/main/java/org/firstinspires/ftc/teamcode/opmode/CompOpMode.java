package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.pipeline.HardwarePipeline;
import org.firstinspires.ftc.teamcode.pipeline.TelemetryPipeline;
import org.firstinspires.ftc.teamcode.utility.Supervisor;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@TeleOp(name = "Competition TeleOp")
public class CompOpMode extends OpMode{
    protected TelemetryPipeline telemetryPipeline;
    protected HardwarePipeline drive;
    protected final Supervisor supervisor = new Supervisor();

    ElapsedTime timer;

    GamepadEx gamepadEx1, gamepadEx2;
    CRServoImplEx fwl, fwr, intake, kicker;

    DcMotorEx flyWheel;
    VoltageSensor controlHub;

    @Override
    public void init() {
        telemetryPipeline = new TelemetryPipeline(telemetry);
        drive = new HardwarePipeline(hardwareMap);

        timer = new ElapsedTime();

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        intake = hardwareMap.get(CRServoImplEx.class, "intake");
        fwr = hardwareMap.get(CRServoImplEx.class, "fwr");
        fwl = hardwareMap.get(CRServoImplEx.class, "fwl");
        kicker = hardwareMap.get(CRServoImplEx.class, "kicker");

        flyWheel = hardwareMap.get(DcMotorEx.class, "flyWheel");
        controlHub = hardwareMap.voltageSensor.get("Control Hub");

        flyWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    boolean r2s, r2e, r2r, xs, xe, xr;
    double fwP = 0.45, flyWheelMinPower = 8875, flyWheelMaxPower = 9125, flyWheelPower = 0, flyWheelAvgPower = -1;

    @Override
    public void loop() {
        r2r = gamepad1.right_bumper;
        xr = gamepad1.x;

        if(r2r && !r2s) r2e = !r2e;
        if(xr && !xs) xe = !xe;

        flyWheel.setPower(r2e ? fwP : 0);
        fwr.setPower(gamepad1.left_bumper ? -1 : 0);
        fwl.setPower(gamepad1.left_bumper ? 1 : 0);
        intake.setPower(xe ? 1 : 0);

        kicker.setPower(gamepad1.b ? -1 : gamepad1.y ? 1 : 0);

        double turnSpeed = gamepad1.right_trigger - gamepad1.left_trigger;

        double strafeSpeed = gamepadEx1.getLeftX();
        double forwardSpeed = gamepadEx1.getLeftY();

        drive.driveRobotCentric(0.5 * -strafeSpeed, 0.5* -forwardSpeed, 0.5 * -turnSpeed);

        supervisor.run(telemetryPipeline);

        flyWheelPower = flyWheel.getCurrent(CurrentUnit.MILLIAMPS) * controlHub.getVoltage();

        if(r2e) {
            if(flyWheelAvgPower == -1) {
                flyWheelAvgPower = flyWheelPower;
            } else {
                flyWheelAvgPower += flyWheelPower;
                flyWheelAvgPower /= 2d;
            }
        }

        telemetryPipeline.addDataPoint("forward Speed", forwardSpeed);
        telemetryPipeline.addDataPoint("turn Speed", turnSpeed);
        telemetryPipeline.addDataPoint("strafe Speed", strafeSpeed);
        telemetryPipeline.addDataPoint("FlyWheel Power (mW)", flyWheelPower);
        telemetryPipeline.addDataPoint("FlyWheel Average Power (mW)", flyWheelAvgPower);
        telemetryPipeline.addDataPoint("FlyWheel Power", fwP);
        telemetryPipeline.refresh();
//
//        if(r2e && flyWheelAvgPower < flyWheelMinPower) fwP += 0.01;
//        if(r2e && flyWheelAvgPower > flyWheelMaxPower) fwP -= 0.01;

        r2s = r2r;
        xs = xr;
    }
}