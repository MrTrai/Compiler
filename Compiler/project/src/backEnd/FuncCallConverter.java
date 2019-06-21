package backEnd;

import graph.BasicBlock;
import graph.CFG;

import java.util.*;

public class FuncCallConverter {
    public FuncCallConverter() {
    }

    private static final int WORD = 4;

    public static ArrayList<Function> toMips(ArrayList<Function> funcs, int allocatorType) {
        ArrayList<Function> mipsFuncs = new ArrayList<>();

        for (Function func : funcs) {
            mipsFuncs.add(toMipsFunc(func, allocatorType));
        }

        return mipsFuncs;
    }

    public static Function toMipsFunc(Function func, int allocatorType) {
        ArrayList<Instruction> result = new ArrayList<>();

        String name = func.getCodes().get(0).getFuncName();
        result.add(
                MipsInstruction.label(name, -1)
                        .comment("------------------------------ FUNCTION " + name +
                                "(" + func.getCodes().get(0).getFuncArgs().toString() +
                                ")-------------------------")
        );

        int arrayMem = totalArrayMem(func);
        int savedRegsMem = totalSavedRegsMem(func);
        int tempRegsMem = totalTempRegsMem(func);
        HashMap<String, Integer> stackOffsetMap = new HashMap<>();

        result.addAll(
                arrayAllocate(func, stackOffsetMap)
        );
        result.addAll(
                spilledRegsAllocate(func, stackOffsetMap, allocatorType)
        );
        result.addAll(
                saveRA(stackOffsetMap)
        );
        result.addAll(
                storeSavedRegs(func, stackOffsetMap)
        );
        result.addAll(
                storeTempRegs(func, stackOffsetMap)
        );
        result.addAll(
                funcBody(func, stackOffsetMap, allocatorType)
        );
        result.add(
                MipsInstruction.label(func.getFuncName() + "_stack_tear_down", -1)
        );
        result.addAll(
                loadTempRegs(func, tempRegsMem, stackOffsetMap)
        );
        result.addAll(
                loadSavedRegs(func, savedRegsMem, stackOffsetMap)
        );
        result.addAll(
                loadRA(stackOffsetMap)
        );
        result.addAll(
                spilledRegsDeallocate(func, stackOffsetMap)
        );
        result.addAll(
                deallocateArray(func, arrayMem, stackOffsetMap)
        );
        result.add(
                MipsInstruction.jr("$ra", -1)
        );

        Function mipsFunction = new Function(result);

        return mipsFunction;
    }

    private static Collection<? extends Instruction> spilledRegsDeallocate(Function func, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> spilledRegs = func.getSpilledRegs();
        result.add(
                MipsInstruction.moveStackPtr(spilledRegs.size() * WORD).comment("DEALLOCATE SPILLED REGISTERS STACK")
        );
        tearDownStackOffsetMap(stackOffsetMap, -spilledRegs.size() * WORD);
        return result;
    }

    private static Collection<? extends Instruction> spilledRegsAllocate(Function func, HashMap<String, Integer> stackOffsetMap, int allocatorType) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> spilledRegs = func.getSpilledRegs();

        if (allocatorType == 2) {
            spilledRegs = RegisterAllocator.getVirtualRegs(func.getCodes());
        }

        String reg;
        result.add(
                MipsInstruction.moveStackPtr((spilledRegs.size()) * -WORD).comment("ALLOCATE SPILLED REGISTERS STACK")
        );
        for (int i = (spilledRegs.size() - 1); i >= 0; i--) {
            reg = spilledRegs.get(i);
            buildUpStackOffsetMap(stackOffsetMap, reg, WORD);
        }

