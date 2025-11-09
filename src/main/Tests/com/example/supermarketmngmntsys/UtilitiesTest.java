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
        shelf = new Shelf(1, new MyLinkedList<GoodItem>(), new MyLinkedList<Integer>());
        monsterEnergy = new GoodItem("Monster Energy", new MyLinkedList<Shelf>(), 1.0, 40, 0.2, 27, "Cold energy drink", "picture of Monster Energy");

        shelf.getGoods().add(monsterEnergy);
        aisle.getShelves().add(shelf);
        area.getAisles().add(aisle);
        floor.getFloorAreas().add(area);
        supermarket.getFloors().add(floor);

    }
    //----------------------------------------------------------------------------------------------------------------------
    @AfterEach
    void tearDown() {
        supermarket = null;
        floor = null;
        area = null;
        aisle = null;
        shelf = null;
        monsterEnergy = null;
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentShelf() {
        Shelf result = Utilities.getParentShelf(supermarket, monsterEnergy);
        assertEquals(shelf, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentAisle() {
        Aisle result = Utilities.getParentAisle(supermarket, monsterEnergy);
        assertEquals(aisle, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentFloorArea() {
        FloorArea result = Utilities.getParentFloorArea(supermarket, monsterEnergy);
        assertEquals(area, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetParentFloor() {
        Floor result = Utilities.getParentFloor(supermarket, monsterEnergy);
        assertEquals(floor, result);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testAllGoods() {
        MyLinkedList<GoodItem> goods = Utilities.allGoods(supermarket);
        assertEquals(1, goods.size());
        assertEquals(monsterEnergy, goods.get(0));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testGetGoodItemPath() {
        String path = Utilities.getGoodItemPath(supermarket, monsterEnergy);
        assertTrue(path.contains("Cloud"));
        assertTrue(path.contains("Floor 1"));
        assertTrue(path.contains("Stone"));
        assertTrue(path.contains("Windmill"));
        assertTrue(path.contains("Shelf 1"));
        assertTrue(path.contains("Monster Energy"));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testCountGoods() {
        int count = Utilities.countGoods(supermarket);
        assertEquals(1, count);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testCheckDupGoodAddReturnsExisting() {
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(monsterEnergy);

        GoodItem duplicate = new GoodItem("Monster Energy", new MyLinkedList<Shelf>(), 1.0, 40, 0.2, 27, "Cold energy drink", "picture of Monster Energy");
        GoodItem found = Utilities.checkDupGoodAdd(duplicate, goods);

        assertNotNull(found);
        assertEquals(monsterEnergy, found);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void testCheckDupGoodAddReturnsNull() {
        MyLinkedList<GoodItem> goods = new MyLinkedList<>();
        goods.add(monsterEnergy);

        GoodItem newItem = new GoodItem("Bee", new MyLinkedList<Shelf>(), 2.0, 8, 0.2, 27, "Bees make honey", "Good morning Vietnam");
        GoodItem found = Utilities.checkDupGoodAdd(newItem, goods);

        assertNull(found);
    }
    //----------------------------------------------------------------------------------------------------------------------
}
