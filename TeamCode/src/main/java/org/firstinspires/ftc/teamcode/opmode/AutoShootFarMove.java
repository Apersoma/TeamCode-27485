package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Shoot Far + Move Forward", group = "Main")
public class AutoShootFarMove extends AutoSuperClass {
    @Override
    public void runOpMode() {
        initialize(true);

//==========================//
        waitForStart();
//==========================//

        shoot(true);
        intake.setPower(1);
        move(0.85,-Math.PI/2, 1000);
        stopFlyWheelNice();
        intake.setPower(0);
    }
}