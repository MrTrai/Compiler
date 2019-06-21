package backEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Instruction {

    private static final ArrayList<String> TIGER_ARITHMETIC = new ArrayList<>(Arrays.asList(
            "add", "sub", "mult", "div", "and", "or"
    ));
    private static final ArrayList<String> TIGER_BRANCH = new ArrayList<>(Arrays.asList(
            "breq", "brneq", "brlt", "brgt", "brgeq", "brleq"
    ));
    private static final ArrayList<Integer> TIGER_FUNC_HEADER_LINES = new ArrayList<>(Arrays.asList(
            0, 1, 2
    ));
    private static final ArrayList<String> TIGER_INTRINSIC_FUNC = new ArrayList<>(Arrays.asList(
            "geti", "getf", "getc", "puti", "putf", "putc"
    ));
    private static final ArrayList<String> MIPS_ARITHMETIC = new ArrayList<>(Arrays.asList(
            "add", "addi", "sub", "mul", "div", "and", "or", "sll", "li"
    ));
    private static final ArrayList<String> MIPS_BRANCH = new ArrayList<>(Arrays.asList(
            "bne", "beq", "bge", "ble", "blt", "bgt"
    ));

    private int lineNum;

    private String code;
    private LinkedList<String> tokens;
    private InstructionType type;

    private String op;

    private String src;

    private String target;

    private String dest;
    private String label;
    private String offset;
    private String arrSize;

    private String funcRetType;
    private String funcRetValue;
    private String funcName;

    private ArrayList<String> funcArgs;
    private ArrayList<String> funcArgsType;

    private ArrayList<String> intList = new ArrayList<>();
    private ArrayList<String> floatList = new ArrayList<>();

    private MipsSection section;
    private String comment = "";

    private String mapInstr = "";

    public Instruction() {
        this.tokens = new LinkedList<>();
        this.tokens.add("");
        this.code = "";
    }

    public Instruction(LinkedList<String> _tokens, String _code, InstructionType _type) {
        this.tokens = _tokens;
        this.code = _code;
        this.type = _type;
        this.init();
    }

    public Instruction(LinkedList<String> _tokens, String _code, InstructionType _type, int _lineNum) {
        this(_tokens, _code, _type);
        this.lineNum = _lineNum;
    }

    public Instruction(LinkedList<String> _tokens, String _code, InstructionType _type, int _lineNum, MipsSection _section) {
        this(_tokens, _code, _type, _lineNum);
        this.section = _section;
    }

    private void init() {
        if (isLabel()) {
            this.label = this.tokens.get(0);
            return;
        }
        this.op = this.tokens.getFirst();
        if (type == InstructionType.TIGER) {
            try {
                this.initTiger();
            } catch (Exception e) {
                //System.out.println(this.tokens.toString());
            }
        } else if (type == InstructionType.MIPS) {
            try {
                this.initMips();
            } catch (Exception e) {
                //System.out.println(this.tokens.toString());
            }
        }
    }

    private void initMips() {
        if (isBranch()) {
            this.src = this.tokens.get(1);
            this.target = this.tokens.get(2);
            this.label = this.tokens.get(3);
        } else if (isMipsJump()) {
            this.label = tokens.get(1);
            this.target = tokens.get(1);
        } else if (isArithmetic()) {
            this.dest = this.tokens.get(1);
            this.src = this.tokens.get(2);
            this.target = this.tokens.get(3);
        } else if (isStore()) {
            this.src = this.tokens.get(1);
            this.offset = this.tokens.get(2);
            this.dest = this.tokens.get(3);
        } else if (isLoad()) {
            this.dest = this.tokens.get(1);
            this.offset = this.tokens.get(2);
            this.src = this.tokens.get(3);
        }
    }

    private void initTiger() {
        if (isTigerGoto()) {
            this.label = this.tokens.get(1);
        } else if (isReturn()) {
            if (this.tokens.size() > 1) {
                this.target = this.tokens.get(1);
                this.src = this.tokens.get(1);
            }
        } else if (isBranch()) {
            this.label = this.tokens.get(1);
            this.src = this.tokens.get(2);
            this.target = this.tokens.get(3);
        } else if (isVarAssign()) {
            this.dest = this.tokens.get(1);
            this.src = this.tokens.get(2);
        } else if (isArrAssignment()) {
            this.dest = this.tokens.get(1);
            this.arrSize = this.tokens.get(2);
            this.src = this.tokens.get(3);
        } else if (isArrStore()) {
            this.src = this.tokens.get(1);
            this.dest = this.tokens.get(2);
            this.offset = this.tokens.get(3);
        } else if (isArrLoad()) {
            this.dest = this.tokens.get(1);
            this.src = this.tokens.get(2);
            this.offset = this.tokens.get(3);
        } else if (isArithmetic()) {
            this.dest = this.tokens.get(1);
            this.src = this.tokens.get(2);
            this.target = this.tokens.get(3);
        } else if (isCall()) {
            this.src = this.tokens.get(1);
            this.funcName = this.tokens.get(1);
            if (this.tokens.size() > 2) {
                int start = 2;
                extractFuncArgs(start);
            }
        } else if (isCallR()) {
            this.dest = this.tokens.get(1);
            this.src = this.tokens.get(2);
            this.funcRetValue = this.tokens.get(1);
            this.funcName = this.tokens.get(2);
            if (this.tokens.size() > 3) {
                int start = 3;
                extractFuncArgs(start);
            }
        } else if (isIntList()) {
            this.intList = new ArrayList<>();
            for (int i = 1; i < this.tokens.size(); i++) {
                this.intList.add(this.tokens.get(i));
            }
        } else if (isFloatList()) {
            this.floatList = new ArrayList<>();
            for (int i = 1; i < this.tokens.size(); i++) {
                this.floatList.add(this.tokens.get(i));
            }
        } else if (isFuncDeclare()) {
            int start = this.code.indexOf("(");
            int end = this.code.indexOf(")");
            String params = this.code.substring(start + 1, end);
            if (params.length() > 0) {
                params = params.replaceAll("\\s+", "");
                String[] nameTokens = params.split(",");
                for (String tk : nameTokens) {
                    String[] var = tk.split(" ");
                }
            }
            String[] nameTokens = this.tokens.get(0).split(" ");
            if (nameTokens.length >= 2) {
                this.funcRetType = nameTokens[0];
                this.funcName = nameTokens[1];
                String[] varTokens;
                this.funcArgsType = new ArrayList<>();
                this.funcArgs = new ArrayList<>();
                for (int i = 1; i < this.tokens.size(); i++) {
                    varTokens = this.tokens.get(i).split(" ");
                    this.funcArgsType.add(varTokens[0]);
                    this.funcArgs.add(varTokens[1]);
                }
            }
        }
    }

    public ArrayList<String> getParameterNames() {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 1; i < tokens.size(); i++) {
            String token = tokens.get(i);
            String[] parts = token.split(" ");
            returnList.add(parts[parts.length - 1]);
        }
        return returnList;
    }

    public ArrayList<String> getDeclaredNames() {
        ArrayList<String> returnList = new ArrayList<>();
        if (tokens.size() > 1) {
            for (int i = 1; i < tokens.size(); i++) {
                String token = tokens.get(i);
                if (token.contains("[")) {
                    String[] parts = token.split("\\[");
                    returnList.add(parts[0]);
                } else {
                    returnList.add(token);
                }
            }
        }
        return returnList;
    }

    public boolean isFuncDeclare() {
        return this.lineNum == 0;
    }


    public boolean isFloatList() {
        return this.tokens.getFirst().equals("float-list");
    }

    public boolean isIntList() {
        return this.tokens.getFirst().equals("int-list");
    }

    private void extractFuncArgs(int start) {
        this.funcArgs = new ArrayList<>();
        for (int i = start; i < this.tokens.size(); i++) {
            this.funcArgs.add(this.tokens.get(i));
        }
    }

    public boolean isFuncHeader() {
        return TIGER_FUNC_HEADER_LINES.contains(this.lineNum);
    }


    public boolean isCallR() { // TODO
        return this.tokens.getFirst().equals("callr");
    }

    public boolean isCall() { // TODO
        return this.tokens.getFirst().equals("call");
    }

    public boolean isLoad() { // TODO
        return this.tokens.getFirst().equals("lw");
    }

    public boolean isStore() { // TODO
        return this.tokens.getFirst().equals("sw");
    }


    public boolean isVarAssign() { // TODO
        return this.tokens.getFirst().equals("assign") && this.tokens.size() == 3;
    }

    public boolean isArrAssignment() { // TODO
        return this.tokens.getFirst().equals("assign") && this.tokens.size() == 4;
    }


    public boolean isArithmetic() { // TODO
        boolean valid = false;
        if (this.type == InstructionType.TIGER) {
            valid = TIGER_ARITHMETIC.contains(tokens.getFirst());
        } else if (this.type == InstructionType.MIPS) {
            valid = MIPS_ARITHMETIC.contains(tokens.getFirst());
        }
        return valid;
    }

    public boolean isArrLoad() { // TODO
        return this.tokens.getFirst().equals("array_load");
    }

    public boolean isArrStore() { // TODO
        return this.tokens.getFirst().equals("array_store");
    }

    public boolean isTigerGoto() {
        return tokens.getFirst().equals("goto");
    }

    public boolean isMipsJump() {
        return tokens.getFirst().equals("j");
    }

    public boolean isLabel() {
        return this.tokens.getFirst().contains(":");
    }

    public boolean isMipsSW() {
        return this.tokens.getFirst().equals("sw");
    }

    public boolean isMipsLW() {
        return this.tokens.getFirst().equals("lw");
    }

    public boolean isBranch() {
        String opName = tokens.getFirst();
        boolean valid = false;
        if (this.type == InstructionType.TIGER) {
            valid = TIGER_BRANCH.contains(opName);
        }
        if (this.type == InstructionType.MIPS) {
            valid = MIPS_BRANCH.contains(opName);
        }
        return valid;
    }

    public boolean isReturn() {
        return this.tokens.getFirst().equals("return");
    }

    public int getLineNum() {
        return lineNum;
    }

    public LinkedList<String> getTokens() {
        return tokens;
    }

    public String getCode() {
        return code;
    }

    public String getOp() {
        return op;
    }

    public String getSrc() {
        return src;
    }

    public String getTarget() {
        return target;
    }

    public String getDest() {
        return dest;
    }

    public String toString() {
//        return this.code + "\t\t\t\t\t\t Defs: " + this.getDefs().toString() + "\t Uses: " + this.getUses().toString();
        String val = this.code;
        if (comment.length() > 0) {
            val += " # " + this.comment;
        }
        val += " # " + this.mapInstr;
        return val;
    }

    public InstructionType getType() {
        return this.type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public String getOffset() {
        return offset;
    }

    public String getArrSize() {
        return arrSize;
    }

    public String getFuncRetValue() {
        return funcRetValue;
    }

    public String getFuncName() {
        return funcName;
    }

    public ArrayList<String> getFuncArgs() {
        if (funcArgs == null) {
            return new ArrayList<>();
        }
        return funcArgs;
    }

    public String getFuncRetType() {
        return funcRetType;
    }

    public ArrayList<String> getFuncArgsType() {
        return funcArgsType;
    }

    public ArrayList<String> getIntList() {
        return intList;
    }

    public ArrayList<String> getFloatList() {
        return floatList;
    }

    public String getLabel() {
        return this.label;
    }

    public MipsSection getSection() {
        return section;
    }

    public void setSection(MipsSection section) {
        this.section = section;
    }

    public ArrayList<String> getDefs() {
        ArrayList<String> defs = new ArrayList<>();

        if (this.isArithmetic()) {
            defs.add(this.tokens.get(1));
        } else if (this.isCall()) {

        } else if (this.isCallR()) {
            defs.add(this.tokens.get(1));
        } else if (this.isMipsLW()) {
            defs.add(this.tokens.get(1));
        } else if (this.isMipsSW()) {
        } else if (this.isBranch()) {
        } else if (this.isMipsJump()) {

        } else if (this.isArrLoad()) { // array_load, t, A, 3
            defs.add(this.tokens.get(1));
        } else if (this.isArrStore()) { // // array_store, t, A, 3
            defs.add(this.tokens.get(2));
            defs.add(this.tokens.get(3));
        }

        return defs;
    }

    public boolean isTigerInstrinsic() {
        boolean valid = false;
        if (this.isCall()) {
            valid = TIGER_INTRINSIC_FUNC.contains(this.tokens.get(1));
        } else if (this.isCallR()) {
            valid = TIGER_INTRINSIC_FUNC.contains(this.tokens.get(2));
        }
        return valid;
    }

    public ArrayList<String> getUses() {
        ArrayList<String> uses = new ArrayList<>();

        if (this.isArithmetic()) {
            try {
                uses.add(this.tokens.get(2));
                uses.add(this.tokens.get(3));
            } catch (Exception ignored) {
            }
        } else if (this.isCall()) {
            if (this.tokens.size() > 2) {
                for (int i = 2; i < this.tokens.size(); i++) {
                    uses.add(this.tokens.get(i));
                }
            }
        } else if (this.isCallR()) {
            if (this.tokens.size() > 3) {
                for (int i = 3; i < this.tokens.size(); i++) {
                    uses.add(this.tokens.get(i));
                }
            }
        } else if (this.isMipsLW()) {
            uses.add(this.tokens.getLast());
        } else if (this.isMipsSW()) {
            try {
                uses.add(this.tokens.get(1));
                uses.add(this.tokens.getLast());
            } catch (Exception ignored) {
            }
        } else if (this.isBranch()) {
            try {
                uses.add(this.tokens.get(1));
                uses.add(this.tokens.get(2));
            } catch (Exception ignored) {
            }
        } else if (this.isMipsJump()) {

        } else if (this.isArrLoad()) { // array_load, t, A, 3
            uses.add(this.tokens.get(2));
            uses.add(this.tokens.get(3));
        } else if (this.isArrStore()) { // // array_store, t, A, 3
            uses.add(this.tokens.get(1));
        }
        return uses;
    }

    public void buildCodeFromTokens() {
        /*if (isArrStore() || isArrLoad()) {
            code = tokens.get(0) + " " + tokens.get(1) + ", " + tokens.get(2) + "(" + tokens.get(3) + ")";
        } else {*/
            for (int i = 0; i < tokens.size(); i++) {
                String myToken = tokens.get(i);
                if (i == 0) {
                    if (type == InstructionType.TIGER) {
                        if (lineNum == 1 || lineNum == 2) {
                            code += myToken + ": ";
                        } else {
                            code += myToken + ", ";
                        }
                    } else {
                        code += myToken + " ";
                    }
                } else if (i == tokens.size() - 1) {
                    code += myToken;
                } else {
                    code += myToken + ", ";
                }
            }
        //}
    }

    public boolean isArrType(String type) {
        return type.contains("[") && type.contains("]");

    }

    public static ArrayList<String> getCallArgs(Instruction instr) {
        ArrayList<String> res = new ArrayList<>();
        for (int i = 2; i < instr.getTokens().size(); i++) {
            res.add(instr.getTokens().get(i));
        }
        return res;
    }

    public static ArrayList<String> getCallrArgs(Instruction instr) {
        ArrayList<String> res = new ArrayList<>();
        for (int i = 3; i < instr.getTokens().size(); i++) {
            res.add(instr.getTokens().get(i));
        }
        return res;
    }

    public void setLineNum(int newLineNum) {
        lineNum = newLineNum;
    }

    public Instruction comment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getMapInstr() {
        return mapInstr;
    }

    public void setMapInstr(String mapInstr) {
        this.mapInstr = mapInstr;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void buildCodeString() {
        if (isMipsSW()) { // sw val, 0(stack)
            this.code = "sw " + src + ", " + offset + "(" + dest + ")";
        } else if (isMipsLW()) { // lw val, 0(stack)
            this.code = "lw " + dest + ", " + offset + "(" + src + ")";
        } else if (isFuncHeader()) {
        } else if (isReturn()) {
            this.code = "return";
            if (this.tokens.size() > 1) {
                this.code += ", " + this.src;
            }
        } else if (isCall()) { // call, puti, i
            this.code = this.tokens.get(0);
            for (int i = 1; i < this.tokens.size(); i++) {
                this.code += ", " + this.tokens.get(i);
            }
        } else if (isCallR()) { // callr, r, func, a, b, c
            this.code = this.tokens.get(0);
            for (int i = 1; i < this.tokens.size(); i++) {
                this.code += ", " + this.tokens.get(i);
            }
        } else if (isArrAssignment()) { // assign, A, 100, 10
            this.code = this.tokens.get(0);
            for (int i = 1; i < this.tokens.size(); i++) {
                this.code += ", " + this.tokens.get(i);
            }
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabels() {
        if (tokens.size() == 1) {
            return tokens.get(0).split(":")[0];
        } else if (tokens.get(0).length() == 3 && tokens.get(0).substring(0, 1).equals("b")) {
            return tokens.get(3);
        } else if (tokens.get(0).equals("j")) {
            return tokens.get(1);
        }
        return null;
    }

}
