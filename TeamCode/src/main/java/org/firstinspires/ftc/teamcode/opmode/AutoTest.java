package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utility.HardwareConstants;

@Autonomous(name = "Test")
public class AutoTest extends AutoSuperClass {

    @Override
    public void runOpMode() throws InterruptedException {
        initialize(true);

//==========================//
        waitForStart();
//==========================//
        sleep(15000);
        sleep(8000);
        sleep(7000);
    }
}
