package org.firstinspires.ftc.teamcode.utility;

public class ReferenceInt {
    public int value;
    public ReferenceInt(int value) {
        this.value = value;
    }

    public Task taskFromValue(int value) {
        return () -> {
            ReferenceInt.this.value = value;
            return Status.SUCCESS;
        };
    }
}
