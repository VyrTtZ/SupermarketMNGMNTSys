package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

public class MarketController {

    @FXML
    private Pane namePane;
    @FXML
    private TreeView<String> treePane;
    @FXML
    private VBox editPane;
    @FXML
    private VBox addOptionsPane;
    @FXML
    private Canvas gridCanvas;
    @FXML
    private Spinner<Integer> floorNum;
    @FXML
    private VBox reportPane;
    @FXML
    private Pane stockPane;
    @FXML
    private Pane searchPane;
    @FXML
    private TextField searchBar;
    @FXML
    private Pane sliderPane;
    private Supermarket selectedSupermarket;
    private Object currentObject;
    private final Map<TreeItem<String>, Object> treeMap = new HashMap<>();
    private Launcher app;

    @FXML
    private void initialize() {
        editPane.setSpacing(10);
        editPane.setPadding(new Insets(15));
        setupAddRows();
    }

    public void setSupermarket(Supermarket supermarket) {
        this.selectedSupermarket = supermarket;
        updateUI();
    }

    public void initData(Launcher app, Supermarket supermarket) {
        this.app = app;
        this.selectedSupermarket = supermarket;
        updateUI();
    }

    private void updateUI() {
        if (selectedSupermarket == null) return;

        namePane.getChildren().clear();
        namePane.getChildren().add(new Label(selectedSupermarket.getName()));

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-selectedSupermarket.getFloors().size(), selectedSupermarket.getFloors().size(), 0, 1);
        floorNum.setValueFactory(valueFactory);
        floorNum.setEditable(true);

        reports();
        buildTree();
        showAllStock();

