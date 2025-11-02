package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MarketController {

    @FXML private Pane namePane;
    @FXML private Pane treePane;
    @FXML private VBox editPane;
    @FXML private VBox addOptionsPane;
    @FXML private Canvas gridCanvas;
    @FXML private Spinner floorNum;

    private Supermarket selectedSupermarket;
    private Object currentObject;
    private final Map<TreeItem<String>, Object> treeMap = new HashMap<>();

    // --------------------------------------------------------------------------------
    @FXML
    private void initialize() {
        editPane.setSpacing(10);
        editPane.setPadding(new Insets(15));
        setupAddRows();

    }

    // --------------------------------------------------------------------------------
    public void setSupermarket(Supermarket supermarket) {
        this.selectedSupermarket = supermarket;
        updateUI();
    }

    @FXML
    private void updateUIForScene() {
        updateUI();
    }

    private void updateUI() {
        if (selectedSupermarket == null) return;

        namePane.getChildren().clear();
        Label nameLabel = new Label(selectedSupermarket.getName());
        namePane.getChildren().add(nameLabel);
        /*SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1*selectedSupermarket.getFloors().size(), selectedSupermarket.getFloors().size(), 0, 1);

        floorNum.setValueFactory(valueFactory);
        floorNum.setEditable(true);*/

        buildTree();
    }

    private Floor numToFloor(int i){
        for(Floor f : selectedSupermarket.getFloors()){
            if(f.getLevel() == i) return f;
        }
        return null;
    }

    // --------------------------------------------------------------------------------
    private void setupAddRows() {
        addOptionsPane.getChildren().clear();
        addOptionsPane.getChildren().add(createFloorRow());
        addOptionsPane.getChildren().add(createFloorAreaRow());
        addOptionsPane.getChildren().add(createAisleRow());
        addOptionsPane.getChildren().add(createShelfRow());
        addOptionsPane.getChildren().add(createGoodRow());

        Button eraseMarket = new Button("Erase market data");
        eraseMarket.setOnAction(actionEvent -> {
            eraseMarket();

            buildTree();
            drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
        });
        addOptionsPane.getChildren().add(eraseMarket);
    }


    private HBox createFloorRow() {
        Label label = new Label("Floor:");

        TextField levelField = new TextField();
        levelField.setPromptText("Level");

        TextField xField = new TextField();
        xField.setPromptText("X");

        TextField yField = new TextField();
        yField.setPromptText("Y");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int level = Integer.parseInt(levelField.getText().trim());
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                LinkedList<Integer> size = new LinkedList<Integer>();
                size.add(x); size.add(y);
                selectedSupermarket.getFloors().add(new Floor(level, new LinkedList<FloorArea>(), size));
                levelField.clear(); xField.clear(); yField.clear();
                drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
                buildTree();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for floor. Use numeric values for level, X, and Y.");
            }
        });

        HBox row = new HBox(5, label, levelField, xField, yField, addButton);
        return row;
    }

    // --------------------------------------------------------------------------------
    private HBox createFloorAreaRow() {
        Label label = new Label("FloorArea:");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField xField = new TextField();
        xField.setPromptText("X");

        TextField yField = new TextField();
        yField.setPromptText("Y");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            if (!(currentObject instanceof Floor f)) {
                showAlert("Select a Floor first to add a FloorArea.");
                return;
            }
            try {
                String name = nameField.getText().trim();
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                LinkedList<Integer> size = new LinkedList<Integer>();
                size.add(x); size.add(y);
                f.getFloorAreas().add(new FloorArea(name, new LinkedList<Aisle>(), size));
                nameField.clear(); xField.clear(); yField.clear();
                drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
                buildTree();
            } catch (NumberFormatException ex) {
                showAlert("Invalid X/Y values for FloorArea.");
            }
        });

        HBox row = new HBox(5, label, nameField, xField, yField, addButton);
        return row;
    }

    // --------------------------------------------------------------------------------
    private HBox createAisleRow() {
        Label label = new Label("Aisle:");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");;

        TextField xField = new TextField();
        xField.setPromptText("X");

        TextField yField = new TextField();
        yField.setPromptText("Y");

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().addAll("Room temperature", "Refrigerated", "Frozen");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            if (!(currentObject instanceof FloorArea fa)) {
                showAlert("Select thing"); //fix
                return;
            }
            try {
                String name = nameField.getText().trim();
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                LinkedList<Integer> size = new LinkedList<Integer>();
                size.add(x); size.add(y);
                int temperatureFinal = switch (temp.getValue().toString()) {
                    case "Room temperature" -> 1;
                    case "Refrigerated" -> 0;
                    case "Frozen" -> -1;
                    default -> 1;
                };
                fa.getAisles().add(new Aisle(name, new LinkedList<Shelf>(), size, temperatureFinal));
                nameField.clear(); xField.clear(); yField.clear();
                drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
                buildTree();
            } catch (NumberFormatException ex) {
                showAlert("Invalid values for Aisle.");
            }
        });

        HBox row = new HBox(5, label, nameField, xField, yField, temp, addButton);
        return row;
    }

    // --------------------------------------------------------------------------------
    private HBox createShelfRow() {
        Label label = new Label("Shelf:");

        TextField numberField = new TextField();
        numberField.setPromptText("Number");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            if (!(currentObject instanceof Aisle a)) {
                showAlert("Select thing");
                return;
            }
            a.getShelves().add(new Shelf(Integer.parseInt(numberField.getText().trim()), new LinkedList<GoodItem>(), new LinkedList<Integer>()));
            numberField.clear();
            drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            buildTree();
        });

        HBox row = new HBox(5, label, numberField, addButton);
        return row;
    }

    // --------------------------------------------------------------------------------
    private HBox createGoodRow() {
        Label label = new Label("Good:");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Qty");

        TextField massField = new TextField();
        massField.setPromptText("Mass");

        TextField sizeField = new TextField();
        sizeField.setPromptText("Size");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            if (!(currentObject instanceof Shelf s)) {
                showAlert("");
                return;
            }
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double mass = Double.parseDouble(massField.getText().trim());
                int size = Integer.parseInt(sizeField.getText().trim());

                s.getGoods().add(new GoodItem(name, new LinkedList<Shelf>(), price, quantity, mass, size));
                nameField.clear(); priceField.clear(); quantityField.clear(); massField.clear(); sizeField.clear();
                drawFloor(numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
                buildTree();
            } catch (NumberFormatException ex) {
                showAlert("Invalid price");
            }
        });

        HBox row = new HBox(5, label, nameField, priceField, quantityField, massField, sizeField, addButton);
        return row;
    }


    // --------------------------------------------------------------------------------
    private void buildTree() { // https://jenkov.com/tutorials/javafx/treeview.html
        treePane.getChildren().clear();
        treeMap.clear();

        TreeItem<String> root = new TreeItem<>(selectedSupermarket.getName());
        treeMap.put(root, selectedSupermarket);

        addToTree(selectedSupermarket.getFloors(), root);

        TreeView<String> treeView = new TreeView<>(root);

        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) currentObject = treeMap.get(newItem);
        });

        treePane.getChildren().add(treeView);
    }
    private <T> void addToTree(LinkedList<T> list, TreeItem<String> parent) {
        if (list == null) return;

        for (T item : list) {
            String name;
            if (item instanceof Floor f) name = "Floor " + f.getLevel();
            else if (item instanceof FloorArea fa) name = "Area: " + fa.getName();
            else if (item instanceof Aisle a) name = "Aisle: " + a.getName();
            else if (item instanceof Shelf s) name = "Shelf: " + s.getNumber();
            else if (item instanceof GoodItem g) name = "Good: " + g.getName();
            else name = item.toString();

            TreeItem<String> node = new TreeItem<>(name);
            treeMap.put(node, item);
            parent.getChildren().add(node);

            if (item instanceof Floor f) addToTree(f.getFloorAreas(), node);
            else if (item instanceof FloorArea fa) addToTree(fa.getAisles(), node);
            else if (item instanceof Aisle a) addToTree(a.getShelves(), node);
            else if (item instanceof Shelf s) addToTree(s.getGoods(), node);
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Action Not Allowed");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void ret() {
        Launcher.setCurrentLoader(0);
    }

    private void drawFloor(Floor floor) {
        if (selectedSupermarket == null || floor == null) return;

        var gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());

        double canvasWidth = gridCanvas.getWidth();
        double canvasHeight = gridCanvas.getHeight();

        LinkedList<Integer> floorSize = floor.getSize();
        double floorWidth = floorSize.get(0);
        double floorHeight = floorSize.get(1);

        double scaleX = canvasWidth / (floorWidth + 20);
        double scaleY = canvasHeight / (floorHeight + 20);
        double scale = Math.min(scaleX, scaleY);

        double xOffset = (canvasWidth - floorWidth * scale) / 2;
        double yOffset = (canvasHeight - floorHeight * scale) / 2;

        gc.setFill(Color.BLUE);
        gc.fillRect(xOffset, yOffset, floorWidth * scale, floorHeight * scale);
        gc.setStroke(Color.GREEN);
        gc.strokeText("Floor " + floor.getLevel(), xOffset + 5, yOffset + 15);

        double faYOffset = yOffset + 20;
        for (FloorArea fa : floor.getFloorAreas()) {
            LinkedList<Integer> faSize = fa.getSize();
            double faWidth = faSize.get(0) * scale;
            double faHeight = faSize.get(1) * scale;

            double faXOffset = xOffset + 10;
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(faXOffset, faYOffset, faWidth, faHeight);
            gc.setStroke(Color.WHITE);
            gc.strokeText(fa.getName(), faXOffset + 5, faYOffset + 15);

            double aisleYOffset = faYOffset + 10;
            for (Aisle a : fa.getAisles()) {
                LinkedList<Integer> aisleSize = a.getSize();
                double aWidth = aisleSize.get(0) * scale;
                double aHeight = aisleSize.get(1) * scale;

                double aisleXOffset = faXOffset + 5;
                gc.setFill(Color.DARKRED);
                gc.fillRect(aisleXOffset, aisleYOffset, aWidth, aHeight);
                gc.setStroke(Color.WHITE);
                gc.strokeText(a.getName(), aisleXOffset + 5, aisleYOffset + 15);

                aisleYOffset += aHeight + 5;
            }

            faYOffset += faHeight + 10;
        }
    }


    private void eraseMarket(){
        LinkedList<Floor> emptyFloors = new LinkedList<Floor>();
        selectedSupermarket.setFloors(emptyFloors);

    }


}
