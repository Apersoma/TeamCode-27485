package org.firstinspires.ftc.teamcode.utility;

import static org.firstinspires.ftc.teamcode.utility.IntakeConstants.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.actions.MotorActions;
import org.firstinspires.ftc.teamcode.pipeline.TelemetryPipeline;

public class ArtificialLimiter {
    final public DcMotorEx linac, arm;
    final public ServoImplEx swivel;
    final public MotorActions.MoveMotor moveArmToUpper, moveArmToLower, lockArm, moveLinacToUpper, moveLinacToLower;
    public boolean linacExtending, linacRetracting, holding;
    public int armTargetPosNotMacro;
    final public TelemetryPipeline telem;
    public ArtificialLimiter(DcMotorEx linac, DcMotorEx arm, ServoImplEx swivel, TelemetryPipeline telem) {
        this.linac = linac;
        this.arm = arm;
        this.swivel = swivel;
        this.telem = telem;
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linac.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //this.moveLinac = new MotorActions(linac, telem).moveMotor(ACTUATOR_FRONT_POS);
        this.moveArmToUpper = new MotorActions(arm, telem).moveMotor(ARM_UPPER_POS);
        this.moveArmToLower = new MotorActions(arm, telem).moveMotor(ARM_LOWER_POS);
        this.lockArm = new MotorActions(arm, telem).moveMotor(ARM_LOWER_POS);
        lockArm.tolerance = 0;
        this.moveLinacToUpper = new MotorActions(linac, telem).moveMotor(LINAC_EXTEND_GOAL);
        this.moveLinacToLower = new MotorActions(linac, telem).moveMotor(LINAC_MIN_POS);

        armTargetPosNotMacro = arm.getCurrentPosition();
    }

    public boolean setPower(double preferredLinacPower, double preferredArmPower, boolean linacExtend, boolean linacRetract) {

        //Positive arm power moves away from the weights.
        //Positive linac power moves out
        boolean limited = false;
        int armPos = arm.getCurrentPosition();
        int linacPos = linac.getCurrentPosition();
        //stops you from putting arm on wrong side
        if (armPos > ARM_UPPER_POS) {
            moveLinacToLower.run();
            arm.setPower(Math.min(preferredArmPower, moveArmToUpper.getPower()));
            telem.addDataPointPerpetual("Arm Mode", "OOB");
            return true;
        }

        linacExtend = linacExtend || linacExtending;
        linacRetract = linacRetract || linacRetracting;

        // ^ = xor
        if(linacExtend ^ linacRetract) {
            if(linacExtend){
                telem.addDataPointPerpetual("Linac Mode", "Extend");
                linacExtending = moveLinacToUpper.run();
                linacRetracting = false;
            } else {
                telem.addDataPointPerpetual("Linac Mode", "Retract");
                linacRetracting = moveLinacToLower.run();
                linacExtending = false;
            }
        } else {
            telem.addDataPointPerpetual("Linac Mode", "Normal");
            linac.setPower(preferredLinacPower);
            linacRetracting = false;
            linacExtending = false;
        }

        if(preferredArmPower < 0 && armPos < ARM_LOWER_POS) preferredArmPower = 0;


        if(Math.abs(preferredArmPower) < 0.1) {
            arm.setTargetPosition(armTargetPosNotMacro);
            if(!holding) {
                holding = true;
                armTargetPosNotMacro = Math.max(arm.getCurrentPosition(), ARM_LOWER_POS/*+10*/);
                arm.setTargetPositionTolerance(10);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                telem.addDataPointPerpetual("Arm Mode", "Hold");
                //lockArm.targetPos = arm.getCurrentPosition();
            }
            //double power = lockArm.getPower();
            //arm.setPower(power*power);
        } else if (armPos > ARM_LOWER_POS /*+ 100*/ || linacPos > LINAC_MIN_POS/* + 50*/){
            if(holding) {
                holding = false;
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telem.addDataPointPerpetual("Arm Mode", "Normal");
            }
            arm.setPower(preferredArmPower);
        } else {
            if(holding) {
                holding = false;
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telem.addDataPointPerpetual("Arm Mode", "Limited");
            }
            arm.setPower(Math.max(preferredArmPower, moveArmToLower.getPower()));
        }


        return false;
    }

    public void setVals(){
        moveArmToUpper.targetPos = ARM_UPPER_POS;
        moveArmToLower.targetPos = ARM_LOWER_POS;
        lockArm.targetPos = ARM_LOWER_POS;
        moveLinacToUpper.targetPos = LINAC_EXTEND_GOAL;
        moveLinacToLower.targetPos = LINAC_MIN_POS;
    }
}
