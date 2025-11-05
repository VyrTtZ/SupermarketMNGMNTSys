module com.example.supermarketmngmntsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires xstream;


    opens com.example.supermarketmngmntsys to javafx.fxml;
    exports com.example.supermarketmngmntsys;
}