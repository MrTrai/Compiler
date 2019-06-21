package backEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MipsInstruction {
    private static final ArrayList<String> regs = new ArrayList<>(Arrays.asList(
            "$s0",
            "$s1",
            "$s2",
            "$s3",
            "$s4",
            "$s5",
            "$s6",
            "$s7",
            "$s8",
            "$t0",
            "$t1",
            "$t2",
            "$t3",
            "$t4",
            "$t5",
            "$t6",
            "$t7",
            "$t8",
            "$t9",
            "$a0",
            "$a1",
            "$a2",
            "$a3",
            "$v0",
            "$v1",
            "$zero",
            "$sp",
            "$ra"
    ));

    public static Instruction sll(String dest, String target, int shamt, int lineNum) {
        return sll(dest, target, Integer.toString(shamt), lineNum);
    }

    public static Instruction sll(String dest, String target, String shamt, int lineNum) {
        String op = "sll";
        String codes = op + " " +  dest + ", " + target + ", " + shamt;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(dest);
        tokens.add(target);
        tokens.add(shamt);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction mul(String dest, String src, int target, int lineNum) {
        return mul(dest, src, Integer.toString(target), lineNum);
    }

    public static Instruction mul(String dest, String src, String target, int lineNum) {
        String op = "mul";
        String codes = op + " " +  dest + ", " + src + ", " + target;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(dest);
        tokens.add(src);
        tokens.add(target);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction li(String reg, int val, int lineNum) {
        return li(reg, Integer.toString(val), lineNum);
    }

    public static Instruction li(String reg, String val, int lineNum) {
        String op = "li";
        String codes = op + " " +  reg + ", " + val;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(reg);
        tokens.add(val);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction la(String argReg, String val, int lineNum) {
        String codes = "la " + argReg + ", " + val;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("la");
        tokens.add(argReg);
        tokens.add(val);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction sysCall(int lineNum) {
        String codes = "syscall";

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("syscall");

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction move(String dest, String src, int lineNum) {
        String op = "move";
        return move_template(dest, src, lineNum, op);
    }

    private static Instruction move_template(String dest, String src, int lineNum, String op) {
        String codes = op + " " + dest + ", " + src;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(dest);
        tokens.add(src);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction move_s(String dest, String src, int lineNum) {
        String op = "mov.s";
        return move_template(dest, src, lineNum, op);
    }

    public static Instruction lw(String dest, String src, int offset, int lineNum) {
        return lw(dest, src, Integer.toString(offset), lineNum);
    }

    public static Instruction lw(String dest, String src, String offset, int lineNum) {
        String op = "lw";
        String codes = op + " " + dest + ", " + offset + "(" + src + ")";
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(dest);
        tokens.add(offset);
        tokens.add(src);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction lw_label(String dest, String src, int lineNum) {
        String op = "lw";
        String codes = op + " " + dest + ", " + src;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(src);
        tokens.add(dest);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction sw_label(String dest, String src, int lineNum) {
        String op = "sw";
        String codes = op + " " + src + ", " + dest;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(src);
        tokens.add(dest);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction sw(String dest, String src, int offset, int lineNum) {
        return sw(dest, src, Integer.toString(offset), lineNum);
    }

    public static Instruction sw(String dest, String src, String offset, int lineNum) {
        String op = "sw";
        String codes = op + " " + src + ", " + offset + "(" + dest + ")";
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add(src);
        tokens.add(offset);
        tokens.add(dest);

        return new Instruction(tokens, codes, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction add(String dest, String src, String target, int lineNum) {
        String mipsOp = "add";

        String code = mipsOp + " " + dest + ", " + src + ", " + target;
        LinkedList<String> tokens = new LinkedList<>();

        tokens.add(mipsOp);
        tokens.add(dest);
        tokens.add(src);
        tokens.add(target);

        return new Instruction(tokens, code, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction addi(String dest, String src, int target, int lineNum) {
        return addi(dest, src, Integer.toString(target), lineNum);
    }

    public static Instruction addi(String dest, String src, String target, int lineNum) {
        String mipsOp = "addi";

        String code = mipsOp + " " + dest + ", " + src + ", " + target;
        LinkedList<String> tokens = new LinkedList<>();

        tokens.add(mipsOp);
        tokens.add(dest);
        tokens.add(src);
        tokens.add(target);

        return new Instruction(tokens, code, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction j(String label, int lineNum) {
        String j = "j";
        String code = j + " " + label;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(j);
        tokens.add(label);

        return new Instruction(tokens, code, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static Instruction label(String name, int lineNum) {
        String code = name + ":";
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(name);

        return new Instruction(tokens, code, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }


    public static Instruction moveStackPtr(int bytes) {
        String val = Integer.toString(bytes);
        return moveStackPtr(val);
    }

    public static Instruction moveStackPtr(String bytes) {
        String op = "addi";
        String str = op + " " + "$sp, $sp, " + bytes;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(op);
        tokens.add("$sp");
        tokens.add("$sp");
        tokens.add(bytes);
        Instruction instr = new Instruction(tokens, str, InstructionType.MIPS, -1, MipsSection.TEXT);
        return instr;
    }

    public static String getArrayDeclarationName(String intVal) {
        StringBuilder builder = new StringBuilder();
        for (char c : intVal.toCharArray()) {
            if (c != '[') {
                builder.append(c);
                break;
            }
        }
        return builder.toString();
    }

    public static Integer getArrayDeclarationSize(String intVal) {
        char[] arr = intVal.toCharArray();
        StringBuilder builder = new StringBuilder();
        int openBracketIndx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '[') {
                openBracketIndx = i;
            }
        }
        for (int i = openBracketIndx + 1; i < arr.length - 1; i++) {
            builder.append(arr[i]);
        }
        return Integer.valueOf(builder.toString());
    }

    public static boolean isArrayDeclaration(String intVal) {
        return intVal.contains("[") && intVal.contains("]");
    }

    public static Instruction jr(String reg, int lineNum) {
        String mipsOp = "jr";

        String code = mipsOp + " " + reg;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(mipsOp);
        tokens.add(reg);

        return new Instruction(tokens, code, InstructionType.MIPS, lineNum, MipsSection.TEXT);
    }

    public static boolean isChar(String str) {
        if (str.length() == 0) {
            return false;
        }
        char[] arr = str.toCharArray();
        return arr[0] == '\'' && arr[arr.length - 1] == '\'';
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static boolean isRegister(String str) {
        return regs.contains(str);
    }
}
