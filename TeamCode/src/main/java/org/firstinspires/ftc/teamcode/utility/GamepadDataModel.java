package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Arrays;

public class GamepadDataModel extends Gamepad {
    public final static int
            LEFT_X = 0,
            LEFT_Y = 1,
            RIGHT_X = 2,
            RIGHT_Y = 3,
            UP = 4,
            DOWN = 5,
            LEFT = 6,
            RIGHT = 7,
            A = 8,
            B = 9,
            X = 10,
            Y = 11,
            GUIDE = 12, //wtf is this
            START = 13,
            BACK = 14,
            LEFT_BUMPER = 15,
            RIGHT_BUMPER = 16,
            LEFT_STICK_BUTTON = 17,
            RIGHT_STICK_BUTTON = 18,
            LEFT_TRIGGER = 19,
            RIGHT_TRIGGER = 20;
    public volatile float left_stick_x = 0f;

    public volatile float left_stick_y = 0f;
    public volatile float right_stick_x = 0f;
    public volatile float right_stick_y = 0f;
    public volatile boolean dpad_up = false;
    public volatile boolean dpad_down = false;
    public volatile boolean dpad_left = false;
    public volatile boolean dpad_right = false;
    public volatile boolean a = false;
    public volatile boolean b = false;
    public volatile boolean x = false;
    public volatile boolean y = false;
    public volatile boolean guide = false;
    public volatile boolean start = false;
    public volatile boolean back = false;
    public volatile boolean left_bumper = false;
    public volatile boolean right_bumper = false;
    public volatile boolean left_stick_button = false;
    public volatile boolean right_stick_button = false;
    public volatile float left_trigger = 0f;
    public volatile float right_trigger = 0f;

    Gamepad gamepad;

    public GamepadDataModel(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public GamepadDataModel(float[] data) {
        left_stick_x = data[0];
        left_stick_y = data[1];
        right_stick_x = data[2];
        right_stick_y = data[3];
        dpad_up = data[4] == 1f;
        dpad_down = data[5] == 1f;
        dpad_left = data[6] == 1f;
        dpad_right = data[7] == 1f;
        a = data[8] == 1f;
        b = data[9] == 1f;
        x = data[10] == 1f;
        y = data[11] == 1f;
        guide = data[12] == 1f;
        start = data[13] == 1f;
        back = data[14] == 1f;
        left_bumper = data[15] == 1f;
        right_bumper = data[16] == 1f;
        left_stick_button = data[17] == 1f;
        right_stick_button = data[18] == 1f;
        left_trigger = data[19];
        right_trigger = data[20];
    }

    public void refreshData() {
        left_stick_x = gamepad.left_stick_x;
        left_stick_y = gamepad.left_stick_y;
        right_stick_x = gamepad.right_stick_x;
        right_stick_y = gamepad.right_stick_y;
        dpad_up = gamepad.dpad_up;
        dpad_down = gamepad.dpad_up;
        dpad_left = gamepad.dpad_left;
        dpad_right = gamepad.dpad_right;
        a = gamepad.a;
        b = gamepad.b;
        x = gamepad.x;
        y = gamepad.y;
        guide = gamepad.guide;
        start = gamepad.start;
        back = gamepad.back;
        left_bumper = gamepad.left_bumper;
        right_bumper = gamepad.right_bumper;
        left_stick_button = gamepad.left_stick_button;
        right_stick_button = gamepad.right_stick_button;
        left_trigger = gamepad.left_trigger;
        right_trigger = gamepad.right_trigger;
    }

    public float[] serialize () {
        return new float[]{
                left_stick_x,
                left_stick_y,
                right_stick_x,
                right_stick_y,
                dpad_up ? 1f : 0f,
                dpad_down ? 1f : 0f,
                dpad_left ? 1f : 0f,
                dpad_right ? 1f : 0f,
                a ? 1f : 0f,
                b ? 1f : 0f,
                x ? 1f : 0f,
                y ? 1f : 0f,
                guide ? 1f : 0f,
                start ? 1f : 0f,
                back ? 1f : 0f,
                left_bumper ? 1f : 0f,
                right_bumper ? 1f : 0f,
                left_stick_button ? 1f : 0f,
                right_stick_button ? 1f : 0f,
                left_trigger,
                right_trigger,
        };
    }

    public boolean compareWithNext() {
        float[] current = serialize();
        refreshData();
        float[] next = serialize();

        return Arrays.equals(current, next);
    }
}
