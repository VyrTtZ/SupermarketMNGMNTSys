package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Reports {

    private final Supermarket supermarket;

    public Reports(Supermarket supermarket) {
        this.supermarket = supermarket;
    }

    public double averageProductsPerShelf() {
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
    public double totalSupermarketSize() {
        double total = 0;

        for (Floor f : supermarket.getFloors()) {
            LinkedList<Integer> size = f.getSize();
            if (size.size() >= 2) {
                total += size.get(0) * size.get(1);
            }
        }

        return total;
    }

    public int totalAmountStock(){
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

    public double totalStockValue() {
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

    public LinkedList<Double> totalStockByTemperature() {
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

        LinkedList<Double> result = new LinkedList<Double>();
        result.add(room);
        result.add(refrigerated);
        result.add(frozen);

        return result;
    }


    public void showAllStock(){
        for (Floor f : supermarket.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {

                        }
                    }
                }
            }
        }
    }
}
