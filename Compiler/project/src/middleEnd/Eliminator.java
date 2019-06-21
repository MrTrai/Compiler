package middleEnd;

import graph.BasicBlock;
import graph.CFG;

import java.util.*;

public class Eliminator {
    private CFG cfg;
    private ArrayList<String> branchOps = new ArrayList<>(Arrays.asList(
            "breq", /*Branch and goto*/
            "brneq",
            "brlt",
            "brgt",
            "brgeq",
            "brleq",
            "goto"
    ));
    private ArrayList<String> funcCall = new ArrayList<>(Arrays.asList(
            "call", /*Function calls*/
            "callr"
    ));


    public Eliminator(CFG _cfg) {
        cfg = _cfg;
    }

    public CFG eliminateDeadCode() {
        HashMap<Integer, Boolean> marks = markCriticalInstructions();
        sweep(cfg, marks);
        return cfg;
    }

    private HashMap<Integer, Boolean> markCriticalInstructions() {
        cfg.sortBlocks("asc");
        HashMap<Integer, Boolean> marks = new HashMap<>();
        Stack<Integer> workList = new Stack<>();
        boolean isCrit;
        for (BasicBlock blk : cfg.getBlocks()) {
            for (int i = 0; i < blk.getCodes().size(); i++) {
                int lineNum = blk.getId() + i;
                LinkedList<String> op = blk.getCodes().get(i);
                isCrit = isCritical(op);
                marks.put(lineNum, isCrit);
                if (isCrit) {
                    workList.push(lineNum);
                }
            }
        }

        while (!workList.empty()) {
            int currOpLineNum = workList.pop();
            if (cfg.getCodeByLineNum(currOpLineNum) == null)
                continue;
            for (int opLineNum : defsReachOps(currOpLineNum)) {
                if (!marks.get(opLineNum)) {
                    marks.replace(opLineNum, true);
                    workList.push(opLineNum);
                }
            }
        }

        marks.replace(0, true);
        marks.replace(1, true);
        marks.replace(2, true);

        return marks;
    }

    private void sweep(CFG cfg, HashMap<Integer, Boolean> marks) {
        Set<Integer> lineNums = marks.keySet();
        for (Integer lineNum : lineNums) {
            if (!marks.get(lineNum))
                cfg.removeCodeByLineNum(lineNum);
        }
    }

    private ArrayList<Integer> defsReachOps(int lineNum) {
        return this.cfg.getReachDef().getReachingDefs(lineNum);
    }

    private boolean hasValidForm(int lineNum) {
        LinkedList<String> op = cfg.getCodeByLineNum(lineNum);
        if (op == null)
            return false;
        ArrayList<String> assignmentInstr = new ArrayList<>(Arrays.asList(
                "assign", "add", "sub", "mult", "div", "and", "or", "callr", "array_load"
        ));
        String opName = op.get(0);
        return assignmentInstr.contains(opName);
    }

    private Boolean isCritical(LinkedList<String> op) {
        String opName = op.get(0);
        boolean isBranch = branchOps.contains(opName);
        boolean isFuncCall = funcCall.contains(opName);
        boolean isReturn = opName.equals("return");
        boolean isArrAssign = false;
        boolean isCritLabel = false;
        if (opName.equals("assign") && op.size() == 4)
            isArrAssign = true;
        if (opName.equals("array_store"))
            isArrAssign = true;
        if (cfg.isLabel(op)) {
            String label = op.get(0);
            label = label.substring(0, label.length() - 1);
            isCritLabel = cfg.belongToGotoBranch(label);
        }

        return isBranch || isFuncCall || isReturn || isArrAssign || isCritLabel;
    }
}