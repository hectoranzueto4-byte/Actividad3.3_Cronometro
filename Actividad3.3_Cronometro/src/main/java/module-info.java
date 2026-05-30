module com.yazidsistems.app.cronometro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.yazidsistems.app.cronometro to javafx.fxml;
    exports com.yazidsistems.app.cronometro;
}