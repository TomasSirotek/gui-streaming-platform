package com.javagui.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AbstractController implements Initializable {

    @FXML
    private VBox header;
    @FXML
    private GridPane companyCards;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int i = 0; i < 5; i++) {
            Label label = new Label("Test");
            String fileName = "/com/javagui/assets/totalrecall-1.jpeg";
            InputStream inputStream = LoginController.class.getResourceAsStream(fileName);
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);


            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

           // imageView.fitHeightProperty().bind(vBox.heightProperty().multiply(0.9));

            imageView.fitHeightProperty().bind(vBox.heightProperty().multiply(0.8));
            imageView.fitWidthProperty().bind(vBox.widthProperty().multiply(0.2));


            vBox.getChildren().add(imageView);
            vBox.getChildren().add(label);

            companyCards.add(vBox,i,0);
        }
    }
}
