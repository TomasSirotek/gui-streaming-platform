package com.javagui.gui.controller;

import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.Objects;

public abstract class AbstractController {
    protected Parent root;

    public Parent getView(){
        return this.root;
    }

    public void setView(Parent node){
        this.root= Objects.requireNonNull(node,"Error: View must not be null");
    }
    public Stage getStage(){
        return (Stage) root.getScene().getWindow();
    }
}