        Utilities.drawFloor(gridCanvas, numToFloor(floorNum.getValue()));
    }


    public Floor numToFloor(int level) {
        for (Floor f : selectedSupermarket.getFloors()) {
            if (f.getLevel() == level) return f;
        }
        return null;
    }

    private void setupAddRows() {
        addOptionsPane.getChildren().clear();
        addOptionsPane.getChildren().add(createFloorRow());
        addOptionsPane.getChildren().add(createFloorAreaRow());
        addOptionsPane.getChildren().add(createAisleRow());
        addOptionsPane.getChildren().add(createShelfRow());
        addOptionsPane.getChildren().add(createGoodRow());

    }

    private HBox createFloorRow() {
        final HBox row = Utilities.createRow("Floor:", 3, null);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddFloor(row));

        return row;
    }

    private void handleAddFloor(HBox row) {
        try {
            TextField levelField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);

            int level = Integer.parseInt(levelField.getText().trim());
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            LinkedList<Integer> size = new LinkedList<Integer>();
            size.add(x);
            size.add(y);

            selectedSupermarket.getFloors().add(new Floor(level, new LinkedList<FloorArea>(), size));

            levelField.clear();
            xField.clear();
            yField.clear();

            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            buildTree();
        } catch (NumberFormatException ex) {
            showAlert("Invalid input");
        }
    }

    private HBox createFloorAreaRow() {
        final HBox row = Utilities.createRow("FloorArea:", 3, null);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddFloorArea(row));

        return row;
    }

    private void handleAddFloorArea(HBox row) {
        if (!(currentObject instanceof Floor f)) {
            showAlert("Select a Floor");
            return;
        }
        try {
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);

            String name = nameField.getText().trim();
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            LinkedList<Integer> size = new LinkedList<Integer>();
            size.add(x);
            size.add(y);

            f.getFloorAreas().add(new FloorArea(name, new LinkedList<Aisle>(), size));

            nameField.clear();
            xField.clear();
            yField.clear();

            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            buildTree();
        } catch (NumberFormatException ex) {
            showAlert("Invalid values");
        }
    }

    private HBox createAisleRow() {
        final HBox row = Utilities.createRow("Aisle:", 3, null);

        ComboBox<String> temp = new ComboBox<>();
        temp.getItems().addAll("Room temperature", "Refrigerated", "Frozen");
        row.getChildren().add(4, temp);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddAisle(row));

        return row;
    }

    private void handleAddAisle(HBox row) {
        if (!(currentObject instanceof FloorArea fa)) {
            showAlert("Select a FloorArea");
            return;
        }
        try {
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);
            ComboBox<String> temp = (ComboBox<String>) row.getChildren().get(4);

            String name = nameField.getText().trim();
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            LinkedList<Integer> size = new LinkedList<Integer>();
            size.add(x);
            size.add(y);

            int temperatureFinal = switch (temp.getValue()) {
                case "Room temperature" -> 1;
                case "Refrigerated" -> 0;
                case "Frozen" -> -1;
                default -> 1;
            };

            Aisle tempAisle = new Aisle(name, new LinkedList<Shelf>(), size, temperatureFinal);
            for (Floor f : selectedSupermarket.getFloors()) {
                for (FloorArea floorArea : f.getFloorAreas()) {
                    for (Aisle aisle : floorArea.getAisles()) {
                        if (tempAisle == aisle) return;
                    }
                }
            }
            fa.getAisles().add(tempAisle);

            nameField.clear();
            xField.clear();
            yField.clear();

            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            buildTree();
        } catch (NumberFormatException ex) {
            showAlert("Invalid values");
        }
    }

    private HBox createShelfRow() {
        final HBox row = Utilities.createRow("Shelf:", 1, null);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddShelf(row));

        return row;
    }

    private void handleAddShelf(HBox row) {
        if (!(currentObject instanceof Aisle a)) {
            showAlert("Select an Aisle");
            return;
        }
        TextField numberField = (TextField) row.getChildren().get(1);
        a.getShelves().add(new Shelf(Integer.parseInt(numberField.getText().trim()), new LinkedList<GoodItem>(), new LinkedList<Integer>()));
        numberField.clear();
        buildTree();
    }

    private HBox createGoodRow() {
        final HBox row = Utilities.createRow("Good:", 7, null);

        Button smartAddButton = new Button("Smart Add");
        row.getChildren().add(smartAddButton);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 2);
        addButton.setOnAction(e ->
        {
            handleAddGood(row);
            reports();
        });
        smartAddButton.setOnAction(e ->
        {
            handleSmartAddGood(row);
            reports();
        });

        return row;
    }

    private void handleAddGood(HBox row) {
        if (!(currentObject instanceof Shelf s)) {
            showAlert("Select a Shelf");
            return;
        }
        try {
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField priceField = (TextField) row.getChildren().get(2);
            TextField quantityField = (TextField) row.getChildren().get(3);
            TextField massField = (TextField) row.getChildren().get(4);
            TextField sizeField = (TextField) row.getChildren().get(5);
            TextField descField = (TextField) row.getChildren().get(6);
            TextField imgField = (TextField) row.getChildren().get(7);

            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String img = imgField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double mass = Double.parseDouble(massField.getText().trim());
            int size = Integer.parseInt(sizeField.getText().trim());
            existanceCheckGood(name, price, quantity, mass, size, desc, img);

            nameField.clear();
            priceField.clear();
            quantityField.clear();
            massField.clear();
            sizeField.clear();

            buildTree();
        } catch (NumberFormatException ex) {
            showAlert("Invalid values");
        }
    }

    private void handleSmartAddGood(HBox row) {
        try {
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField priceField = (TextField) row.getChildren().get(2);
            TextField quantityField = (TextField) row.getChildren().get(3);
            TextField massField = (TextField) row.getChildren().get(4);
            TextField sizeField = (TextField) row.getChildren().get(5);
            TextField descField = (TextField) row.getChildren().get(6);
            TextField imgField = (TextField) row.getChildren().get(7);

            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String img = imgField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double mass = Double.parseDouble(massField.getText().trim());
            int size = Integer.parseInt(sizeField.getText().trim());

            existanceCheckGood(name, price, quantity, mass, size, desc, img);

            Shelf target = similarityShelf(selectedSupermarket, new GoodItem(name, new LinkedList<Shelf>(), price, quantity, mass, size, desc, img));
            target.getGoods().add(new GoodItem(name, new LinkedList<Shelf>(), price, quantity, mass, size, desc, img));

            nameField.clear();
            priceField.clear();
            quantityField.clear();
            massField.clear();
            sizeField.clear();

            buildTree();
        } catch (NumberFormatException ex) {
            showAlert("Invalid values");
        }
    }


    // --------------------------------------------------------------------------------
    private void buildTree() {
        treeMap.clear();


        TreeItem<String> root = new TreeItem<>(selectedSupermarket.getName());
        treeMap.put(root, selectedSupermarket);


        addToTree(selectedSupermarket.getFloors(), root);
        root.setExpanded(true);


        treePane.setRoot(root);


        treePane.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) currentObject = treeMap.get(newItem);
        });


        treePane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<String> selectedItem = treePane.getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem != root) {
                    Object obj = treeMap.get(selectedItem);
                    TreeItem<String> parentItem = selectedItem.getParent();
                    Object parentObj = treeMap.get(parentItem);

                    sliderPane.getChildren().clear();

                    if (obj instanceof GoodItem good) {
                        sliderPane.setVisible(true);

                        Label label = new Label("Remove quantity for: " + good.getName());
                        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                        Slider slider = new Slider(1, good.getStock(), 1);
                        slider.setMajorTickUnit(1);
                        slider.setMinorTickCount(0);
                        slider.setSnapToTicks(true);
                        slider.setShowTickMarks(true);
                        slider.setShowTickLabels(true);

                        Button removeButton = new Button("Remove");
                        removeButton.setOnAction(e -> {
                            int qty = (int) slider.getValue();
                            if (qty >= good.getStock()) {
                                if (parentObj instanceof Shelf sParent) sParent.getGoods().remove(good);
                                parentItem.getChildren().remove(selectedItem);
                                treeMap.remove(selectedItem);
                            } else {
                                good.setStock(good.getStock() - qty);
                                selectedItem.setValue("Good: " + good.getName() + " (" + good.getStock() + ")");
                            }
                            sliderPane.getChildren().clear();
                            sliderPane.setVisible(false);
                        });

                        sliderPane.getChildren().addAll(label, slider, removeButton);

                    } else {
                        if (parentObj instanceof Supermarket s && obj instanceof Floor f) s.getFloors().remove(f);
                        else if (parentObj instanceof Floor fParent && obj instanceof FloorArea fa) fParent.getFloorAreas().remove(fa);
                        else if (parentObj instanceof FloorArea faParent && obj instanceof Aisle a) faParent.getAisles().remove(a);
                        else if (parentObj instanceof Aisle aParent && obj instanceof Shelf sObj) aParent.getShelves().remove(sObj);

                        parentItem.getChildren().remove(selectedItem);
                        treeMap.remove(selectedItem);
                    }
                }
            }
        });
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
    private void ret() throws Exception {
        if (app != null) {
            app.showDashboard();
        } else {
            // fallback, still clears canvas
            GraphicsContext gc = gridCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        }
    }


    public Shelf similarityShelf(Supermarket selectedSupermarket, GoodItem newGood) { //https://mayurdhvajsinhjadeja.medium.com/jaccard-similarity-34e2c15fb524
        Shelf bestShelf = null;
        double bestSimilarity = -1;

        for (Floor floor : selectedSupermarket.getFloors()) {
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        for (GoodItem existingGood : shelf.getGoods()) {

                            LinkedList<String> newNameTokens = tokenize(newGood.getName());
                            LinkedList<String> existingNameTokens = tokenize(existingGood.getName());
                            LinkedList<String> newDescTokens = tokenize(newGood.getDesc());
                            LinkedList<String> existingDescTokens = tokenize(existingGood.getDesc());

                            double nameSim = jaccardSimilarity(newNameTokens, existingNameTokens);
                            double descSim = jaccardSimilarity(newDescTokens, existingDescTokens);


                            double priceDiff = Math.abs(newGood.getPrice() - existingGood.getPrice());
                            double massDiff = Math.abs(newGood.getMass() - existingGood.getMass());
                            double sizeDiff = Math.abs(newGood.getSize() - existingGood.getSize());


                            double textSimilarity = 0.7 * nameSim + 0.3 * descSim;
                            double attributePenalty = priceDiff + massDiff + sizeDiff;

                            double finalScore = textSimilarity * (1.0 / (1.0 + Math.pow(Math.E, attributePenalty)));

                            if (finalScore > bestSimilarity) {
                                bestSimilarity = finalScore;
                                bestShelf = shelf;
                            }
                        }
                    }
                }
            }
        }

        return bestShelf;
    }


    public LinkedList<String> tokenize(String text) {
        LinkedList<String> tokens = new LinkedList<String>();
        if (text == null || text.isEmpty()) return tokens;

        text = text.toLowerCase();
        String word = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                word = word + c;
            } else if (!word.isEmpty()) {
                tokens.add(word);
                word = "";
            }
        }

        if (!word.isEmpty()) tokens.add(word);
        return tokens;
    }


    public double jaccardSimilarity(LinkedList<String> list1, LinkedList<String> list2) {
        if (list1 == null || list2 == null) return 0.0;
        if (list1.isEmpty() && list2.isEmpty()) return 1.0;
        if (list1.isEmpty() || list2.isEmpty()) return 0.0;

        int intersection = 0;
        int union = 0;

        LinkedList<String> list2Copy = list2;

        for (int i = 0; i < list1.size(); i++) {
            String token1 = list1.get(i);
            boolean found = false;

            for (int j = 0; j < list2Copy.size(); j++) {
                if (token1.equals(list2Copy.get(j))) {
                    intersection++;
                    list2Copy.remove(list2Copy.get(j));
                    found = true;
                    break;
                }
            }
            union++;
        }

        union += list2Copy.size();

        return (union == 0) ? 0.0 : ((double) intersection / union);
    }

    @FXML
    private void reports() {
        if (selectedSupermarket == null) {
            showAlert("No supermarket selected!");
            return;
        }

        Reports reports = new Reports(selectedSupermarket);

        double avgProducts = reports.averageProductsPerShelf();
        double totalSize = reports.totalSupermarketSize();
        double totalValue = reports.totalStockValue();
        LinkedList<Double> temps = reports.totalStockByTemperature();

        reportPane.getChildren().clear();

        Label title = new Label("Reports");
        Label avgLabel = new Label("Average products per shelf: " + avgProducts);
        Label sizeLabel = new Label("Total supermarket size: " + totalSize);
        Label amountLabel = new Label("Total number of stock: " + reports.totalAmountStock());
        Label valueLabel = new Label("Total stock value: " + totalValue);
        Label roomLabel = new Label("Room temperature stock: " + temps.get(0));
        Label refrLabel = new Label("Refrigerated stock: " + temps.get(1));
        Label frozenLabel = new Label("Frozen stock: " + temps.get(2));

        reportPane.getChildren().addAll(title, avgLabel, sizeLabel, amountLabel, valueLabel, roomLabel, refrLabel, frozenLabel);
    }

    public void existanceCheckGood(String name, double price, int quantity, double mass, int size, String desc, String img) {
        GoodItem goodItem = new GoodItem(name, new LinkedList<Shelf>(), price, quantity, mass, size, desc, img);
        for (Floor floor : selectedSupermarket.getFloors()) {
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        GoodItem duplicate = Utilities.checkDupGoodAdd(goodItem, shelf.getGoods());

                        if (duplicate != null) {
                            duplicate.setStock(duplicate.getStock() + goodItem.getStock());
                            return;
                        } else {
                            shelf.getGoods().add(goodItem);
                        }
                    }
                }
            }
        }
    }

    public void showAllStock() {
        stockPane.getChildren().clear();

        for (Floor f : selectedSupermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {

                            Label goodLabel = new Label(
                                    g.getName() + " | Price: " + g.getPrice() +
                                            " | Stock: " + g.getStock() +
                                            " | Value: " + g.getPrice() * g.getStock()
                            );

                            goodLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

                            stockPane.getChildren().add(goodLabel);
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void search() {
        String prompt = searchBar.getText().trim().toLowerCase();
        searchPane.getChildren().clear();

        if (prompt.isEmpty()) {
            searchPane.getChildren().add(new Label("Enter something to search."));
            return;
        }

        LinkedList<String> results = new LinkedList<String>();

        for (Floor f : selectedSupermarket.getFloors()) {
            String floorName = "Floor " + f.getLevel();
            if (floorName.toLowerCase().contains(prompt)) {
                results.add(floorName + " in " + selectedSupermarket.getName());
            }

            for (FloorArea fa : f.getFloorAreas()) {
                if (fa.getName().toLowerCase().contains(prompt)) {
                    results.add("FloorArea: " + fa.getName() + " (Floor " + f.getLevel() + ")");
                }

                for (Aisle a : fa.getAisles()) {
                    if (a.getName().toLowerCase().contains(prompt)) {
                        results.add("Aisle: " + a.getName() + " (Floor " + f.getLevel() + ", Area: " + fa.getName() + ")");
                    }

                    for (Shelf s : a.getShelves()) {
                        String shelfLabel = "Shelf " + s.getNumber();
                        if (shelfLabel.toLowerCase().contains(prompt)) {
                            results.add(shelfLabel + " (Aisle: " + a.getName() + ")");
                        }

                        for (GoodItem g : s.getGoods()) {
                            String gName = g.getName().toLowerCase();
                            String gDesc = (g.getDesc() == null ? "" : g.getDesc().toLowerCase());
                            if (gName.contains(prompt) || gDesc.contains(prompt)) {
                                String path = Utilities.getGoodItemPath(selectedSupermarket, g);
                                results.add(g.getName() + " — " + path);
                            }
                        }
                    }
                }
            }
        }

        if (results.size() == 0) {
            Label none = new Label("No results found for \"" + prompt + "\".");
            none.setStyle("-fx-text-fill: gray;");
            searchPane.getChildren().add(none);
            return;
        }

        int limit = Math.min(10, results.size());
        for (int i = 0; i < limit; i++) {
            Label label = new Label(results.get(i));
            label.setStyle("-fx-text-fill: white;");
            searchPane.getChildren().add(label);
        }


    }

    public void setLauncher(Launcher app) {
        this.app = app;
    }
}



