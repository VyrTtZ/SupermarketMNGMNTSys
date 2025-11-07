package com.example.supermarketmngmntsys;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import LinkedList.LinkedList;

/**
 * DashboardController handles the supermarket dashboard view.
 * Displays all supermarkets, allows adding/removing them,
 * and lets the user open or save a selected supermarket.
 */
public class DashboardController {

    private final LinkedList<Supermarket> list = Parent.getMarkets();

    @FXML
    private GridPane marketsGrid;

    @FXML
    private TextField textInDash;

    private static Supermarket selectedSupermarket;

    public void initialize() {
        if (marketsGrid != null) refreshGrid();
    }

    @FXML
    private void addMarket() {
        String name = textInDash.getText().trim();
        if (name.isEmpty()) {
            System.out.println("⚠ Please enter a market name before adding.");
            return;
        }

        Supermarket newMarket = new Supermarket(name, new LinkedList<Floor>());
        list.add(newMarket);
        Parent.setMarkets(list);
        textInDash.clear();
        refreshGrid();
    }

    private void refreshGrid() {
        if (marketsGrid == null) return;

        // Reset grid before repopulating
        for (Object node : marketsGrid.getChildren()) {
            if (node instanceof Pane pane) {
                pane.getChildren().clear();
                pane.setOnMousePressed(null);
                pane.setStyle(
                        "-fx-background-color: #2f2f31; "
                                + "-fx-background-radius: 10; "
                                + "-fx-border-color: #3a3a3a; "
                                + "-fx-border-radius: 10; "
                                + "-fx-cursor: hand; "
                                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 6, 0, 0, 2);"
                );
            }
        }

        int count = Math.min(list.size(), marketsGrid.getChildren().size());

        for (int i = 0; i < count; i++) {
            Supermarket market = list.get(i);
            Pane pane = (Pane) marketsGrid.getChildren().get(i);

            // --- Name Label ---
            Label nameLabel = new Label(market.getName());
            nameLabel.setStyle(
                    "-fx-text-fill: #e0e0e0; "
                            + "-fx-font-family: 'Segoe UI Semibold'; "
                            + "-fx-font-size: 20px; "
                            + "-fx-font-weight: bold;"
            );
            nameLabel.setLayoutX(20);
            nameLabel.setLayoutY(15);

            // --- Floor Preview Canvas ---
            Canvas gridCanvas = new Canvas(160, 100);
            gridCanvas.setLayoutX(20);
            gridCanvas.setLayoutY(55);
            gridCanvas.setStyle("-fx-background-color: #1e1e1e; -fx-background-radius: 6;");

            if (!market.getFloors().isEmpty()) {
                Utilities.drawFloor(gridCanvas, market.getFloors().get(0));
            }

            // --- Erase Button ---
            Button eraseMarket = new Button("Erase");
            eraseMarket.setLayoutX(200);
            eraseMarket.setLayoutY(20);
            eraseMarket.setStyle(
                    "-fx-background-color: transparent; "
                            + "-fx-text-fill: #ff5c5c; "
                            + "-fx-font-size: 12px; "
                            + "-fx-font-weight: bold; "
                            + "-fx-border-color: #ff5c5c; "
                            + "-fx-border-radius: 6; "
                            + "-fx-padding: 2 8 2 8; "
                            + "-fx-cursor: hand;"
            );
            eraseMarket.setOnAction(event -> {
                list.remove(market);
                Parent.setMarkets(list);
                refreshGrid();
            });

            // --- Save Button ---
            Button saveButton = new Button("Save");
            saveButton.setLayoutX(265);
            saveButton.setLayoutY(20);
            saveButton.setStyle(
                    "-fx-background-color: #0078d7; "
                            + "-fx-text-fill: white; "
                            + "-fx-font-size: 12px; "
                            + "-fx-font-weight: bold; "
                            + "-fx-background-radius: 6; "
                            + "-fx-padding: 2 10 2 10; "
                            + "-fx-cursor: hand;"
            );
            saveButton.setOnAction(e -> {
                try {
                    Parent.save();
                    System.out.println("💾 Saved supermarket data successfully.");
                } catch (Exception ex) {
                    System.err.println("Error saving data: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            // --- Add children (label & buttons first so they appear above canvas) ---
            pane.getChildren().addAll(nameLabel, eraseMarket, saveButton, gridCanvas);

            // --- Click events for the pane ---
            pane.setOnMousePressed(e -> {
                if (e.isPrimaryButtonDown()) {
                    selectedSupermarket = market;
                    Launcher.setCurrentSupermarket(selectedSupermarket);
                    Launcher.setCurrentLoader(1);
                } else if (e.isSecondaryButtonDown()) {
                    list.remove(market);
                    Parent.setMarkets(list);
                    refreshGrid();
                }
            });

            // --- Hover Effect ---
            pane.setOnMouseEntered(e ->
                    pane.setStyle("-fx-background-color: #353538; -fx-background-radius: 10; "
                            + "-fx-border-color: #0078d7; -fx-border-radius: 10; "
                            + "-fx-cursor: hand;"));
            pane.setOnMouseExited(e ->
                    pane.setStyle("-fx-background-color: #2f2f31; -fx-background-radius: 10; "
                            + "-fx-border-color: #3a3a3a; -fx-border-radius: 10;"));
        }
    }

    public static Supermarket getSelectedSupermarket() {
        return selectedSupermarket;
    }
}
