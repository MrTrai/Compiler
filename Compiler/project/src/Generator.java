import backEnd.*;
import middleEnd.IROptimizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Generator {

    public static void main(String[] args) throws IOException, IncorrectInstructionTypeException {
        String irPath = args[0];
        String dumpLocation = args[1];
        int allocatorType = Integer.parseInt(args[2]);
        boolean prePass = false;

        if (args.length == 4) {
            prePass = true;
        }

        IROptimizer optimizer = new IROptimizer();
        optimizer.optimize(irPath, prePass);
        optimizer.dumpOptimizedCode(dumpLocation + ".ir");

        ArrayList<Function> funcList = optimizer.getOptimizedFuncs();
        ArrayList<Function> intermediateMipsFuncs = IntermediateMipsMapper.toMipsProgram(funcList);
        Program myProgram = new Program(intermediateMipsFuncs);
        RegisterAllocator myAllocator = new RegisterAllocator(myProgram, allocatorType);
        myProgram = myAllocator.getMyProgram();
        ArrayList<Function> mipsFuncs = FuncCallConverter.toMips(new ArrayList<>(myProgram.getMyFunctions()), allocatorType);
        ArrayList<String> formattedMips = MipsFormatter.format(mipsFuncs);
        MipsFormatter.dumpMipsFile(dumpLocation + ".s", formattedMips);
    }
}
