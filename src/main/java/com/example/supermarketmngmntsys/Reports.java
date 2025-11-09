package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
//----------------------------------------------------------------------------------------------------------------------
public class Reports {//FIELDS

    private final Supermarket supermarket;

    public Reports(Supermarket supermarket) {//CONSTRUCTOR NEEDING SUPERMARKET TO GET THE VALUES FROM SAID SUPERMARKET
        this.supermarket = supermarket;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double averageProductsPerShelf() {//GET ALL THE SHELVES AND ALL THE PRODUCT IN THOSE SHELVES AND THEN DIVIDE BY TOTAL SHELVES
        int totalProducts = 0;
        int totalShelves = 0;

        for (Floor f : supermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        totalShelves++;
                        for (GoodItem g : s.getGoods()) {
                            totalProducts += g.getStock();
                        }
                    }
                }
            }
        }

        if (totalShelves == 0) return 0;
        return (double) totalProducts / totalShelves;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double totalSupermarketSize() {
        double total = 0;

        for (Floor f : supermarket.getFloors()) { //GET THE VALUES FROM THE LINKED LISTS AND ADD MULTIPLY THEM TOGETHER TO GET TOTAL SIZE OF THE SUPERMARKET
            total += f.getSize().get(0) * f.getSize().get(1);
        }

        return total;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int totalAmountStock(){//ADD UP ALL THE GOODS STOCK
        int total = 0;
        for (Floor f : supermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {
                            total += g.getStock();
                        }
                    }
                }
            }
        }
        return total;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double totalStockValue() {//ADD UP TOGETHER ALL THE GOODS PRICE MULTIPLIED BY THEIR STOCK
        double total = 0;

        for (Floor f : supermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {
                            total += g.getPrice() * g.getStock();
                        }
                    }
                }
            }
        }

        return total;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public MyLinkedList<Double> totalStockWithTemp() {//TOTAL STOCK ACCORDING TO THE TEMPERATURE THAT ITS STORED AT
        double frozen = 0;
        double refrigerated = 0;
        double room = 0;

        for (Floor f : supermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    double aisleValue = 0;
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {
                            aisleValue += g.getPrice() * g.getStock();
                        }
                    }

                    if (a.getRefrigerated() == -1) frozen += aisleValue;
                    else if (a.getRefrigerated() == 0) refrigerated += aisleValue;
                    else room += aisleValue;
                }
            }
        }

        MyLinkedList<Double> result = new MyLinkedList<Double>();
        result.add(room);
        result.add(refrigerated);
        result.add(frozen);

        return result;
    }
    //----------------------------------------------------------------------------------------------------------------------
}
