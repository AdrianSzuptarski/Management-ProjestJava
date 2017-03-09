package javafxapplication1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafxapplication1.models.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by xyz on 2016-05-16.
 */
public class ManagementController implements Initializable {

    private static Baza db;
    private Person user;
    private boolean isMgr = false;

    /* Tabs */
    @FXML
    private Tab addPersonTab;
    @FXML
    private TabPane tabsPane;

    @FXML
    private Tab editPersonTab;
    @FXML
    private Tab addProjectTab;
    @FXML
    private Tab manageTasksTab;
    @FXML
    private Tab reportsTab;
    /* Controls grouped by tabs */
    /* Add person */
    @FXML
    private TextField nameAddField;
    @FXML
    private TextField surnameAddField;
    @FXML
    private TextField personalIDAddField;
    @FXML
    private TextField loginAddField;
    @FXML
    private TextField passwordAddField;
    @FXML
    private PasswordField passwordRepeatAddField;
    @FXML
    private CheckBox mgrAddCheckbox;
    @FXML
    private ComboBox<Project> projectsPersonAddComboBox;
    /* Edit person */
    @FXML
    private ComboBox<Person> peopleEditCombobox;
    @FXML
    private TextField nameEditField;
    @FXML
    private TextField surnameEditField;
    @FXML
    private TextField personalIDEditField;
    @FXML
    private CheckBox mgrEditCheckbox;
    @FXML
    private TextField loginEditField;
    @FXML
    private PasswordField passwordEditField;
    @FXML
    private PasswordField passwordRepeatEditField;
    @FXML
    private ComboBox<Project> projectsPersonEditComboBox;
    @FXML
    private Label lastLoginDate;
    /* Add project */
    @FXML
    private TextField projectNameField;
    @FXML
    private DatePicker projectStartDatePicker;
    @FXML
    private DatePicker projectEndDatePicker;
    @FXML
    private TextArea projectDescriptionArea;
    @FXML
    private ComboBox<Project> projectsDelComboBox;
    @FXML
    private ComboBox<Person> projectMgrComboBox;
    @FXML
    private Label projectMgrLabel;
    /* Add/edit task */
    @FXML
    private ComboBox<Project> projectsTaskComboBox;
    @FXML
    private TextField taskNameField;
    @FXML
    private ComboBox<Task> tasksComboBox;
    @FXML
    private ComboBox<Status> taskStatusComboBox;
    @FXML
    private ComboBox<Priority> taskPriorityComboBox;
    @FXML
    private ComboBox<Person> taskAsignComboBox;
    @FXML
    private Label taskProgressLabel;
    @FXML
    private ProgressBar taskProgress;
    @FXML
    private Label projectProgressLabel;
    @FXML
    private ProgressBar projectProgress;
    @FXML
    private Label projectDateLabel;
    /* Reports */
    @FXML
    private ComboBox<Project> reportsComboBox;

    private FXMLDocumentController maincontroller;

    @FXML
    public void logout() {
        this.maincontroller.loadMenuscreen();
    }

