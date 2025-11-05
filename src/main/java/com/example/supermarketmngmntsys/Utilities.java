package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import com.example.supermarketmngmntsys.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class Utilities {

    public static HBox createRow(String labelText, int fieldCount, Runnable addAction) {
        HBox hbox = new HBox(5);

        Label label = new Label(labelText);
        hbox.getChildren().add(label);

        for (int i = 0; i < fieldCount; i++) {
            TextField tf = new TextField();
            hbox.getChildren().add(tf);
        }

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addAction.run());
        hbox.getChildren().add(addButton);

        return hbox;
    }

    /* ======================= PARENT RESOLVERS ======================= */

    public static Shelf getParentShelf(Supermarket supermarket, GoodItem target) {
        if (supermarket == null || target == null) return null;

        for (Floor floor : supermarket.getFloors()) {
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

    public static Aisle getParentAisle(Supermarket supermarket, GoodItem target) {
        Shelf shelf = getParentShelf(supermarket, target);
        if (shelf == null) return null;

        for (Floor floor : supermarket.getFloors()) {
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

    public static FloorArea getParentFloorArea(Supermarket supermarket, GoodItem target) {
        Aisle aisle = getParentAisle(supermarket, target);
        if (aisle == null) return null;

        for (Floor floor : supermarket.getFloors()) {
            for (FloorArea area : floor.getFloorAreas()) {
                if (area.getAisles().contains(aisle)) {
                    return area;
                }
            }
        }
        return null;
    }

    public static Floor getParentFloor(Supermarket supermarket, GoodItem target) {
        FloorArea area = getParentFloorArea(supermarket, target);
        if (area == null) return null;

        for (Floor floor : supermarket.getFloors()) {
            if (floor.getFloorAreas().contains(area)) {
                return floor;
            }
        }
        return null;
    }

    /* ======================= PATH ======================= */

    public static String getGoodItemPath(Supermarket supermarket, GoodItem target) {
        if (supermarket == null || target == null) return null;

        Floor floor = getParentFloor(supermarket, target);
        FloorArea area = getParentFloorArea(supermarket, target);
        Aisle aisle = getParentAisle(supermarket, target);
        Shelf shelf = getParentShelf(supermarket, target);

        if (floor == null || area == null || aisle == null || shelf == null) return null;

        return supermarket.getName() +
                " > Floor " + floor.getLevel() +
                " > " + area.getName() +
                " > " + aisle.getName() +
                " > Shelf " + shelf.getNumber() +
                " > " + target.getName();
    }

    /* ======================= AGGREGATION ======================= */

    public static int countGoods(Supermarket supermarket) {
        if (supermarket == null) return 0;

        int count = 0;
        for (Floor floor : supermarket.getFloors()) {
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

    public static GoodItem checkDupGoodAdd(GoodItem g, LinkedList<GoodItem> goodItems) {
        for (GoodItem temp : goodItems) {
            if (temp.getName().equals(g.getName()) &&
                    temp.getPrice() == g.getPrice() &&
                    temp.getMass() == g.getMass() &&
                    temp.getSize() == g.getSize() &&
                    ((temp.getDesc() == null && g.getDesc() == null) ||
                            (temp.getDesc() != null && temp.getDesc().equals(g.getDesc()))) &&
                    ((temp.getImgUrl() == null && g.getImgUrl() == null) ||
                            (temp.getImgUrl() != null && temp.getImgUrl().equals(g.getImgUrl())))
            ) {
                return temp;
            }
        }
        return null;
    }

    public static void drawFloor(Canvas gridCanvas, Floor floor) {

        if (floor == null) return;

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

}
