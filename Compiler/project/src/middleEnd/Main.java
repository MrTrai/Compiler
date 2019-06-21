package middleEnd;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        String outputFile = args[1];
        IROptimizer optimizer = new IROptimizer();
        optimizer.optimize(inputFile, true);
        optimizer.dumpOptimizedCode(outputFile);
        System.out.println(inputFile + " successfully optimized into " + outputFile);
        /*String exampleIR = "./src/example/example.ir";
        String sqrtIR = "./src/public_test_cases/sqrt/sqrt.ir";
        String qkIR = "./src/public_test_cases/quicksort/quicksort.ir";
        String exampleDump = "./src/example/public_test_cases/example-optimized.ir";
        String sqrtDump = "./src/public_test_cases/sqrt/sqrt-optimized.ir";
        String qkDump = "./src/public_test_cases/quicksort/quicksort-optimized.ir";

        IROptimizer optimizerEx = new IROptimizer();
        optimizerEx.optimize(exampleIR);
        optimizerEx.dumpOptimizedCode(exampleDump);

        IROptimizer optimizerSqrt = new IROptimizer();
        optimizerSqrt.optimize(sqrtIR);
        optimizerSqrt.dumpOptimizedCode(sqrtDump);

        IROptimizer optimizerQk = new IROptimizer();
        optimizerQk.optimize(qkIR);
        optimizerQk.dumpOptimizedCode(qkDump);*/

    }
}