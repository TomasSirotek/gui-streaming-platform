package com.javagui.gui.controller;

import com.javagui.gui.model.CurrentUser;
import io.github.palexdev.materialfx.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController extends AbstractController implements Initializable {
    @FXML
    private Label loggedUser;
    @FXML
    private Label errorLabel;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    private AbstractController loadNodes(String path) throws IOException {
        return ControllerFactory.loadFxmlFile(path);
    }

    private void swapViews(Parent parent){
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
    }

    @FXML
    public void tryLogin(ActionEvent actionEvent) {
        redirectHome();
//        if(isValidated()){
//            CurrentUser currentUser = CurrentUser.getInstance();
//            currentUser.login(emailField.getText(),pswField.getText());
//            if(currentUser.isAuthorized()){
//                redirectHome();
//                actionEvent.consume();
//            }else {
//                errorLabel.setVisible(true);
//                errorLabel.setText("Wrong email or password");
//                actionEvent.consume();
//            }
//        }
    }

    private boolean isValidated() {
        boolean isValidated = false;
        if (emailField.getText().isEmpty() || pswField.getText().isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Warning: Cannot be empty");
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private void redirectHome() {

        AbstractController parent = null;
        try {
            parent = loadNodes("home-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

       // spinner.setVisible(true);

        AbstractController finalParent = parent;
        swapViews(finalParent.getView());
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000); // change to like 5s
//                Platform.runLater(() -> {
//                    CurrentUser c = CurrentUser.getInstance();
//                    spinner.setVisible(false);
//                    navItemOne.setVisible(true);
//                    navItemTwo.setVisible(true);
//                    navItemThree.setVisible(true);
//                    loggedUser.setVisible(true);
//                    loggedUser.setText(c.getUserName());
//                    navActionBtn.setText("LOG OUT");
//
//                    swapViews(finalParent.getView());
//                });
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

    }

}