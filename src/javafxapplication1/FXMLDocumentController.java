/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package javafxapplication1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * @author xyz
 */
public class FXMLDocumentController {

    @FXML
    private StackPane mainpane;

    @FXML
    public void initialize() {
        loadMenuscreen();
    }

    void loadMenuscreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("menuscreen.fxml"));
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuScreen menuScreen = loader.getController();
        menuScreen.setMaincontroller(this);

        setScreen(pane);
    }

    void setScreen(Pane pane) {
        mainpane.getChildren().clear();
        mainpane.getChildren().add(pane);
    }

    void setScreen(TabPane pane) {
        mainpane.getChildren().clear();
        mainpane.getChildren().add(pane);
    }

}
