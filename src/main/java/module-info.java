module com.example.supermarketmngmntsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires xstream;

    // Open all your main package for FXML and XStream
    opens com.example.supermarketmngmntsys to javafx.fxml, com.thoughtworks.xstream;

    // Open the LinkedList package (use top-level if that is what it is)
    opens com.example.supermarketmngmntsys.mylinkedlist to com.thoughtworks.xstream;  // <-- key change

    // Export main package for JavaFX application instantiation
    exports com.example.supermarketmngmntsys to javafx.graphics;
}
