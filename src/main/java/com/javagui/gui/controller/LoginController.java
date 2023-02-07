package com.javagui.gui.controller;

import com.javagui.gui.model.CurrentUser;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AbstractController implements Initializable {
    @FXML
    private MFXScrollPane pane,pane1,pane2;
    private final CurrentUser currentUser = CurrentUser.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUI();
    }

    private void fillUI() {
        pane.setContent(constructGridPane());
        pane1.setContent(constructGridPane());
        pane2.setContent(constructGridPane());
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private GridPane constructGridPane(){
        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        for (int i = 0; i < 13 ; i++) {
            Label label = new Label("Test");
            String fileName = "/com/javagui/assets/totalrecall-1.jpeg";
            InputStream inputStream = LoginController.class.getResourceAsStream(fileName);
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(140);
            imageView.getStyleClass().add("image-card");
            // Create the text label
            label.getStyleClass().add("text-label");
            pane2.add(imageView,i,0);
        }
        return pane2;
    }


}
