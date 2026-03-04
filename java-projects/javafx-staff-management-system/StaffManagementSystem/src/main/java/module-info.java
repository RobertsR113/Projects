module com.example.staffmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example to javafx.fxml;
    opens com.example.Admin to javafx.fxml;
    opens com.example.Staff to javafx.fxml;
    opens com.example.Models to javafx.base, javafx.fxml;

    exports com.example;
}