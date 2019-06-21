package backEnd;

import graph.*;

import java.lang.reflect.Array;
import java.util.*;

public class RegisterAllocator {

    private int allocationType;
    private Program myProgram;
    private Map<String, String> alreadyGeneratedNames;
    private static Set<String> alreadyUsedNewNames = new HashSet<>();
    private static Map<String, String> currentlyFilledRegs = new HashMap<>();

    public static String zeroReg = "$0";
    public static String assembleReg = "$at";
    public static String globalPoint = "$gp"; //IGNORE
    public static String stackPoint = "$sp";
    public static String framePoint = "$fp";
    public static String returnAddr = "$ra";
    public static String[] returnRegs = {"$v0", "$v1"};
    public static String[] argRegs = {"$a0", "$a1", "$a2", "$a3"};
    public static String[] tempRegs = {"$t0", "$t1", "$t2", "$t3", "$t4",
            "$t5", "$t6", "$t7", "$t8", "$t9"};
    public static String[] savedRegs = {"$s0", "$s1", "$s2", "$s3", "$s7"};
    public static String[] specialSavedRegs = {"$4", "$5", "$s6"};
    public static String[] kernelRegs = {"$k0", "$k1"}; //DO NOT USE

    public static String[] reservedWords = {"b", "j"};


    public RegisterAllocator(Program _myProgram, int type) {
        myProgram = _myProgram;
        allocationType = type;
        renameReoccurringVariables();
        performAllocation();
    }

    private void renameReoccurringVariables() {
        Set<String> allLabelsParsed = new HashSet<>();
        ArrayList<Function> newFuncs = new ArrayList<>();
        for (Function func : myProgram.getMyFunctions()) {
            //System.out.println("LABELS: " + func.getLabelNames());
            alreadyGeneratedNames = new HashMap<>();
            //List<String> myVirtualRegs = getVirtualRegs(func.getCodes());
            //Set<String> overlapRegs = new HashSet<>(myVirtualRegs);
            //overlapRegs.retainAll(allVirtualRegs);
            Set<String> overlapRegs = new HashSet<>(Arrays.asList(reservedWords)); //
            //overlapRegs.addAll(Arrays.asList((reservedWords)));
            for (String badName : overlapRegs) {
                generateVariableName(5, badName);
            }

            Set<String> labelsThatNeedRenaming = new HashSet<>(func.getLabelNames());
            labelsThatNeedRenaming.retainAll(allLabelsParsed);

            for (String label : labelsThatNeedRenaming) {
                extendName(func.getFunctionName(), label);
            }

            Set<String> allRenamings = new HashSet<>(overlapRegs);
            allRenamings.addAll(labelsThatNeedRenaming);

            //System.out.println("OVERLAPPING VARS: " + overlapRegs);
            //if (!overlapRegs.isEmpty()) {
            //System.out.println("RENAMINGS: " + allRenamings);
                ArrayList<Instruction> newCodes = new ArrayList<>();
                for (Instruction myInstruction : func.getCodes()) {
                    Instruction newInstruction = instructionRenameReplace(myInstruction, allRenamings);
                    newCodes.add(newInstruction);
                    //System.out.println("OLD: " + myInstruction.getTokens() + myInstruction.getLineNum());
                    //System.out.println("NEW: " + newInstruction.getTokens());
                }
                Function newFunc = new Function(newCodes);
                newFunc.buildMipsCfg();
                newFuncs.add(newFunc);
            /*} else {
                newFuncs.add(func);
            }*/
            //allVirtualRegs.addAll(myVirtualRegs);
            allLabelsParsed.addAll(func.getLabelNames());

        }
        myProgram = new Program(newFuncs);
        //System.out.println("RENAME FINISHED");
    }

