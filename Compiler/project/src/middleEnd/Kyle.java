package middleEnd;

import graph.CFG;
import graph.TigerCFG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Kyle {
    public static void main(String[] args) throws IOException, NumberFormatException {
        String exampleIR = "./src/example/example.ir";
        String sqrtIR = "./src/public_test_cases/sqrt/sqrt.ir";
        IR theIR = new IR(exampleIR);
        LinkedBlockingQueue<ArrayList<LinkedList<String>>> theQueue = theIR.getCodeQueue();
        while (!theQueue.isEmpty()) {
            CFG theCFG = new TigerCFG(theQueue.poll());
            ReachingDefBuilder myBuilder = new ReachingDefBuilder(theCFG);
            theCFG.printCodes();
            System.out.println(myBuilder.getReachingDefs(20));
        }
    }
}