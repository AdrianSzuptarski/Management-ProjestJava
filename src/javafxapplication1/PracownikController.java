/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafxapplication1.models.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author xyz
 */
public class PracownikController implements Initializable {

    private static Baza db;

    private Project pr;

    private Person user;

    @FXML
    private Label projectNameLabel;
    @FXML
    private ComboBox<Task> tasksComboBox;
    @FXML
    private ComboBox<Status> taskStatusComboBox;
    @FXML
    private ComboBox<Priority> taskPriorityComboBox;
    @FXML
    private TextArea projectDescriptionArea;
    @FXML
    private Slider taskProgressSlider;
    @FXML
    private Label taskProgressLabel;
    @FXML
    private TextArea noteTextArea;

    private FXMLDocumentController maincontroller;

    @FXML
    public void wyjsc() {
        maincontroller.loadMenuscreen();
    }

    public void setMaincontroller(FXMLDocumentController maincontroller) {
        this.maincontroller = maincontroller;
    }

    @FXML
    public void editTask() {
        if (tasksComboBox.getValue() == null) {
            Utils.alert("Popraw dane!", "Nie wybrano zadania do edycji!.", Alert.AlertType.WARNING);

        } else {
            try {
                int ret = db.updateTask(tasksComboBox.getValue().getId(), noteTextArea.getText(), ((int) taskProgressSlider.getValue()));

                if (ret <= 0) {
                    Utils.alert("Błąd edycji zadania!", "Nie udało się zapisać zedytowanego zadania.", Alert.AlertType.ERROR);
                } else {
                    Utils.alert("Sukces!", "Zadanie " + tasksComboBox.getValue().getName() + " zostało zapisane do bazy danych.", Alert.AlertType.INFORMATION);
                    tasksComboBox.getItems().clear();
                    this.noteTextArea.setText(null);
                    this.projectDescriptionArea.setText(null);
                    this.taskPriorityComboBox.setValue(null);
                    this.taskStatusComboBox.setValue(null);
                    tasksComboBox.getItems().setAll(db.listTasks(pr.getId(), user.getId()));
                }
            } catch (SQLException sqle) {
                Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    public void init(int id) {
        db = Baza.getInstance();
        pr = null;

        try {
            this.user = db.getPerson(id);
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }

        try {
            pr = db.getProject(this.user.getProjectID());
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }

        if (pr != null) {
            this.projectNameLabel.setText(pr.getName());
            this.projectDescriptionArea.setText(pr.getDesc());
        }

        this.tasksComboBox.setOnAction(event -> {
            Task t = this.tasksComboBox.getValue();

            if (t != null) {
                noteTextArea.setDisable(false);
                taskProgressSlider.setDisable(false);
                for (Status st : taskStatusComboBox.getItems()) {
                    if (st.getId() == t.getStatusId()) {
                        taskStatusComboBox.setValue(st);
                        break;
                    }
                }
                for (Priority pt : taskPriorityComboBox.getItems()) {
                    if (pt.getId() == t.getPriorityId()) {
                        taskPriorityComboBox.setValue(pt);
                        break;
                    }
                }
                taskProgressSlider.setValue(t.getProgress());
                noteTextArea.setText(t.getNote());
            }
        });

        this.taskProgressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.taskProgressLabel.setText(String.format("%d %%", newValue.intValue()));
        });

        try {
            this.tasksComboBox.getItems().addAll(db.listTasks(pr.getId(), user.getId()));
            this.taskStatusComboBox.getItems().addAll(db.listStatuses());
            this.taskPriorityComboBox.getItems().addAll(db.listPriorities());
        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noteTextArea.setDisable(true);
        taskProgressSlider.setDisable(true);
    }
}
