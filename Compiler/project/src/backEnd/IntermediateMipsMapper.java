package backEnd;

import java.util.ArrayList;
import java.util.LinkedList;

public class IntermediateMipsMapper {

    private IntermediateMipsMapper() {
    }

    public static ArrayList<Function> toMipsProgram(ArrayList<Function> funcList) throws IncorrectInstructionTypeException {
        int listCount = 0;
        ArrayList<Function> mipsFuncs = new ArrayList<>();
        for (Function func : funcList) {
            ArrayList<Instruction> instructions = new ArrayList<>();
            for (int i = 0; i < func.getCodes().size(); i++) {
                Instruction instruction = func.getCodes().get(i);
                if (instruction.isArrAssignment()) {
                    instructions.addAll(toMipsInstr(instruction));
                } else {
                    instructions.addAll(toMipsInstr(instruction));
                }
            }
            Function mipsFunc = new Function(instructions);
            System.out.println("---------------------Intermediate MIPS--------------------------");
            System.out.println(mipsFunc.toString());
            mipsFunc.buildMipsCfg();
            System.out.println("---------------------CFG------------------------");
            mipsFunc.getCfg().print();
            mipsFuncs.add(mipsFunc);
        }
        return mipsFuncs;
    }

    public static ArrayList<Instruction> toMipsInstr(Instruction instr, String listName) throws IncorrectInstructionTypeException {
        if (instr.getCode() == null || instr.getTokens() == null || instr.getType() == null) {
            throw new NullPointerException("The instruction has null values. (code or tokens or type)");
        }
        if (instr.getType() != InstructionType.TIGER) {
            throw new IncorrectInstructionTypeException("The instruction: " + instr.getCode() +
                    ", line: " + instr.getLineNum() + " is not TIGER." +
                    " Consider setting the type and format correctly.");
        }

        ArrayList<Instruction> mipsInstrs = new ArrayList<>();

        if (instr.isBranch()) {
            branchToMips(instr, mipsInstrs);
        } else if (instr.isTigerGoto()) {
            gotoMips(instr, mipsInstrs);

        } else if (instr.isArithmetic()) {
            arithmeticToMips(instr, mipsInstrs);

        } else if (instr.isArrStore()) {
//            arrStoreMips(instr, mipsInstrs);
            mipsInstrs.add(instr);

        } else if (instr.isArrLoad()) {
//            arrLoadMips(instr, mipsInstrs);
            mipsInstrs.add(instr);

        } else if (instr.isFuncHeader()) {
            funcHeaderToMips(instr, mipsInstrs);

        } else if (instr.isVarAssign()) {
            varAssignToMips(instr, mipsInstrs);

        } else if (instr.isLabel()) {
            labelToMips(instr, mipsInstrs);

        } else if (instr.isArrAssignment()) {
            arrAssignToMips(instr, listName, mipsInstrs);

        } else if (instr.isCall()) {
            /**
             *  TODO:
             *  - In the case, where it is Intrinsic Function, then use syscall (Refer to Mips Doc)
             *  - Handle Function Call Convention without return value
             */
            mipsInstrs.add(instr);

        } else if (instr.isCallR()) {
            /**
             *  TODO:
             *  - Handle Function Call Convention with return value
             */
            mipsInstrs.add(instr);

        } else if (instr.isReturn()) {
            returnToMips(instr, mipsInstrs);
        }

        if (mipsInstrs.size() > 0) {
            mipsInstrs.get(0).setMapInstr(instr.getCode());
        }

        return mipsInstrs;
    }

    private static void returnToMips(Instruction instr, ArrayList<Instruction> mipsInstrs) {
        mipsInstrs.add(instr);
    }

    public static ArrayList<Instruction> toMipsInstr(Instruction instr) throws IncorrectInstructionTypeException {
        return IntermediateMipsMapper.toMipsInstr(instr, "");
    }

    private static void arrAssignToMips(Instruction instr, String listName, ArrayList<Instruction> result) {
        // assign, A, 100, 10
        String arr = instr.getTokens().get(1);
        String size = instr.getTokens().get(2);
        String val = instr.getTokens().get(3);

        String code = "call, init_arr, " + arr + ", " + size + ", " + val;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("call");
        tokens.add("init_arr");
        tokens.add(arr);
        tokens.add(size);
        tokens.add(val);

        result.add(
                new Instruction(tokens, code, InstructionType.TIGER, instr.getLineNum(), MipsSection.TEXT)
        );
    }

