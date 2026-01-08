package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class IntakeLimiter {
    ServoImplEx swivel, stage2, intake;
    DcMotorEx arm, linac;

    double armPos, linacPos;

    public IntakeLimiter(ServoImplEx swivel, ServoImplEx stage2, ServoImplEx intake,
                         DcMotorEx arm, DcMotorEx linac) {
        this.swivel = swivel;
        this.stage2 = stage2;
        this.intake = intake;
        this.arm = arm;
        this.linac = linac;
    }

    public void update() {
        armPos = arm.getCurrentPosition();
        linacPos = linac.getCurrentPosition();
    }

    public double limitLinac(double wantedValue) {
        if(armPos < 300 & linacPos > 3800) return 0;

        return wantedValue;
    }

    public double limitArm(double wantedValue) {
        if(linacPos < 1400 & armPos > 150) return 0;

        return wantedValue;
    }
}
