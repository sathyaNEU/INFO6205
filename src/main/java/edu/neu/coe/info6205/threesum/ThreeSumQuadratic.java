package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        Arrays.sort(this.a);
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();  //j is the fixed index
        int start = 0; //start index
        int end = length - 1; //end index
        while (start <  j && end >  j) { //2 pointer solution to run the loop until the start and end does not cross the fixed index
            int sum = this.a[start] + this.a[end] + this.a[j];
            if(sum == 0)   { // return the triplet if sum is 0
                triples.add(new Triple( this.a[start],this.a[j],this.a[end]));
                start++;
                end--;
            }
            else{
                if (sum>0) end--; //if the sum is greater than target (0), we need a lesser integer to bring the sum closer to the target
                else start++; //opposite case of the above comment stmt
            }
        }
        return triples;
    }

    private final int[] a;
    private final int length;

}


