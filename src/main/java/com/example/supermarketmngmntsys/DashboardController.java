package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.*;

//----------------------------------------------------------------------------------------------------------------------
public class DashboardController { //FIELDS
    @FXML private GridPane marketsGrid;
    @FXML public TextField textInDash;
    private Launcher mlauncher;
    private MyLinkedList<Supermarket> supermarkets;
    //----------------------------------------------------------------------------------------------------------------------
    public void setLauncher(Launcher launcher) {// SETS THE LAUNCHER FOR THE CONTROLLER INSTANCE AND SETS THE SUPERMARKET LIST + REFRESHES GRID FOR ANY NEW MARKETS
        mlauncher = launcher;
        this.supermarkets = Parent.getMarkets();
        refreshGrid();
    }
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    public void addMarket() { //GETS THE DATA FROM THE FXML FIELD AND UPON BUTTON CLICK ADDS THE MARKET TO THE SUPERMARKETS LINKED LIST
        String name = textInDash.getText().trim();
        if (name.isEmpty()) {//NOTHING HAPPENS IF THERE IS NO NAME
            Utilities.showAlert("Please enter a market name before adding.");
            return;
        }
        for(Supermarket s : supermarkets){ //CHECKS FOR NAME COLLISIONS WITH OTHER EXISTING SUPERMARKETS
            if(s.getName().equals(name)) {
                Utilities.showAlert("A Supermarket with this name already exists!");
                return;
            }
        }
        Supermarket newMarket = new Supermarket(name, new MyLinkedList<Floor>());
        supermarkets.add(newMarket);
        Parent.setMarkets(supermarkets);
        textInDash.clear();
        refreshGrid();
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void refreshGrid() { //REFRESHES THE SUPERMARKET GRID
        if (marketsGrid == null || supermarkets == null) return;

        for (Object node : marketsGrid.getChildren()) {//STYLES AND PREPARES THE GRID FOR ADDITION OF MARKETS
            if (node instanceof Pane pane) {
                pane.getChildren().clear();
                pane.setOnMousePressed(null);
                pane.setStyle("""
            -fx-background-color: #2f2f31;
            -fx-background-radius: 10;
            -fx-border-color: #3a3a3a;
            -fx-border-radius: 10;
        """);
            }
        }

        int count = Math.min(supermarkets.size(), marketsGrid.getChildren().size());//THE AMOUNT OF SUPERMARKETS OR THE AMOUNT OF AVAILABLE PANES
        for (int i = 0; i < count; i++) {//GETS ALL THE SUPERMARKETS AND ADDS THEM TO THE PANES IN GRID WITH USE OF POPULATEMARKETS()
            Supermarket market = supermarkets.get(i);
            Pane pane = (Pane) marketsGrid.getChildren().get(i);
            populateMarkets(pane, market);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void populateMarkets(Pane pane, Supermarket market) {//SETS UP THE PANE AND ADDS MARKET TO IT, WITH STYLING
        Label nameLabel = new Label(market.getName()); //NAME OF MARKET
        nameLabel.setStyle(""" 
            -fx-text-fill: #e0e0e0;
            -fx-font-family: 'Segoe UI Semibold';
            -fx-font-size: 20px;
            -fx-font-weight: bold;
        """);//STYLING THE LABEL AND SETTING ITS LAYOUT WITHIN THE PANE
        nameLabel.setLayoutX(20);
        nameLabel.setLayoutY(15);

        Canvas gridCanvas = new Canvas(140, 90); //NEW CANVAS FOR THE DIAGRAM OF GROUND FLOOR OF THE MARKET
        gridCanvas.setLayoutX(20);
        gridCanvas.setLayoutY(55);
        if (!market.getFloors().isEmpty()) { //NOTHING IS DRAWN IF MARKET IS EQUAL TO NULL
            Utilities.drawFloor(gridCanvas, market.getFloors().get(0));
        }
        //--------------------------------------------------
        Button eraseButton = new Button("Erase"); //ADD THE ERASE MARKET BUTTON TO THE PANE
        eraseButton.setLayoutX(10);
        eraseButton.setLayoutY(160);
        eraseButton.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #ff5c5c;
            -fx-font-size: 12px;
            -fx-font-weight: bold;
            -fx-border-color: #ff5c5c;
            -fx-border-radius: 6;
            -fx-cursor: hand;
        """);//SETS THE ERASE BUTTON STYLING
        eraseButton.setOnAction(e -> {
            market.setFloors(new MyLinkedList<Floor>());
            refreshGrid();
        });
        //--------------------------------------------------
        Button saveButton = new Button("Save");//ADD THE SAVE MARKET BUTTON TO THE PANE
        saveButton.setLayoutX(120);
        saveButton.setLayoutY(160);
        saveButton.setStyle("""
            -fx-background-color: #0078d7;
            -fx-text-fill: white;
            -fx-font-size: 12px;
            -fx-font-weight: bold;
            -fx-background-radius: 6;
        """);//SETS THE ADD BUTTON STYLING
        saveButton.setOnAction(e -> {//ADDS ON ACTION SAVING THE MARKET TO XML
            try {
                    XStream xStream = new XStream(new DomDriver());
                    ObjectOutputStream objectOutputStream = xStream.createObjectOutputStream(new FileWriter("superMarketManagementSys.xml"));
                    objectOutputStream.writeObject(market);
                    objectOutputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //--------------------------------------------------
        pane.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) { //PRIMARY BUTTON OPENS THE MARKET CONTROLLER + THE SELECTED SUPERMARKET
                openSupermarket(market);
            } else if (e.isSecondaryButtonDown()) { //SECONDARY BUTTON REMOVES THE MAREKT FROM PARENT SUPERMARKET LIST
                supermarkets.remove(market);
                Parent.setMarkets(supermarkets);
                refreshGrid();
            }
        });
        //--------------------------------------------------
        pane.setOnMouseEntered(e -> pane.setStyle("""
            -fx-background-color: #353538;
            -fx-background-radius: 10;
            -fx-border-color: #0078d7;
            -fx-border-radius: 10;
        """));
        pane.setOnMouseExited(e -> pane.setStyle("""
            -fx-background-color: #2f2f31;
            -fx-background-radius: 10;
            -fx-border-color: #3a3a3a;
            -fx-border-radius: 10;
        """));

        pane.getChildren().addAll(nameLabel, eraseButton, saveButton, gridCanvas); //ADDS ALL THE ELEMENTS TO THE PANE
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void openSupermarket(Supermarket market) {//OPENS SUPERMARKET
        try {//SHOWS MARKET + TRANSFER TO THE MARKET CONTROLLER
            mlauncher.showMarket(market);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    private void loadMarket() throws IOException, ClassNotFoundException {
        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Supermarket.class, Floor.class, FloorArea.class, Aisle.class, Shelf.class, GoodItem.class});
        ObjectInputStream is = xStream.createObjectInputStream(new FileReader("superMarketManagementSys.xml"));
        supermarkets.add((Supermarket) is.readObject());
        is.close();
        refreshGrid();
    }

}
