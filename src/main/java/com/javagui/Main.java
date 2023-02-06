package com.javagui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("hello-view.fxml")));
        stage.setTitle("Movie Recommendation System 0.01 Beta");
        // primaryStage.setFullScreen(true);
        stage.sizeToScene();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setScene(new Scene(root));
        stage.show();

        Bounds rootBounds = root.getBoundsInLocal();
        double deltaW = stage.getWidth() - rootBounds.getWidth();
        double deltaH = stage.getHeight() - rootBounds.getHeight();

        Bounds prefBounds = getPrefBounds(root);

        stage.setMinWidth(prefBounds.getWidth() + deltaW);
        stage.setMinHeight(prefBounds.getHeight() + deltaH);
    }

    private Bounds getPrefBounds(Node node) {
        double prefWidth ;
        double prefHeight ;

        Orientation bias = node.getContentBias();
        if (bias == Orientation.HORIZONTAL) {
            prefWidth = node.prefWidth(-1);
            prefHeight = node.prefHeight(prefWidth);
        } else if (bias == Orientation.VERTICAL) {
            prefHeight = node.prefHeight(-1);
            prefWidth = node.prefWidth(prefHeight);
        } else {
            prefWidth = node.prefWidth(-1);
            prefHeight = node.prefHeight(-1);
        }
        return new BoundingBox(0, 0, prefWidth, prefHeight);
    }

    public static void main(String[] args) {
        launch();
    }
}
