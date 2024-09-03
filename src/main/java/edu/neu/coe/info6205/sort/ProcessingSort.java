/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort;

/**
 * Interface ProcessingSort.
 * This interface defines the behavior of a sort with pre/post-processing.
 * Note that the underlying type X is not required to be Comparable.
 *
 * @param <X> the underlying type.
 */
public interface ProcessingSort<X> extends Sort<X> {

    /**
     * Perform pre-processing step for this Sort.
     *
     * @param xs the elements to be pre-processed.
     */
    default X[] preProcess(X[] xs) {
        init(xs.length);
        return xs;
    }

    /**
     * Post-process the given array, i.e. after sorting has been completed.
     *
     * @param xs an array of Xs.
     */
    void postProcess(X[] xs);

}