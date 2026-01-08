package org.firstinspires.ftc.teamcode.threading;

interface Identity {
    String getName();
    int getId();

    Identity ANY = new Identity() {
        @Override public String getName() { return "Broadcast"; }
        @Override public int getId() { return -1; }
    };
}
