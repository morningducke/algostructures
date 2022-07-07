/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null)
            throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(item == null)
            throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        size--;
        if(isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev; //go to the (n-1)th
        if(last != null) last.next = null; //delete nth
        size--;
        if(isEmpty()) first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() { return cur != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeFirst();
        deque.addLast(2);
        deque.removeLast();
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.removeLast();
        deque.removeFirst();
        for(Integer i : deque)
            StdOut.println(i);
    }

}