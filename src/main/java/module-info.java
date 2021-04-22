module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    opens org.openjfx.controllers to javafx.fxml;
    opens org.openjfx.dataModels to javafx.base;

    exports org.openjfx;
}