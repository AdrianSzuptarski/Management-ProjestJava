package javafxapplication1.models;

/**
 * Created by xyz on 2016-04-09.
 */
public class Task {

    private int id;
    private int projectId;
    private int asignedId = -1;
    private int statusId;
    private int priorityId;
    private int progress;
    private String note;
    private String name;

    public Task(int id, int projectId, int asignedId, int statusId, int priorityId, String name, String note, int progress) {

        this.id = id;
        this.projectId = projectId;
        this.asignedId = asignedId;
        this.statusId = statusId;
        this.priorityId = priorityId;
        this.name = name;
        this.progress = progress;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getAsignedId() {
        return asignedId;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public int getProgress() {
        return progress;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
