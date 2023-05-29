module com.example.integrativetask_ii_ced {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integrativetask_ii_ced to javafx.fxml;
    exports com.example.integrativetask_ii_ced;
    exports com.example.integrativetask_ii_ced.model.drawing;
    opens com.example.integrativetask_ii_ced.model.drawing to javafx.fxml;
    exports com.example.integrativetask_ii_ced.model.entities;
    opens com.example.integrativetask_ii_ced.model.entities to javafx.fxml;

}