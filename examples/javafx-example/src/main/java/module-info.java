module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    exports org.openjfx.controller;
    opens org.openjfx.controller to javafx.fxml;
}