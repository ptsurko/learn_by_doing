package com.company.collections;

import java.util.*;

// How ArrayList works internally in Java - https://netjs.blogspot.com/2015/08/how-arraylist-works-internally-in-java.html

// TODO: make generic

public class ArrayList extends AbstractList {
    Object[] elementsData;
    private int size = 0;
    private static int DEFAULT_CAPACITY = 10;
    private static int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 1;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        elementsData = new Object[initialCapacity];
    }

    public ArrayList(Collection c) {
        elementsData = c.toArray();
        size = elementsData.length;
    }

    @Override
    public Object get(int index) {
        ensureIndexInRange(index);
        return elementsData[index];
    }

    @Override
    public boolean add(Object o) {
        ensureEnoughCapacity(size + 1);
        elementsData[size] = o;
        size += 1;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        ensureIndexInRange(index);
        ensureEnoughCapacity(size + 1);
        System.arraycopy(elementsData, index, elementsData, index + 1, size - index);
        elementsData[index] = element;
        size += 1;
    }

    @Override
    public boolean addAll(Collection c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        ensureIndexInRange(index);

        Object[] array = c.toArray();
        if (array.length == 0) {
            return false;
        }

        ensureEnoughCapacity(size + array.length);

        System.arraycopy(elementsData, index, elementsData, index + array.length, size - index);
        int iteratorIndex = index;
        for (Object e : c) {
            elementsData[iteratorIndex++] = e;
        }
        size += array.length;
        return true;
    }

    @Override
    public Object set(int index, Object element) {
        ensureIndexInRange(index);

        Object prevElement = elementsData[index];
        elementsData[index] = element;
        return prevElement;
    }

    @Override
    public Object remove(int index) {
        ensureIndexInRange(index);

        Object objectToRemove = elementsData[index];
        System.arraycopy(elementsData, index + 1, elementsData, index, size - (index + 1));
        size -= 1;
        return objectToRemove ;
    }

    @Override
    public boolean remove(Object o) {
        Iterator iter = iterator();

        int index = 0;
        while(iter.hasNext()) {
            Object item = iter.next();
            if ((o == null && item == null) || (o != null && o.equals(item))) {
                remove(index);
                return true;
            }
            index += 1;
        }

        return false;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("To index cannot be smaller than from");
        }

        ensureIndexInRange(fromIndex);
        ensureIndexInRange(toIndex);

        System.arraycopy(elementsData, toIndex, elementsData, fromIndex, size - toIndex);
        size -= (toIndex - fromIndex);
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return super.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return super.removeAll(c);
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureIndexInRange(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private int newCapacity(int minCapacity) {
//        System.out.println("Old capacity: " + minCapacity);
        int oldCapacity = elementsData.length;
        if (minCapacity > MAX_ARRAY_SIZE || minCapacity < 0) {
            throw new OutOfMemoryError();
        }

        if (oldCapacity == 0) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (minCapacity > newCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity > MAX_ARRAY_SIZE || newCapacity < 0) {
            newCapacity = MAX_ARRAY_SIZE;
        }
//        System.out.println("New capacity: " + newCapacity);
        return newCapacity;
    }

    private void ensureEnoughCapacity(int minCapacity) {
        if (minCapacity > elementsData.length) {
            grow(newCapacity(minCapacity));
        }
    }

    private void grow(int newCapacity) {
        elementsData = Arrays.copyOf(elementsData, newCapacity);
    }

    public void trimToSize() {
        Object[] newArray = new Object[size];
        System.arraycopy(elementsData, 0, newArray, 0, size);
        elementsData = newArray;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Collection)) {
            return false;
        }

        Iterator i1 = this.iterator();
        Iterator i2 = ((Collection)o).iterator();

        while (i1.hasNext()) {
            Object el1 = i1.next();
            if (!i2.hasNext()) {
                return false;
            }

            Object el2 = i2.next();
            if (!Objects.equals(el1, el2)) {
                return false;
            }
        }

        return !i2.hasNext();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elementsData);
    }
}
