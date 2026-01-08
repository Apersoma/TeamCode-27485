package org.firstinspires.ftc.teamcode.hardware;

public class DcMotorConstants {
    public final static double ENCODER_RESOLUTION = 537.7;
    public final static double MOTOR_RPM = 312d;
    public final static double MOTOR_CURRENT = 250d;
    public final static double STALL_TORQUE = 2.38; // Nm
    public final static double WHEEL_DIAMETER = 0.104; // Meter
    public final static double MAX_VELOCITY = ENCODER_RESOLUTION * MOTOR_RPM / 60;
    public final static double TICKS_TO_METER = WHEEL_DIAMETER / ENCODER_RESOLUTION;
}
