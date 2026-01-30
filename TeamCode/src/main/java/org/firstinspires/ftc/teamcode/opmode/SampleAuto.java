package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.threading.ThreadedSupervisor;
import org.firstinspires.ftc.teamcode.threading.ThreadedWorker;

@Autonomous(name = "Sample Auto Path", group = "Z so its sorted last")
public class SampleAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(24,0)
                .lineToXSplineHeading(12,Math.PI/2);

        waitForStart();

        Actions.runBlocking(tab.build());
    }
}