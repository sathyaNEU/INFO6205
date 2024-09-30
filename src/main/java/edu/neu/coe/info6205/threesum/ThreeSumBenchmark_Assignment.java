package edu.neu.coe.info6205.threesum;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ThreeSumBenchmark_Assignment {
    int n_exp = 8;
    int[] len_exp = new int[n_exp];

    public ThreeSumBenchmark_Assignment() {
        System.out.println("Experiment Batches");
        for(int i=1; i<=n_exp; ++i) {
            len_exp[i - 1] = (int) (Math.pow(10, 3) * Math.pow(2, i));
            System.out.println(len_exp[i - 1]);
        }
        System.out.println("-------------------------------------------------------------------");
    }

    public static void gen_exp_csv(){
        String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\neu\\coe\\info6205\\threesum\\results.csv";
        System.out.println(filePath);
        File file = new File(filePath);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "array_size", "time_in_ms"});
            ThreeSumBenchmark_Assignment benchmark = new ThreeSumBenchmark_Assignment();
            for ( int i = 0; i < benchmark.len_exp.length; ++i){
                long start = System.currentTimeMillis();
//                System.out.println("Experiment : "+(i+1)+" -> Size : "+benchmark.len_exp[i]);
                ThreeSumQuadratic tsq = new ThreeSumQuadratic(benchmark.gen_random_ints(benchmark.len_exp[i]));
                Triple[] triples = tsq.getTriples();
//            System.out.println("triples: " + Arrays.toString(triples));
                long end = System.currentTimeMillis();
                long elapsedTime = end - start;
                System.out.println("elapsedTime: "+elapsedTime);
                data.add(new String[] {String.valueOf(benchmark.len_exp[i]),String.valueOf(elapsedTime)});
//                System.out.println("-------------------------------------------------------------------");
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

    public int[] gen_random_ints(int length){
        Random rd = new Random(); // creating Random object
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = rd.nextInt(); // storing random integers in an array
        return arr;
    }

    public static void main(String[] args) {
    gen_exp_csv();
    }
}
