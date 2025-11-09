package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {

    private Supermarket supermarket;
    private Floor floor;
    private FloorArea area;
    private Aisle aisle;
    private Shelf shelf;
    private GoodItem apple;

    @BeforeEach
    void setUp() {
        // Build a simple supermarket structure
        supermarket = new Supermarket("TestMart", new MyLinkedList<Floor>());
        floor = new Floor(1, new MyLinkedList<FloorArea>(), new MyLinkedList<Integer>());
        area = new FloorArea("Produce", new MyLinkedList<Aisle>(), new MyLinkedList<Integer>());
        aisle = new Aisle("Fruits", new MyLinkedList<Shelf>(), new MyLinkedList<Integer>(), 0);
        shelf = new Shelf(1, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>());

        apple = new GoodItem("Apple",new MyLinkedList<Shelf>(), 1.0, 40,0.2, 27, "Fresh apple", "picture of apple");

        shelf.getGoods().add(apple);
        aisle.getShelves().add(shelf);
        area.getAisles().add(aisle);
        floor.getFloorAreas().add(area);
        supermarket.getFloors().add(floor);
    }

    @AfterEach
    void tearDown() {
        supermarket = null;
        floor = null;
        area = null;
        aisle = null;
        shelf = null;
        apple = null;
    }

    @Test
    void testGetParentShelf() {
        Shelf result = Utilities.getParentShelf(supermarket, apple);
        assertEquals(shelf, result);
    }

    @Test
    void testGetParentAisle() {
        Aisle result = Utilities.getParentAisle(supermarket, apple);
        assertEquals(aisle, result);
    }

    @Test
    void testGetParentFloorArea() {
        FloorArea result = Utilities.getParentFloorArea(supermarket, apple);
        assertEquals(area, result);
    }

    @Test
    void testGetParentFloor() {
        Floor result = Utilities.getParentFloor(supermarket, apple);
        assertEquals(floor, result);
    }

    @Test
    void testAllGoods() {
        MyLinkedList<GoodItem> goods = Utilities.allGoods(supermarket);
        assertEquals(1, goods.size());
        assertEquals(apple, goods.get(0));
    }

    @Test
    void testGetGoodItemPath() {
        String path = Utilities.getGoodItemPath(supermarket, apple);
        assertTrue(path.contains("TestMart"));
        assertTrue(path.contains("Floor 1"));
        assertTrue(path.contains("Produce"));
        assertTrue(path.contains("Fruits"));
        assertTrue(path.contains("Shelf 1"));
        assertTrue(path.contains("Apple"));
    }

    @Test
    void testCountGoods() {
        int count = Utilities.countGoods(supermarket);
        assertEquals(1, count);
    }

    @Test
    void testCheckDupGoodAddReturnsExisting() {
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(apple);

        GoodItem duplicate = new GoodItem("Apple",new MyLinkedList<Shelf>(), 1.0, 40,0.2, 27, "Fresh apple", "picture of apple");
        GoodItem found = Utilities.checkDupGoodAdd(duplicate, goods);

        assertNotNull(found);
        assertEquals(apple, found);
    }

    @Test
    void testCheckDupGoodAddReturnsNull() {
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(apple);

        GoodItem banana = new GoodItem("Bee",new MyLinkedList<Shelf>(), 2.0, 8,0.2, 27, "Bees make honey", "Good morning Vietnam");
        GoodItem found = Utilities.checkDupGoodAdd(banana, goods);

        assertNull(found);
    }
}
