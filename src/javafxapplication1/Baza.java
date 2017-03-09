package javafxapplication1;

import javafxapplication1.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

final class Baza {

    private static Baza db;

    private Connection connect = null;

    private Baza(String host, String password, String username, String dbname) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname + "?useUnicode=true&characterEncoding=utf-8&user=" + username + "&password=" + password + "");
        } catch (Exception e) {
            System.out.println("Nie można sie połączyć z bazą danych!");
        }
    }

    static synchronized Baza getInstance() {
        if (db == null) {
            db = new Baza("localhost:3306", "", "root", "projectmgr");
        }

        return db;
    }

    Connection getConnectionString() {
        return this.connect;
    }

    int login(String username, String password) throws SQLException {
        String query = "SELECT idPracownika FROM Pracownicy WHERE login = ? AND haslo = ? LIMIT 1;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            query = "INSERT INTO Daty_logowania (Pracownicy_idPracownika, data_logowania) VALUES (?, ?);";
            pst = this.connect.prepareStatement(query);
            pst.setInt(1, id);
            pst.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            pst.executeUpdate();
            return id;
        }

        return -1;
    }

    boolean isManager(String username) throws SQLException {
        String query = "SELECT kierownik FROM Pracownicy WHERE login = ? LIMIT 1;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();

        return rs.next() && rs.getBoolean(1);
    }

    Person getPerson(int id) throws SQLException {
        Person retVal = null;
        String query = "SELECT * FROM Pracownicy WHERE idPracownika = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            retVal = new Person(
                    rs.getInt("idPracownika"), rs.getString("imie"), rs.getString("nazwisko"),
                    rs.getString("pesel"), rs.getBoolean("kierownik"), rs.getString("login"),
                    rs.getString("haslo"), rs.getInt("idProjektu")
            );
        }

        return retVal;
    }

    /*List<Person> listPeople(int mgrId) throws SQLException {
        List<Person> returnList = new ArrayList<>();
        String query = "SELECT * FROM pracownicy p " +
                    "WHERE p.login != 'admin' AND p.idPracownika IN (SELECT idPracownika " +
                    "FROM pracownicy pr LEFT JOIN projekty pro ON pro.idProjektu = pr.idProjektu " +
                    "WHERE pro.id_kierownika = ? OR pr.idPracownika = ?);";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, mgrId);
        pst.setInt(2, mgrId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnList.add(
                    new Person(
                            rs.getInt("idPracownika"), rs.getString("imie"), rs.getString("nazwisko"),
                            rs.getString("pesel"), rs.getBoolean("kierownik"), rs.getString("login"),
                            rs.getString("haslo"), rs.getInt("idProjektu")
                    )
            );
        }
        return returnList;
    }*/

    List<Person> listPeople(boolean isMgr, int mgrId) throws SQLException {
        List<Person> returnList = new ArrayList<>();
        String query;
        if (isMgr) {
            query = "SELECT * FROM pracownicy p " +
                    "WHERE p.login != 'admin' AND p.idPracownika IN (SELECT idPracownika " +
                    "FROM pracownicy pr LEFT JOIN projekty pro ON pro.idProjektu = pr.idProjektu " +
                    "WHERE pro.id_kierownika = ? OR pr.idPracownika = ?);";
        } else {
            query = "SELECT * FROM pracownicy WHERE login != 'admin';";
        }
        PreparedStatement pst = this.connect.prepareStatement(query);
        if (isMgr) {
            pst.setInt(1, mgrId);
            pst.setInt(2, mgrId);
        }
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnList.add(
                    new Person(
                            rs.getInt("idPracownika"), rs.getString("imie"), rs.getString("nazwisko"),
                            rs.getString("pesel"), rs.getBoolean("kierownik"), rs.getString("login"),
                            rs.getString("haslo"), rs.getInt("idProjektu")
                    )
            );
        }
        return returnList;
    }

    String get5LastLoginDates(int id) throws SQLException {
        String ret = "";
        String query = "SELECT data_logowania FROM daty_logowania dl WHERE Pracownicy_idPracownika = ? " +
                "ORDER BY data_logowania DESC LIMIT 5";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            ret += rs.getString("data_logowania") + ";";
        }
        return ret;
    }

    List<Person> listPeople(int id) throws SQLException {
        List<Person> returnList = new ArrayList<>();
        String query = "SELECT * FROM pracownicy WHERE idProjektu = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnList.add(
                    new Person(
                            rs.getInt("idPracownika"), rs.getString("imie"), rs.getString("nazwisko"),
                            rs.getString("pesel"), rs.getBoolean("kierownik"), rs.getString("login"),
                            rs.getString("haslo"), rs.getInt("idProjektu")
                    )
            );
        }
        return returnList;
    }

    int addPerson(String name, String surname, String pesel, Boolean isManager, String login, String password, int projectId) throws SQLException {
        String query = "INSERT INTO Pracownicy (imie, nazwisko, pesel, kierownik, login, haslo, idProjektu) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, surname);
        pst.setString(3, pesel);
        pst.setBoolean(4, isManager);
        pst.setString(5, login);
        pst.setString(6, password);
        if (projectId < 0) {
            pst.setNull(7, Types.INTEGER);
        } else {
            pst.setInt(7, projectId);
        }
        return pst.executeUpdate();
    }

    int editPerson(int id, String name, String surname, String pesel, Boolean isManager, String login, String password, int projectId) throws SQLException {
        String query = "UPDATE pracownicy SET imie = ?, nazwisko = ?, pesel = ?, kierownik = ?, login = ?, haslo = ?, idProjektu = ? WHERE idPracownika = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, surname);
        pst.setString(3, pesel);
        pst.setBoolean(4, isManager);
        pst.setString(5, login);
        pst.setString(6, password);
        if (projectId < 0) {
            pst.setNull(7, Types.INTEGER);
        } else {
            pst.setInt(7, projectId);
        }
        pst.setInt(8, id);
        return pst.executeUpdate();
    }