    private Instruction instructionRenameReplace(Instruction oldInstr, Set<String> notAllowedNames) {
        ArrayList<String> usedVariables;
        if (oldInstr.getLineNum() == 0) {
            usedVariables = oldInstr.getParameterNames();
        } else if (oldInstr.getLineNum() < 3) {
            usedVariables = oldInstr.getDeclaredNames();
        } else {
            usedVariables = oldInstr.getUses();
            usedVariables.addAll(oldInstr.getDefs());
            usedVariables.add(oldInstr.getLabels());
            //System.out.println(oldInstr.getLabels());
        }
        //System.out.println("USED: " + usedVariables);

        Set<String> intersection = new HashSet<>(notAllowedNames);
        intersection.retainAll(usedVariables);
        //System.out.println("INTERSECTION: " + intersection);

        //System.out.println(oldInstr.getTokens());
        //System.out.println(intersection);

        if (!intersection.isEmpty()) {
            //System.out.println("intersection: " + intersection);
            LinkedList<String> newTokens = new LinkedList<>();
            for (int i = 0; i < oldInstr.getTokens().size(); i++) {
                String newToken = oldInstr.getTokens().get(i);
                //if (i != 0) {
                    for (String notAllowed : intersection) {
                        String replacementVar = alreadyGeneratedNames.get(notAllowed);
                        //System.out.println(notAllowed + " becomes " + replacementVar);
                        if (oldInstr.getLineNum() == 0) {
                            //System.out.println("HELLOOOO");
                            //System.out.println(newToken.split(" ")[1]);
                            String[] parts = newToken.split(" ");
                            String var = parts[parts.length - 1];
                            if (var.equals(notAllowed)) {
                                //System.out.println("DETECTED");

                                newToken = newToken.replace(" " + notAllowed, " " + replacementVar);
                            }
                        } else if (oldInstr.getLineNum() < 3) {
                            if (newToken.contains(notAllowed + "[")) {
                                newToken = newToken.replace(notAllowed + "[", replacementVar + "[");
                            } else if (newToken.equals(notAllowed)) {
                                newToken = replacementVar;
                            }
                        } else {
                            if (!newToken.contains(":")) {
                                if (newToken.equals(notAllowed)) {
                                    //System.out.println(notAllowed + " becomes " + replacementVar);
                                    //System.out.println(oldInstr.getTokens());
                                    newToken = replacementVar;
                                }
                            } else {
                                //System.out.println(newToken.split(":")[0]);
                                if (newToken.split(":")[0].equals(notAllowed)) {
                                    newToken = replacementVar + ":";
                                }
                            }

                        }
                    }
                //}
                newTokens.add(newToken);
            }
            //System.out.println("OLD: " + oldInstr.getTokens());
            //System.out.println("NEW: " + newTokens);

            Instruction newInstr = new Instruction(newTokens, "", oldInstr.getType(), oldInstr.getLineNum(), oldInstr.getSection());
            newInstr.buildCodeFromTokens();
            //System.out.println("OLD CODE: " + oldInstr.getCode());
            //System.out.println("NEW CODE: " + newInstr.getCode());
            return newInstr;
        }
        return oldInstr;
    }

    private void generateVariableName(int length, String badName) {
        Random r = new Random();
        String returnString = "";
        for (int i = 0; i < length; i++) {
            returnString += "a";
        }
        while(alreadyUsedNewNames.contains(returnString)) {
            returnString = "";
            for (int i = 0; i < length; i++) {
                char c = (char) (r.nextInt(26) + 'a');
                returnString += c;
            }
        }
        alreadyGeneratedNames.put(badName, returnString);
        alreadyUsedNewNames.add(returnString);
    }

    private void extendName(String functionName, String labelName) {
        alreadyGeneratedNames.put(labelName, labelName + functionName);
        alreadyUsedNewNames.add(labelName + functionName);
    }

