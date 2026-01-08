package org.firstinspires.ftc.teamcode.threading;

enum Signal {
    CONTINUE,   // Task successful, move to next
    REPEAT,     // Task incomplete, run again
    STOP,       // Stop worker gracefully
    ABORT       // Stop worker immediately due to failure
}