/*    int editPerson(int id, int projectId) throws SQLException {
        String query = "UPDATE pracownicy SET idProjektu = ? WHERE idPracownika = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);

        if (projectId < 0) {
            pst.setNull(1, Types.INTEGER);
        } else {
            pst.setInt(1, projectId);
        }
        pst.setInt(2, id);
        return pst.executeUpdate();
    }*/

    int deletePerson(int id) throws SQLException {
        String query = "DELETE FROM pracownicy WHERE idPracownika = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        return pst.executeUpdate();
    }

    List<Project> listProjects() throws SQLException {
        List<Project> returnVal = new ArrayList<>();
        String query = "SELECT * FROM projekty;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnVal.add(
                    new Project(
                            rs.getInt("idProjektu"), rs.getString("nazwa"), rs.getString("opis"),
                            rs.getDate("data_rozpoczecia"), rs.getDate("data_zakonczenia"), rs.getInt("id_kierownika")
                    )
            );
        }

        return returnVal;
    }

    Project getProject(int id) throws SQLException {
        Project returnVal = null;
        String query = "SELECT * FROM projekty WHERE idProjektu = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            returnVal = new Project(
                    rs.getInt("idProjektu"), rs.getString("nazwa"), rs.getString("opis"),
                    rs.getDate("data_rozpoczecia"), rs.getDate("data_zakonczenia"), rs.getInt("id_kierownika")
            );
        }

        return returnVal;
    }

    int addProject(String name, String desc, java.util.Date startDate, java.util.Date endDate, int mgrid) throws SQLException {
        String query = "INSERT INTO projekty (nazwa, opis, data_rozpoczecia, data_zakonczenia, id_kierownika) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, desc);
        pst.setDate(3, new Date(startDate.getTime()));
        pst.setDate(4, new Date(endDate.getTime()));
        pst.setInt(5, mgrid);
        return pst.executeUpdate();
    }

    int deleteProject(int id) throws SQLException {
        String query = "DELETE FROM projekty WHERE idProjektu = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        return pst.executeUpdate();
    }

    List<Task> listTasks(int id) throws SQLException {
        List<Task> returnList = new ArrayList<>();
        String query = "SELECT * FROM zadania WHERE idProjektu = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnList.add(
                    new Task(
                            rs.getInt("idZadania"), rs.getInt("idProjektu"), rs.getInt("idPracownika"),
                            rs.getInt("idStatus"), rs.getInt("idPriorytet"), rs.getString("nazwa"),
                            rs.getString("notatka"), rs.getInt("progress"))
            );
        }
        return returnList;
    }

    List<Task> listTasks(int id, int userID) throws SQLException {
        List<Task> returnList = new ArrayList<>();
        String query = "SELECT * FROM zadania WHERE idProjektu = ? AND idPracownika = ? AND idStatus != (SELECT s.idstatusu FROM status s WHERE LOWER(s.nazwa) = 'Completed');";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, id);
        pst.setInt(2, userID);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            returnList.add(
                    new Task(
                            rs.getInt("idZadania"), rs.getInt("idProjektu"), rs.getInt("idPracownika"),
                            rs.getInt("idStatus"), rs.getInt("idPriorytet"), rs.getString("nazwa"),
                            rs.getString("notatka"), rs.getInt("progress"))
            );
        }
        return returnList;
    }

    int addTask(int projectId, String name) throws SQLException {
        String query = "INSERT INTO zadania (idProjektu, nazwa) VALUES (?, ?);";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, projectId);
        pst.setString(2, name);
        return pst.executeUpdate();
    }

    int updateTask(int id, int asignedId, int priorityId, int statusId) throws SQLException {
        String query = "UPDATE zadania SET idPracownika = ?, idPriorytet = ?, idStatus = ? WHERE idZadania = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, asignedId);
        pst.setInt(2, priorityId);
        pst.setInt(3, statusId);
        pst.setInt(4, id);
        return pst.executeUpdate();
    }

    int updateTask(int id, String note, int progress) throws SQLException {
        String query = "UPDATE zadania SET progress = ?, notatka = ?, " +
                "idStatus = CASE WHEN ? > 0 AND ? < 100 THEN " +
                "(SELECT s.idstatusu FROM status s WHERE LOWER(s.nazwa) LIKE '%progress%') " +
                "WHEN ? = 100 THEN " +
                "(SELECT s.idstatusu FROM status s WHERE LOWER(s.nazwa) = 'completed') " +
                "ELSE idStatus END " +
                "WHERE idZadania = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, progress);
        pst.setString(2, note);
        pst.setInt(3, progress);
        pst.setInt(4, progress);
        pst.setInt(5, progress);
        pst.setInt(6, id);
        return pst.executeUpdate();
    }

    List<Status> listStatuses() throws SQLException {
        List<Status> returnList = new ArrayList<>();
        String query = "SELECT * FROM status;";
        PreparedStatement preparedStatement = this.connect.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            returnList.add(new Status(rs.getInt("idStatusu"), rs.getString("nazwa")));
        }
        return returnList;
    }

    List<Priority> listPriorities() throws SQLException {
        List<Priority> returnList = new ArrayList<>();
        String query = "SELECT * FROM priorytet;";
        PreparedStatement preparedStatement = this.connect.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            returnList.add(new Priority(rs.getInt("idPriorytet"), rs.getString("nazwa")));
        }
        return returnList;
    }