    private void performAllocation() {

        //naive allocation
        if (allocationType == 1) {

            LinkedList<Function> newFunctions = new LinkedList<>();

            for (Function func : myProgram.getMyFunctions()) {
                ArrayList<String> parameterNames = func.getCodes().get(0).getParameterNames();

                Map<String, String> alreadyMadeAllocation = new HashMap<>();
                for (int i = 0; i < parameterNames.size(); i++) {
                    String parameter = parameterNames.get(i);
                    alreadyMadeAllocation.put(parameter, argRegs[i]);
                }

                //ArrayList<String> spillNames = func.getFunctionArrayNames();
                //spillNames.addAll(getVirtualRegs(func.getCodes()));
                ArrayList<String> spillNames = getVirtualRegs(func.getCodes());
                spillNames.removeAll(parameterNames);

                ArrayList<Instruction> newInstructions = new ArrayList<>();
                int lineCounter = 3;
                for (int i = 0; i < func.getCodes().size(); i++) {
                    Instruction oldInstr = func.getCodes().get(i);
                    if (i <= 2) {
                        newInstructions.add(oldInstr);
                    } else {
                        ArrayList<Instruction> newAdditions = chaitinInstructionReplace(oldInstr, lineCounter, alreadyMadeAllocation);
                        newInstructions.addAll(newAdditions);
                        lineCounter += newAdditions.size();
                    }
                }

                Function newFunc = new Function(newInstructions);
                newFunc.buildMipsCfg();
                newFunc.setSpilledRegs(spillNames);
                newFunc.setMyRegisterAllocation(alreadyMadeAllocation);
                newFunctions.add(newFunc);

                System.out.println("Array Names: " + newFunc.getFunctionArrayNames());
                System.out.println("Parameter Names: " + parameterNames);
                System.out.println("intList: " + cleanUpList(newFunc.getCodes().get(1).getIntList()));
                System.out.println("floatList: " + cleanUpList(newFunc.getCodes().get(2).getIntList()));
                System.out.println("Spilled Regs: " + newFunc.getSpilledRegs());
                System.out.println("Allocation: " + newFunc.getMyRegisterAllocation());
            }
            myProgram.replaceFunctions(newFunctions);

        //intra-block allocation
        } else if (allocationType == 2) {
            for (Function func : myProgram.getMyFunctions()) {
                ArrayList<String> arrayNames = func.getFunctionArrayNames();
                ArrayList<String> parameterNames = func.getCodes().get(0).getParameterNames();
                CFG myCFG = func.getCfg();
                ArrayList<BasicBlock> myBlocks = myCFG.getBlocks();

                Map<String, String> alreadyMadeAllocation = new HashMap<>();
                for (int i = 0; i < parameterNames.size(); i++) {
                    String parameter = parameterNames.get(i);
                    alreadyMadeAllocation.put(parameter, argRegs[i]);
                }
                System.out.println("BAHAHAHAHAHAHAHAHAHAHAHAH");
                System.out.println(alreadyMadeAllocation);

                for (BasicBlock block : myBlocks) {
                    ArrayList<LinkedList<String>> codes = block.getCodes();
                    int lineCounter = block.getId();
                    ArrayList<Instruction> bbInstructions = new ArrayList<>();
                    //converts tokens into full instructions
                    for (LinkedList<String> tokens : codes) {
                        Instruction newInstruction = new Instruction(tokens, "", guessType(tokens), lineCounter);
                        newInstruction.buildCodeFromTokens();
                        lineCounter++;
                        bbInstructions.add(newInstruction);
                    }

                    currentlyFilledRegs = new HashMap<>();

                    //for each block we get the live ranges for each variable
                    LiveSetGenerator myGenerator = new LiveSetGenerator(bbInstructions, arrayNames);
                    Map<String, Integer> firstLineMap = myGenerator.getFirstLineLiveMap();
                    Map<String, Integer> lastLineMap = myGenerator.getLastLineLiveMap();

                    //then we make a Colored Graph to decide the allocations
                    ArrayList<ColorNode> listOfNodes = new ArrayList<>();

                    //for (Instruction each : bbInstructions) {
                    //    System.out.println(each.getTokens());
                    //}
                    //System.out.println(firstLineMap.keySet());
                    //System.out.println(getVirtualRegs(bbInstructions));

                    for (String virtualReg : getVirtualRegs(bbInstructions)) {
                        if (!arrayNames.contains(virtualReg)) {
                            ColorNode tempNode = new ColorNode(virtualReg,
                                    firstLineMap.get(virtualReg), lastLineMap.get(virtualReg));
                            listOfNodes.add(tempNode);
                        }
                    }

                    ArrayList<String> spillArrayNames = func.getFunctionArrayNames();
                    spillArrayNames.removeAll(parameterNames);

                    ColorGraph myGraph = new ColorGraph(listOfNodes, alreadyMadeAllocation, spillArrayNames, 2);

                    //pass on the map and every instruction, and return a list of replacement instructions
                    ArrayList<Instruction> newInstructions = new ArrayList<>();
                    lineCounter = block.getId();
                    for (int i = 0; i < bbInstructions.size(); i++) {
                        Instruction oldInstr = bbInstructions.get(i);
                        if (oldInstr.getLineNum() <= 2) {
                            newInstructions.add(oldInstr);
                            lineCounter += 1;
                        } else {
                            ArrayList<Instruction> newAdditions = chaitinInstructionReplace(oldInstr, lineCounter, myGraph.getVirtualToPhysicalMap());
                            newInstructions.addAll(newAdditions);
                            lineCounter += newAdditions.size();
                        }
                    }

                    //assigns everything to the basic block
                    block.setMyInstructions(newInstructions);
                    block.setMyRegisterAllocation(myGraph.getVirtualToPhysicalMap());
                    block.setSpilledRegs(myGraph.getSpillList());

                    //FOR DEBUGGING PURPOSES
                    System.out.println("BLOCK ID: " + block.getId());
                    System.out.println("ALLOCATION MAP: " + block.getMyRegisterAllocation());
                    System.out.println("SPILLED REGS: " + block.getSpilledRegs());
                    for (Instruction each : block.getInstructions()) {
                        System.out.println(each.getTokens());
                    }

                }
            }
        //global chaitin briggs allocation
        } else if (allocationType == 3) {
            LinkedList<Function> newFunctions = new LinkedList<>();
            for (Function func : myProgram.getMyFunctions()) {
                ArrayList<String> parameterNames = func.getCodes().get(0).getParameterNames();

                Map<String, String> alreadyMadeAllocation = new HashMap<>();
                for (int i = 0; i < parameterNames.size(); i++) {
                    String parameter = parameterNames.get(i);
                    alreadyMadeAllocation.put(parameter, argRegs[i]);
                }

                currentlyFilledRegs = new HashMap<>();

                //for each function, we get the Live Ranges for each variable
                ArrayList<Instruction> myInstructions = func.getCodes();
                LiveSetGenerator myGenerator = new LiveSetGenerator(myInstructions, func.getFunctionArrayNames());
                Map<String, Integer> firstLineMap = myGenerator.getFirstLineLiveMap();
                Map<String, Integer> lastLineMap = myGenerator.getLastLineLiveMap();

                //then we make a Colored Graph to decide the allocations
                ArrayList<ColorNode> listOfNodes = new ArrayList<>();

                //System.out.println(firstLineMap.keySet());
                //System.out.println(getVirtualRegs(func.getCodes()));

                for (String virtualReg : getVirtualRegs(func.getCodes())) {
                    if (!func.getFunctionArrayNames().contains(virtualReg)) {
                        ColorNode tempNode = new ColorNode(virtualReg,
                                firstLineMap.get(virtualReg), lastLineMap.get(virtualReg));
                        listOfNodes.add(tempNode);
                    }
                }

                ArrayList<String> spillArrayNames = func.getFunctionArrayNames();
                spillArrayNames.removeAll(parameterNames);

                ColorGraph myGraph = new ColorGraph(listOfNodes, alreadyMadeAllocation, spillArrayNames, 3);
                //System.out.println(myGraph.getSpillList());
                //System.out.println(myGraph.getVirtualToPhysicalMap());

                //pass on the map and every instruction, and return a list of replacement instructions
                ArrayList<Instruction> newInstructions = new ArrayList<>();
                int lineCounter = 3;
                for (int i = 0; i < func.getCodes().size(); i++) {
                    Instruction oldInstr = func.getCodes().get(i);
                    if (i <= 2) {
                        newInstructions.add(oldInstr);
                        //lineCounter += 1;
                    } else {
                        ArrayList<Instruction> newAdditions = chaitinInstructionReplace(oldInstr, lineCounter, myGraph.getVirtualToPhysicalMap());
                       newInstructions.addAll(newAdditions);
                       lineCounter += newAdditions.size();
                    }
                }

                Function newFunc = new Function(newInstructions);
                newFunc.buildMipsCfg();
                newFunc.setSpilledRegs(myGraph.getSpillList());
                newFunc.setMyRegisterAllocation(myGraph.getVirtualToPhysicalMap());
                newFunctions.add(newFunc);

                System.out.println("Array Names: " + newFunc.getFunctionArrayNames());
                System.out.println("Parameter Names: " + parameterNames);
                System.out.println("intList: " + cleanUpList(newFunc.getCodes().get(1).getIntList()));
                System.out.println("floatList: " + cleanUpList(newFunc.getCodes().get(2).getIntList()));
                System.out.println("Spilled Regs: " + newFunc.getSpilledRegs());
                System.out.println("Allocation: " + newFunc.getMyRegisterAllocation());
            }
            myProgram.replaceFunctions(newFunctions);
        }
    }

