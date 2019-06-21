import backEnd.*;
import middleEnd.IROptimizer;

import java.io.IOException;
import java.util.ArrayList;

public class TraiBackend {
    public static void main(String[] args) throws IOException, IncorrectInstructionTypeException {
        IROptimizer optimizer = new IROptimizer();
//        String dir = "example/test_cases/bfs/";
        String dir = "example/array/";
        String file = "arr_assign";
        String ir = ".ir";
        String optimizedOut = ".ir";
        String s = ".s";

        int allocatorType = 3;

        optimizer.optimize(dir + file + ir, false);
        optimizer.dumpOptimizedCode(file + ir);

        System.out.println("--------------------------------------------------");

        ArrayList<Function> funcList = optimizer.getOptimizedFuncs();
        ArrayList<Function> intermediateMipsFuncs = IntermediateMipsMapper.toMipsProgram(funcList);
        Program myProgram = new Program(intermediateMipsFuncs);

        RegisterAllocator allocator3 = new RegisterAllocator(myProgram, allocatorType);
        myProgram = allocator3.getMyProgram();
        ArrayList<Function> mipsFuncs = FuncCallConverter.toMips(new ArrayList<>(myProgram.getMyFunctions()), allocatorType);

        /*for (Function func : mipsFuncs) {
            System.out.println(func.toString());
        }*/

        ArrayList<String> formattedMips = MipsFormatter.format(mipsFuncs);
        MipsFormatter.dumpMipsFile(file + s, formattedMips);
    }
}

