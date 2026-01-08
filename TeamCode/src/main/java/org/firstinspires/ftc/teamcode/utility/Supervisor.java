package org.firstinspires.ftc.teamcode.utility;

import org.firstinspires.ftc.teamcode.pipeline.TelemetryPipeline;

import java.util.ArrayList;

public class Supervisor {
    private final ArrayList<Worker> workers = new ArrayList<>();

    public Worker getWorkerById(int id) {
        return workers.get(id);
    }

    public int getWorkerId(Worker worker) {
        return worker.hashCode();
    }

    public Supervisor addWorker(Worker worker) {
        workers.add(worker);
        return this;
    }

    public Worker createAddWorkerFromTask(Task task, boolean restart, String identifier) {
        Worker worker = new Worker(restart, identifier)
                .addTask(task);
        workers.add(worker);
        return worker;
    }

    public void run(TelemetryPipeline telemetry) {
        if(workers.isEmpty())
            return;
        workers.forEach(worker -> {
            worker.run();

            String identifier = worker.getIdentifier();
            String taskIdentifier = worker.getCurrentTaskIdentifier();
            String status = worker.getCurrentStatus()+"";

            telemetry.addDataPoint("Worker", identifier);
            telemetry.addDataPoint("Task", taskIdentifier);
            telemetry.addDataPoint("Status", status);
        });
    }

}