    private InstructionType guessType(LinkedList<String> tokens) {
        String instruction = tokens.get(0);
        if (instruction.equals("call") || instruction.equals("callr") ||
        instruction.equals("return")) {
            return InstructionType.TIGER;
        } else {
            return InstructionType.MIPS;
        }
    }

    private ArrayList<Instruction> chaitinInstructionReplace(Instruction oldInstruction, int lineNumber, Map<String, String> virtualToPhysicalMap) {
        //ArrayList<Instruction> replacementInstructions = new ArrayList<>();
        //currentlyFilledRegs: physical -> virtual
        //virtualToPhysicalMap: virtual -> physical

        ArrayList<String> myDefs = oldInstruction.getDefs();
        ArrayList<String> myUses = oldInstruction.getUses();

        ArrayList<String> myRealDefs = new ArrayList<>(myDefs);
        myRealDefs.retainAll(virtualToPhysicalMap.keySet());

        ArrayList<String> myRealUses = new ArrayList<>(myUses);
        myRealUses.retainAll(virtualToPhysicalMap.keySet());

        //might not be needed but just in case
        /*Map<String, String> tempMap = new HashMap<>();
        int counter = 0;
        for (String use : myRealUses) {
            tempMap.put(use, specialSavedRegs[counter]);
            counter++;
        }*/

        //there should only be 1 def-- save whatever reg is currently in there, cause it is about to be overwritten
        //there can be up to 4 uses-- function call parameters, and they could theoretically
        //  all want the same physical reg (as one another or the def reg)

        ArrayList<Instruction> beforeInstruction = new ArrayList<>();
        Instruction newInstruction;
        //ArrayList<Instruction> afterInstruction = new ArrayList<>();

        /*for (String def : myRealDefs) {
            String variableInMyRegister = currentlyFilledRegs.getOrDefault(virtualToPhysicalMap.get(def), "");

            /*Set<String> variablesThatCouldBeInMyReg = new HashSet<>();
            for (String var : virtualToPhysicalMap.keySet()) {
                if (virtualToPhysicalMap.get(var).equals(virtualToPhysicalMap.get(def))) {

                }
            }*/

            //DEBUGGING PURPOSES
            /*if (variableInMyRegister.equals(def)) {
                System.out.println(def + " already in register " + virtualToPhysicalMap.get(def));
            } else if ("".equals(variableInMyRegister)) {
                System.out.println(virtualToPhysicalMap.get(def) + " is currently not filled!");
            } else {
                System.out.println(virtualToPhysicalMap.get(def) + " already taken by " + variableInMyRegister);
            }*/


            /*if (!variableInMyRegister.equals(def) && !"".equals(variableInMyRegister)) {
                //save the reg cause it is about to be overwritten
                //beforeInstruction.add(generateStoreInstruction(variableInMyRegister, virtualToPhysicalMap.get(def), lineNumber));
            }
            currentlyFilledRegs.put(virtualToPhysicalMap.get(def), def);*/


        //}

        //Set<String> varsThatUsedTempMap = new HashSet<>();

        /*for (String use : myRealUses) {
            String variableInMyRegister = currentlyFilledRegs.getOrDefault(virtualToPhysicalMap.get(use), "");
            if (!variableInMyRegister.equals(use)) {
                if (myRealUses.contains(variableInMyRegister) || myRealDefs.contains(variableInMyRegister)) {
                    //one of the other important vars is currently occupying my register-- tempMap needed
                    String variableInMyTempReg = currentlyFilledRegs.getOrDefault(tempMap.get(use), "");
                    boolean alreadyLoadedAsDef = myRealDefs.contains(use);
                    if (!variableInMyTempReg.equals(use) && !alreadyLoadedAsDef) {
                        beforeInstruction.add(generateLoadInstruction(use, tempMap.get(use), lineNumber));
                        currentlyFilledRegs.put(tempMap.get(use), use);
                        varsThatUsedTempMap.add(use);
                    }
                    if (variableInMyTempReg.equals(use)) {
                        varsThatUsedTempMap.add(use);
                    }
                } else {
                    //if ("".equals(variableInMyRegister)) {
                        //just overwrite it
                        //beforeInstruction.add(generateLoadInstruction(use, virtualToPhysicalMap.get(use), lineNumber));
                    //} else {
                        //save and replace the use register
                        //beforeInstruction.add(generateStoreInstruction(variableInMyRegister, virtualToPhysicalMap.get(variableInMyRegister), lineNumber));
                        beforeInstruction.add(generateLoadInstruction(use, virtualToPhysicalMap.get(use), lineNumber));
                    //}
                }
            }
        }*/

        //create new instruction tokens
        ///////////////////////

        LinkedList<String> newTokens = new LinkedList<>();
        for (String oldToken : oldInstruction.getTokens()) {
            //if (varsThatUsedTempMap.contains(oldToken)) {
            //    newTokens.add(tempMap.get(oldToken));
            //} else {
            //if (oldInstruction.getTokens().size() != 2) {
                newTokens.add(virtualToPhysicalMap.getOrDefault(oldToken, oldToken));
            //} else {
            //    newTokens.add(oldToken);
            //}
            //}
        }

        newInstruction = new Instruction(newTokens, "", oldInstruction.getType(), lineNumber, oldInstruction.getSection());
        newInstruction.buildCodeFromTokens();

        ArrayList<Instruction> replacementInstructions = new ArrayList<>(beforeInstruction);
        //replacementInstructions.addAll(beforeInstruction);
        replacementInstructions.add(newInstruction);
        //replacementInstructions.addAll(afterInstruction);

        //loop through and set line numbers
        for (int i = 0; i < replacementInstructions.size(); i++) {
            replacementInstructions.get(i).setLineNum(lineNumber + i);
        }

        System.out.println(oldInstruction.getTokens() + " " + oldInstruction.getLineNum());
        System.out.println("BECAME:");
        for (Instruction each : replacementInstructions) {
            System.out.println(each.getTokens() + " " + each.getLineNum());
        }
        System.out.println("----------------------");

        return replacementInstructions;
    }

