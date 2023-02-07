package com.javagui.gui.controller;


import com.javagui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerFactory {

    public static AbstractController loadFxmlFile(String file) throws IOException {
        Objects.requireNonNull(file,"Error: FXML file must not be null");

        final URL fxmlFileURL = Main.class.getResource(file);
        final FXMLLoader loader = new FXMLLoader(fxmlFileURL);
        final Parent view = loader.load();
        final AbstractController controller = loader.getController();
        controller.setView(view);
        return controller;
    }
}
