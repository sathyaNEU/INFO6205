/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TO BE IMPLEMENTED  do move
        this.x = this.x + dx;
        this.y = this.y + dy;
        // END SOLUTION
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED
        for(int i = 0; i < m; i++)
            randomMove();
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED 
         return Math.sqrt(this.x * this.x + this.y * this.y); //sqrt((y2-y1)^2 - (x2-x1)^2) ; y2 = this.y ; y1 = 0 ; x2 = this.x ; x1 = 0
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void generateValidationData(int[] exp_m, int n){
        String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\neu\\coe\\info6205\\randomwalk\\results.csv";
        System.out.println(filePath);
        File file = new File(filePath);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "m", "d"});
//            data.add(new String[] { "Aman", "10", "620" });
//            data.add(new String[] { "Suraj", "10", "630" });
            for(int m : exp_m) {
                for(int exp = 0; exp < 60 ;exp++) {
                    double meanDistance = randomWalkMulti(m, n);
                    data.add(new String[] {String.valueOf(m),String.valueOf(meanDistance)});
                    System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
                }
            }
            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        if (args.length == 0)
//            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
//        int m = Integer.parseInt(args[0]);
//        int n = 30;
//        if (args.length > 1) n = Integer.parseInt(args[1]);
        int[] exp_m = {5,10,15,20,25,30,35,40,45,50}; //number of steps
//        int[] exp_n = {100,1000,10000,100000,1000000,10000000}; //number of experimentations
        int n = 100;
        generateValidationData(exp_m,n);
    }

}