package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ArtificialActuatorLimiter {
    DcMotorEx actuator, arm;
    ServoImplEx servo;
    public ArtificialActuatorLimiter(DcMotorEx actutor, DcMotorEx arm, ServoImplEx servo) {
        this.actuator = actutor;
        this.arm = arm;
        this.servo = servo;
    }

    public boolean setPower(double prefferedPower)  {
        int armPos = arm.getCurrentPosition();
        int actPos = actuator.getCurrentPosition();

        if(
                (armPos < 2190) ||
                (armPos > 3160)
        ) {
            if(actPos > IntakeConstants.LINAC_MIN_POS) {
                actuator.setPower(-1);
            } else {
                actuator.setPower(0);
            }

            return true;
        }

        actuator.setPower(prefferedPower);

        return false;
    }
}
