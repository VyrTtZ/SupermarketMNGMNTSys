package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketControllerTest {

    private MarketController controller;

    @BeforeEach
    void setUp() {
        controller = new MarketController();
    }

    // -------- tokenize() TESTS --------

    @Test
    void tokenize_basicSentence() {
        MyLinkedList<String> tokens = controller.tokenize("Redbull bowl ring stone");
        assertEquals(4, tokens.size());
        assertEquals("redbull", tokens.get(0));
        assertEquals("bowl", tokens.get(1));
        assertEquals("ring", tokens.get(2));
        assertEquals("stone", tokens.get(3));
    }

    @Test
    void tokenize_withSymbolsAndMixedCase() {
        MyLinkedList<String> tokens = controller.tokenize("Redbull! Bowl, RING-Stone??");
        assertEquals(4, tokens.size());
        assertEquals("redbull", tokens.get(0));
        assertEquals("bowl", tokens.get(1));
        assertEquals("ring", tokens.get(2));
        assertEquals("stone", tokens.get(3));
    }

    @Test
    void tokenize_emptyOrNull() {
        MyLinkedList<String> tokens1 = controller.tokenize("");
        MyLinkedList<String> tokens2 = controller.tokenize(null);
        assertTrue(tokens1.isEmpty());
        assertTrue(tokens2.isEmpty());
    }

    // -------- jaccardSimilarity() TESTS --------

    @Test
    void jaccardSimilarity_identicalLists() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        list1.add("redbull");
        list1.add("bowl");
        list2.add("redbull");
        list2.add("bowl");

        double similarity = controller.jaccardSimilarity(list1, list2);
        assertEquals(1.0, similarity);
    }

    @Test
    void jaccardSimilarity_partialOverlap() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();

        list1.add("ring");
        list1.add("stone");
        list2.add("stone");
        list2.add("bowl");

        double similarity = controller.jaccardSimilarity(list1, list2);
        // intersection = 1 ("stone"), union = 3 ("ring", "stone", "bowl")
        assertEquals(1.0 / 3.0, similarity);
    }

    @Test
    void jaccardSimilarity_noOverlap() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        list1.add("redbull");
        list2.add("ring");

        double similarity = controller.jaccardSimilarity(list1, list2);
        assertEquals(0.0, similarity);
    }

    @Test
    void jaccardSimilarity_emptyLists() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        double similarity = controller.jaccardSimilarity(list1, list2);
        assertEquals(1.0, similarity);
    }

    @Test
    void jaccardSimilarity_nullLists() {
        double similarity = controller.jaccardSimilarity(null, null);
        assertEquals(0.0, similarity);
    }

    @Test
    void jaccardSimilarity_oneEmptyList() {
        MyLinkedList<String> list1 = new MyLinkedList<>();
        MyLinkedList<String> list2 = new MyLinkedList<>();
        list1.add("bowl");

        double similarity = controller.jaccardSimilarity(list1, list2);
        assertEquals(0.0, similarity);
    }
}
