package javafxapplication1.models;

/**
 * Created by xyz  on 2016-04-10.
 */
public class Report {

    private String task;
    private String asigned;
    private String status;
    private String priority;
    private int progress;

    public Report(String task, String asigned, String status, String priority, int progress) {
        this.task = task;
        this.asigned = asigned;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
    }

    public String getTask() {
        return task;
    }

    public String getAsigned() {
        return asigned;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public int getProgress() {
        return progress;
    }
}
