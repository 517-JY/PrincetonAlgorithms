/* *****************************************************************************
 *  Name: Jiayin Li
 *  Date: 210728
 *  Description: Deque Implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n; // number of elements
    private Node<Item> first;  // begining of deque
    private Node<Item> last;   // end of deque


    // a helper linked list class
    private static class Node<Item> {
        Item item;
        Node<Item> prev;
        Node<Item> next;
    }

    // Construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    /**
     * Returns true if this deque is empty
     *
     * @return true if this deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }


    /**
     * Returns the number of items on the deque
     *
     * @return the number of items on the deque
     */
    public int size() {
        return n;
    }


    /**
     * Adds the item to the front
     *
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not call addFirst() with a null argument");
        }
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            first.prev = oldFirst;
        }
        n++;
    }


    /**
     * Adds the item to the back
     *
     * @param item the item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not call addLast() with a null argument");
        }
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        n++;
    }


    /**
     * Removes and return the item from the front
     *
     * @return the item at the front
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can not call removeFirst() on an empty deque");
        }

        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;

    }


    /**
     * Removes and return the item from the back
     *
     * @return the item at the back
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can not call removeLast() on an empty deque");
        }

        Item item = last.item;
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        return item;
    }


    /**
     * Returns an iterator to this deque that iterates through the items from front to back
     *
     * @return an iterator to this deque that iterates through the items from front to back
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    /**
     * The DequeIterator class
     */
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        // Testing addFirst
        Deque<Integer> queue = new Deque<Integer>();
        for (int i = 0; i < n; i++) {
            queue.addFirst(i);
        }
        StdOut.println("Deque size is : " + queue.size());
        StdOut.println("Whether deque is empty: " + queue.isEmpty());
        StdOut.print(queue.removeFirst() + " " + queue.removeFirst());
        StdOut.println();
        for (int a : queue) {
            StdOut.print(a + " ");
        }
        StdOut.println();

        for (int i = 0; i < n; i++) {
            queue.addLast(i);
        }
        StdOut.print(queue.removeLast() + " " + queue.removeLast());
        StdOut.println();
        for (int a : queue) {
            StdOut.print(a + " ");
        }
        StdOut.println();
    }
}
