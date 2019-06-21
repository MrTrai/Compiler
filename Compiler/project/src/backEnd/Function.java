package backEnd;

import graph.MipsCFG;
import graph.TigerCFG;
import graph.CFG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Function {
    private ArrayList<Instruction> codes;
    private ArrayList<String> functionArrayNames;
    private CFG cfg;
    private int startLine = 0;
    private Map<String, String> myRegisterAllocation;
    private ArrayList<String> usedSavedRegs = new ArrayList<>(Arrays.asList(
            "$s0",
            "$s1",
            "$s2",
            "$s3"
    ));
    private ArrayList<String> usedTempRegs = new ArrayList<>(Arrays.asList(
            "$t0",
            "$t1",
            "$t2",
            "$t3",
            "$t4",
            "$t5",
            "$t6",
            "$t7",
            "$t8",
            "$t9"
    ));
    private String funcName = "";
    private ArrayList<String> intList = new ArrayList<>();
    private ArrayList<String> floatList = new ArrayList<>();
    private ArrayList<String> spilledRegs = new ArrayList<>();

    public Function(ArrayList<Instruction> _codes) {
        this.codes = _codes;
        functionArrayNames = new ArrayList<>();
        this.intList =_codes.get(1).getIntList();
        this.floatList =_codes.get(2).getFloatList();
        setFuncName(_codes);
        setFunctionArrayNames();
    }

    public Function(ArrayList<Instruction> _codes, int start_line_num) {
        this.codes = _codes;
        this.startLine = start_line_num;
        functionArrayNames = new ArrayList<>();
        this.intList =_codes.get(1).getIntList();
        this.floatList =_codes.get(2).getFloatList();
        setFuncName(_codes);
        setFunctionArrayNames();
    }

    private void setFuncName(ArrayList<Instruction> _codes) {
        for (Instruction code : _codes) {
            if (code.isFuncDeclare()) {
                this.funcName = code.getFuncName();
            }
        }
    }

    public ArrayList<Instruction> getCodes() {
        return this.codes;
    }

    public void setCodes(ArrayList<Instruction> _codes) {
        this.codes = _codes;
    }

    public CFG getCfg() {
        return this.cfg;
    }

    public void buildTigerCfg() {
        ArrayList<LinkedList<String>> _codes = new ArrayList<>();
        for (int i = 0; i < this.codes.size(); i++) {
            _codes.add(this.codes.get(i).getTokens());
        }
        this.cfg = new TigerCFG(_codes, this.startLine);
    }

    public void buildMipsCfg() {
        ArrayList<LinkedList<String>> _codes = new ArrayList<>();
        for (int i = 0; i < this.codes.size(); i++) {
            _codes.add(this.codes.get(i).getTokens());
        }
        this.cfg = new MipsCFG(_codes, this.startLine);
    }

    public String toString() {
        String out = "";
        for (Instruction instr : codes) {
            out += "Line: " + instr.getLineNum() + "\t\t" + instr.toString() + "\n";
        }
        return out;
    }

    private void setFunctionArrayNames() {
        LinkedList<String> functionHeader = codes.get(0).getTokens();
        LinkedList<String> intList = codes.get(1).getTokens();
        LinkedList<String> floatList = codes.get(2).getTokens();
        for (int i = 1; i < functionHeader.size(); i++) {
            String headerToken = functionHeader.get(i);
            if (headerToken.contains("[")) {
                String[] subTokens = headerToken.split(" ");
                functionArrayNames.add(subTokens[subTokens.length - 1]);
            }
        }
        for (int j = 1; j < intList.size(); j++) {
            String intListToken = intList.get(j);
            if (intListToken.contains("[")) {
                String arrayName = intListToken.split("\\[")[0];
                arrayName = arrayName.replaceAll(" ", "");
                functionArrayNames.add(arrayName);
            }
        }
        for (int j = 1; j < floatList.size(); j++) {
            String floatListToken = floatList.get(j);
            if (floatListToken.contains("[")) {
                String arrayName = floatListToken.split("\\[")[0];
                arrayName = arrayName.replaceAll(" ", "");
                functionArrayNames.add(arrayName);
            }
        }
    }

    public ArrayList<String> getFunctionArrayNames() {
        return new ArrayList<>(functionArrayNames);
    }

    public ArrayList<String> getUsedSavedRegs() {
        return usedSavedRegs;
    }

    public void setUsedSavedRegs(ArrayList<String> usedSavedRegs) {
        this.usedSavedRegs = usedSavedRegs;
    }

    public ArrayList<String> getUsedTempRegs() {
        return usedTempRegs;
    }

    public void setUsedTempRegs(ArrayList<String> usedTempRegs) {
        this.usedTempRegs = usedTempRegs;
    }

    public String getFuncName() {
        return funcName;
    }

    public ArrayList<String> getIntList() {
        return intList;
    }

    public ArrayList<String> getFloatList() {
        return floatList;
    }

    public void setMyRegisterAllocation(Map<String, String> newAllocation) {
        myRegisterAllocation = new HashMap<>(newAllocation);
    }

    public Map<String, String> getMyRegisterAllocation() {
        return myRegisterAllocation;
    }

    public ArrayList<String> getSpilledRegs() {
        return spilledRegs;
    }

    public void setSpilledRegs(ArrayList<String> spilledRegs) {
        this.spilledRegs = spilledRegs;
    }

    public ArrayList<String> getFuncArguments() {
        return this.getCodes().get(0).getFuncArgs();
    }

    public String mapRegToArgReg(String reg) {
        ArrayList<String> args = getFuncArguments();
        String argReg = "";
        for (int i = 0; i < args.size(); i++) {
            if (reg == args.get(i)) {
                argReg = "$a" + i;
            }

        }
        return argReg;
    }

    public ArrayList<String> getLabelNames() {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 3; i < codes.size(); i++) {
            Instruction myInstr = codes.get(i);
            if (myInstr.getTokens().size() == 1) {
                returnList.add(myInstr.getTokens().get(0).split(":")[0]);
            }
        }
        return returnList;
    }

    public String getFunctionName() {
        return codes.get(0).getTokens().get(0).split(" ")[1];
    }
}
