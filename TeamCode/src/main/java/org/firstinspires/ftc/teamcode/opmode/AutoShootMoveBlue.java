package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Shoot + Move (Blue)", group = "Main")
public class AutoShootMoveBlue extends AutoSuperClass {
    @Override
    public void runOpMode() {
        initialize(true);

//==========================//
        waitForStart();
//==========================//

        shootClose();
        move(0.85,(2*Math.PI)/3, 1500);
        stopFlyWheelNice();
    }
}