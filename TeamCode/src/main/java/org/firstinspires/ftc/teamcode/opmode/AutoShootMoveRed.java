package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Shoot + Move (Red)", group = "Main")
public class AutoShootMoveRed extends AutoSuperClass {

    @Override
    public void runOpMode() {
        initialize(true);

//==========================//
        waitForStart();
//==========================//

        shoot();
        move(0.85, (-(2*Math.PI)/3), 1500, -0.05);
        stopFlyWheelNice();
    }
}