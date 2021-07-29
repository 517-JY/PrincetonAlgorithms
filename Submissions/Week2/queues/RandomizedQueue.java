/* *****************************************************************************
 *  Name: Jiayin Li
 *  Date: 210728
 *  Description: RandomizedQueue implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;

    private Item[] q; // array of elements
    private int n; // number of elements
    private int first; // index of first element
    private int last;  // index of last element


    /**
     * Initializes an empty randomized queue
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }


    /**
     * Returns whether this randomized queue is empty
     *
     * @return ture if this randomized queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }


    /**
     * Returns the number of items in this randomized queue
     *
     * @return the number of items in this randomized queue
     */
    public int size() {
        return n;
    }


    /**
     * Adds the item into this randomized queue
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not call enqueue() with a null argument");
        }
        if (n == q.length) {
            resize(2 * q.length);
        }
        q[last++] = item;
        if (last == q.length) {
            last = 0;
        }
        n++;
    }


    /**
     * Removes and returns a random item on this randomized queue
     *
     * @return a random item on this randomized queue
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can not call dequeue() on an empty randomized queue");
        }

        int randomIdx;
        if (first < last) {
            randomIdx = StdRandom.uniform(first, last);
        }
        else if (first == last) {
            randomIdx = StdRandom.uniform(n);
        }
        else {
            randomIdx = StdRandom.uniform(first, first + n) % q.length;
        }

        // Swap elements between index first and randomIdx
        Item item = q[randomIdx];
        q[randomIdx] = q[first];
        q[first] = null;
        first++;
        n--;
        if (first == q.length) {
            first = 0;
        }

        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }


    /**
     * Returns a random item on this randomized queue
     *
     * @return a random item on this randomized queue
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can not call sample() on an empty randomized queue");
        }

        int randomIdx;
        if (first < last) {
            randomIdx = StdRandom.uniform(first, last);
        }
        else if (first == last) {
            randomIdx = StdRandom.uniform(n);
        }
        else {
            randomIdx = StdRandom.uniform(first, first + n) % q.length;
        }

        return q[randomIdx];
    }


    /**
     * Resizes the underlying array with the given capacity
     *
     * @param capacity the capacity needs to resize the array
     */
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[(i + first) % q.length];
        }
        q = copy;
        first = 0;
        last = n;
    }


    /**
     * Returns an independent iterator over items in random order
     *
     * @return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    /**
     * The helpder RandomizedQueueIterator class
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;

        public RandomizedQueueIterator() {
            // StdOut.println("Before Shuffling, q is :");
            // for (int k = 0; k < n; k++) {
            //     StdOut.print(q[k] + " ");
            // }
            // StdOut.println();

            // Item[] copy = (Item[]) new Object[q.length];
            // for (int j = 0; j < n; j++) {
            //     copy[j] = q[(j + first) % q.length];
            // }
            // q = copy;
            // first = 0;
            // last = n;
            i = 0;
            StdRandom.shuffle(q, 0, n);
            Item[] copy = (Item[]) new Object[q.length];
            int k2 = 0;
            for (int k1 = 0; k1 < q.length; k1++) {
                if (q[k1] != null) {
                    copy[k2++] = q[k1];
                }
            }

            q = copy;
            first = 0;
            last = n;

            // StdOut.println("After Shuffling, q is :");
            // for (int k = 0; k < n; k++) {
            //     StdOut.print(q[k] + " ");
            // }
            // StdOut.println();

        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "Can not calls the next() method in the iterator when the randomized queue is empty.");
            }
            Item item = q[i];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < m; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        StdOut.println("Call dequeue() on this queue: " + queue.dequeue());
        StdOut.println("Call dequeue() on this queue: " + queue.dequeue());
        StdOut.println("Call dequeue() on this queue: " + queue.dequeue());


    }
}
