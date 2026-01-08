package org.firstinspires.ftc.teamcode.pipeline;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.Optional;

public class AutoHardwarePipeline extends HardwarePipeline {
    public VisionPortal visionPortal;
    public AprilTagProcessor aprilTag;

    public AutoHardwarePipeline(HardwareMap hardwareMap) {
        super(hardwareMap);

        aprilTag = new AprilTagProcessor.Builder()
            .setDrawAxes(true)
            .setDrawCubeProjection(true)
            .setDrawTagOutline(true)
            .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
            .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary()) // change this next szn
            .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
            .build();

        visionPortal = new VisionPortal.Builder()
                .addProcessor(aprilTag)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        FtcDashboard.getInstance().startCameraStream(visionPortal, 0);
    }

    public Optional<AprilTagDetection> getDetection() {
        ArrayList<AprilTagDetection> detections = aprilTag.getDetections();
        if(detections.isEmpty()) {
            return Optional.empty();
        } else {
            return detections.stream().findFirst();
        }
    }
}
