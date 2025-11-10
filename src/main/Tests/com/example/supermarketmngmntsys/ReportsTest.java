package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//----------------------------------------------------------------------------------------------------------------------
class ReportsTest {

    private Supermarket supermarket;
    private Reports reports;
    //----------------------------------------------------------------------------------------------------------------------
    @BeforeEach
    void setUp() {
        supermarket = new Supermarket("TestMart", new MyLinkedList<Floor>());

        Floor floor1 = new Floor(1, new MyLinkedList<FloorArea>(), new MyLinkedList<Integer>());
        floor1.getSize().add(20);
        floor1.getSize().add(30);

        FloorArea area = new FloorArea("General", new MyLinkedList<Aisle>(), new MyLinkedList<Integer>());

        Aisle aisle1 = new Aisle("Aisle 1", new MyLinkedList<Shelf>(), new MyLinkedList<Integer>(), 1);
        Aisle aisle2 = new Aisle("Aisle 2", new MyLinkedList<Shelf>(), new MyLinkedList<Integer>(), 0);
        Aisle aisle3 = new Aisle("Aisle 3", new MyLinkedList<Shelf>(), new MyLinkedList<Integer>(), -1);

        Shelf shelf1 = new Shelf(1, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>());
        Shelf shelf2 = new Shelf(2, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>());

        GoodItem redbull = new GoodItem("Redbull",2.5, 400,0.3, 6, "Flying potion", null);
        redbull.setStock(10);

        GoodItem bowl = new GoodItem("Bowl",5.0, 30,1.0, 2, "Bowling", null);
        bowl.setStock(3);

        GoodItem ring = new GoodItem("Ring",100.0, 2, 0.05, 1, "Silver ring", null);
        ring.setStock(1);

        GoodItem stone = new GoodItem("Stone",0.5, 678, 1.5, 5, "Stoned", null);
        stone.setStock(20);

        shelf1.getGoods().add(redbull);
        shelf1.getGoods().add(bowl);

        shelf2.getGoods().add(ring);
        shelf2.getGoods().add(stone);

        aisle1.getShelves().add(shelf1);
        aisle2.getShelves().add(shelf2);
        aisle3.getShelves().add(new Shelf(3, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>()));

        area.getAisles().add(aisle1);
        area.getAisles().add(aisle2);
        area.getAisles().add(aisle3);

        floor1.getFloorAreas().add(area);
        supermarket.getFloors().add(floor1);

        reports = new Reports(supermarket);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testAverageProductsPerShelf() {
        double result = reports.averageProductsPerShelf();
        assertEquals(34.0 / 3.0, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testTotalSupermarketSize() {
        double result = reports.totalSupermarketSize();
        assertEquals(600.0, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testTotalAmountStock() {
        int result = reports.totalAmountStock();
        assertEquals(34, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testTotalStockValue() {
        double result = reports.totalStockValue();
        assertEquals(150.0, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testTotalStockByTemperature() {
        MyLinkedList<Double> result = reports.totalStockWithTemp();
        assertEquals(3, result.size());
        assertEquals(40.0, result.get(0));
        assertEquals(110.0, result.get(1));
        assertEquals(0.0, result.get(2));
    }
    //----------------------------------------------------------------------------------------------------------------------
}
