package edu.neu.coe.info6205.sort.par;

public class ProcessorInfo {
    public static void main(String[] args) {
        // Get the number of available processors (logical processors)
        int logicalProcessors = Runtime.getRuntime().availableProcessors();

        System.out.println("Logical processors (threads): " + logicalProcessors);
    }
}
