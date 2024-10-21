/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.pq.KaryHeap;
import edu.neu.coe.info6205.pq.PQException;
import edu.neu.coe.info6205.pq.PQ;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static edu.neu.coe.info6205.util.SortBenchmarkHelper.getWords;

public class PQBenchmark {

    public PQBenchmark(Config config) {
        this.config = config;
    }

    public static void main(String[] args) throws IOException {
        Config config = Config.load(PQBenchmark.class);
        logger.info("SortBenchmark.main: " + config.get("huskysort", "version") + " with word counts: " + Arrays.toString(args));
        if (args.length == 0) logger.warn("No word counts specified on the command line");
        PQBenchmark benchmark = new PQBenchmark(config);
        System.out.println("with floyd: " + benchmark.insertDeleteN(16000, 4000, true));
        System.out.println("no floyd: " + benchmark.insertDeleteN(16000, 4000, false));

/// Benchmark the 4-ary Heap with 16,000 insertions and 4,000 removals
        System.out.println("4-ary Heap with Floyd: " + benchmark.insertDeleteNWithKaryHeap(16000, 4000, 4));
        System.out.println("4-ary Heap without Floyd: " + benchmark.insertDeleteNWithKaryHeap(16000, 4000, 4));

    }

    // Insert and delete random integer array with floyd methods according to parameter
    private void insertArray(int[] a, final boolean floyd) {
        // Create a PriorityQueue with the correct constructor parameters
        PQ<Integer> pq = new PQ<>(a.length, 1, true, Comparator.naturalOrder(), floyd);
        final Random random = new Random();

        // Insert 16,000 random elements
        for (int j : a) {
            pq.give(j); // Insert element
        }

        // Remove 4,000 elements
        for (int i = 0; i < 4000; i++) {
            if (!pq.isEmpty()) { // Check if the queue is not empty
                try {
                    pq.take(); // Remove the highest priority element
                } catch (PQException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double insertDeleteN(final int n, int m, final boolean floyd) {
        // Create an array of random integers
        final Random ran = new Random();
        int[] random = new int[n];

        // Fill array with random numbers
        for (int i = 0; i < n; i++) {
            random[i] = ran.nextInt(n); // You can adjust this range if needed
        }

        // Set up a benchmark for the PriorityQueue operations
        Benchmark<Boolean> bm = new Benchmark_Timer<>(
                "testPQwithFloydoff",
                null,
                b -> insertArray(random, floyd), // Benchmark function
                null
        );

        // Run the benchmark for 100 iterations or any desired count
        return bm.run(true, 1000); // You can change 100 to however many runs you want for averaging
    }


    //new
    private void insertArrayKaryHeap(int[] a, final boolean floyd, int k) {
        KaryHeap<Integer> karyHeap = new KaryHeap<>(a.length, k, Comparator.naturalOrder());
        final Random random = new Random();
        for (int j : a) {
            karyHeap.insert(j);
            if (random.nextBoolean()) {
                karyHeap.remove();
            }
        }
    }

    private double insertDeleteNWithKaryHeap(final int n, final int m, final int k) {
        // Create a benchmark instance
        Benchmark<Boolean> bm = new Benchmark_Timer<>(
                "insertDeleteNWithKaryHeap",
                null,
                b -> {
                    KaryHeap<Integer> karyHeap = new KaryHeap<>(n, k, Comparator.naturalOrder());
                    final Random random = new Random();

                    // Insert 16000 random elements
                    for (int i = 0; i < n; i++) {
                        int element = random.nextInt(n);
                        karyHeap.insert(element);
                    }

                    // Remove 4000 elements
                    for (int i = 0; i < m; i++) {
                        if (!karyHeap.isEmpty()) {
                            karyHeap.remove();
                        }
                    }

                     // return value for the benchmark, can be any dummy value
                },
                null
        );

        // Run the benchmark with m repetitions
        return bm.run(true, 1000); // Change 10 to however many runs you want for averaging
    }


// In the main method, you can now benchmark the KaryHeap as follows:



    /**
     * For mergesort, the number of array accesses is actually six times the number of comparisons.
     * That's because, in addition to each comparison, there will be approximately two copy operations.
     * Thus, in the case where comparisons are based on primitives,
     * the normalized time per run should approximate the time for one array access.
     */
    public final static TimeLogger[] timeLoggersLinearithmic = {
            new TimeLogger("Raw time per run (mSec): ", null),
            new TimeLogger("Normalized time per run (n log n): ", SortBenchmark::minComparisons)
    };

    final static LazyLogger logger = new LazyLogger(PQBenchmark.class);

    final static Pattern regexLeipzig = Pattern.compile("[~\\t]*\\t(([\\s\\p{Punct}\\uFF0C]*\\p{L}+)*)");

    /**
     * This is the mean number of inversions in a randomly ordered set of n elements.
     * For insertion sort, each (low-level) swap fixes one inversion, so on average, this number of swaps is required.
     * The minimum number of comparisons is slightly higher.
     *
     * @param n the number of elements
     * @return one quarter n-squared more or less.
     */
    static double meanInversions(int n) {
        return 0.25 * n * (n - 1);
    }

    private static Collection<String> lineAsList(String line) {
        List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

    private static Collection<String> getLeipzigWords(String line) {
        return getWords(regexLeipzig, line);
    }

    // CONSIDER: to be eliminated soon.
    private static Benchmark<LocalDateTime[]> benchmarkFactory(String description, Consumer<LocalDateTime[]> sorter, Consumer<LocalDateTime[]> checker) {
        return new Benchmark_Timer<>(
                description,
                (xs) -> Arrays.copyOf(xs, xs.length),
                sorter,
                checker
        );
    }

    private static final double LgE = Utilities.lg(Math.E);

    boolean isConfigBoolean(String section, String option) {
        return config.getBoolean(section, option);
    }

    private final Config config;
}