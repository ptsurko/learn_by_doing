package com.company.collections;

        import org.junit.Test;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;

public class LinkedList2Test {

    @Test
    public void testPush() {
        LinkedList2 al = new LinkedList2();
        al.push(10);

        assertEquals(1, al.size());
        assertEquals(10, al.pop());
    }

    @Test
    public void testAddLast() {
        LinkedList2 al = new LinkedList2();
        al.addLast(10);
        al.addLast(20);

        assertEquals(10, al.pop());
        assertEquals(20, al.pop());
        assertEquals(0, al.size());
    }

    @Test
    public void testRemoveLast() {
        LinkedList2 al = new LinkedList2();
        al.addLast(10);
        al.addLast(20);

        assertEquals(20, al.removeLast());
        assertEquals(10, al.removeLast());
        assertEquals(0, al.size());
    }
}