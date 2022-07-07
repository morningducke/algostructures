/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[4];
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }


    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (N == q.length) resize(2 * q.length);
        q[N++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int rnd = StdRandom.uniform(N);
        Item buff = q[rnd];
        q[rnd] = q[N-1];
        q[N-1] = null;
        N--;
        if (N > 0 && N == q.length / 4) resize(q.length / 2);
        return buff;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int rnd = StdRandom.uniform(N);
        return q[rnd];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] copy = (Item[]) new Object[N];
        public RQIterator() {
            System.arraycopy(q, 0, copy, 0, N);
            StdRandom.shuffle(copy, 0, N);
        }

        public boolean hasNext() {
            return (i < N);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (i == N) throw new NoSuchElementException();
            return copy[i++];
        }

    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.sample());
        System.out.println(rq.size());
        for (Integer i : rq) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println(rq.isEmpty());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.isEmpty());
        //rq.print();

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

    }
}