package edu.neu.coe.info6205.sort.assignment6;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;

import java.util.Random;

public class DualPivotQuickSortStats {

    // Static variables to track stats
    private static long comparisons = 0;
    private static long swaps = 0;
    private static long copies = 0;
    private static long hits = 0;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("-N=")) {
            System.out.println("Usage: java DualPivotQuickSortStats -N=<array_size>");
            return;
        }

        // Parse array size
        int arraySize = Integer.parseInt(args[0].substring(3));
        if (arraySize <= 0) {
            System.out.println("Array size must be a positive integer.");
            return;
        }

        // Generate random array
        Integer[] array = new Random().ints(arraySize, 1, 100).boxed().toArray(Integer[]::new);

        // Sort and measure statistics
        long startTime = System.nanoTime();
        dualPivotQuickSort(array, 0, array.length - 1);
        long endTime = System.nanoTime();

        // Print statistics
        System.out.println("Dual-Pivot Quick Sort Statistics:");
        System.out.println("Array size: " + arraySize);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println("Copies: " + copies);
        System.out.println("Array accesses (hits): " + hits);
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
    }

    public static void dualPivotQuickSort(Integer[] array, int low, int high) {
        if (low < high) {
            // Partition the array and get pivots
            int[] pivots = partition(array, low, high);

            // Recursively sort the partitions
            dualPivotQuickSort(array, low, pivots[0] - 1);
            dualPivotQuickSort(array, pivots[0] + 1, pivots[1] - 1);
            dualPivotQuickSort(array, pivots[1] + 1, high);
        }
    }

    public static int[] partition(Integer[] array, int low, int high) {
        if (array[low].compareTo(array[high]) > 0) {
            swap(array, low, high);  // Ensure the pivot1 < pivot2
        }
        hits += 2; // Access array[low] and array[high]
        comparisons++; // Compare array[low] and array[high]

        Integer pivot1 = array[low];
        Integer pivot2 = array[high];
        hits += 2; // Access pivots

        int lt = low + 1;
        int gt = high - 1;
        int i = lt;

        while (i <= gt) {
            hits++; // Access array[i]
            if (array[i].compareTo(pivot1) < 0) {
                swap(array, i, lt++);
                comparisons++; // Compare array[i] with pivot1
            } else if (array[i].compareTo(pivot2) > 0) {
                swap(array, i, gt--);
                comparisons++; // Compare array[i] with pivot2
            } else {
                i++;
            }
        }

        swap(array, low, lt - 1);  // Place pivot1 in its correct position
        swap(array, high, gt + 1);  // Place pivot2 in its correct position

        // Return indices of the two pivots to help divide the array into 3 subarrays
        return new int[]{lt - 1, gt + 1};
    }

    private static void swap(Integer[] array, int i, int j) {
        // Ensure indices are within bounds before swapping
        if (i >= 0 && i < array.length && j >= 0 && j < array.length) {
            hits += 2; // Access array[i] and array[j]
            if (i != j) {
                Integer temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                swaps++;  // Actual swap operation
            }
        } else {
            System.out.println("Warning: Attempted to swap out-of-bounds indices: " + i + " and " + j);
        }
    }

}
