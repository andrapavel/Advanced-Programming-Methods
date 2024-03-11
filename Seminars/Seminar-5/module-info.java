module gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    opens main to javafx.fxml;

    exports gui;
    exports main;
}