package javafxapplication1.models;

/**
 * Created by xyz on 2016-04-09.
 */
public class Status {

    private int id;
    private String name;

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
