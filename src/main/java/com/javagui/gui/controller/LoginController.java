package com.javagui.gui.controller;

import com.javagui.gui.model.AppModel;
import com.javagui.gui.model.CurrentUser;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends AbstractController implements Initializable {
    @FXML
    private MFXScrollPane pane,pane1,pane2;

    private AppModel appModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appModel = new AppModel();
        fillUI();
    }

    private void fillUI() {
        pane.setContent(constructGridPane());
       // pane1.setContent(constructGridPane());
       // pane2.setContent(constructGridPane());
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      //  pane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
       // pane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private GridPane constructGridPane(){
        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(10);
        pane2.setAlignment(Pos.CENTER);


        for (int i = 0; i < 13 ; i++) {
            Label label = new Label("Test");

            label.setOpacity(0);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);
            label.setMinWidth(140);
            label.setMinHeight(30);
            label.setTextFill(Color.WHITE);

            label.setOnMouseEntered(event -> {
                label.setOpacity(1);
                label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
            });
            label.setOnMouseExited(event -> label.setOpacity(0));

            String fileName = "/com/javagui/assets/totalrecall-1.jpeg";
            InputStream inputStream = LoginController.class.getResourceAsStream(fileName);
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);

            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(140);


            StackPane stack = new StackPane();
            stack.getChildren().addAll(imageView, label);
            stack.getStyleClass().add("image-card");
            stack.setAlignment(Pos.CENTER);
            GridPane.setHgrow(stack, Priority.ALWAYS);

            pane2.add(stack, i, 0);
        }
        return pane2;
    }


}
