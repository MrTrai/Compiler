package backEnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Program {

    private LinkedList<Function> myFunctions;

    public Program() {
        myFunctions = new LinkedList<>();
    }

    public Program(ArrayList<Function> newFuncList) {
        myFunctions = new LinkedList<>(newFuncList);
    }

    public void addFunction(Function newFunc) {
        myFunctions.add(newFunc);
    }

    public void replaceFunctions(LinkedList<Function> newFunctions) {
        myFunctions = newFunctions;
    }

    public LinkedList<Function> getMyFunctions() {
        return myFunctions;
    }

    public void printLines() {
        for (Function func : myFunctions) {
            System.out.println("---------");
            for (Instruction instr : func.getCodes()) {
                //if (!instr.getTokens().get(0).equals("sw") && !instr.getTokens().get(0).equals("lw")) {
                    System.out.println(instr.getTokens());
                //}
            }
        }
    }

    public void printFuncMappings() {
        for (Function func : myFunctions) {
            System.out.println(func.getMyRegisterAllocation());
        }
    }

}
