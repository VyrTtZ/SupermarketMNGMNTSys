package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
//----------------------------------------------------------------------------------------------------------------------
public class UtilitiesTest {
    private Supermarket supermarket;
    private Floor floor;
    private FloorArea area;
    private Aisle aisle;
    private Shelf shelf;
    private GoodItem monsterEnergy;
    //----------------------------------------------------------------------------------------------------------------------
    @BeforeEach
    void setUp() {
        supermarket = new Supermarket("Cloud", new MyLinkedList<Floor>());
        floor = new Floor(1, new MyLinkedList<FloorArea>(), new MyLinkedList<Integer>());
        area = new FloorArea("Stone", new MyLinkedList<Aisle>(), new MyLinkedList<Integer>());
        aisle = new Aisle("Windmill", new MyLinkedList<Shelf>(), new MyLinkedList<Integer>(), 0);
        shelf = new Shelf(0, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>());
        monsterEnergy = new GoodItem("Monster Energy", 1.0, 40, 0.2, 27, "Scary", "Tuff");

        shelf.getGoods().add(monsterEnergy);
        aisle.getShelves().add(shelf);
        area.getAisles().add(aisle);
        floor.getFloorAreas().add(area);
        supermarket.getFloors().add(floor);

    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentShelf() {
        assertEquals(shelf, Utilities.getParentShelf(supermarket, monsterEnergy));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentAisle() {
        assertEquals(aisle, Utilities.getParentAisle(supermarket, monsterEnergy));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentFloorArea() {
        assertEquals(area, Utilities.getParentFloorArea(supermarket, monsterEnergy));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentFloor() {
        assertEquals(floor, Utilities.getParentFloor(supermarket, monsterEnergy));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testAllGoods() {
        assertEquals(1, Utilities.allGoods(supermarket).size());
        assertEquals(monsterEnergy, Utilities.allGoods(supermarket).get(0));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testCountGoods() {
        assertEquals(1, Utilities.countGoods(supermarket));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testDupGoodExisting() {
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(monsterEnergy);

        GoodItem duplicate = new GoodItem("Monster Energy",1.0, 40, 0.2, 27, "Scary", "Tuff");

        assertNotNull(Utilities.checkDupGoodAdd(duplicate, goods));
        assertEquals(monsterEnergy, Utilities.checkDupGoodAdd(duplicate, goods));
    }
    @Test
    void testDupGoodNonexisting(){
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(monsterEnergy);

        GoodItem duplicate = new GoodItem("Bird",1.0, 40, 0.2, 27, "Scary", "Tuff");
        assertNull(Utilities.checkDupGoodAdd(duplicate, goods));
    }
}
