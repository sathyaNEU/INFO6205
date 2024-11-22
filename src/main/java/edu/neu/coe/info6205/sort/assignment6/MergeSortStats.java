package edu.neu.coe.info6205.sort.assignment6;

import java.util.Arrays;
import java.util.Random;

public class MergeSortStats {
    private static long comparisons = 0; // To count comparisons
    private static long copies = 0; // To count swaps or copies
    private static long hits = 0; // To count array accesses
    private static long memoryUsed = 0; // Additional memory usage

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("-N=")) {
            System.out.println("Usage: java MergeSortStats -N=<array_size>");
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
        mergeSort(array, new int[array.length], 0, array.length - 1);
        long endTime = System.nanoTime();

        // Print statistics
        System.out.println("Merge Sort Statistics:");
        System.out.println("Array size: " + arraySize);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Copies: " + copies);
        System.out.println("Array accesses (hits): " + hits);
        System.out.println("Additional memory used: " + memoryUsed + " bytes");
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
    }

    private static void mergeSort(int[] array, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(array, temp, left, mid);
        mergeSort(array, temp, mid + 1, right);
        merge(array, temp, left, mid, right);
    }

    private static void merge(int[] array, int[] temp, int left, int mid, int right) {
        // Copy data to temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
            hits++; // Access original array
            copies++; // Copy to temp array
        }

        memoryUsed += (right - left + 1) * Integer.BYTES;

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            comparisons++; // Comparison happens here
            if (temp[i] <= temp[j]) {
                array[k++] = temp[i++];
                hits += 2; // Access temp[i] and assign to array[k]
                copies++; // Copy to array
            } else {
                array[k++] = temp[j++];
                hits += 2; // Access temp[j] and assign to array[k]
                copies++; // Copy to array
            }
        }

        while (i <= mid) {
            array[k++] = temp[i++];
            hits += 2; // Access temp[i] and assign to array[k]
            copies++; // Copy to array
        }

        while (j <= right) {
            array[k++] = temp[j++];
            hits += 2; // Access temp[j] and assign to array[k]
            copies++; // Copy to array
        }
    }
}

