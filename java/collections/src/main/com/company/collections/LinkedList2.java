package com.company.collections;

import java.util.*;

public class LinkedList2 extends AbstractSequentialList implements List, Deque {
    class Node {
        Object item;
        Node prev;
        Node next;

        Node(Node prev, Node next, Object item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    Node head = null;
    Node tail = null;
    int size = 0;

    private void checkIndexInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    @Override
    public ListIterator listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public void addFirst(Object o) {
        Node newNode = new Node(null, head, o);
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size += 1;
    }

    @Override
    public void addLast(Object o) {
        Node newNode = new Node(tail, null, o);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size += 1;
    }

    @Override
    public boolean offerFirst(Object o) {
        return false;
    }

    @Override
    public boolean offerLast(Object o) {
        return false;
    }

    @Override
    public Object removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        Node firstNode = head;
        head = head.next;
        if (head == null) {
            tail = head;
        }
        size -= 1;
        return firstNode.item;
    }

    @Override
    public Object removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        Node lastNode = tail;
        tail = tail.prev;
        if (tail == null) {
            head = tail;
        }
        size -= 1;
        return lastNode.item;
    }

    @Override
    public Object pollFirst() {
        return null;
    }

    @Override
    public Object pollLast() {
        return null;
    }

    @Override
    public Object getFirst() {
        return null;
    }

    @Override
    public Object getLast() {
        return null;
    }

    @Override
    public Object peekFirst() {
        return null;
    }

    @Override
    public Object peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }

    @Override
    public void push(Object o) {
        addLast(o);
    }

    @Override
    public Object pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator descendingIterator() {
        return null;
    }

    private class ListItr implements ListIterator {
        Node current;
        int index;

        ListItr(int index) {
            this.current = head;
            this.index = index;

//            int i = 0;
//            current =
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Object next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return index >= 0;
        }

        @Override
        public Object previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(Object o) {

        }

        @Override
        public void add(Object o) {

        }
    }
}