    private Instruction generateStoreInstruction(String varToStore, String physRegUsing, int lineNumber) {
        LinkedList<String> storeTokens = new LinkedList<>();
        storeTokens.add("sw");
        storeTokens.add(physRegUsing);
        //storeTokens.add("0");
        storeTokens.add(varToStore);
        //code string
        String storeString = "sw " + physRegUsing + ", " + varToStore;

        //Instruction myInstr = new
        return new Instruction(storeTokens, storeString, InstructionType.MIPS, lineNumber);
    }

    private Instruction generateLoadInstruction(String varToLoad, String physRegToUse, int lineNumber) {
        LinkedList<String> loadTokens = new LinkedList<>();
        loadTokens.add("lw");
        loadTokens.add(physRegToUse);
        //loadTokens.add("0");
        loadTokens.add(varToLoad);
        //code string
        String loadString = "lw " + physRegToUse + ", " + varToLoad;

        return new Instruction(loadTokens, loadString, InstructionType.MIPS, lineNumber);
    }

    private LinkedList<Instruction> generateNaiveAllocation(
            Instruction oldInstruction, Map<String, String> argMap, int lineNumber, ArrayList<String> arrayNames) {

        LinkedList<Instruction> totalReturnList = new LinkedList<>();

        //get readVars, writeVars, and type-check everything
        ArrayList<String> allVars = new ArrayList<>();
        ArrayList<String> readVars = oldInstruction.getUses();
        System.out.println("readVars: " + readVars);
        for (int i = 0; i < readVars.size(); i++) {
            String variable = readVars.get(i);
            try {
                float myFloat = Float.parseFloat(variable);
                readVars.remove(variable);
            } catch (NumberFormatException e) {
                if (!variable.contains("$")) {
                    allVars.add(variable);
                } else {
                    readVars.remove(variable);
                }
            }
        }
        ArrayList<String> writeVars = oldInstruction.getDefs();
        System.out.println("writeVars: " + writeVars);
        for (int i = 0; i < writeVars.size(); i++) {
            String variable = writeVars.get(i);
            try {
                float myFloat = Float.parseFloat(variable);
                writeVars.remove(variable);
            } catch (NumberFormatException e) {
                if (!variable.contains("$")) {
                    allVars.add(variable);
                } else {
                    readVars.remove(variable);
                }
            }
        }

        ArrayList<String> regsForUse = new ArrayList<>(Arrays.asList(tempRegs));
        regsForUse.addAll(Arrays.asList(savedRegs));
        regsForUse.addAll(Arrays.asList(specialSavedRegs));

        //assign each variable involved to a physical temp register
        Map<String, String> virtualToPhysical = new HashMap<>();
        for (int i = 0; i < allVars.size(); i++) {
            String variable = allVars.get(i);
            //if (!arrayNames.contains(variable)) {
                if (argMap.containsKey(variable)) {
                    virtualToPhysical.put(variable, argMap.get(variable));
                } else {
                    String register = regsForUse.get(i);
                    virtualToPhysical.put(variable, register);
                }
            //}
        }

        //creates new load instructions
        for (String readVar : readVars) {
            //System.out.println("using: " + readVar);
            if (virtualToPhysical.containsKey(readVar)) {
                if (!virtualToPhysical.get(readVar).contains("a")) {
                    totalReturnList.add(generateLoadInstruction(readVar, virtualToPhysical.get(readVar), lineNumber));
                    lineNumber++;
                }
            }
        }

        ///////create modified version of instruction

        //makes new token list
        LinkedList<String> newTokens = new LinkedList<>();
        for (String token : oldInstruction.getTokens()) {
            newTokens.add(virtualToPhysical.getOrDefault(token, token));
        }

        //actually creates the modified instruction and adds to list
        Instruction replacementInstr;
        if (oldInstruction.getTokens().get(0).equals("lw") || oldInstruction.getTokens().get(0).equals("sw")) {
           String newString = newTokens.get(0) + " " + newTokens.get(1) + ", " + newTokens.get(2) + "(" + newTokens.get(3) + ")";
            replacementInstr = new Instruction(newTokens, newString, oldInstruction.getType(), lineNumber, oldInstruction.getSection());
        } else {
            replacementInstr = new Instruction(newTokens, "", oldInstruction.getType(), lineNumber, oldInstruction.getSection());
            replacementInstr.buildCodeFromTokens();
        }
        lineNumber++;
        totalReturnList.add(replacementInstr);


        //creates new store instructions
        for (String writeVar : writeVars) {
            if (virtualToPhysical.containsKey(writeVar)) {
                //System.out.println("start");
                totalReturnList.add(generateStoreInstruction(writeVar, virtualToPhysical.get(writeVar), lineNumber));
                lineNumber++;
                //System.out.println("finish");
            }
        }

        System.out.println(oldInstruction.getTokens() + " " + oldInstruction.getLineNum());
        System.out.println("BECAME:");
        for (Instruction each : totalReturnList) {
            System.out.println(each.getTokens() + " " + each.getLineNum());
        }
        System.out.println("----------------------");

        //returns list of all new Instructions*/
        return totalReturnList;
    }

