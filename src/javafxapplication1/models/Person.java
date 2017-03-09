package javafxapplication1.models;

/**
 * Created by xyz on 2016-04-08.
 */
public class Person {

    private int id;
    private String name;
    private String surname;
    private String pesel;
    private boolean isManager;
    private String login;
    private String password;

    public int getProjectID() {
        return projectID;
    }

    private int projectID;

    public Person(int id, String name, String surname, String pesel, boolean isManager, String login, String password, int projectID) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.isManager = isManager;
        this.login = login;
        this.password = password;
        this.projectID = projectID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel;
    }

    public boolean isManager() {
        return isManager;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return this.surname + " " + this.name;
    }
}