    private static void labelToMips(Instruction instr, ArrayList<Instruction> result) {
        instr.setType(InstructionType.MIPS);
        result.add(instr);
    }

    private static void varAssignToMips(Instruction instr, ArrayList<Instruction> result) {
        String dest = instr.getDest();
        String src = instr.getSrc();
        String target = "$zero";

        if (isNumeric(instr.getSrc())) { // assign, a, 3 -> addi a, $0, 3
            src = "$zero";
            target = instr.getSrc();
            result.add(
                    MipsInstruction.addi(dest, src, target, instr.getLineNum())
            );
        } else {// assign, a, b -> add a, b, $0
            result.add(
                    MipsInstruction.add(dest, src, target, instr.getLineNum())
            );
        }
    }

    private static void funcHeaderToMips(Instruction instr, ArrayList<Instruction> result) {
        result.add(instr);
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }



    private static void arithmeticToMips(Instruction instr, ArrayList<Instruction> result) {
        String op = instr.getOp();
        String mipsOp;
        switch (op) {
            case "mult":
                mipsOp = "mul";
                break;
            default:
                mipsOp = op;
                break;
        }

        String src = instr.getSrc();
        String target = instr.getTarget();

        if (isNumeric(instr.getSrc())) {
            Instruction tempRegInstructor = storeImmToReg(instr.getSrc(), "temp1", instr);
            src = tempRegInstructor.getDest();
            result.add(tempRegInstructor);
        }

        if (isNumeric(instr.getTarget())) {
            Instruction tempRegInstructor = storeImmToReg(instr.getTarget(), "temp2", instr);
            target = tempRegInstructor.getDest();
            result.add(tempRegInstructor);
        }

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(mipsOp);
        tokens.add(instr.getDest());
        tokens.add(src);
        tokens.add(target);
        String code = mipsOp + " " + instr.getDest() + ", " + src + ", " + target;
        Instruction mips = new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT);

        result.add(mips);
    }

    private static Instruction storeImmToReg(String imm, String tmp, Instruction instr) {
        String mipsOp = "addi";
        LinkedList<String> tokens = new LinkedList<>();
        String zero = "$zero";
        tokens.add(mipsOp);
        tokens.add(tmp);
        tokens.add(zero);
        tokens.add(imm);
        String code = mipsOp + " " + tmp + ", " + zero + ", " + imm;
        return new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT);
    }

    private static void gotoMips(Instruction instr, ArrayList<Instruction> result) {
        String mipsOp = "j";
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(mipsOp);
        tokens.add(instr.getLabel());
        String code = mipsOp + " " + instr.getLabel();
        Instruction mips = new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT);
        result.add(mips);
    }

    private static void branchToMips(Instruction instr, ArrayList<Instruction> result) {
        String op = instr.getOp();
        String mipsOp;
        switch (op) {
            case "breq":
                mipsOp = "beq";
                break;
            case "brneq":
                mipsOp = "bne";
                break;
            case "brlt":
                mipsOp = "blt";
                break;
            case "brgt":
                mipsOp = "bgt";
                break;
            case "brgeq":
                mipsOp = "bge";
                break;
            case "brleq":
                mipsOp = "ble";
                break;
            default:
                mipsOp = "";
        }

        String src = instr.getSrc();
        String target = instr.getTarget();
        Instruction tempRegInstruction;
        String temp1 = "br_temp1";
        String temp2 = "br_temp2";

        if (isNumeric(instr.getSrc())) {
            tempRegInstruction = storeImmToReg(instr.getSrc(), temp1, instr);
            src = tempRegInstruction.getDest();
            result.add(tempRegInstruction);
        }

        if (isNumeric(instr.getTarget())) {
            tempRegInstruction = storeImmToReg(instr.getTarget(), temp2, instr);
            target = tempRegInstruction.getDest();
            result.add(tempRegInstruction);
        }

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(mipsOp);
        tokens.add(src);
        tokens.add(target);
        tokens.add(instr.getLabel());
        String code = mipsOp + " " + src + ", " + target + ", " + instr.getLabel();
        Instruction mips = new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT);
        result.add(mips);
    }
}
