module com.javagui.javagui {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires feign.core;
    requires feign.gson;
    requires org.slf4j;

    exports com.javagui;
    exports com.javagui.gui.controller;
    exports com.javagui.gui.model;
    exports com.javagui.be;
    exports com.javagui.bll;
    opens com.javagui to javafx.fxml, feign.core, feign.gson, org.slf4j;
    opens com.javagui.gui.controller to javafx.fxml, feign.core, feign.gson, org.slf4j;
    opens com.javagui.bll to feign.gson, feign.core, org.slf4j;
    opens com.javagui.gui.model to feign.gson, feign.core;
}