    @FXML
    public void addPerson() {
        if (!this.checkPersonalID(this.personalIDAddField.getText())) {
            Utils.alert("Niepoprawny PESEL!", "Podany numer PESEL jest niepoprawny!.", Alert.AlertType.ERROR);
            return;
        }
        if (!this.passwordAddField.getText().equals(this.passwordRepeatAddField.getText())) {
            Utils.alert("Niepoprawne hasło!", "Wpisano dwa różne hasła!.", Alert.AlertType.ERROR);
            return;
        }
        try {
            int projectID = -1;
            if (this.projectsPersonAddComboBox.getValue() != null) {
                projectID = this.projectsPersonAddComboBox.getValue().getId();
            }

            int ret = db.addPerson(this.nameAddField.getText(), this.surnameAddField.getText(), this.personalIDAddField.getText(),
                    this.mgrAddCheckbox.selectedProperty().getValue(), this.loginAddField.getText(), this.passwordAddField.getText(),
                    projectID);

            if (ret <= 0) {
                Utils.alert("Błąd dodawnia nowej osoby!", "Nie udało się dodać nowej osoby do bazy danych.", Alert.AlertType.ERROR);
            } else {
                Utils.alert("Sukces!", this.nameAddField.getText() + " " + this.surnameAddField.getText() + " został dodany do bazy danych.", Alert.AlertType.INFORMATION);
                this.nameAddField.clear();
                this.surnameAddField.clear();
                this.personalIDAddField.clear();
                this.mgrAddCheckbox.setSelected(false);
                this.loginAddField.clear();
                this.passwordAddField.clear();
            }
            this.updatePeopleEditComboBox();
            this.updateProjectsComboBox();
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    void setMaincontroller(FXMLDocumentController maincontroller) {
        this.maincontroller = maincontroller;
    }

    @FXML
    public void editPerson() {
        if (!this.checkPersonalID(this.personalIDEditField.getText())) {
            Utils.alert("Niepoprawny PESEL!", "Podany numer PESEL jest niepoprawny!.", Alert.AlertType.ERROR);
            return;
        }
        String pass = this.peopleEditCombobox.getValue().getPassword();
        if (!this.passwordEditField.getText().equals("") || !this.passwordRepeatEditField.getText().equals("")) {
            if (!this.passwordEditField.getText().equals(this.passwordRepeatEditField.getText())) {
                Utils.alert("Niepoprawne hasło!", "Wpisano hasła są różne!.", Alert.AlertType.ERROR);
                return;
            }
            pass = this.passwordEditField.getText();
        }

        try {
            int projectID = -1;
            if (this.projectsPersonEditComboBox.getValue() != null) {
                projectID = this.projectsPersonEditComboBox.getValue().getId();
            }
            int ret = db.editPerson(this.peopleEditCombobox.getValue().getId(), this.nameEditField.getText(), this.surnameEditField.getText(),
                    this.personalIDEditField.getText(), this.mgrEditCheckbox.selectedProperty().getValue(),
                    this.loginEditField.getText(), pass, projectID);

            if (ret <= 0) {
                Utils.alert("Błąd edycji osoby!", "Nie udało się zapisać zmienionych danych do bazy.", Alert.AlertType.ERROR);
            } else {
                Utils.alert("Sukces!", "Dane " + this.nameEditField.getText() + " " + this.surnameEditField.getText() + " zostały zmienione w bazie danych.", Alert.AlertType.INFORMATION);
            }
            this.updatePeopleEditComboBox();
            this.updateProjectsComboBox();

        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deletePerson() {
        try {
            int ret = db.deletePerson(this.peopleEditCombobox.getValue().getId());
            if (ret <= 0) {
                Utils.alert("Błąd usuwania osoby!", "Nie udało się usunąć danych z bazy.", Alert.AlertType.ERROR);
            } else {
                Utils.alert("Sukces!", "Dane " + this.nameEditField.getText() + " " + this.surnameEditField.getText() + " zostały usunięte z bazy danych.", Alert.AlertType.INFORMATION);
            }
            this.updatePeopleEditComboBox();
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void addProject() {
        try {
            Date start = this.localDateToDate(this.projectStartDatePicker.getValue());
            Date end = this.localDateToDate(this.projectEndDatePicker.getValue());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date now = cal.getTime();
            if (start.after(end) || start.before(now)) {
                Utils.alert("Niepoprawna data!", "Data rozpoczęcia nie może być późniejsza niż data dzisiejsza lub data zakończenia.", Alert.AlertType.WARNING);
                return;
            }
            if (this.projectMgrComboBox.getValue() == null && !this.isMgr) {
                Utils.alert("Brak kierownika!", "Projekt musi zostać przypisany do kierownika!.", Alert.AlertType.WARNING);
                return;
            }
            int mgrid = this.isMgr ? this.user.getId() : this.projectMgrComboBox.getValue().getId();
            int ret = db.addProject(this.projectNameField.getText(), this.projectDescriptionArea.getText(), start, end, mgrid);
            if (ret <= 0) {
                Utils.alert("Błąd dodawnia nowego projektu!", "Nie udało się dodać nowego projektu do bazy danych.", Alert.AlertType.ERROR);
            } else {
                Utils.alert("Sukces!", "Projekt " + this.projectNameField.getText() + " został dodany do bazy danych.", Alert.AlertType.INFORMATION);
                this.projectNameField.clear();
                this.projectDescriptionArea.clear();
                this.projectStartDatePicker.setValue(null);
                this.projectEndDatePicker.setValue(null);
                this.projectMgrComboBox.setValue(null);
            }
            this.updateProjectsComboBox();
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void deleteProject() {
        try {
            int ret = db.deleteProject(this.projectsDelComboBox.getValue().getId());
            if (ret <= 0) {
                Utils.alert("Błąd usuwania projektu!", "Nie udało się usunąć danych z bazy.", Alert.AlertType.ERROR);
            } else {
                Utils.alert("Sukces!", "Projekt " + this.projectsDelComboBox.getValue().getName() + " został usunięty z bazy danych.", Alert.AlertType.INFORMATION);
            }
            this.updateProjectsComboBox();
            this.updateProjectsComboBox();
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void addTask() {
        if (this.projectsTaskComboBox.getValue() == null) {
            Utils.alert("Popraw dane!", "Nie wybrano projektu, dla którego ma zostć utworzone zadanie.", Alert.AlertType.WARNING);
        } else {
            try {
                Project p = this.projectsTaskComboBox.getValue();
                int ret = db.addTask(p.getId(), this.taskNameField.getText());
                if (ret <= 0) {
                    Utils.alert("Błąd dodawnia nowego zadania!", "Nie udało się dodać nowego zadania do bazy danych.", Alert.AlertType.ERROR);
                } else {
                    Utils.alert("Sukces!", "Zadanie " + this.taskNameField.getText() + " zostało dodane do bazy danych.", Alert.AlertType.INFORMATION);
                    this.taskNameField.clear();
                    this.tasksComboBox.getItems().clear();
                    this.tasksComboBox.getItems().setAll(db.listTasks(p.getId()));
                }
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void editTask() {
        if (this.tasksComboBox.getValue() == null || this.taskAsignComboBox.getValue() == null) {
            Utils.alert("Popraw dane!", "Nie wybrano zadania do edycji!.", Alert.AlertType.WARNING);
        } else {
            try {
                int ret = db.updateTask(this.tasksComboBox.getValue().getId(), this.taskAsignComboBox.getValue().getId(),
                        this.taskPriorityComboBox.getValue().getId(), this.taskStatusComboBox.getValue().getId());
                if (ret <= 0) {
                    Utils.alert("Błąd edycji zadania!", "Nie udało się zapisać zedytowanego zadania.", Alert.AlertType.ERROR);
                } else {
                    Utils.alert("Sukces!", "Zadanie " + this.tasksComboBox.getValue().getName() + " zostało zapisane do bazy danych.", Alert.AlertType.INFORMATION);
                    this.tasksComboBox.getItems().clear();
                    this.tasksComboBox.getItems().setAll(db.listTasks(this.projectsTaskComboBox.getValue().getId()));
                }
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    private void updatePeopleEditComboBox() {
        this.peopleEditCombobox.getItems().clear();

        try {
            ObservableList<Person> peopleList = FXCollections.observableList(db.listPeople(this.isMgr, this.isMgr ? this.user.getId() : -1));
            this.peopleEditCombobox.getItems().addAll(peopleList);
            if (!this.isMgr) {
                this.projectMgrComboBox.getItems().addAll(db.listPeople(this.isMgr, this.isMgr ? this.user.getId() : -1).stream().filter(Person::isManager).collect(Collectors.toList()));
            }
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateProjectsComboBox() {
        this.projectsDelComboBox.getItems().clear();
        this.projectsPersonAddComboBox.getItems().clear();
        this.projectsPersonEditComboBox.getItems().clear();
        this.projectsTaskComboBox.getItems().clear();
        this.reportsComboBox.getItems().clear();

        try {
            ObservableList<Project> projectsList;
            if (this.isMgr) {
                projectsList = FXCollections.observableList(db.listProjects().stream().filter(p -> p.getMgrID() == this.user.getId()).collect(Collectors.toList()));
            } else {
                projectsList = FXCollections.observableList(db.listProjects());
            }
            this.projectsDelComboBox.getItems().addAll(projectsList);
            this.projectsPersonAddComboBox.getItems().addAll(projectsList);
            this.projectsPersonEditComboBox.getItems().addAll(projectsList);
            this.projectsTaskComboBox.getItems().addAll(projectsList);
            this.reportsComboBox.getItems().addAll(projectsList);
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Date localDateToDate(LocalDate dt) {
        Instant instant = Instant.from(dt.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    private LocalDate dateToLocalDate(Date dt) {
        Instant instant = dt.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void init(boolean isMgr, int id) {
        db = Baza.getInstance();
        this.isMgr = isMgr;

        if (this.isMgr) {
            try {
                this.user = db.getPerson(id);
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }

            this.tabsPane.getTabs().remove(this.addPersonTab);
            this.projectMgrComboBox.setVisible(false);
            this.projectMgrLabel.setVisible(false);
            this.nameEditField.setEditable(false);
            this.surnameEditField.setEditable(false);
            this.personalIDEditField.setEditable(false);
            this.mgrEditCheckbox.setDisable(true);
            this.loginEditField.setEditable(false);
        }

        this.peopleEditCombobox.setOnAction(event -> {
            Person p = this.peopleEditCombobox.getValue();
            String lastLogins = "";
            try {
                lastLogins = db.get5LastLoginDates(p.getId());
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }

            this.nameEditField.setText(p.getName());
            this.surnameEditField.setText(p.getSurname());
            this.personalIDEditField.setText(p.getPesel());
            this.mgrEditCheckbox.setSelected(p.isManager());
            this.loginEditField.setText(p.getLogin());
            //this.passwordEditField.setText(p.getPassword());
            for (Project proj : this.projectsPersonEditComboBox.getItems()) {
                if (proj.getId() == p.getProjectID()) {
                    this.projectsPersonEditComboBox.setValue(proj);
                    break;
                } else {
                    this.projectsPersonEditComboBox.setValue(null);
                }
            }
            if (!lastLogins.equals("")) {
                this.lastLoginDate.setText(lastLogins.replaceAll(";", "\n"));
            } else {
                this.lastLoginDate.setText("Brak zapisanych logowań");
            }
        });


        this.projectsDelComboBox.setOnAction(event -> {
            Project pt = this.projectsDelComboBox.getValue();
            if (pt != null) {
                // projectStartDatePicker.setText(pt.getStartDate().toString());
                // projectEndDatePicker.setText(pt.getEndDate());
                this.projectDescriptionArea.setText(pt.getDesc());
            } else {
                this.projectDescriptionArea.clear();
            }


        });

        this.projectsTaskComboBox.setOnAction(event -> {
            this.tasksComboBox.getItems().clear();
            this.taskAsignComboBox.getItems().clear();
            try {
                Project p = this.projectsTaskComboBox.getValue();
                if (p != null) {
                    this.tasksComboBox.getItems().setAll(db.listTasks(p.getId()));
                    this.taskAsignComboBox.getItems().setAll(db.listPeople(p.getId()));

                    double projProgr = 0;
                    int taskCount = this.tasksComboBox.getItems().size();
                    for (Task task : this.tasksComboBox.getItems()) {
                        projProgr += (double) task.getProgress() / (double) taskCount;
                    }
                    this.projectProgress.setProgress(projProgr / 100);
                    this.projectProgressLabel.setText(String.format("%d %%", (int) projProgr));

                    this.projectDateLabel.setText(p.getStartDate().toString() + " - " + p.getEndDate().toString());
                }
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }
        });

        this.tasksComboBox.setOnAction(event -> {
            Task t = this.tasksComboBox.getValue();

            if (t != null) {
                for (Status st : this.taskStatusComboBox.getItems()) {
                    if (st.getId() == t.getStatusId()) {
                        this.taskStatusComboBox.setValue(st);
                        break;
                    }
                }
                for (Priority pt : this.taskPriorityComboBox.getItems()) {
                    if (pt.getId() == t.getPriorityId()) {
                        this.taskPriorityComboBox.setValue(pt);
                        break;
                    }
                }
                if (t.getAsignedId() > 0) {
                    for (Person p : this.taskAsignComboBox.getItems()) {
                        if (p.getId() == t.getAsignedId()) {
                            this.taskAsignComboBox.setValue(p);
                            break;
                        }
                    }
                } else {
                    this.taskAsignComboBox.setValue(null);
                }
                this.taskProgressLabel.setText(String.format("%d %%", t.getProgress()));
                this.taskProgress.setProgress((double) t.getProgress() / 100);
            }
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*") && change.getControlNewText().length() < 12) {
                return change;
            }
            return null;
        };

        this.personalIDAddField.setTextFormatter(new TextFormatter<String>(filter));
        this.personalIDEditField.setTextFormatter(new TextFormatter<String>(filter));

        try {
            this.taskStatusComboBox.getItems().addAll(db.listStatuses());
            this.taskPriorityComboBox.getItems().addAll(db.listPriorities());
            if (!this.isMgr) {
                this.projectMgrComboBox.getItems().addAll(db.listPeople(this.isMgr, this.isMgr ? this.user.getId() : -1).stream().filter(Person::isManager).collect(Collectors.toList()));
            }
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }

        this.updatePeopleEditComboBox();
        this.updateProjectsComboBox();
    }

    public void generateReport(ActionEvent actionEvent) {
        Project p = this.reportsComboBox.getValue();

        if (p != null) {
            Utils.generatePDFReport(null, p.getId(), db.getConnectionString());
        } else {
            Utils.alert("Informacja", "Wybierz projekt z listy", Alert.AlertType.INFORMATION);
        }
    }

    private boolean checkPersonalID(String personalID) {
        int[] wages = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        if (personalID.length() != 11) {
            return false;
        }
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Integer.parseInt(personalID.substring(i, i + 1)) * wages[i];
        }

        int controlNumber = Integer.parseInt(personalID.substring(10, 11));

        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        return (sum == controlNumber);
    }
}
