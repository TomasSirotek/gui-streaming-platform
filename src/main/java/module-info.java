module com.javagui.javagui {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    exports com.javagui;
    exports com.javagui.gui.controller;
    opens com.javagui to javafx.fxml;
    opens com.javagui.gui.controller to javafx.fxml;
}