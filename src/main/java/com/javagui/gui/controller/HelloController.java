package com.javagui.gui.controller;

import com.javagui.Main;
import io.github.palexdev.materialfx.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController extends AbstractController implements Initializable {
    @FXML
    private MFXProgressSpinner spinner;
    @FXML
    private MFXButton navActionBtn;
    @FXML
    private MFXButton navItemOne;
    @FXML
    private MFXButton navItemTwo;
    @FXML
    private MFXButton navItemThree;

    @FXML

    private StackPane contentPane;
    @FXML
    private MFXPasswordField pswField;
    @FXML
    private MFXTextField emailField;
    @FXML
    private MFXButton tryLogin;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label welcomeText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // tryLogin.setOnAction(e -> tryLogin(e,"login-view.fxml"));
    }

    private void tryLogin(ActionEvent actionEvent,String path) {

    }

    private AbstractController loadNodes(String path) throws IOException {
        return ControllerFactory.loadFxmlFile(path);
    }

    private void swapViews(Parent parent){
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
    }


    public void tryLogin(ActionEvent actionEvent) {
        AbstractController parent = null;
        try {
            parent = loadNodes("login-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        spinner.setVisible(true);

        AbstractController finalParent = parent;
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(() -> {
                    spinner.setVisible(false);
                    swapViews(finalParent.getView());

                    navItemOne.setVisible(true);
                    navItemTwo.setVisible(true);
                    navItemThree.setVisible(true);
                    navActionBtn.setText("LOG OUT");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        actionEvent.consume();
    }

}