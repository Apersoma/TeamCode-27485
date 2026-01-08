package org.firstinspires.ftc.teamcode.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadedSupervisor implements Identity {
    private final String name;
    private final int id;
    private final SignalBroker broker = new SignalBroker();
    private final List<ThreadedWorker> workers = new ArrayList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public ThreadedSupervisor(String name) {
        this.name = name;
        this.id = broker.generateId();
    }

    public ThreadedWorker createWorker(String workerName) {
        ThreadedWorker worker = new ThreadedWorker(workerName, broker);
        workers.add(worker);
        return worker;
    }

    public void start() {
        workers.forEach(executor::submit);
    }

    public void broadcastStop() {
        broker.send(new Packet(this, Identity.ANY, Signal.STOP));
    }

    public void shutdown() {
        broadcastStop();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    @Override public String getName() { return name; }
    @Override public int getId() { return id; }
}