        return result;
    }

    private static void tearDownStackOffsetMap(HashMap<String, Integer> map, int offset) {
        int neg_offset = Math.abs(offset) * -1;
        Set<String> keys = map.keySet();
        ArrayList<String> removeKeys = new ArrayList<>();
        for (String k : keys) {
            int reg_offset = map.get(k);
            int total_offset = reg_offset + neg_offset;
            if (total_offset < 0) {
                removeKeys.add(k);
            } else {
                map.replace(k, total_offset);
            }
        }

        for (String k : removeKeys) {
            map.remove(k);
        }
    }


    private static Map buildUpStackOffsetMap(Map<String, Integer> stackOffsetMap, String reg, int offset) {
        Set<String> keys = stackOffsetMap.keySet();
        for (String k : keys) {
            int reg_offset = stackOffsetMap.get(k);
            stackOffsetMap.replace(k, reg_offset + offset);
        }

        if (reg.length() > 0) {
            if (stackOffsetMap.get(reg) != null) {
                stackOffsetMap.replace(reg, 0);
            } else {
                stackOffsetMap.put(reg, 0);
            }
        }

        return stackOffsetMap;
    }

    private static Collection<? extends Instruction> loadRA(HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        result.addAll(
                Arrays.asList(
                        MipsInstruction.lw("$ra", "$sp", 0, -1).comment("LOAD RETURN ADDRESS"),
                        MipsInstruction.addi("$sp", "$sp", WORD, -1).comment("DEALLOCATE ONE WORD FOR $RA")
                )
        );
        tearDownStackOffsetMap(stackOffsetMap, -WORD);
        return result;
    }

    public static Collection<? extends Instruction> saveRA(HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        result.addAll(
                Arrays.asList(
                        MipsInstruction.addi("$sp", "$sp", -WORD, -1).comment("ALLOCATE ONE WORD FOR $RA"),
                        MipsInstruction.sw("$sp", "$ra", 0, -1).comment("SAVE RETURN ADDRESS")
                )
        );
        buildUpStackOffsetMap(stackOffsetMap, "", WORD);
        return result;
    }

    public static Collection<? extends Instruction> deallocateArray(Function func, int arrayMem, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        result.add(
                MipsInstruction.moveStackPtr(arrayMem).comment("DEALLOCATE ARRAY STACK")
        );
        tearDownStackOffsetMap(stackOffsetMap, -arrayMem);
        return result;
    }

    public static Collection<? extends Instruction> storeSavedRegs(Function func, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> savedRegs = func.getUsedSavedRegs();
        String reg;
        result.add(
                MipsInstruction.moveStackPtr(savedRegs.size() * -WORD).comment("ALLOCATE SAVED REGISTERS STACK")
        );
        buildUpStackOffsetMap(stackOffsetMap, "", savedRegs.size() * WORD);
        for (int i = 0; i < savedRegs.size(); i++) {
            reg = savedRegs.get(i);
            result.add(
                    MipsInstruction.sw("$sp", reg, i * WORD, -1)
            );
        }
        for (int i = (savedRegs.size() - 1); i >= 0; i--) {
            reg = savedRegs.get(i);
        }
        return result;
    }

    public static Collection<? extends Instruction> loadSavedRegs(Function func, int savedRegsMem, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> savedRegs = func.getUsedSavedRegs();
        String reg;
        for (int i = 0; i < savedRegs.size(); i++) {
            reg = savedRegs.get(i);
            result.add(
                    MipsInstruction.lw(reg, "$sp", i * WORD, -1)
            );
        }
        result.add(
                MipsInstruction.moveStackPtr(savedRegs.size() * WORD).comment("DEALLOCATE SAVED REGISTERS STACK")
        );
        tearDownStackOffsetMap(stackOffsetMap, -savedRegs.size() * WORD);
        return result;
    }

    public static Collection<? extends Instruction> loadTempRegs(Function func, int tempRegsMem, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> tempRegs = func.getUsedTempRegs();
        String reg;
        for (int i = 0; i < tempRegs.size(); i++) {
            reg = tempRegs.get(i);
            result.add(
                    MipsInstruction.lw(reg, "$sp", i * WORD, -1)
            );
        }
        result.add(
                MipsInstruction.moveStackPtr(tempRegs.size() * WORD).comment("DEALLOCATE TEMP REGISTERS STACK")
        );
        tearDownStackOffsetMap(stackOffsetMap, -tempRegs.size() * WORD);
        return result;
    }

    public static Collection<? extends Instruction> storeTempRegs(Function func, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> tempRegs = func.getUsedTempRegs();
        String reg;
        result.add(
                MipsInstruction.moveStackPtr(tempRegs.size() * -WORD).comment("ALLOCATE TEMP REGISTERS STACK")
        );
        buildUpStackOffsetMap(stackOffsetMap, "", tempRegs.size() * WORD);
        for (int i = 0; i < tempRegs.size(); i++) {
            reg = tempRegs.get(i);
            result.add(
                    MipsInstruction.sw("$sp", reg, i * WORD, -1)
            );
        }
        for (int i = (tempRegs.size() - 1); i >= 0; i--) {
            reg = tempRegs.get(i);
        }
        return result;
    }


    public static Collection<? extends Instruction> arrayAllocate(Function func, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> sizes = new ArrayList<>();

        for (String intVal : func.getIntList()) {
            if (MipsInstruction.isArrayDeclaration(intVal)) {
                String name = MipsInstruction.getArrayDeclarationName(intVal);
                int size = MipsInstruction.getArrayDeclarationSize(intVal);
                types.add("int");
                names.add(name);
                sizes.add(size);
            }
        }

        for (String floatVal : func.getFloatList()) {
            if (MipsInstruction.isArrayDeclaration(floatVal)) {
                String name = MipsInstruction.getArrayDeclarationName(floatVal);
                int size = MipsInstruction.getArrayDeclarationSize(floatVal);
                types.add("float");
                names.add(name);
                sizes.add(size);
            }
        }

        for (int i = 0; i < names.size(); i++) {
            String arrayName = names.get(i);
            String arrayType = types.get(i);
            int arraySize = sizes.get(i);
            int totalByte = arraySize * typeToByte(arrayType);

            result.addAll(
                    Arrays.asList(
                            MipsInstruction.addi("$sp", "$sp", -totalByte, -1).comment("ALLOCATE ARRAY " + arrayName)
//                            MipsInstruction.sw_label(arrayName, "$sp", -1).comment("SAVE $SP ADDRESS TO ARRAY LABEL " + arrayName)
                    )
            );
            buildUpStackOffsetMap(stackOffsetMap, arrayName, totalByte);
        }

        return result;
    }

    public static int getPadding(int totalByte) {
        return totalByte % 8;
    }

    public static boolean divisibleBy8(int totalByte) {
        return totalByte % 8 == 0;
    }

    public static int totalTempRegsMem(Function func) {
        return func.getUsedTempRegs().size() * 4;
    }

    public static int totalSavedRegsMem(Function func) {
        return func.getUsedSavedRegs().size() * 4;
    }

    public static int totalArrayMem(Function func) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> sizes = new ArrayList<>();
        int totalArrayMem = 0;

        for (String intVal : func.getIntList()) {
            if (MipsInstruction.isArrayDeclaration(intVal)) {
                names.add(
                        MipsInstruction.getArrayDeclarationName(intVal)
                );
                types.add("int");
                sizes.add(
                        MipsInstruction.getArrayDeclarationSize(intVal)
                );
            }
        }

        for (String floatVal : func.getFloatList()) {
            if (MipsInstruction.isArrayDeclaration(floatVal)) {
                names.add(
                        MipsInstruction.getArrayDeclarationName(floatVal)
                );
                types.add("float");
                sizes.add(
                        MipsInstruction.getArrayDeclarationSize(floatVal)
                );
            }
        }

        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            int size = sizes.get(i);
            totalArrayMem += (size * typeToByte(type));
        }
        return totalArrayMem;
    }

    public static int typeToByte(String type) {
        return 4;
    }

    public static ArrayList<Instruction> funcBody(Function func, HashMap<String, Integer> stackOffsetMap, int allocatorType) {
        ArrayList<Instruction> mipsCodes = new ArrayList<>();
        if (allocatorType == 2) {
            handleIntraBlockAllocator(func, stackOffsetMap);
        }
        ArrayList<Instruction> codes = handleSpilledRegister(func, stackOffsetMap);
        for (Instruction instr : codes) {
            if (instr.isFuncDeclare()) {
            } else if (instr.isIntList()) {
            } else if (instr.isFloatList()) {
            } else if (instr.isCall()) {
                if (instr.isTigerInstrinsic()) {
                    mipsCodes.addAll(callToSysCall(func, instr, stackOffsetMap));
                } else {
                    mipsCodes.addAll(callToConvention(func, instr, stackOffsetMap));
                }
            } else if (instr.isCallR()) {
                if (instr.isTigerInstrinsic()) {
                    mipsCodes.addAll(callrToSysCall(func, instr, stackOffsetMap));
                } else {
                    mipsCodes.addAll(callrToConvention(func, instr, stackOffsetMap));
                }
            } else if (instr.isArrAssignment()) {
            } else if (instr.isReturn()) {
                if (MipsInstruction.isNumeric(instr.getTarget())) {
                    mipsCodes.add(
                            MipsInstruction.li("$v0", instr.getTarget(), -1).comment("RETURN " + instr.getTarget())
                    );
                } else if (MipsInstruction.isRegister(instr.getTarget())) {
                    mipsCodes.add(
                            MipsInstruction.move("$v0", instr.getTarget(), -1).comment("RETURN " + instr.getTarget())
                    );
                } else { // Virtual
                    mipsCodes.add(
                            MipsInstruction.lw("$s4", "$sp", stackOffsetMap.get(instr.getTarget()), -1)
                    );
                    mipsCodes.add(
                            MipsInstruction.move("$v0", "$s4", -1).comment("RETURN " + instr.getTarget())
                    );
                }
                mipsCodes.add(
                        MipsInstruction.j(func.getFuncName() + "_stack_tear_down", -1)
                );
            } else {
                mipsCodes.add(instr);
            }
        }
        if (mipsCodes.size() > 0) {
            mipsCodes.get(0).comment("------- START FUNCTION BODY -------");
            mipsCodes.get(mipsCodes.size() - 1).comment("------- END FUNCTION BODY -------");
        }
        return mipsCodes;
    }

    private static void handleIntraBlockAllocator(Function func, HashMap<String, Integer> stackOffsetMap) {
        CFG cfg = func.getCfg();
        ArrayList<BasicBlock> blocks = cfg.getBlocks();
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> args = func.getCodes().get(0).getParameterNames();
        result.add(
                func.getCodes().get(0)
        );
        result.add(
                func.getCodes().get(1)
        );
        result.add(
                func.getCodes().get(2)
        );

        func.getCodes().remove(0);
        func.getCodes().remove(0);
        func.getCodes().remove(0);

        for (BasicBlock blk : blocks) {
            Map<String, String> regMap = blk.getMyRegisterAllocation();
            ArrayList<Instruction> instructions = blk.getInstructions();

            for (String virtualReg : regMap.keySet()) {
                if (args.contains(virtualReg)) {
                    continue;
                }
                String physicalReg = regMap.get(virtualReg);
                result.add(
                        MipsInstruction.lw(physicalReg, "$sp", stackOffsetMap.get(virtualReg), -1)
                );
            }

            result.addAll(instructions);

            for (String virtualReg : regMap.keySet()) {
                if (args.contains(virtualReg)) {
                    continue;
                }
                String physicalReg = regMap.get(virtualReg);
                result.add(
                        MipsInstruction.sw("$sp", physicalReg, stackOffsetMap.get(virtualReg), -1)
                );
            }

        }

        func.setCodes(result);
    }


    private static Function assignFuncParamToArgRegs(Function func) {
        ArrayList<String> args = func.getFuncArguments();

        for (Instruction instr : func.getCodes()) {
            if (instr.isArithmetic()) {
            } else if (instr.isStore()) { // sw src, offset(dest)
            } else if (instr.isLoad()) { // lw dest, offset(src)
            } else if (instr.isCallR()) {
            } else if (instr.isCall()) {
            } else if (instr.isReturn()) { // return, t
            } else if (instr.isBranch()) {
            } else {
            }
        }

        return func;
    }

    private static ArrayList<Instruction> handleSpilledRegister(Function func, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        for (Instruction instr : func.getCodes()) {
            if (instr.isArithmetic()) {
                result.addAll(
                        handleArithmeticSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isStore()) { // sw src, offset(dest)
                result.addAll(
                        handleSWSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isLoad()) {
                result.addAll(
                        handleLWSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isCallR()) {
                if (instr.isTigerInstrinsic()) {
                    result.addAll(
                            handleIntrinsicCallRSpill(func, instr, stackOffsetMap)
                    );
                } else {
                    result.addAll(
                            handleCallRSpill(func, instr, stackOffsetMap)
                    );
                }
            } else if (instr.isCall()) {
                if (instr.isTigerInstrinsic()) {
                    result.addAll(
                            handleIntrinsicCallSpill(func, instr, stackOffsetMap)
                    );
                } else {
                    result.addAll(
                            handleCallSpill(func, instr, stackOffsetMap)
                    );
                }
            } else if (instr.isReturn()) { // return, t
                result.addAll(
                        handleReturnSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isBranch()) {
                result.addAll(
                        handleBranchSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isArrStore()) {
                result.addAll(
                        handleArrStoreSpill(func, instr, stackOffsetMap)
                );
            } else if (instr.isArrLoad()) {
                result.addAll(
                        handleArrLoadSpill(func, instr, stackOffsetMap)
                );
            } else {
                result.add(instr);
            }
        }
        return result;
    }

    private static Collection<? extends Instruction> handleArrLoadSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        return arrLoadMips(instr, stackOffsetMap);
    }

    private static Collection<? extends Instruction> handleArrStoreSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        return arrStoreMips(instr, stackOffsetMap);
    }

    private static ArrayList<Instruction> arrStoreMips(Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        /**
         * array_store, val, A, offset
         *
         * src: val
         * dest: A
         *
         * Convert to:
         *
         *
         */
        ArrayList<Instruction> result = new ArrayList<>();
        String src = instr.getTokens().get(1);
        String arr = instr.getTokens().get(2);
        String indx = instr.getTokens().get(3);

        if (MipsInstruction.isRegister(arr)) { // Arr is in $a0
            if (MipsInstruction.isNumeric(indx)) { // immediate
                result.add(
                        MipsInstruction.li("$s6", indx, instr.getLineNum())
                );
            } else if (MipsInstruction.isRegister(indx)) { // physical reg
                result.add(
                        MipsInstruction.move("$s6", indx, instr.getLineNum())
                );
            } else { // Virtual reg
                result.add(
                        MipsInstruction.lw("$s6", "$sp", stackOffsetMap.get(indx), instr.getLineNum())
                );
            }

            result.add(
                    MipsInstruction.sll("$s6", "$s6", 2, instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", arr, "$s6", instr.getLineNum())
            );

            if (MipsInstruction.isRegister(src)) {
                result.add(
                        MipsInstruction.move("$s5", src, instr.getLineNum())
                );
            } else {
                result.add(
                        MipsInstruction.lw("$s5", "$sp", stackOffsetMap.get(src), instr.getLineNum())
                );
            }

            result.add(
                    MipsInstruction.sw("$s4", "$s5", 0, instr.getLineNum())
            );
        } else { // Arr is Virtual Reg
            result.add(
                    MipsInstruction.li("$s5", stackOffsetMap.get(arr), -1)
            );
            if (MipsInstruction.isNumeric(indx)) {
                result.add(
                        MipsInstruction.li("$s6", indx, instr.getLineNum())
                );
            } else if (MipsInstruction.isRegister(indx)) {
                result.add(
                        MipsInstruction.move("$s6", indx, instr.getLineNum())
                );
            } else {
                result.add(
                        MipsInstruction.lw("$s6", "$sp", stackOffsetMap.get(indx), instr.getLineNum())
                );
            }
            result.add(
                    MipsInstruction.sll("$s6", "$s6", 2, instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", "$s5", "$s6", instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", "$sp", "$s4", instr.getLineNum())
            );
            if (MipsInstruction.isNumeric(src)) {
                result.add(
                        MipsInstruction.li("$s5", src, instr.getLineNum())
                );
            } else if (MipsInstruction.isRegister(src)) {
                result.add(
                        MipsInstruction.move("$s5", src, instr.getLineNum())
                );
            } else {
                result.add(
                        MipsInstruction.lw("$s5", "$sp", stackOffsetMap.get(src), instr.getLineNum())
                );
            }
            result.add(
                    MipsInstruction.sw("$s4", "$s5", 0, instr.getLineNum())
            );
        }
        return result;
    }

    private static ArrayList<Instruction> arrLoadMips(Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        /**
         * array_load, val, A, indx
         * src: A
         * dest: val
         * Convert to:
         *
         *li s5, A_offset
         * add(i) s4, s5, indx
         * add s4, sp, s4
         * lw s4, 0(s4)
         * sw s4, val_offset(sp)
         */
        ArrayList<Instruction> result = new ArrayList<>();
        String dest = instr.getTokens().get(1);
        String arr = instr.getTokens().get(2);
        String indx = instr.getTokens().get(3);

        if (MipsInstruction.isRegister(arr)) {// Arr is in $a0
            if (MipsInstruction.isNumeric(indx)) { // immediate
                result.add(
                        MipsInstruction.li("$s6", indx, instr.getLineNum())
                );
            } else if (MipsInstruction.isRegister(indx)) { // physical reg
                result.add(
                        MipsInstruction.move("$s6", indx, instr.getLineNum())
                );
            } else { // Virtual reg
                result.add(
                        MipsInstruction.lw("$s6", "$sp", stackOffsetMap.get(indx), instr.getLineNum())
                );
            }
            result.add(
                    MipsInstruction.sll("$s6", "$s6", 2, instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", arr, "$s6", instr.getLineNum())
            );
            result.add(
                    MipsInstruction.lw("$s5", "$s4", 0, instr.getLineNum())
            );
        } else { // Arr is in Virtual Reg --> Use offset
            result.add(
                    MipsInstruction.li("$s5", stackOffsetMap.get(arr), -1)
            );

            if (MipsInstruction.isNumeric(indx)) { // immediate
                result.add(
                        MipsInstruction.li("$s6", indx, instr.getLineNum())
                );
            } else if (MipsInstruction.isRegister(indx)) { // physical reg
                result.add(
                        MipsInstruction.move("$s6", indx, instr.getLineNum())
                );
            } else { // Virtual reg
                result.add(
                        MipsInstruction.lw("$s6", "$sp", stackOffsetMap.get(indx), instr.getLineNum())
                );
            }

            result.add(
                    MipsInstruction.sll("$s6", "$s6", 2, instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", "$s5", "$s6", instr.getLineNum())
            );
            result.add(
                    MipsInstruction.add("$s4", "$sp", "$s4", instr.getLineNum())
            );
            result.add(
                    MipsInstruction.lw("$s5", "$s4", 0, instr.getLineNum())
            );
        }

        if (MipsInstruction.isRegister(dest)) {
            result.add(
                    MipsInstruction.move(dest, "$s5", instr.getLineNum())
            );
        } else {
            result.add(
                    MipsInstruction.sw("$sp", "$s5", stackOffsetMap.get(dest), instr.getLineNum())
            );
        }


        return result;
    }

    private static Collection<? extends Instruction> handleIntrinsicCallRSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_ret = instr.getTokens().get(1);
        String new_ret = old_ret;

        if (stackOffsetMap.get(old_ret) != null && !MipsInstruction.isRegister(old_ret)) {
            new_ret = "$s4";
        }

        String intrinsic_callr_code = "callr, " + ", " + new_ret + ", " + instr.getFuncName(); // call, puti, $s4/t

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("callr");
        tokens.add(new_ret);
        tokens.add(instr.getFuncName());

        Instruction intrinsic_callr = new Instruction(tokens, intrinsic_callr_code, InstructionType.TIGER, instr.getLineNum(), MipsSection.TEXT);

        result.add(intrinsic_callr);

        if (stackOffsetMap.get(old_ret) != null && !MipsInstruction.isRegister(old_ret)) { // ret spill
            int r_offset = stackOffsetMap.get(old_ret);
            result.add(
                    MipsInstruction.sw("$sp", new_ret, r_offset, instr.getLineNum())
            );
        }


        return result;
    }

    private static Collection<? extends Instruction> handleIntrinsicCallSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_arg = instr.getTokens().get(2);
        String new_arg = old_arg;

        if (stackOffsetMap.get(old_arg) != null && !MipsInstruction.isRegister(old_arg) && !MipsInstruction.isChar(old_arg)) { // spill argument
            int r_offset = stackOffsetMap.get(old_arg);
            result.add(
                    MipsInstruction.lw("$s4", "$sp", r_offset, instr.getLineNum())
            );
            new_arg = "$s4";
        }

        String intrinsic_call_code = "call, " + instr.getFuncName() + ", " + new_arg; // call, puti, $s4/t

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("call");
        tokens.add(instr.getFuncName());
        tokens.add(new_arg);

        Instruction intrinsic_call = new Instruction(tokens, intrinsic_call_code, InstructionType.TIGER, instr.getLineNum(), MipsSection.TEXT);

        result.add(intrinsic_call);

        return result;
    }

    private static Collection<? extends Instruction> handleBranchSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String src = instr.getTokens().get(1);
        String target = instr.getTokens().get(2);
        String label = instr.getTokens().get(3);

        String new_src = src;
        String new_target = target;

        if (stackOffsetMap.get(src) != null && !MipsInstruction.isRegister(src)) {
            int a_offset = stackOffsetMap.get(src);
            result.add(
                    MipsInstruction.lw("$s4", "$sp", a_offset, instr.getLineNum())
            );
            new_src = "$s4";
        }

        if (stackOffsetMap.get(target) != null && !MipsInstruction.isRegister(target)) {
            int b_offset = stackOffsetMap.get(target);
            result.add(
                    MipsInstruction.lw("$s5", "$sp", b_offset, instr.getLineNum())
            );
            new_target = "$s5";
        }

        String op = instr.getOp();
        String code = op + " " + new_src + ", " + new_target + ", " + label;
        LinkedList<String> tokens = new LinkedList<>();

        tokens.add(op);
        tokens.add(new_src);
        tokens.add(new_target);
        tokens.add(label);

        Instruction branch = new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT);

        result.add(branch);

        return result;
    }

    private static Collection<? extends Instruction> handleReturnSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        if (instr.getTokens().size() == 1) {
            result.add(instr);
            return result;
        }

        String src = instr.getTokens().get(1);

        if (MipsInstruction.isRegister(src) || MipsInstruction.isNumeric(src)) {
            result.add(instr);
            return result;
        }

        if (stackOffsetMap.get(src) != null && !MipsInstruction.isRegister(src)) {
            int r_offset = stackOffsetMap.get(src);
            result.add(
                    MipsInstruction.lw("$s4", "$sp", r_offset, -1)
            );
            instr.getTokens().set(1, "$s4");
            instr.setCode("return, $s4");
            instr.setSrc("$s4");
            instr.setTarget("$s4");
            result.add(instr);
        }

        return result;
    }

    private static Collection<? extends Instruction> handleCallSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        result.add(instr);
        return result;
    }


    private static Collection<? extends Instruction> handleCallRSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_dest = instr.getTokens().get(1);
        String new_dest = old_dest;

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            new_dest = "$s4";
        }

        String callr_code = "callr, " + new_dest + ", " + instr.getFuncName();
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("callr");
        tokens.add(new_dest);
        tokens.add(instr.getFuncName());

        for (int i = 0; i < instr.getFuncArgs().size(); i++) {
            callr_code += ", " + instr.getFuncArgs().get(i);
            tokens.add(instr.getFuncArgs().get(i));
        }

        Instruction callr = new Instruction(tokens, callr_code, InstructionType.TIGER, instr.getLineNum(), MipsSection.TEXT);

        result.add(callr);

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            int r_offset = stackOffsetMap.get(old_dest);
            result.add(
                    MipsInstruction.sw("$sp", new_dest, r_offset, -1)
            );
        }

        return result;
    }

    private static Collection<? extends Instruction> handleLWSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_src;
        String old_dest;
        String old_target;
        String offset;

        old_dest = instr.getTokens().get(1);
        offset = instr.getTokens().get(2);
        old_src = instr.getTokens().get(3);

        String new_dest = old_dest;
        String new_src = old_src;

        if (stackOffsetMap.get(old_src) != null && !MipsInstruction.isRegister(old_src)) { // spill src
            int b_offset = stackOffsetMap.get(old_src);
            result.add(
                    MipsInstruction.addi("$s5", "$sp", b_offset, -1) // addi $s5, b_offset(sp)
            );
            new_src = "$s5";
        }

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            new_dest = "$s4";
        }

        result.add(
                MipsInstruction.lw(new_dest, new_src, offset, -1) // lw $s4, offset($s5) or offset(reg)
        );

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            int a_offset = stackOffsetMap.get(old_dest);
            result.add(
                    MipsInstruction.sw("$sp", "$s4", a_offset, -1) // sw $sp, a_offset($s4)
            );
        }


        return result;
    }

    private static Collection<? extends Instruction> handleSWSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_src;
        String old_dest;
        String old_target;
        String offset;

        old_src = instr.getTokens().get(1);
        offset = instr.getTokens().get(2);
        old_dest = instr.getTokens().get(3);

        String new_dest = old_dest;
        String new_src = old_src;


        if (stackOffsetMap.get(old_src) != null && !MipsInstruction.isRegister(old_src)) {
            int a_offset = stackOffsetMap.get(old_src);
            result.add(
                    MipsInstruction.lw("$s4", "$sp", a_offset, -1)
            );
            new_src = "$s4";
        }

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            int b_offset = stackOffsetMap.get(old_dest);
            result.add(
                    MipsInstruction.addi("$s5", "$sp", b_offset, -1)
            );
            new_dest = "$s5";
        }

        result.add(
                MipsInstruction.sw(new_dest, new_src, offset, -1)
        );

        return result;
    }

    private static Collection<? extends Instruction> handleArithmeticSpill(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        String old_src;
        String old_dest;
        String old_target;
        String offset;

        old_dest = instr.getTokens().get(1);
        old_src = instr.getTokens().get(2);
        old_target = instr.getTokens().get(3);

        String new_dest = old_dest;
        String new_src = old_src;
        String new_target = old_target;

        if (stackOffsetMap.get(old_src) != null && !MipsInstruction.isRegister(old_src) && !MipsInstruction.isNumeric(old_src)) {
            result.add(
                    lwSpillReg(old_src, "$sp", "$s4", stackOffsetMap)
            );
            new_src = "$s4";
        }
        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest)) {
            result.add(
                    lwSpillReg(old_dest, "$sp", "$s5", stackOffsetMap)
            );
            new_dest = "$s5";
        }
        if (stackOffsetMap.get(old_target) != null && !MipsInstruction.isRegister(old_target) && !MipsInstruction.isNumeric(old_target)) {
            result.add(
                    lwSpillReg(old_target, "$sp", "$s6", stackOffsetMap)
            );
            new_target = "$s6";
        }

        String code = instr.getOp() + " " + new_dest + ", " + new_src + ", " + new_target;

        LinkedList<String> tokens = new LinkedList<>();
        tokens.add(instr.getOp());
        tokens.add(new_dest);
        tokens.add(new_src);
        tokens.add(new_target);

        result.add(new Instruction(tokens, code, InstructionType.MIPS, instr.getLineNum(), MipsSection.TEXT));

        if (stackOffsetMap.get(old_dest) != null && !MipsInstruction.isRegister(old_dest) && !MipsInstruction.isNumeric(old_dest)) {
            result.add(
                    swSpillReg(old_dest, "$sp", "$s5", stackOffsetMap)
            );
        }
        return result;
    }

    private static Instruction lwSpillReg(String reg, String src, String bufferReg, HashMap<String, Integer> stackOffsetMap) {
        return MipsInstruction.lw(bufferReg, src, stackOffsetMap.get(reg), -1);
    }

    private static Instruction swSpillReg(String reg, String dest, String bufferReg, HashMap<String, Integer> stackOffsetMap) {
        return MipsInstruction.sw(dest, bufferReg, stackOffsetMap.get(reg), -1);
    }


    public static ArrayList<Instruction> arrayAssignment(int size, int value) {
        /**
         * TODO: handle array assignment
         *
         * Create a initialize forloop, and pass the access to the array reference
         */
        return null;
    }

    public static Collection<? extends Instruction> callrToSysCall(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        String sysCallName = instr.getTokens().get(2);

        result.addAll(
                saveArgsRegToStack(instr, stackOffsetMap)
        );

        switch (sysCallName) {
            case "geti": // read_int
                result.addAll(
                        Arrays.asList(
                                MipsInstruction.li("$v0", 5, -1).comment("START GETI"),
                                MipsInstruction.sysCall(-1),
                                MipsInstruction.move(instr.getDest(), "$v0", -1).comment("END GETI")
                        )
                );
                break;
            case "getf": // read_float
                result.addAll(
                        Arrays.asList(
                                MipsInstruction.li("$v0", 6, -1).comment("START GETF"),
                                MipsInstruction.sysCall(-1),
                                MipsInstruction.move(instr.getDest(), "$v0", -1).comment("END GETF")
                        )
                );
                break;
            case "getc": // read_string
                result.addAll(
                        Arrays.asList(
                                MipsInstruction.li("$v0", 12, -1).comment("START GETC"),
                                MipsInstruction.sysCall(-1),
                                MipsInstruction.move(instr.getDest(), "$v0", -1).comment("END GETC")
                        )
                );
                break;
            default:
                break;
        }

        result.addAll(
                loadArgsRegFromStack(instr, stackOffsetMap)
        );

        return result;
    }

    public static Collection<? extends Instruction> callToSysCall(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        String sysCallName = instr.getTokens().get(1);
        String arg;
        int sysCallCode;
        result.addAll(
                saveArgsRegToStack(instr, stackOffsetMap)
        );


        switch (sysCallName) {
            case "puti": // print_int
                arg = Instruction.getCallArgs(instr).get(0);
                sysCallCode = 1;
                result.add(
                        MipsInstruction.li("$v0", sysCallCode, -1).comment("START PUTI")
                );
                if (MipsInstruction.isNumeric(arg)) {
                    result.add(
                            MipsInstruction.li("$a0", arg, -1)
                    );
                } else {
                    result.add(
                            MipsInstruction.move("$a0", arg, -1)
                    );
                }
                result.add(
                        MipsInstruction.sysCall(-1).comment("END PUTI")
                );
                break;
            case "putf": // print_float
                arg = Instruction.getCallArgs(instr).get(0);
                sysCallCode = 2;
                result.addAll(
                        Arrays.asList(
                                MipsInstruction.li("$v0", sysCallCode, -1).comment(" START PUTF"),
                                MipsInstruction.move_s("$f12", arg, -1),
                                MipsInstruction.sysCall(-1).comment("END PUTF")
                        )
                );
                break;
            case "putc": // print_char
                arg = Instruction.getCallArgs(instr).get(0);
                sysCallCode = 11;
                result.add(
                        MipsInstruction.li("$v0", sysCallCode, -1).comment("START PUTC")
                );
                if (MipsInstruction.isChar(arg)) {
                    result.add(
                            MipsInstruction.la("$a0", arg, -1)
                    );
                } else if (MipsInstruction.isNumeric(arg)) {
                    result.add(
                            MipsInstruction.li("$a0", arg, -1)
                    );
                } else {
                    result.add(
                            MipsInstruction.move("$a0", arg, -1)
                    );
                }
                result.add(
                        MipsInstruction.sysCall(-1).comment("END PUTC")
                );
                break;
            default:
                break;
        }
        result.addAll(
                loadArgsRegFromStack(instr, stackOffsetMap)
        );


        return result;
    }


    /**
     * @param func
     * @param instr          : callr, t, func, a, b, c, d
     * @param stackOffsetMap
     * @return sw $a0, 0($sp)
     * sw $a1, 4($sp)
     * sw $a2, 8($sp)
     * sw $a3, 12($sp)
     * <p>
     * add $a0, a, $0
     * add $a1, b, $0
     * add $a2, c, $0
     * add $a3, d, $0
     * <p>
     * jal func
     * <p>
     * add t, $v0, $0 <--- get return value
     * <p>
     * lw $a0, 12($sp)
     * lw $a1, 8($sp)
     * lw $a2, 4($sp)
     * lw $a3, 0($sp)
     */
    public static Collection<? extends Instruction> callrToConvention(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        String ret = instr.getTokens().get(1);
        String name = instr.getTokens().get(2);

        result.addAll(
                saveArgsRegToStack(instr, stackOffsetMap)
        );

        result.addAll(
                initArgsReg(func, instr, stackOffsetMap)
        );

        result.addAll(
                jalFunc(name)
        );

        result.addAll(
                loadReturnVal(ret)
        );

        result.addAll(
                loadArgsRegFromStack(instr, stackOffsetMap)
        );

        return result;
    }

    public static Collection<? extends Instruction> loadReturnVal(String s) {
        return new ArrayList<>(Arrays.asList(
                MipsInstruction.move(s, "$v0", -1).comment("LOAD RETURN VALUE TO " + s)
        ));
    }

    /**
     * @param func
     * @param instr          : call, func, a, b, c, d
     * @param stackOffsetMap
     * @return sw $a0, 0($sp)
     * sw $a1, 4($sp)
     * sw $a2, 8($sp)
     * sw $a3, 12($sp)
     * <p>
     * add $a0, a, $0
     * add $a1, b, $0
     * add $a2, c, $0
     * add $a3, d, $0
     * <p>
     * jal func
     * <p>
     * lw $a0, 0($sp)
     * lw $a1, 4($sp)
     * lw $a2, 8($sp)
     * lw $a3, 12($sp)
     */
    public static Collection<? extends Instruction> callToConvention(Function func, Instruction instr, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();

        result.addAll(
                saveArgsRegToStack(instr, stackOffsetMap)
        );

        result.addAll(
                initArgsReg(func, instr, stackOffsetMap)
        );

        result.addAll(
                jalFunc(instr.getTokens().get(1))
        );

        result.addAll(
                loadArgsRegFromStack(instr, stackOffsetMap)
        );

        return result;
    }

    public static ArrayList<Instruction> loadArgsRegFromStack(Instruction call, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> args = Instruction.getCallArgs(call);
        int totalArgRegs = 4;
        int $a = 0;
        for (int i = 0; i < totalArgRegs; i++) {
            result.add(
                    MipsInstruction.lw("$a" + i, "$sp", i * WORD, -1)
            );
        }
        result.add(
                MipsInstruction.moveStackPtr(totalArgRegs * WORD).comment("DEALLOCATE ARG REGISTERS")
        );
        tearDownStackOffsetMap(stackOffsetMap, -totalArgRegs * WORD);
        return result;
    }

    public static ArrayList<Instruction> jalFunc(String funcName) {
        ArrayList<Instruction> result = new ArrayList<>();
        String codes = "jal " + funcName;
        LinkedList<String> tokens = new LinkedList<>();
        tokens.add("jal");
        tokens.add(funcName);
        result.add(new Instruction(tokens, codes, InstructionType.MIPS, -1, MipsSection.TEXT).comment("JUMP TO FUNCTION"));
        return result;
    }

    public static ArrayList<Instruction> initArgsReg(Function func, Instruction call, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> args = call.getFuncArgs();

        int $a = 0;
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            if (MipsInstruction.isNumeric(args.get(i))) {
                result.add(
                        MipsInstruction.li("$a" + $a, arg, call.getLineNum()).comment("INIT " + "$a" + $a)
                );
            } else if (func.getFunctionArrayNames().contains(arg)) { // Handle array as parameter, pass by reference

                result.add(
                        MipsInstruction.li("$s4", stackOffsetMap.get(arg), call.getLineNum())
                );
                result.add(
                        MipsInstruction.add("$s4", "$s4", "$sp", call.getLineNum())
                );
                result.add(
                        MipsInstruction.move("$a" + $a, "$s4", call.getLineNum()).comment("INIT " + "$a" + $a)
                );
            } else if (!MipsInstruction.isRegister(arg) && stackOffsetMap.get(arg) != null) {
                int offset = stackOffsetMap.get(arg);
                result.add(
                        MipsInstruction.lw("$a" + $a, "$sp", offset, call.getLineNum()).comment("INIT " + "$a" + $a)
                );
            } else {
                result.add(
                        MipsInstruction.move("$a" + $a, arg, call.getLineNum()).comment("INIT " + "$a" + $a)
                );
            }
            $a++;
        }

        return result;
    }

    public static ArrayList<Instruction> saveArgsRegToStack(Instruction call, HashMap<String, Integer> stackOffsetMap) {
        ArrayList<Instruction> result = new ArrayList<>();
        ArrayList<String> args = Instruction.getCallArgs(call);
        int totalArgRegs = 4;

        int $a = 0;
        int offset = 0;
        result.add(
                MipsInstruction.moveStackPtr(totalArgRegs * -4).comment("SAVE ARGUMENT")
        );
        buildUpStackOffsetMap(stackOffsetMap, "", totalArgRegs * WORD);
        for (int i = 0; i < totalArgRegs; i++) {
            result.add(
                    MipsInstruction.sw("$sp", "$a" + $a, offset, -1)
            );
            $a++;
            offset += 4;
        }

        return result;
    }
}