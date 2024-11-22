package edu.neu.coe.info6205.sort.assignment6;

import java.util.Random;

public class ShellSortStats {
    private static long comparisons = 0; // To count comparisons
    private static long copies = 0; // To count swaps or copies
    private static long hits = 0; // To count array accesses
    private static long memoryUsed = 0; // Additional memory usage (minimal for Shell Sort)

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("-N=")) {
            System.out.println("Usage: java ShellSortStats -N=<array_size>");
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
        shellSort(array);
        long endTime = System.nanoTime();

        // Print statistics
        System.out.println("Shell Sort Statistics:");
        System.out.println("Array size: " + arraySize);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Copies: " + copies);
        System.out.println("Array accesses (hits): " + hits);
        System.out.println("Additional memory used: " + memoryUsed + " bytes");
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
    }

    private static void shellSort(int[] array) {
        int n = array.length;

        // Determine initial gap size (Knuth sequence)
        int gap = 1;
        while (gap < n / 3) {
            gap = 3 * gap + 1; // 1, 4, 13, 40, ...
        }

        // Shell Sort logic
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                hits++; // Access array[i]
                int j = i;

                while (j >= gap && array[j - gap] > temp) {
                    comparisons++; // Compare array[j - gap] and temp
                    hits += 2; // Access array[j - gap] and array[j]
                    array[j] = array[j - gap];
                    hits++; // Write to array[j]
                    copies++; // Count assignment
                    j -= gap;
                }

                array[j] = temp;
                hits++; // Write temp to array[j]
                copies++; // Count assignment
            }

            gap /= 3; // Reduce the gap
        }
    }
}
