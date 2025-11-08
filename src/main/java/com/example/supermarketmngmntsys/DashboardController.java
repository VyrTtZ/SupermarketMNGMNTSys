package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DashboardController {


    @FXML private GridPane marketsGrid;
    @FXML public TextField textInDash;
    private Launcher launcher;
    private LinkedList<Supermarket> supermarkets;


    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
        this.supermarkets = Parent.getMarkets();
        refreshGrid();
    }

    @FXML
    private void initialize() {}

    @FXML
    public void addMarket() {
        String name = textInDash.getText().trim();
        if (name.isEmpty()) {
            showAlert("Please enter a market name before adding.");
            return;
        }

        Supermarket newMarket = new Supermarket(name, new LinkedList<Floor>());
        supermarkets.add(newMarket);
        Parent.setMarkets(supermarkets);
        textInDash.clear();
        refreshGrid();
    }

    public void refreshGrid() {
        if (marketsGrid == null || supermarkets == null) return;

        for (var node : marketsGrid.getChildren()) {
            if (node instanceof Pane pane) {
                pane.getChildren().clear();
                pane.setOnMousePressed(null);
                pane.setStyle(basePaneStyle());
            }
        }

        int count = Math.min(supermarkets.size(), marketsGrid.getChildren().size());
        for (int i = 0; i < count; i++) {
            Supermarket market = supermarkets.get(i);
            Pane pane = (Pane) marketsGrid.getChildren().get(i);
            populateMarketPane(pane, market);
        }
    }

    private void populateMarketPane(Pane pane, Supermarket market) {
        Label nameLabel = new Label(market.getName());
        nameLabel.setStyle("""
                -fx-text-fill: #e0e0e0;
                -fx-font-family: 'Segoe UI Semibold';
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);
        nameLabel.setLayoutX(20);
        nameLabel.setLayoutY(15);

        Canvas gridCanvas = new Canvas(160, 100);
        gridCanvas.setLayoutX(20);
        gridCanvas.setLayoutY(55);
        gridCanvas.setStyle("-fx-background-color: #1e1e1e; -fx-background-radius: 6;");
        if (!market.getFloors().isEmpty()) {
            Utilities.drawFloor(gridCanvas, market.getFloors().get(0));
        }

        Button eraseButton = new Button("Erase");
        eraseButton.setLayoutX(200);
        eraseButton.setLayoutY(20);
        eraseButton.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #ff5c5c;
                -fx-font-size: 12px;
                -fx-font-weight: bold;
                -fx-border-color: #ff5c5c;
                -fx-border-radius: 6;
                -fx-padding: 2 8 2 8;
                -fx-cursor: hand;
                """);
        eraseButton.setOnAction(e -> {
            supermarkets.remove(market);
            Parent.setMarkets(supermarkets);
            refreshGrid();
        });

        Button saveButton = new Button("Save");
        saveButton.setLayoutX(265);
        saveButton.setLayoutY(20);
        saveButton.setStyle("""
                -fx-background-color: #0078d7;
                -fx-text-fill: white;
                -fx-font-size: 12px;
                -fx-font-weight: bold;
                -fx-background-radius: 6;
                -fx-padding: 2 10 2 10;
                -fx-cursor: hand;
                """);
        saveButton.setOnAction(e -> {
            try {
                Parent.save();
                showInfo("Saved supermarket data successfully.");
            } catch (Exception ex) {
                showAlert("Error saving data: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        pane.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) {
                openSupermarket(market);
            } else if (e.isSecondaryButtonDown()) {
                supermarkets.remove(market);
                Parent.setMarkets(supermarkets);
                refreshGrid();
            }
        });

        pane.setOnMouseEntered(e -> pane.setStyle(hoverPaneStyle()));
        pane.setOnMouseExited(e -> pane.setStyle(basePaneStyle()));

        pane.getChildren().addAll(nameLabel, eraseButton, saveButton, gridCanvas);
    }

    private void openSupermarket(Supermarket market) {
        if (launcher == null) {
            System.err.println("Launcher not set on DashboardController");
            return;
        }
        try {
            launcher.showMarket(market);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to open supermarket: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String basePaneStyle() {
        return """
                -fx-background-color: #2f2f31;
                -fx-background-radius: 10;
                -fx-border-color: #3a3a3a;
                -fx-border-radius: 10;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 6, 0, 0, 2);
                """;
    }

    private String hoverPaneStyle() {
        return """
                -fx-background-color: #353538;
                -fx-background-radius: 10;
                -fx-border-color: #0078d7;
                -fx-border-radius: 10;
                -fx-cursor: hand;
                """;
    }
}
