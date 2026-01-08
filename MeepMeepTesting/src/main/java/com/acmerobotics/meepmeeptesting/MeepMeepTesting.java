package com.acmerobotics.meepmeeptesting;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track widths
                .setConstraints(30, 30, PI, PI, 18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, -54, PI/2))
                        .strafeTo(new Vector2d(0, -33))
                        .strafeTo(new Vector2d(0, -54))
                        .turn(-PI/2)
                        .strafeTo(new Vector2d(36, -54))
                        .turn(PI/2)
                        .strafeTo(new Vector2d(0, -54))
                        .strafeTo(new Vector2d(0, -33))
                        .strafeTo(new Vector2d(48, -54))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}