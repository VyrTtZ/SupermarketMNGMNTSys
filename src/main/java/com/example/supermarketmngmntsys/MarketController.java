package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;
//----------------------------------------------------------------------------------------------------------------------
public class MarketController {//FIELDS

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
    private VBox stockPane;
    @FXML
    private Pane searchPane;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox sliderPane;
    @FXML
    private VBox resultPane;
    private Supermarket selectedSupermarket;
    private Object currentObject;
    private final Map<TreeItem<String>, Object> treeMap = new HashMap<>();
    private Launcher app;
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    private void initialize() {//ADDS THE ROWS CREATED IN METHODS BELOW TO THE FXML VIEW
        setupAddRows();
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setSupermarket(Supermarket supermarket) {
        this.selectedSupermarket = supermarket;
        updateUI();
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void updateUI() { //UPDATES THE UI
        if (selectedSupermarket == null) return;

        namePane.getChildren().clear(); //CLEARS THE NAME PANE
        Label nameLabel = new Label(selectedSupermarket.getName()); //SETS THE NEW NAME FOR THE SUPERMARKET
        nameLabel.setStyle("""
        -fx-alignment: center;
        -fx-text-fill: #FFFFFF;
        -fx-font-family: 'Segoe UI Semibold';
        -fx-font-size: 30px;
        -fx-font-weight: bold;
        """);
        nameLabel.layoutXProperty().bind( //CENTERS THE NAME
                namePane.widthProperty().subtract(nameLabel.widthProperty()).divide(2)
        );
        nameLabel.layoutYProperty().bind(
                namePane.heightProperty().subtract(nameLabel.heightProperty()).divide(2)
        );

        namePane.getChildren().add(nameLabel);
        //-----------------------------------------
        SpinnerValueFactory<Integer> valueFactory = //ADDS A SPINNER WHICH WORKS AS A FLOOR SELECTOR
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-selectedSupermarket.getFloors().size(), selectedSupermarket.getFloors().size(), 0, 1);
        floorNum.setValueFactory(valueFactory);//GIVES SPINNER RULES ABOVE
        floorNum.valueProperty().addListener((obs, oldValue, newValue) -> {
            Utilities.drawFloor(gridCanvas, numToFloor(newValue));
        });
        //-----------------------------------------
        reports();//UPDATES THE REPORTS PANE
        buildTree(); //UPDATES THE TREE VIEW
        showAllStock(); //UPDATES THE SHOW STOCK PANE

        Utilities.drawFloor(gridCanvas, numToFloor(floorNum.getValue())); //DRAWS THE SELECTED FLOOR OF THE SUPERMARKET
    }
    //----------------------------------------------------------------------------------------------------------------------
    public Floor numToFloor(int level) { //TAKES IN A NUMBER AND RETURNS A FLOOR WITH SAID NUMBER
        for (Floor f : selectedSupermarket.getFloors()) {
            if (f.getLevel() == level) return f;
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void setupAddRows() {//SETUPS THE ROWS USED TO CREATING OBJECTS IN THE MARKET
        addOptionsPane.getChildren().clear(); //CLEARS PANE UPON CALL
        addOptionsPane.getChildren().add(createFloorRow());
        addOptionsPane.getChildren().add(createFloorAreaRow());
        addOptionsPane.getChildren().add(createAisleRow());
        addOptionsPane.getChildren().add(createShelfRow());
        addOptionsPane.getChildren().add(createGoodRow());

    }
    //----------------------------------------------------------------------------------------------------------------------
    private HBox createFloorRow() {//CREATES AN HBOX FOR ADDING FLOORS
        final HBox row = Utilities.createRow("Floor:", 3);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddFloor(row));//CALLS THE ADDING FLOOR METHOD

        return row;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleAddFloor(HBox row) {
        try {//TRIES TO RETRIVE INFORMATION FROM TEXT FIELDS IN THE FXML VIEW
            TextField levelField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);

            //PARSES THE TEXTFIELD INTO STRING INTO INT
            int level = Integer.parseInt(levelField.getText().trim());
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            MyLinkedList<Integer> size = new MyLinkedList<Integer>();
            size.add(x);
            size.add(y);

            //ADDS NEW FLOOR TO THE SUPERMARKET
            selectedSupermarket.getFloors().add(new Floor(level, new MyLinkedList<FloorArea>(), size));

            levelField.clear();
            xField.clear();
            yField.clear();

            //CALLS FOR THE FLOOR TO BE DRAWN AND UPDATES UI
            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            updateUI();
        } catch (NumberFormatException ex) {
            Utilities.showAlert("Invalid input");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private HBox createFloorAreaRow() {//SAME METHOD AS THE CREATING HBOX FOR FLOOR
        final HBox row = Utilities.createRow("FloorArea:", 3);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddFloorArea(row));

        return row;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleAddFloorArea(HBox row) {//ADDS FLOOR WITH THE HBOX PARAMETERS
        if (!(currentObject instanceof Floor f)) {//THERE NEEDS TO BE PARENT FLOOR SELECTED
            Utilities.showAlert("Select a Floor");
            return;
        }
        try {//TRIES TO GET DATA FROM THE HBOX
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);

            //PARSES TEXTFIELD INTO STRING INTO INT
            String name = nameField.getText().trim();
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            MyLinkedList<Integer> size = new MyLinkedList<Integer>();
            size.add(x);
            size.add(y);

            //ADDS FLOOR AREA TO THE FLOOR
            f.getFloorAreas().add(new FloorArea(name, new MyLinkedList<Aisle>(), size));


            nameField.clear();
            xField.clear();
            yField.clear();

            //DRAWS THE UPDATED FLOOR WITH THE FLOOR AREAS AND UPDATES UI
            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            updateUI();
        } catch (NumberFormatException ex) {
            Utilities.showAlert("Invalid values");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private HBox createAisleRow() {//SAME METHOD AS THE CREATING HBOX FOR FLOOR AND FLOOR AREA
        final HBox row = Utilities.createRow("Aisle:", 3);

        ComboBox<String> temp = new ComboBox<>();//ADDS COMBO BOX TO THE HBOX
        temp.getItems().addAll("Room temperature", "Refrigerated", "Frozen");
        row.getChildren().add(4, temp);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddAisle(row));

        return row;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleAddAisle(HBox row) {//ADDS THE AISLE TO THE SELECTED FLOOR AREA
        if (!(currentObject instanceof FloorArea fa)) {//SELECTED FLOOR AREA
            Utilities.showAlert("Select a FloorArea");
            return;
        }
        try {//TRIES TO GET DATA FROM TEXT FIELDS AND THE COMBO BOX
            TextField nameField = (TextField) row.getChildren().get(1);
            TextField xField = (TextField) row.getChildren().get(2);
            TextField yField = (TextField) row.getChildren().get(3);
            ComboBox<String> temp = (ComboBox<String>) row.getChildren().get(4);


            //PARSES TEXTFIELDS
            String name = nameField.getText().trim();
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            MyLinkedList<Integer> size = new MyLinkedList<Integer>();
            size.add(x);
            size.add(y);

            //PARSES THE VALUE FROM COMBOBOX INTO INT INDICATOR
            int temperatureFinal = switch (temp.getValue()) {
                case "Room temperature" -> 1;
                case "Refrigerated" -> 0;
                case "Frozen" -> -1;
                default -> 1;
            };
            //CHECKS FOR THE COLLISION WITH OTHER AISLES IN THE SUPERMARKET
            Aisle tempAisle = new Aisle(name, new MyLinkedList<Shelf>(), size, temperatureFinal);
            for (Floor f : selectedSupermarket.getFloors()) {
                for (FloorArea floorArea : f.getFloorAreas()) {
                    for (Aisle aisle : floorArea.getAisles()) {
                        if (tempAisle.getName() == aisle.getName()) Utilities.showAlert("This aisle name is taken");
                    }
                }
            }

            fa.getAisles().add(tempAisle);

            nameField.clear();
            xField.clear();
            yField.clear();

            //DRAWS THE UPDATED FLOOR WITH THE FLOOR AREAS, AISLES AND UPDATES UI
            Utilities.drawFloor(gridCanvas, numToFloor(Integer.parseInt(floorNum.getValue().toString().trim())));
            updateUI();
        } catch (NumberFormatException ex) {
            Utilities.showAlert("Invalid values");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private HBox createShelfRow() {//SAME METHOD AS THE CREATING HBOX FOR FLOOR, FLOOR AREA AND AISLES
        final HBox row = Utilities.createRow("Shelf:", 1);

        Button addButton = (Button) row.getChildren().get(row.getChildren().size() - 1);
        addButton.setOnAction(e -> handleAddShelf(row));

        return row;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleAddShelf(HBox row) {//ADDS THE SHELF TO SELECTED AISLE
        if (!(currentObject instanceof Aisle a)) {//SELECTED OBJECT MUST BE AN AISLE
            Utilities.showAlert("Select an Aisle");
            return;
        }

        TextField numberField = (TextField) row.getChildren().get(1);//GETS THE TEXTFIELD VALUE AND PARSES IT IN THE ADDITION METHOD
        a.getShelves().add(new Shelf(Integer.parseInt(numberField.getText().trim()), new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>()));
        numberField.clear();
        updateUI();
    }
    //----------------------------------------------------------------------------------------------------------------------
    private VBox createGoodRow() {//SAME METHOD AS THE CREATING HBOX FOR FLOOR, FLOOR AREA, AISLES AND SHELVES
        VBox container = new VBox(5);//DUE TO LARGE AMOUNT OF PARAMETERS 2 ROWS NEED TO BE MADE
        HBox row1 = new HBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Amount");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField massField = new TextField();
        massField.setPromptText("Mass");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Size");

        row1.getChildren().addAll(nameField, quantityField, priceField, massField, sizeField); //ADDS FIELDS ABOVE TO THE FIRST ROW

        HBox row2 = new HBox(10);
        TextField descField = new TextField();
        descField.setPromptText("Description");
        TextField imgField = new TextField();
        imgField.setPromptText("Image URL");

        Button addButton = new Button("Add");
        Button smartAddButton = new Button("Smart Add");

        row2.getChildren().addAll(descField, imgField, addButton, smartAddButton);//ADDS FIELDS ABOVE TO SECOND ROW


        addButton.setOnAction(e -> handleAddGood(row1, row2));//CALLS FOR THE HANDLE METHODS BELOW
        smartAddButton.setOnAction(e -> handleSmartAdd(row1, row2));

        container.getChildren().addAll(row1, row2);
        return container;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleAddGood(HBox row1, HBox row2) {//ADDS A PRODUCT
        if (!(currentObject instanceof Shelf s)) { //SELECTED PRODUCT MNUST BE AN INSTANCE OF SHELF FOR DEFAULT ADD
            Utilities.showAlert("Select a Shelf");
            return;
        }
        try {//TRIES TO GET DATA FROM THE HBOXES IN THE PARAMETERS
            TextField nameField = (TextField) row1.getChildren().get(0);
            TextField quantityField = (TextField) row1.getChildren().get(1);
            TextField priceField = (TextField) row1.getChildren().get(2);
            TextField massField = (TextField) row1.getChildren().get(3);
            TextField sizeField = (TextField) row1.getChildren().get(4);

            TextField descField = (TextField) row2.getChildren().get(0);
            TextField imgField = (TextField) row2.getChildren().get(1);

            //PARSES THE DATA
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            double mass = Double.parseDouble(massField.getText().trim());
            int size = Integer.parseInt(sizeField.getText().trim());
            String desc = descField.getText().trim();
            String img = imgField.getText().trim();


            if(Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket))==null)
                s.getGoods().add(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img));//ADDS GOOD IF THERE IS NO DUPLICATE
            else{
                Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket)).setStock(
                        Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket)).getStock()+quantity//IF THE ITEM EXISTS JUST ADD THE AMOUNT TO THE EXISTING ITEM
                );
            }

            nameField.clear();
            quantityField.clear();
            priceField.clear();
            massField.clear();
            sizeField.clear();
            descField.clear();
            imgField.clear();

            updateUI();
        } catch (NumberFormatException ex) {//EXCEPTION IF THERE ARENT NUMBERS WHERE THEY ARE EXPECTED
            Utilities.showAlert("Invalid values");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void handleSmartAdd(HBox row1, HBox row2) {//HANDLES SMART ADD OF PRODUCTS
        //NO NEED FOR A SHELF TO BE SELECTED FOR SMART ADD
        try {//TRIEST TO GET THE DATA FROM THE HBOXES IN PARAMETERS
            TextField nameField = (TextField) row1.getChildren().get(0);
            TextField quantityField = (TextField) row1.getChildren().get(1);
            TextField priceField = (TextField) row1.getChildren().get(2);
            TextField massField = (TextField) row1.getChildren().get(3);
            TextField sizeField = (TextField) row1.getChildren().get(4);

            TextField descField = (TextField) row2.getChildren().get(0);
            TextField imgField = (TextField) row2.getChildren().get(1);

            //PARSES DATA
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            double mass = Double.parseDouble(massField.getText().trim());
            int size = Integer.parseInt(sizeField.getText().trim());
            String desc = descField.getText().trim();
            String img = imgField.getText().trim();

            //FINDS THE TARGET SHELF USING THE SIMILARITY SHELF METHOD
            Shelf target = similarityShelf(selectedSupermarket, new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img));
            //CHECKS FOR THE GOOD ALREADY EXISTING IN THE MARKET AS ABOVE
            if(Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket))==null)
                target.getGoods().add(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img));
            else{
                Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket)).setStock(
                        Utilities.checkDupGoodAdd(new GoodItem(name, new MyLinkedList<Shelf>(), price, quantity, mass, size, desc, img), Utilities.allGoods(selectedSupermarket)).getStock()+quantity
                );
            }


