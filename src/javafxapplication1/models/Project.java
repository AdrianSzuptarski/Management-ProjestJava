package javafxapplication1.models;

import java.util.Date;

/**
 * Created by xyz on 2016-04-09.
 */
public class Project {

    private int id;
    private String name;
    private String desc;
    private Date startDate;
    private Date endDate;
    private int mgrID;

    public Project(int id, String name, String desc, Date startDate, Date endDate, int mgrID) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mgrID = mgrID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getMgrID() {
        return mgrID;
    }

    @Override
    public String toString() {
        return name;
    }
}
