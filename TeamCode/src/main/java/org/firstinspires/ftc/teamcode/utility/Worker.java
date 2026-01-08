package org.firstinspires.ftc.teamcode.utility;

import static org.firstinspires.ftc.teamcode.utility.Status.*;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class Worker {
    private Status currentStatus = RUNNING;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int currentTask = 0;
    private boolean restart = true;
    private String identifier = "";

    public Worker(boolean restart, String identifier) {
        this.restart = restart;
        this.identifier = identifier;
    }

    public Worker addTask(Task task) {
        tasks.add(task);
        return this;
    }

    public Worker addTask(Action action, String name) {
        tasks.add(ActionWrapper.toTask(action, name));
        return this;
    }


    public Status run() {
        if(currentStatus != FAIL) {
            if(currentTask < tasks.size()) {
                currentStatus = tasks.get(currentTask).run();
            } else {
                return SUCCESS;
            }

            switch(currentStatus) {
                case FAIL:
                case RUNNING:
                    return currentStatus;
                case SUCCESS:
                    currentTask++;
                default:
                    return RUNNING;
            }
        } else {
            restart();
        }

        return currentStatus;
    }

    public String getCurrentTaskIdentifier() {
        if(currentTask < tasks.size()) {
            return tasks.get(currentTask).identifier;
        } else {
            currentStatus = SUCCESS;
            return "Task OOB";
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public boolean restart() {
        if(restart) {
            currentStatus = RUNNING;
            currentTask = 0;
        }

        return restart;
    }

}
