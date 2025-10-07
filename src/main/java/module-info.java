module com.example.supermarketmngmntsys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.supermarketmngmntsys to javafx.fxml;
    exports com.example.supermarketmngmntsys;
}