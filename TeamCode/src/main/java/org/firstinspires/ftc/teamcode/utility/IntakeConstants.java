package org.firstinspires.ftc.teamcode.utility;

import com.acmerobotics.dashboard.config.Config;

@Config
public class IntakeConstants {
    public static double INTAKE_OUT = 0d;
    public static double INTAKE_IN = .1065;
    public static double SWIVEL_OUT = .185; // connected directly to linear actuator
    public static double SWIVEL_IN = .3;
    public static double STAGE2_SPEC = .053; // spec
    public static double STAGE2_SAMP = .005; // sample
    public static int ARM_LOWER_POS = 190;
    public static int ARM_UPPER_POS = 4000;
    public static int LINAC_MIN_POS = 50;
    public static int LINAC_EXTEND_GOAL = 3400;
}
