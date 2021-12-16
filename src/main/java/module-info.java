module com.iesebre.provascenebuilder {
    requires javafx.controls;
    requires javafx.fxml;
    //Per poder usar la biblioteca ControlsFX
    requires controlsfx;
    requires javax.persistence;
    requires java.sql;
    requires objectdb;

    opens com.iesebre.provascenebuilder to javafx.fxml;
    exports com.iesebre.provascenebuilder;
    exports com.iesebre.provascenebuilder.util;
    opens com.iesebre.provascenebuilder.util to javafx.fxml;

    //Per poder posar columnes no String a les TableColumn
    exports com.iesebre.provascenebuilder.model;
    opens com.iesebre.provascenebuilder.model to javafx.fxml;
}