package edu.neu.coe.info6205.sort.assignment6;

import java.util.Random;

public class HeapSortStats {
    private static long comparisons = 0; // To count comparisons
    private static long copies = 0; // To count swaps or copies
    private static long hits = 0; // To count array accesses
    private static long memoryUsed = 0; // Memory used by the heap (minimal for Heap Sort)

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("-N=")) {
            System.out.println("Usage: java HeapSortStats -N=<array_size>");
            return;
        }

        // Parse array size
        int arraySize = Integer.parseInt(args[0].substring(3));
        if (arraySize <= 0) {
            System.out.println("Array size must be a positive integer.");
            return;
        }

        // Generate random array
        int[] array = new Random().ints(arraySize, 1, 100).toArray();

        // Calculate initial memory used by the array
        memoryUsed += arraySize * Integer.BYTES;

        // Sort and measure statistics
        long startTime = System.nanoTime();
        heapSort(array);
        long endTime = System.nanoTime();

        // Print statistics
        System.out.println("Heap Sort Statistics:");
        System.out.println("Array size: " + arraySize);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Copies: " + copies);
        System.out.println("Array accesses (hits): " + hits);
        System.out.println("Additional memory used: " + memoryUsed + " bytes");
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
    }

    private static void heapSort(int[] array) {
        int n = array.length;

        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // Swap the root with the last element
            swap(array, 0, i);

            // Heapify the reduced heap
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // Check if left child is larger than root
        if (left < n) {
            comparisons++; // Comparison between array[left] and array[largest]
            hits += 2; // Access array[left] and array[largest]
            if (array[left] > array[largest]) {
                largest = left;
            }
        }

        // Check if right child is larger than largest
        if (right < n) {
            comparisons++; // Comparison between array[right] and array[largest]
            hits += 2; // Access array[right] and array[largest]
            if (array[right] > array[largest]) {
                largest = right;
            }
        }

        // If largest is not root, swap and recursively heapify
        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    private static void swap(int[] array, int i, int j) {
        hits += 2; // Access array[i] and array[j]
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        hits += 2; // Write to array[i] and array[j]
        copies += 3; // Count three assignments (swap operation)
    }
}
