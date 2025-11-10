module com.example.supermarketmngmntsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires xstream;

    opens com.example.supermarketmngmntsys to javafx.fxml, xstream;
    opens com.example.supermarketmngmntsys.mylinkedlist to xstream;
    exports com.example.supermarketmngmntsys to javafx.graphics;
}