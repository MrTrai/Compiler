package middleEnd;

import backEnd.Instruction;
import backEnd.InstructionType;
import backEnd.Function;
import graph.CFG;
import graph.TigerCFG;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class IROptimizer {
    private List<String> optimizedCode = new ArrayList<>();
    private ArrayList<Function> optimizedFuncs = new ArrayList<>();

    public IROptimizer() {
    }

    public void optimize(String fileName, boolean doElimination) throws IOException {
        IR ir = new IR(fileName);
        LinkedBlockingQueue<ArrayList<LinkedList<String>>> optimizedQueue = new LinkedBlockingQueue<>();
        Function func;
        Instruction instr;

        for (ArrayList<LinkedList<String>> function : ir.getCodeQueue()) {
            CFG cfg = new TigerCFG(function);
            Eliminator el = new Eliminator(cfg);
            if (doElimination) {
                el.eliminateDeadCode();
            }
            ArrayList<LinkedList<String>> _optimizedFunc = cfg.constructCodes();
            optimizedQueue.add(_optimizedFunc);
        }

        for (ArrayList<LinkedList<String>> eachOptimizedFunc : optimizedQueue) {
            ArrayList<Instruction> instrs = new ArrayList<>();
            LinkedList<String> code;
            optimizedCode.add("#start_function");

            String funcName = initFuncNameString(eachOptimizedFunc.get(0));
            optimizedCode.add(funcName); // Function name
            instr = new Instruction(eachOptimizedFunc.get(0), funcName, InstructionType.TIGER, 0);
            instrs.add(instr);

            String initList = initListString(eachOptimizedFunc.get(1));
            optimizedCode.add(initList);
            instr = new Instruction(eachOptimizedFunc.get(1), initList, InstructionType.TIGER, 1);
            instrs.add(instr);

            String floatList = initListString(eachOptimizedFunc.get(2));
            optimizedCode.add(floatList);
            instr = new Instruction(eachOptimizedFunc.get(2), floatList, InstructionType.TIGER, 2);
            instrs.add(instr);

            for (int i = 3; i < eachOptimizedFunc.size(); i++) {
                code = eachOptimizedFunc.get(i);
                String str = codeToIrString(code, i);
                optimizedCode.add(str);
                instr = new Instruction(code, str, InstructionType.TIGER, i);
                instrs.add(instr);
            }

            optimizedFuncs.add(new Function(instrs, 3));

            optimizedCode.add("#end_function");
        }
    }

    public ArrayList<Function> getOptimizedFuncs() {
        return this.optimizedFuncs;
    }

    public String initFuncNameString(LinkedList<String> code) {
        StringBuilder func = new StringBuilder();
        func.append(code.get(0)).append("(");
        String token;
        for (int i = 1; i < code.size(); i++) {
            token = code.get(i);
            if (i == code.size() - 1) {
                func.append(token);
            } else {
                func.append(token).append(", ");
            }
        }
        func.append("):");
        return func.toString();
    }

    public String initListString(LinkedList<String> code) {
        StringBuilder out = new StringBuilder();
        out.append(code.get(0)).append(": ");
        String token;
        for (int i = 1; i < code.size(); i++) {
            token = code.get(i);
            if (i == code.size() - 1) {
                out.append(token);
            } else {
                out.append(token).append(", ");
            }
        }
        return out.toString();
    }

    public String codeToIrString(LinkedList<String> code, int index) {
        if (index == 0)
            return code.get(0);
        StringBuilder out = new StringBuilder();
        String token;
        for (int i = 0; i < code.size(); i++) {
            token = code.get(i);
            if (i == code.size() - 1) {
                out.append(token);
            } else {
                out.append(token).append(", ");
            }
        }
        return out.toString();
    }

    public void dumpOptimizedCode(String outFileName) throws IOException {
        Path filePath = Paths.get(outFileName);
        Files.write(filePath, optimizedCode, Charset.forName("UTF-8"));
    }


}