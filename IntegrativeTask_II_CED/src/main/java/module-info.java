module com.example.integrativetask_ii_ced {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.integrativetask_ii_ced to javafx.fxml;
    exports com.example.integrativetask_ii_ced;
}