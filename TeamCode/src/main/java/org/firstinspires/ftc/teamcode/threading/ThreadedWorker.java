package org.firstinspires.ftc.teamcode.threading;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadedWorker implements Runnable, Identity {
    private final String name;
    private final int id;
    private final SignalBroker broker;
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    private volatile boolean running = true;
    private int taskPointer = 0;

    protected ThreadedWorker(String name, SignalBroker broker) {
        this.name = name;
        this.broker = broker;
        this.id = broker.generateId();
        broker.register(this);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void run() {
        try {
            while (running && !Thread.currentThread().isInterrupted()) {
                processSignals();

                if (!running) break;

                if (taskPointer < tasks.size()) {
                    Signal result = tasks.get(taskPointer).execute();
                    handleTaskResult(result);
                } else {
                    running = false;
                }
            }
        } finally {
            broker.unregister(this);
        }
    }

    private void processSignals() {
        // Poll for signals without blocking the task execution loop too long
        broker.receive(this, 0).ifPresent(packet -> {
            if (packet.getSignal() == Signal.STOP || packet.getSignal() == Signal.ABORT) {
                this.running = false;
            }
        });
    }

    private void handleTaskResult(Signal result) {
        switch (result) {
            case CONTINUE: taskPointer++; break;
            case ABORT:    running = false; break;
            case REPEAT:   break;
            case STOP:     running = false; break;
        }
    }

    @Override public String getName() { return name; }
    @Override public int getId() { return id; }
}