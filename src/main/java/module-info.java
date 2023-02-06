module com.javagui.javagui {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens com.javagui to javafx.fxml;
    exports com.javagui;
    exports com.javagui.gui.controller;
    opens com.javagui.gui.controller to javafx.fxml;
}