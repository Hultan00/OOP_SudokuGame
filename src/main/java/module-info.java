module com.emilhu.oop_sudokugame {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.emilhu.oop_sudokugame to javafx.fxml;
    exports com.emilhu.oop_sudokugame;
}