package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class InsertionSortBenchmark {
    public static void main(String[] args) throws Exception {
        int exp = 100;
        int[] arraySize = new int[]{1000,2000,4000,8000,16000};

        // Consumer to perform insertion sort on an Integer array using a lambda expression
        Consumer<Integer[]> insertion_sort_lbd = (Integer[] xs) -> new InsertionSort<Integer>().sort(xs);

        for(int size: arraySize) {
            System.out.println("Array Length : "+size);

            //random array of integers - benchmark
            Benchmark_Timer<Integer[]> InsertionSortRandomBM = new Benchmark_Timer<>("Random Order of size "+size, insertion_sort_lbd);
            double random_rt = InsertionSortRandomBM.runFromSupplier(genRandomSupplier.apply(size),exp);

            //sorted array of integers - benchmark
            Benchmark_Timer<Integer[]> InsertionSortSortedBM = new Benchmark_Timer<>("Sorted Order of size "+size, insertion_sort_lbd);
            double sorted_rt = InsertionSortSortedBM.runFromSupplier(genSortedSupplier.apply(size),exp);

            //reverse sorted array of integers - benchmark
            Benchmark_Timer<Integer[]> InsertionSortReverseSortedBM = new Benchmark_Timer<>("Reverse Sorted Order of size "+size, insertion_sort_lbd);
            double reverse_sorted_rt = InsertionSortReverseSortedBM.runFromSupplier(genReverseSortedSupplier.apply(size),exp);

            //partial sorted array of integers - benchmark
            Benchmark_Timer<Integer[]> InsertionSortPartiallySortedBM = new Benchmark_Timer<>("Partially Sorted Order of size "+size, insertion_sort_lbd);
            double partial_sorted_rt = InsertionSortPartiallySortedBM.runFromSupplier(genPartiallySortedSupplier.apply(size),exp);

            System.out.println("Random Order (N="+size+") "+random_rt);
            System.out.println("Sorted Order (N="+size+") "+sorted_rt);
            System.out.println("Reverse Order (N="+size+") "+reverse_sorted_rt);
            System.out.println("Partially Sorted (N="+size+") "+partial_sorted_rt);
        }
    }

    public static Integer[] genArray(int n){
        Integer[] x = new Integer[n];
        Random random = new Random();
        for (int i = 0 ;i < n; i++) {
            x[i] = random.nextInt(1000);
        }
        return x;
    }

    public static Function<Integer, Supplier<Integer[]>> genRandomSupplier = (Integer n) -> () ->
            genArray(n);

    public static Function<Integer, Supplier<Integer[]>> genSortedSupplier = (Integer n) -> () ->
        Arrays.stream(genArray(n)).sorted().toArray(Integer[]::new);

    public static Function<Integer, Supplier<Integer[]>> genReverseSortedSupplier = (Integer n) -> () ->
            Arrays.stream(genArray(n)).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);

    public static Function<Integer, Supplier<Integer[]>> genPartiallySortedSupplier = (Integer n) -> () ->
            Stream.concat(
                Arrays.stream(genArray(n / 2)).sorted(),       // First half, sorted
                Arrays.stream(genArray(n - (n / 2)))           // Second half, handling the leftover when n is odd
        ).toArray(Integer[]::new);
}
