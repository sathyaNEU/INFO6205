package edu.neu.coe.info6205.pq;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class KaryHeap<K> implements Iterable<K> {
    private final K[] heap;
    private int size;
    private final int k;
    private final Comparator<K> comparator;

    public KaryHeap(int capacity, int k, Comparator comparator) {
        if (k < 2) {
            throw new IllegalArgumentException("k must be 2 or greater");
        }
        this.heap = (K[]) new Object[capacity];
        this.size = 0;
        this.k = k;
        this.comparator = comparator;
    }

    public void insert(K key) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = key;
        size++;
        swim(size - 1);
    }

    public K remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        K root = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        sink(0);
        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void swim(int k) {
        while (k > 0 && comparator.compare(heap[k], heap[parent(k)]) < 0) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    private void sink(int k) {
        while (firstChild(k) < size) {
            int smallestChild = firstChild(k);
            for (int i = 1; i < k; i++) {
                int nextChild = smallestChild + i;
                if (nextChild < size && comparator.compare(heap[nextChild], heap[smallestChild]) < 0) {
                    smallestChild = nextChild;
                }
            }
            if (comparator.compare(heap[k], heap[smallestChild]) <= 0) {
                break;
            }
            swap(k, smallestChild);
            k = smallestChild;
        }
    }

    private void swap(int i, int j) {
        K temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int parent(int k) {
        return (k - 1) / this.k;
    }

    private int firstChild(int k) {
        return k * this.k + 1;
    }

    @Override
    public Iterator<K> iterator() {
        return Arrays.stream(heap).filter(e -> e != null).iterator();
    }

    public static void main(String[] args) {
        KaryHeap<Integer> karyHeap = new KaryHeap<>(10,4,Comparator.naturalOrder());

        karyHeap.insert(5);
        karyHeap.insert(10);
        karyHeap.insert(3);
        karyHeap.insert(8);
        karyHeap.insert(1);

        while (!karyHeap.isEmpty()) {
            System.out.println("Removed: " + karyHeap.remove());
        }
    }
}