            nameField.clear();
            quantityField.clear();
            priceField.clear();
            massField.clear();
            sizeField.clear();
            descField.clear();
            imgField.clear();

            updateUI();
        } catch (NumberFormatException ex) {
            Utilities.showAlert("Invalid values");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    private void buildTree() {//METHOD TO BUILD THE TREEVIEW
        treeMap.clear();//CLEARS THE SPACE FOR THE TREE


        TreeItem<String> root = new TreeItem<>(selectedSupermarket.getName());//NAME OF MARKET IS ROOT
        treeMap.put(root, selectedSupermarket);


        addToTree(selectedSupermarket.getFloors(), root);//ADDS ALL OBJETS TO THE TREEVIEW
        root.setExpanded(true);//EXPANDED BY DEFAULT


        treePane.setRoot(root);


        treePane.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) currentObject = treeMap.get(newItem);//SETS THE CURRENT ITEM IN THE CONTROLLER TO BE WHICHEVER PART OF THE TREEVIEW IS CLICKED ON
        });


        treePane.setOnMouseClicked(event -> { //REMOVE METHOD
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<String> selectedItem = treePane.getSelectionModel().getSelectedItem();//GETS THE SELECTED TREE ITEM
                if (selectedItem != null && selectedItem != root) {//CANT BE ROOT
                    Object obj = treeMap.get(selectedItem);
                    TreeItem<String> parentItem = selectedItem.getParent();
                    Object parentObj = treeMap.get(parentItem);

                    sliderPane.getChildren().clear(); //SLIDERPANE FOR PARTIAL REMOVAL OF GOODS

                    if (obj instanceof GoodItem good) { //EXCEPTION CASE FOR GOODS
                        sliderPane.setVisible(true);//SETS SLIDER VISIBILITY

                        Label label = new Label("Remove quantity for: " + good.getName());
                        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                        Slider slider = new Slider(1, good.getStock(), 1);
                        slider.setMajorTickUnit((int)Math.pow(good.getStock(), 0.5));//TICKS ARE THE SQUARES OF FULL AMOUNT
                        slider.setMinorTickCount(0);///STARTS AT 0
                        slider.setShowTickLabels(true);

                        Button removeButton = new Button("Remove");
                        removeButton.setOnAction(e -> {
                            int amount = (int) slider.getValue();
                            if (amount >= good.getStock()) {//FULL REMOVAL
                                if (parentObj instanceof Shelf parent)
                                    parent.getGoods().remove(good);//REMOVAL IN LINKED LIST
                                parentItem.getChildren().remove(selectedItem);//REMOVAL IN UI
                                treeMap.remove(selectedItem);
                            } else {//PARTIAL REMOVAL
                                good.setStock(good.getStock() - amount);
                                selectedItem.setValue("Good: " + good.getName());
                            }
                            sliderPane.getChildren().clear();//CLEARS DATA IN SLIDER
                            sliderPane.setVisible(false);//CLOSES TO SLIDER
                            updateUI();
                        });

                        sliderPane.getChildren().addAll(label, slider, removeButton);

                    } else {//NORMAL REMOVALS
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
    //----------------------------------------------------------------------------------------------------------------------
    private <T> void addToTree(MyLinkedList<T> list, TreeItem<String> parent) {//ADDS ITEMS TO THE TREE VIEW
        if (list == null) return;

        for (T item : list) {//GETS ITEMS IN THE LIST
            String name;//SETS NAME
            if (item instanceof Floor f) name = "Floor " + f.getLevel();
            else if (item instanceof FloorArea fa) name = "Area: " + fa.getName();
            else if (item instanceof Aisle a) name = "Aisle: " + a.getName();
            else if (item instanceof Shelf s) name = "Shelf: " + s.getNumber();
            else if (item instanceof GoodItem g) name = "Good: " + g.getName();
            else name = item.toString();

            TreeItem<String> node = new TreeItem<>(name);//CREATE NEW TREE VIEW NODE
            treeMap.put(node, item);//ADD THE NODE
            parent.getChildren().add(node);


            //RECURSIVELY ADDS OBJECTS UNTIL THERE ARE NO MORE
            if (item instanceof Floor f) addToTree(f.getFloorAreas(), node);
            else if (item instanceof FloorArea fa) addToTree(fa.getAisles(), node);
            else if (item instanceof Aisle a) addToTree(a.getShelves(), node);
            else if (item instanceof Shelf s) addToTree(s.getGoods(), node);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    private void ret() throws Exception {//RETURN METHOD FOR GOING BACK TO THE DASHBOARD
        app.showDashboard();
    }
    //----------------------------------------------------------------------------------------------------------------------
    public Shelf similarityShelf(Supermarket selectedSupermarket, GoodItem newGood) { //https://mayurdhvajsinhjadeja.medium.com/jaccard-similarity-34e2c15fb524
        Shelf bestShelf = null;
        double bestSimilarity = -1;

        //LOOKS FOR ALL THE GOODS IN THE SYSTEM
        for (Floor floor : selectedSupermarket.getFloors()) {
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        for (GoodItem existingGood : shelf.getGoods()) {

                            //TOKENIZES THE NAME AND DESCRIPTION OF OLD GOOD AND GOOD TO BE ADDED
                            MyLinkedList<String> newNameTokens = tokenize(newGood.getName());
                            MyLinkedList<String> existingNameTokens = tokenize(existingGood.getName());
                            MyLinkedList<String> newDescTokens = tokenize(newGood.getDesc());
                            MyLinkedList<String> existingDescTokens = tokenize(existingGood.getDesc());

                            //SIMILARITY VALUES
                            double nameSim = jaccardSimilarity(newNameTokens, existingNameTokens);
                            double descSim = jaccardSimilarity(newDescTokens, existingDescTokens);

                            //OTHER SIMILARITY BETWEEN THE MATH VALUES
                            double priceDiff = Math.abs(newGood.getPrice() - existingGood.getPrice());
                            double massDiff = Math.abs(newGood.getMass() - existingGood.getMass());
                            double sizeDiff = Math.abs(newGood.getSize() - existingGood.getSize());

                            //WEIGHTED VALUES FOR NAME AND DESCRIPTION SIMS
                            double textSimilarity = 0.7 * nameSim + 0.3 * descSim;
                            //EXTRA VALUES
                            double attributePenalty = priceDiff + massDiff + sizeDiff;

                            //SIMILARITIES PUT TOGETHER
                            double finalScore = textSimilarity * (1.0 / (1.0 + Math.pow(Math.E, attributePenalty)));

                            if (finalScore > bestSimilarity) { //IF THE SIMILARITY IS HIGHER THAN CURRENTLY SAVED CHANGE BEST SHELF
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
    //----------------------------------------------------------------------------------------------------------------------
    public MyLinkedList<String> tokenize(String text) {//TOKENIZES A STRING
        MyLinkedList<String> tokens = new MyLinkedList<String>();
        if (text == null || text.isEmpty()) return tokens;

        //ALL INTO LOWERCASE
        text = text.toLowerCase();
        String word = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {//DIVIDES THE LONGER STRINGS INTO LIST OF WORDS
                word = word + c;
            }
            else if (!word.isEmpty()) {//ADDS THE WORD TO LIST ON SPECIAL CHARS + RESETS WORD
                tokens.add(word);
                word = "";
            }
        }

        if (!word.isEmpty()) tokens.add(word);
        return tokens;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double jaccardSimilarity(MyLinkedList<String> list1, MyLinkedList<String> list2) {
        //COMPARES TWO SETS WITH JACCARD SIMILARITY
        if (list1 == null || list2 == null) return 0.0;
        if (list1.isEmpty() && list2.isEmpty()) return 1.0;
        if (list1.isEmpty() || list2.isEmpty()) return 0.0;

        int intersection = 0;
        int union = 0;

        MyLinkedList<String> list2Copy = list2;

        for (int i = 0; i < list1.size(); i++) {
            String token1 = list1.get(i);

            for (int j = 0; j < list2Copy.size(); j++) {//COMPARE FIRST TOKEN TO ALL TOKENS IN LIST2 AND INCREASE INTERSECTION
                if (token1.equals(list2Copy.get(j))) {
                    intersection++;
                    list2Copy.remove(list2Copy.get(j));//REMOVE ALREADY USED VALUE
                    break;
                }
            }
            union++;//ALWAYS INCREMENT UNION
        }

        union += list2Copy.size();//UNION IS THE FIRST SET + THE 2ND SET WITH NO REPEATS

        if (union == 0) {
            return 0.0;
        } else {
            return (double) intersection / union;
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    private void reports() {//GIVES REPORTS FOR ALL ITEMS IN MARKET
        if (selectedSupermarket == null) {
            Utilities.showAlert("No supermarket selected!");
            return;
        }

        Reports reports = new Reports(selectedSupermarket);//USES THE REPORTS CLASS

        double avgProducts = reports.averageProductsPerShelf();
        double totalSize = reports.totalSupermarketSize();
        double totalValue = reports.totalStockValue();
        MyLinkedList<Double> temps = reports.totalStockWithTemp();

        reportPane.getChildren().clear();

        Label title = new Label("Reports");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label avgLabel = new Label("Average products per shelf: " + avgProducts);
        avgLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label sizeLabel = new Label("Total supermarket size: " + totalSize);
        sizeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label amountLabel = new Label("Total number of stock: " + reports.totalAmountStock());
        amountLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label valueLabel = new Label("Total stock value: " + totalValue);
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label roomLabel = new Label("Room temperature stock: " + temps.get(0));
        roomLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label refrLabel = new Label("Refrigerated stock: " + temps.get(1));
        refrLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Label frozenLabel = new Label("Frozen stock: " + temps.get(2));
        frozenLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        reportPane.getChildren().addAll(
                title, avgLabel, sizeLabel, amountLabel, valueLabel, roomLabel, refrLabel, frozenLabel
        );//ADDS ALL ELEMENTS TO REPORT PANE
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void showAllStock() {//SHOWS ALL STOCK IN THE MARKET AND FORMATS IT IN THE STOCKPANE
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
    //----------------------------------------------------------------------------------------------------------------------
    @FXML
    private void search() {//METHOD FOR SEARCHING FOR OBJECTS IN THE MARKET
        String prompt = searchBar.getText().trim().toLowerCase();//GET DATA FROM INPUT FIELD
        resultPane.getChildren().clear();//CLEAR RESULT PANE WHEN CALLED FOR
        if (prompt.isEmpty()) {
            Label empty = new Label("Enter something to search.");
            empty.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            searchPane.getChildren().add(empty);
            return;
        }

        MyLinkedList<String> results = new MyLinkedList<String>();

        //SEARCHING BASED ON THE CONTAINS METHOD WITHIN THE NAMES OF THE OBJECTS
        for (Floor f : selectedSupermarket.getFloors()) {
            String floorName = "Floor " + f.getLevel();
            if (floorName.toLowerCase().contains(prompt)) {
                results.add(floorName + " in " + selectedSupermarket.getName());
            }

            for (FloorArea fa : f.getFloorAreas()) {
                if (fa.getName().toLowerCase().contains(prompt)) {
                    results.add("FloorArea: " + fa.getName() + " -> Floor: " + f.getLevel());
                }

                for (Aisle a : fa.getAisles()) {
                    if (a.getName().toLowerCase().contains(prompt)) {
                        results.add("Aisle: " + a.getName() + " -> Floor: " + f.getLevel() + ", -> Floor Area: " + fa.getName());
                    }

                    for (Shelf s : a.getShelves()) {
                        String shelfLabel = "Shelf: " + s.getNumber();
                        if (shelfLabel.toLowerCase().contains(prompt)) {
                            results.add(shelfLabel + " -> Aisle: " + a.getName());
                        }

                        for (GoodItem g : s.getGoods()) {
                            String gName = g.getName().toLowerCase();
                            String gDesc = (g.getDesc() == null ? "" : g.getDesc().toLowerCase());
                            if (gName.contains(prompt) || gDesc.contains(prompt)) {
                                String path = Utilities.getGoodItemPath(selectedSupermarket, g);//RETURNS THE FULL PATH TO THE GOOD
                                results.add(g.getName() + " — " + path);
                            }
                        }
                    }
                }
            }
        }

        if (results.isEmpty()) {
            Label noResults = new Label("No results found");
            noResults.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");
            resultPane.getChildren().add(noResults);
            return;
        }

        int limit = Math.min(10, results.size());//LIMITS THE AMOUNT OF RESULTS NO TO OVERFLOW THE VBOX
        for (int i = 0; i < limit; i++) {
            Label label = new Label(results.get(i));
            label.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
            resultPane.getChildren().add(label);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setLauncher(Launcher app) {
        this.app = app;
    }
}



