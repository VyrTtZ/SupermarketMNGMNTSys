package com.example.supermarketmngmntsys;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import LinkedList.LinkedList;

import java.awt.*;

public class DashboardController {

    private LinkedList<Supermarket> list = Parent.getMarkets();

    @FXML
    private GridPane marketsGrid;

    @FXML
    private TextField textInDash;

    private static Supermarket selectedSupermarket;

    public void initialize() {
        if (marketsGrid != null) {
            refreshGrid();
        }
    }

    @FXML
    private void addMarket() {
        String name = textInDash.getText();
        if (name.isEmpty()) {
            System.out.println("Please input a name");
            return;
        }
        Supermarket s = new Supermarket(name, new LinkedList<Floor>());
        list.add(s);
        Parent.setMarkets(list);
        refreshGrid();
        textInDash.clear();
    }

    private void refreshGrid() {

        //-----------------------------SETS UP THE PANES
        if (marketsGrid == null) return;
        for (Object node : marketsGrid.getChildren()) {
            if (node instanceof Pane pane) {
                pane.getChildren().clear();
                pane.setOnMousePressed(null); // remove old handlers
            }
        }

        int size = Math.min(list.size(), marketsGrid.getChildren().size());

        //-------------------------------SETS UP THE SUPERMARKETS GRID AND ALLOWS FOR SELECTION
        for (int i = 0; i < size; i++) {
            Supermarket s = list.get(i);
            Pane pane = (Pane) marketsGrid.getChildren().get(i);
            Label label = new Label(s.getName());
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 30px;");
            Button eraseMarket = new Button("-");
            eraseMarket.setOnAction(actionEvent -> {
                LinkedList<Floor> emptyFloors = new LinkedList<Floor>();
                s.setFloors(emptyFloors);
            });
            Button saveButton = new Button("Save");
            saveButton.setOnAction(actionEvent -> {
                try {
                    Parent.save();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Canvas gridCanvas = new Canvas();
            pane.getChildren().addAll(label, eraseMarket, saveButton, gridCanvas);
            if(!s.getFloors().isEmpty())
                Utilities.drawFloor(gridCanvas, s.getFloors().get(0));



            pane.setOnMousePressed(e -> {
                if (e.isPrimaryButtonDown()) {
                    selectedSupermarket = s;
                    Launcher.setCurrentSupermarket(selectedSupermarket);
                    Launcher.setCurrentLoader(1);
                } else if (e.isSecondaryButtonDown()) {
                    list.remove(s);
                    Parent.setMarkets(list);
                    refreshGrid();
                }
            });

        }
        //---------------------------------
    }

    public static Supermarket getSelectedSupermarket() {
        return selectedSupermarket;
    }
}
