package org.firstinspires.ftc.teamcode.utility;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys.Button;

public class ButtonToggle {
    public final boolean isStickButton;
    public boolean toggle;
    public final Button button;

    private long lastPressed;

    private boolean wasHeld;


    public ButtonToggle(@NonNull Button button, boolean toggle) {
        wasHeld = false;
        lastPressed = 0;
        this.button = button;
        isStickButton = button == Button.LEFT_STICK_BUTTON || button == Button.RIGHT_STICK_BUTTON;
        this.toggle = toggle;
    }

    public boolean check(@NonNull GamepadEx ctrl) {
        boolean isHeld = ctrl.getButton(button);
        if(isStickButton) {
            if(!isHeld)
                return toggle;
            // stick buttons are really finicky and when held down they often will be read as
            // briefly not being held down so this adds tolerance so it doesn't constantly switch
            long now = System.currentTimeMillis();
            toggle ^= now - lastPressed > 100;
            lastPressed = now;
        } else {
            toggle ^= isHeld && !wasHeld;
            wasHeld = isHeld;
        }
        return toggle;
    }

}
