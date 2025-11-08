package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketControllerTest {

    private MarketController controller;
    private Supermarket market;

    @BeforeEach
    void setUp() {
        controller = new MarketController();

        LinkedList<Floor> floors = new LinkedList<Floor>();
        Floor floor0 = new Floor(0, new LinkedList<FloorArea>(), new LinkedList<Integer>());
        floor0.getSize().add(10);
        floor0.getSize().add(10);
        floors.add(floor0);

        FloorArea dairy = new FloorArea("Dairy", new LinkedList<Aisle>(), new LinkedList<Integer>());
        dairy.getSize().add(5);
        dairy.getSize().add(5);
        floor0.getFloorAreas().add(dairy);

        Aisle milkAisle = new Aisle("Milk", new LinkedList<Shelf>(), new LinkedList<Integer>(), 0);
        milkAisle.getSize().add(4);
        milkAisle.getSize().add(2);
        dairy.getAisles().add(milkAisle);

        Shelf shelf1 = new Shelf(1, new LinkedList<GoodItem>(), new LinkedList<Integer>());
        milkAisle.getShelves().add(shelf1);

        GoodItem milk = new GoodItem("Milk", new LinkedList<Shelf>(),
                3.99, 10, 1.0, 1, "Fresh milk", "milk.jpg");
        shelf1.getGoods().add(milk);

        market = new Supermarket("TestMart", floors);
        controller.setSupermarket(market);
    }

    @Test
    void numToFloor_returnsCorrectFloor() {
        assertEquals(market.getFloors().get(0), controller.numToFloor(0));
        assertNull(controller.numToFloor(99));
    }

    @Test
    void tokenize_handlesNullAndEmpty() {
        assertTrue(controller.tokenize(null).isEmpty());
        assertTrue(controller.tokenize("").isEmpty());
        assertTrue(controller.tokenize("   ").isEmpty());
    }

    @Test
    void tokenize_splitsWords() {
        LinkedList<String> t = controller.tokenize("Hello, world! 2025.");
        assertEquals(4, t.size());
        assertEquals("hello", t.get(0));
        assertEquals("world", t.get(1));
        assertEquals("2025", t.get(3));
    }

    @Test
    void jaccardSimilarity_emptyCases() {
        LinkedList<String> empty = new LinkedList<String>();
        assertEquals(1.0, controller.jaccardSimilarity(empty, empty));

        LinkedList<String> listWithA = new LinkedList<String>();
        listWithA.add("a");
        assertEquals(0.0, controller.jaccardSimilarity(empty, listWithA));
    }

    @Test
    void jaccardSimilarity_identical() {
        LinkedList<String> a = new LinkedList<String>();
        a.add("apple");
        a.add("banana");

        LinkedList<String> b = new LinkedList<String>();
        b.add("apple");
        b.add("banana");

        assertEquals(1.0, controller.jaccardSimilarity(a, b));
    }

    @Test
    void jaccardSimilarity_partial() {
        LinkedList<String> a = new LinkedList<String>();
        a.add("apple");
        a.add("banana");
        a.add("cherry");

        LinkedList<String> b = new LinkedList<String>();
        b.add("banana");
        b.add("date");

        assertEquals(0.25, controller.jaccardSimilarity(a, b), 0.0001);
    }

    @Test
    void similarityShelf_findsBestShelf() {
        GoodItem newGood = new GoodItem("Whole Milk", new LinkedList<Shelf>(),
                4.10, 8, 1.1, 1, "2% fat", "");

        Shelf best = controller.similarityShelf(market, newGood);
        assertNotNull(best);
        assertEquals(1, best.getNumber());
    }

    @Test
    void similarityShelf_noGoods_returnsNull() {
        Supermarket empty = new Supermarket("Empty", new LinkedList<Floor>());
        controller.setSupermarket(empty);

        GoodItem g = new GoodItem("Test", new LinkedList<Shelf>(),
                1.0, 1, 1.0, 1, "", "");
        assertNull(controller.similarityShelf(empty, g));
    }

    @Test
    void existanceCheckGood_mergesDuplicate() {
        controller.existanceCheckGood("Milk", 3.99, 5, 1.0, 1,
                "Fresh milk", "milk.jpg");

        // Manual search for "Milk"
        GoodItem found = null;
        for (Floor f : market.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {
                            if ("Milk".equals(g.getName())) {
                                found = g;
                                break;
                            }
                        }
                        if (found != null) break;
                    }
                    if (found != null) break;
                }
                if (found != null) break;
            }
            if (found != null) break;
        }

        assertNotNull(found);
        assertEquals(15, found.getStock()); // 10 + 5
    }

    @Test
    void existanceCheckGood_addsNewItem() {
        controller.existanceCheckGood("Yogurt", 2.49, 6, 0.5, 1,
                "Greek", "");

        // Manual search for "Yogurt"
        GoodItem found = null;
        for (Floor f : market.getFloors()) {
            for (FloorArea fa : f.getFloorAreas()) {
                for (Aisle a : fa.getAisles()) {
                    for (Shelf s : a.getShelves()) {
                        for (GoodItem g : s.getGoods()) {
                            if ("Yogurt".equals(g.getName())) {
                                found = g;
                                break;
                            }
                        }
                        if (found != null) break;
                    }
                    if (found != null) break;
                }
                if (found != null) break;
            }
            if (found != null) break;
        }

        assertNotNull(found);
        assertEquals(6, found.getStock());
    }

    @Test
    void buildTree_createsRootAndFirstLevel() throws Exception {
        java.lang.reflect.Method buildTree = MarketController.class.getDeclaredMethod("buildTree");
        buildTree.setAccessible(true);
        buildTree.invoke(controller);

        java.lang.reflect.Field mapField = MarketController.class.getDeclaredField("treeMap");
        mapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        java.util.Map<Object, Object> map = (java.util.Map<Object, Object>) mapField.get(controller);

        assertFalse(map.isEmpty());

        Object rootObj = null;
        for (Object value : map.values()) {
            if (value == market) {
                rootObj = value;
                break;
            }
        }
        assertNotNull(rootObj);
    }
}