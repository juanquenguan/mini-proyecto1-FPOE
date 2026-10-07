/**
 * Game module configuration.
 */
module com.example.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.escriturarapida;
    exports com.example.escriturarapida.view;

    opens com.example.escriturarapida.controller to javafx.fxml;
}