/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Mirai
 */
public class MenuScreen extends Application {

    private static Baza db;

    private FXMLDocumentController maincontroller;

    private Stage stage;

    @FXML
    public TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;


    @FXML
    public void login() {
        String pass = passwordField.getText();
        String username = usernameField.getText();

        try {
            int id = db.login(username, pass);
            if (id >= 0) {
                if (username.equals("admin") || db.isManager(username)) {
                    this.openMgmtPanel(!username.equals("admin") && db.isManager(username), id);
                } else {
                    this.openWorkerPanel(id);
                }
            } else {
                this.errorLabel.setTextFill(Color.RED);
                this.errorLabel.setText("Nieprawidłowy login lub hasło!");
                Utils.alert("Błąd logowania!", "Nieprawidłowy login lub hasło!", AlertType.ERROR);
            }


        } catch (SQLException sqle) {
            Utils.alert("Błąd zapytania!", sqle.getMessage(), AlertType.ERROR);
        }
    }

    private void openMgmtPanel(boolean isMgr, int id) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Management.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ManagementController mgmtcontroller = loader.getController();
        mgmtcontroller.init(isMgr, id);
        mgmtcontroller.setMaincontroller(maincontroller);
        maincontroller.setScreen(pane);
    }

    private void openWorkerPanel(int id) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Pracownik.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PracownikController pracownikcontroller = loader.getController();
        pracownikcontroller.setMaincontroller(maincontroller);
        pracownikcontroller.init(id);
        maincontroller.setScreen(pane);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        this.stage.setScene(scene);
        this.stage.setTitle("Zarządzanie zadaniami");
        this.stage.show();
        db = Baza.getInstance();
    }

    void setMaincontroller(FXMLDocumentController maincontroller) {
        this.maincontroller = maincontroller;
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