    public static ArrayList<String> getVirtualRegs(ArrayList<Instruction> instrStruct) {

        Set<String> myVirtualRegs = new HashSet<>();
        //ArrayList<Instruction> instrStruct = myFunc.getCodes();


        for (int i = 0; i < instrStruct.size(); i++) {
            ArrayList<String> defs;
            ArrayList<String> uses = new ArrayList<>();
            Instruction theInstr = instrStruct.get(i);
            if (theInstr.getLineNum() == 0) {
                defs = theInstr.getParameterNames();
            } else if (theInstr.getLineNum() == 1) {
                defs = cleanUpList(theInstr.getIntList());
            } else if (theInstr.getLineNum() == 2) {
                defs = cleanUpList(theInstr.getFloatList());
            } else {
                defs = theInstr.getDefs();
                uses = theInstr.getUses();
            }

            //System.out.println(theInstr.getTokens());
            //System.out.println(defs);
            //System.out.println(uses);
            for (int j = 0; j < defs.size(); j++) {
                String variable = defs.get(j);
                try {
                    float myFloat = Float.parseFloat(variable);
                } catch (NumberFormatException e) {
                    if (!variable.contains("$") && !variable.contains("'")) {
                        myVirtualRegs.add(variable);
                    }
                }
            }
            for (int j = 0; j < uses.size(); j++) {
                String variable = uses.get(j);
                try {
                    float myFloat = Float.parseFloat(variable);
                } catch (NumberFormatException e) {
                    if (!variable.contains("$") && !variable.contains("'")) {
                        myVirtualRegs.add(variable);
                    }
                }
            }
        }

        ArrayList<String> cleanedUpRegs = new ArrayList<>(myVirtualRegs);
        for (String variable : myVirtualRegs) {
            try {
                float myFloat = Float.parseFloat(variable);
                cleanedUpRegs.remove(variable);
            } catch (NumberFormatException e) {
                if (variable.contains("$") || variable.contains("'")) {
                    cleanedUpRegs.remove(variable);
                }
            }
        }
        //System.out.println("CLEANED UP REGS " + cleanedUpRegs);

        //System.out.println(cleanedUpRegs);
        return cleanedUpRegs;

    }

    private boolean inArray(String word, String[] theArray) {
        for (String checked : theArray) {
            if (checked.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public Program getMyProgram() {
        return myProgram;
    }

    public static ArrayList<String> cleanUpList(ArrayList<String> oldList) {
        ArrayList<String> newList = new ArrayList<>();
        for (String each : oldList) {
            if (each.contains("[")) {
                newList.add(each.split("\\[")[0]);
            } else {
                newList.add(each);
            }
        }
        return newList;
    }
}
