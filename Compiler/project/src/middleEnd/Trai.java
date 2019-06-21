package middleEnd;

import java.io.IOException;

public class Trai {
    public static void main(String[] args) throws IOException {
        IROptimizer optimizer = new IROptimizer();
        optimizer.optimize("./middleEnd/example/quicksort.ir", true);
        optimizer.dumpOptimizedCode("./out.ir");
    }
}
