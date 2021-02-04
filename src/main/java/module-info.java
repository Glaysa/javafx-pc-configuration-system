module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx to javafx.fxml;
    opens org.openjfx.controllers to javafx.fxml;
    opens org.openjfx.data_models to javafx.base;

    exports org.openjfx;
}