/*
    String listPriorities(int idProiorytet) throws SQLException {
        String a;
        String query = "SELECT * FROM priorytet WHERE idPriorytet = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, idProiorytet);
        ResultSet rs = pst.executeQuery();
        a = rs.getString("nazwa");
        return a;
    }

    List<Report> getReport(int projectid) throws SQLException {
        List<Report> returnList = new ArrayList<>();
        String query = "SELECT z.nazwa AS zadanie, p.imie, p.nazwisko, z.progress AS progress, st.nazwa AS status, pt.nazwa AS priorytet "
                + "FROM zadania z LEFT OUTER JOIN pracownicy p ON z.idPracownika = p.idPracownika "
                + "JOIN priorytet pt ON z.idPriorytet = pt.idPriorytet "
                + "JOIN status st ON z.idStatus = st.idstatusu WHERE z.idProjektu = ?;";
        PreparedStatement pst = this.connect.prepareStatement(query);
        pst.setInt(1, projectid);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            String name = rs.getString("imie");
            if (rs.wasNull()) {
                name = "";
            }
            String surname = rs.getString("nazwisko");
            if (rs.wasNull()) {
                surname = "";
            }

            returnList.add(
                    new Report(
                            rs.getString("zadanie"), name + " " + surname,
                            rs.getString("status"), rs.getString("priorytet"), rs.getInt("progress")
                    )
            );
        }
        return returnList;
    }*/
}
