package com.javagui.gui.controller;

import com.javagui.gui.model.AppModel;
import com.javagui.gui.model.CurrentUser;
import io.github.palexdev.materialfx.controls.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AbstractController implements Initializable {
    @FXML
    private Label loggedUser,
            errorLabel;
    @FXML
    private MFXButton navActionBtn,
            navItemOne,
            navItemTwo,
            navItemThree,
            tryLogin;
    @FXML

    private StackPane contentPane;
    @FXML
    private MFXPasswordField pswField;
    @FXML
    private MFXTextField emailField;
    @FXML
    private MFXProgressSpinner spinner;

    private AppModel appModel;

    private long timerStartMillis = 0;
    private String timerMsg = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.appModel = new AppModel();
       tryLogin.setOnAction(this::tryLogin);
    }

    private AbstractController loadNodes(String path) throws IOException {
        return ControllerFactory.loadFxmlFile(path);
    }

    private void swapViews(Parent parent) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
    }

    public void tryLogin(ActionEvent actionEvent) {
        if (isValidated()) {
            spinner.setVisible(true);
            errorLabel.setText("");

            Task<Void> loadDataTask = new Task<>() {
                @Override
                protected Void call() {
                    startTimer("Loading users ");
                    appModel.loadUsers();
                    return null;
                }
            };
            loadDataTask.setOnSucceeded(event -> {
                stopTimer();
                CurrentUser currentUser = CurrentUser.getInstance();
                startTimer("Logging in ...");
                currentUser.login(emailField.getText(), pswField.getText());
                if (currentUser.isAuthorized()) {
                    stopTimer();
                    setSettings(currentUser);
                    redirectHome();
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Wrong email or password");
                }
                spinner.setVisible(false);
            });
            new Thread(loadDataTask).start();
        }
    }


    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }


    private void setSettings(CurrentUser currentUser) {
        spinner.setVisible(false);
        navItemOne.setVisible(true);
        navItemTwo.setVisible(true);
        navItemThree.setVisible(true);
        loggedUser.setVisible(true);
        loggedUser.setText(currentUser.getLoggedUser().getName());
        navActionBtn.setText("LOG OUT");
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
        AbstractController finalParent = parent;
        HomeController homeController = (HomeController) finalParent;
        homeController.setParentController(this);
        homeController.setData(appModel,navActionBtn);
        swapViews(finalParent.getView());
    }

}