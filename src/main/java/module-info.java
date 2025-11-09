module com.example.supermarketmngmntsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires xstream;

    opens com.example.supermarketmngmntsys to javafx.fxml, com.thoughtworks.xstream;
    opens com.example.supermarketmngmntsys.mylinkedlist to com.thoughtworks.xstream;
    exports com.example.supermarketmngmntsys to javafx.graphics;
}
