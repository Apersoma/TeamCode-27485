package org.firstinspires.ftc.teamcode.threading;

import java.util.function.Supplier;

class TaskBuilder {
    private String name = "Unnamed Task";
    private int id = 0;
    private Supplier<Signal> logic;

    public TaskBuilder name(String name) { this.name = name; return this; }
    public TaskBuilder id(int id) { this.id = id; return this; }
    public TaskBuilder action(Supplier<Signal> logic) { this.logic = logic; return this; }

    public Task build() {
        return new Task() {
            @Override public String getName() { return name; }
            @Override public int getId() { return id; }
            @Override public Signal execute() { return logic.get(); }
        };
    }
}