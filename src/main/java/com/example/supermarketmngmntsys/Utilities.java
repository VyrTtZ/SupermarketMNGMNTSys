package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

//----------------------------------------------------------------------------------------------------------------------
public class Utilities {
    //----------------------------------------------------------------------------------------------------------------------
    public static HBox createRow(String labelText, int fieldCount) {
        HBox hbox = new HBox(5);//NEW HBOX WITH SPACING 5

        Label label = new Label(labelText);
        hbox.getChildren().add(label);

        for (int i = 0; i < fieldCount; i++) {//ADDS FIELDCOUNT AMOUNT OF TEXT FIELDS
            TextField tf = new TextField();
            hbox.getChildren().add(tf);
        }

        Button addButton = new Button("Add"); //ADD BUTTON
        hbox.getChildren().add(addButton);

        return hbox;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static Shelf getParentShelf(Supermarket supermarket, GoodItem target) { //RETURNS THE PARENT SHELF OF A GOOD
        if (supermarket == null || target == null) return null;

        for (Floor floor : supermarket.getFloors()) { //LOOPS THROUGH ALL THE GOODS UNTIL ONE MATCHES THE ONE IN PARAMETERS AND RETURNS THE SHELF
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        for (GoodItem good : shelf.getGoods()) {
                            if (good == target) {
                                return shelf;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static Aisle getParentAisle(Supermarket supermarket, GoodItem target) {//RETURNS THE AISLE FROM A GOOD
        Shelf shelf = getParentShelf(supermarket, target);//SETS THE SHELF FROM THE GOOD FROM PARAMETER
        if (shelf == null) return null;

        for (Floor floor : supermarket.getFloors()) {//LOOPS THROUGH ALL SHELVES AND RETURNS THE AISLE WHEN SHELF MATCHES THE ONE IN FIRST LINE OF METHOD
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    if (aisle.getShelves().contains(shelf)) {
                        return aisle;
                    }
                }
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static FloorArea getParentFloorArea(Supermarket supermarket, GoodItem target) {//RETURNS THE PARENT FLOOR AREA FROM A GOOD
        Aisle aisle = getParentAisle(supermarket, target);//GETS THE AISLE FOR THE GOOD
        if (aisle == null) return null;

        for (Floor floor : supermarket.getFloors()) {//LOOPS THROUGH ALL AISLES UNTIL IT MATCHES THE ONE FROM THE FIRST LINE OF CODE
            for (FloorArea area : floor.getFloorAreas()) {
                if (area.getAisles().contains(aisle)) {
                    return area;
                }
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static Floor getParentFloor(Supermarket supermarket, GoodItem target) {//RETURNS THE FLOOR FROM A GOOD
        FloorArea area = getParentFloorArea(supermarket, target);//SETS THE FLOOR AREA FROM THE GOOD FROM PARAMETER
        if (area == null) return null;

        for (Floor floor : supermarket.getFloors()) {
            if (floor.getFloorAreas().contains(area)) {//LOOPS THROUGH ALL FLOOR AREAS UNTIL ONE MATCHES THE ONE FROM FIRST LINE
                return floor;
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static MyLinkedList<GoodItem> allGoods(Supermarket s){ //RETURNS ALL THE GOODS IN A MARKET
        MyLinkedList<GoodItem> ret = new MyLinkedList<GoodItem>(); //INIT EMPTY LIST OF GOODS
        for (Floor floor : s.getFloors()) {//LOOPS THROUGH ALL GOODS AND ADDS THEM TO THE LIST
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        for (GoodItem good : shelf.getGoods()) {
                            ret.add(good);
                        }
                    }
                }
            }
        }
        return ret;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static String getGoodItemPath(Supermarket supermarket, GoodItem target) { //RETURNS THE PATH TO THE ITEM IN THE MARKET
        if (supermarket == null || target == null) return null;

        Floor floor = getParentFloor(supermarket, target); //CALLS THE PREVIOUS METHOD TO GET ALL THE PARENTS IN ORDER
        FloorArea area = getParentFloorArea(supermarket, target);
        Aisle aisle = getParentAisle(supermarket, target);
        Shelf shelf = getParentShelf(supermarket, target);

        if (floor == null || area == null || aisle == null || shelf == null) return null;//GOOD CANT EXIST WITHOUT BEING ATTACHED TO THESE

        return supermarket.getName() +
                " -> " + floor.getLevel() +
                " -> " + area.getName() +
                " -> " + aisle.getName() +
                " ->" + shelf.getNumber() +
                " -> " + target.getName();
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static int countGoods(Supermarket supermarket) {//RETURNS HOW MANY GOODS EXIST IN THE MARKET
        if (supermarket == null) return 0;

        int count = 0;
        for (Floor floor : supermarket.getFloors()) {//LOOPS THROUGH ALL GOODS AND INCREMENTS THE COUNT BY THE AMOUNT OF SAID GOOD IN STOCK
            for (FloorArea area : floor.getFloorAreas()) {
                for (Aisle aisle : area.getAisles()) {
                    for (Shelf shelf : aisle.getShelves()) {
                        count += shelf.getGoods().size();
                    }
                }
            }
        }
        return count;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static GoodItem checkDupGoodAdd(GoodItem g, MyLinkedList<GoodItem> goodItems) {
        for (GoodItem temp : goodItems) {//LOOPS THROUGH ALL THE GOODS AND COMPARES ALL THE PARAMETERS OTHER THAN STOCK
            if (temp.getName().equals(g.getName()) &&
                    temp.getPrice() == g.getPrice() &&
                    temp.getMass() == g.getMass() &&
                    temp.getSize() == g.getSize() &&
                    temp.getDesc().equals(g.getDesc()) &&
                    temp.getImgUrl().equals(g.getImgUrl()))
            {
                return temp;
            }
        }
        return null; //RETURNS NULL IF NO OBJECT MATCHES
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void drawFloor(Canvas gridCanvas, Floor floor) { //DRAWS FLOORS ON A CANVAS FROM PARAMETER
        if (floor == null) return;

        //---------------------------------------------------
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight()); //CLEARS THE GRID FROM ANY PREVIOUS DRAWINGS

        double canvasWidth = gridCanvas.getWidth(); //SETS DIMENSIONS
        double canvasHeight = gridCanvas.getHeight();

        MyLinkedList<Integer> floorSize = floor.getSize();//GETS SIZES OF THE FLOOR
        double floorWidth = floorSize.get(0);
        double floorHeight = floorSize.get(1);

        double scaleX = canvasWidth / (floorWidth + 20); //SCALES THE DRAWING TO FIT THE CANVAS
        double scaleY = canvasHeight / (floorHeight + 20);
        double scale = Math.min(scaleX, scaleY);//PICKS MIN TO ENSURE FIT

        double xOffset = (canvasWidth - floorWidth * scale) / 2;//SETS THE FLOOR TO BE DRAWN IN THE CENTER OF CANVAS
        double yOffset = (canvasHeight - floorHeight * scale) / 2;


        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(xOffset, yOffset, floorWidth * scale, floorHeight * scale);

        double faYOffset = yOffset + 20;

        //---------------------------------------------------
        for (FloorArea fa : floor.getFloorAreas()) {//GETS ALL THE FLOOR AREAS SAME WAY THE FLOORS WERE DONE
            MyLinkedList<Integer> faSize = fa.getSize();
            double faWidth = faSize.get(0) * scale;
            double faHeight = faSize.get(1) * scale;

            double faXOffset = xOffset + 10;
            gc.setFill(Color.GRAY);
            gc.fillRect(faXOffset, faYOffset, faWidth, faHeight);

            double aisleYOffset = faYOffset + 10;

            //---------------------------------------------------
            for (Aisle a : fa.getAisles()) { //GETS ALL THE AISLES SAME WAY AS BOTH FLOORS AND FLOOR AREAS WERE MADE
                MyLinkedList<Integer> aisleSize = a.getSize();
                double aWidth = aisleSize.get(0) * scale;
                double aHeight = aisleSize.get(1) * scale;

                double aisleXOffset = faXOffset + 5;
                gc.setFill(Color.DARKGRAY);
                gc.fillRect(aisleXOffset, aisleYOffset, aWidth, aHeight);

                aisleYOffset += aHeight + 5;
            }
            //---------------------------------------------------
            faYOffset += faHeight + 10;
        }
        //---------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------------------------------
    static void showAlert(String message) { //METHOD TO SHOW ALERTS WHEN ERRORS APPEAR DURING OTHER METHODS
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
    //----------------------------------------------------------------------------------------------------------------------

}
