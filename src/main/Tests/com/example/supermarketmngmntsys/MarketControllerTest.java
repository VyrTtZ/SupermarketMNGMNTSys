package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//----------------------------------------------------------------------------------------------------------------------
class MarketControllerTest {

    private MarketController controller;
    //----------------------------------------------------------------------------------------------------------------------
    @BeforeEach
    void setUp() {
        controller = new MarketController();
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void tokenize() {
        MyLinkedList<String> tokens = controller.tokenize("Redbull bowl ring stone");
        assertEquals(4, tokens.size());
        assertEquals("redbull", tokens.get(0));
        assertEquals("bowl", tokens.get(1));
        assertEquals("ring", tokens.get(2));
        assertEquals("stone", tokens.get(3));
    }
    @Test
    void tokenizeEmpty(){
        MyLinkedList<String> tokens = controller.tokenize("");
        assertNotEquals("redbull", tokens.get(0));
        assertNull(tokens.get(0));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void jaccardSimilaritySame() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        list1.add("redbull");
        list1.add("bowl");
        list2.add("redbull");
        list2.add("bowl");
        assertEquals(1.0, controller.jaccardSimilarity(list1, list2));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void jaccardSimilaritySome() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();

        list1.add("ring");
        list1.add("stone");
        list2.add("stone");
        list2.add("bowl");

        assertEquals(1.0 / 3.0, controller.jaccardSimilarity(list1, list2));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Test
    void jaccardSimilarityNone() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        list1.add("redbull");
        list2.add("ring");
        assertEquals(0.0, controller.jaccardSimilarity(list1, list2));
    }
    //----------------------------------------------------------------------------------------------------------------------
}
