package backEnd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class NaiveAllocator extends Allocator {
    @Override
    public Program allocate(Program program) {
        Program newProg = new Program();
        LinkedList<Function> funcs = new LinkedList<>();

        for (Function func : program.getMyFunctions()) {
            HashSet<String> spilledRegs = new HashSet<>();

            for (String _int : func.getIntList()) {
                if (! MipsInstruction.isArrayDeclaration(_int)) {
                    spilledRegs.add(_int);
                }
            }

            for (String _float : func.getFloatList()) {
                if (! MipsInstruction.isArrayDeclaration(_float)) {
                    spilledRegs.add(_float);
                }
            }
            spilledRegs.addAll(RegisterAllocator.getVirtualRegs(func.getCodes()));

            func.setSpilledRegs(new ArrayList<>(spilledRegs));
            funcs.add(func);
        }

        newProg.replaceFunctions(funcs);
        return newProg;
    }
}
