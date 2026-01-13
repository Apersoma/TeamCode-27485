package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Shoot1 + Move")
public class AutoShootMove extends AutoSuperClass {

    @Override
    public void runOpMode() {
        initialize(true);

//==========================//
        waitForStart();
//==========================//

        shoot1();
        move(0.7,(2*Math.PI)/3, 1000);
    }
}
