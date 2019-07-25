package com.company.collections;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ArrayListTest {

    @Test
    public void testAdd() {
        ArrayList al = new ArrayList();
        al.add(10);

        assertEquals(10, al.get(0));
    }

    @Test
    public void testAddShrink() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);

        assertEquals(10, al.get(0));
        assertEquals(20, al.get(1));
        assertEquals(30, al.get(2));
        assertEquals(40, al.get(3));
        assertEquals(50, al.get(4));
        assertEquals(5, al.size());
    }

    @Test
    public void testAddShrinkInternal() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        assertEquals(1, al.elementsData.length);
        al.add(20);
        assertEquals(2, al.elementsData.length);
        al.add(30);
        assertEquals(3, al.elementsData.length);
        al.add(40);
        assertEquals(4, al.elementsData.length);
        al.add(50);
        assertEquals(6, al.elementsData.length);
    }

    @Test
    public void testSize() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);

        assertEquals(5, al.size());
    }

    @Test
    public void testSet() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);

        al.set(0, 100);
        al.set(1, 200);
        al.set(2, 300);
        al.set(3, 400);
        al.set(4, 500);

        assertEquals(100, al.get(0));
        assertEquals(200, al.get(1));
        assertEquals(300, al.get(2));
        assertEquals(400, al.get(3));
        assertEquals(500, al.get(4));
        assertEquals(5, al.size());
    }

    @Test
    public void testAddAllToEmptyArray() {
        ArrayList al = new ArrayList(1);
        al.addAll(Arrays.asList(10, 20, 30));

        assertEquals(10, al.get(0));
        assertEquals(20, al.get(1));
        assertEquals(30, al.get(2));
        assertEquals(3, al.size());
    }

    @Test
    public void testAddAllToTheEnd() {
        ArrayList al = new ArrayList(1);
        al.addAll(Arrays.asList(10, 20, 30));
        al.addAll(Arrays.asList(40, 50));

        assertEquals(10, al.get(0));
        assertEquals(20, al.get(1));
        assertEquals(30, al.get(2));
        assertEquals(40, al.get(3));
        assertEquals(50, al.get(4));
        assertEquals(5, al.size());
    }

    @Test
    public void testRemoveByIndexFirst() {
        ArrayList al = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

        al.remove(0);
        assertEquals(2, al.get(0));
        assertEquals(4, al.size());
    }

    @Test
    public void testRemoveByIndexMiddle() {
        ArrayList al = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

        al.remove(2);
        assertEquals(4, al.get(2));
        assertEquals(4, al.size());
    }

    @Test
    public void testRemoveByIndexLast() {
        ArrayList al = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

        al.remove(4);
        assertEquals(4, al.get(3));
        assertEquals(4, al.size());
    }

    @Test
    public void testRemoveObject() {
        ArrayList al = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

        al.remove((Object)4);
        assertEquals(5, al.get(3));
        assertEquals(4, al.size());
    }

    @Test
    public void testRemoveRange() {
        ArrayList al = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));

        al.removeRange(1, 3);
        assertEquals(4, al.get(1));
        assertEquals(3, al.size());
    }


    @Test
    public void testEquals() {
        ArrayList al = new ArrayList(Arrays.asList(1,2,3));

        assertTrue(al.equals(Arrays.asList(1,2,3)));
    }

    @Test
    public void testNotEquals() {
        ArrayList al = new ArrayList(Arrays.asList(1,2,3));

        assertFalse(al.equals(Arrays.asList(1,2,3,4)));
        assertFalse(al.equals(Arrays.asList(1,2)));
        assertFalse(al.equals(Arrays.asList()));
        assertFalse(al.equals(null));
    }

    @Test
    public void testTrimToSize() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);

        assertEquals(6, al.elementsData.length);

        al.trimToSize();

        assertEquals(5, al.elementsData.length);
    }

    @Test
    public void testShrinkAfterTrim() {
        ArrayList al = new ArrayList(1);
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);

        al.trimToSize();

        assertEquals(5, al.elementsData.length);

        al.add(60);

        assertEquals(6, al.size());
        assertEquals(7, al.elementsData.length);
    }
}