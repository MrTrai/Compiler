package graph;

import backEnd.Instruction;
import backEnd.InstructionType;

import java.util.*;

public class LiveSetGenerator {

    private ArrayList<Instruction> myInstructions;
    private Map<String, Integer> firstLineLiveMap;
    private Map<String, Integer> lastLineLiveMap;
    private ArrayList<String> myArrayNames;

    public LiveSetGenerator(ArrayList<Instruction> _instructions, ArrayList<String> arrayNames) {
        myInstructions = _instructions;
        firstLineLiveMap = new HashMap<>();
        lastLineLiveMap = new HashMap<>();
        myArrayNames = arrayNames;
        generateLiveSets();

        for (String var : firstLineLiveMap.keySet()) {
            System.out.println(var + ": " + firstLineLiveMap.get(var) + ", " + lastLineLiveMap.get(var));
        }
    }

    private void generateLiveSets() {
        int lineCounter = 0;
        for (Instruction tempInstruction : myInstructions) {
            //System.out.println(tempInstruction.getTokens() + " " + tempInstruction.getLineNum());
            if (tempInstruction.getLineNum() == 0) {
                ArrayList<String> args = tempInstruction.getParameterNames();
                for (String arg : args) {
                    //System.out.println("ARG: " + arg);
                    firstLineLiveMap.put(arg, lineCounter + 1);
                    lastLineLiveMap.put(arg, lineCounter + 1);
                }
            } else if ((tempInstruction.getLineNum() >= 3 || tempInstruction.getLineNum() < 0)
                    && !tempInstruction.getTokens().get(0).contains(":")) {
                //System.out.println(tempInstruction.getTokens());
                Set<String> definedVars = typeCheckVirtualRegs(tempInstruction.getDefs());
                Set<String> usedVars = typeCheckVirtualRegs((tempInstruction.getUses()));
                for (String defVar : definedVars) {
                    //System.out.println("DEF: " + defVar);
                    if (!firstLineLiveMap.containsKey(defVar)) {
                        firstLineLiveMap.put(defVar, lineCounter + 1);
                        lastLineLiveMap.put(defVar, lineCounter + 1);
                    }
                }
                for (String useVar : usedVars) {
                    if (!myArrayNames.contains(useVar)) {
                        //System.out.println("USE: " + useVar);
                        //System.out.println(tempInstruction.getTokens().get(0));
                        if (!firstLineLiveMap.containsKey(useVar)) {
                            firstLineLiveMap.put(useVar, lineCounter);
                            lastLineLiveMap.put(useVar, lineCounter);
                        }
                        if (lineCounter > lastLineLiveMap.get(useVar)) {
                            lastLineLiveMap.put(useVar, lineCounter);
                        }
                    }
                }
            } else if (tempInstruction.getLineNum() == 1) {
                ArrayList<String> declares = tempInstruction.getIntList();
                for (String var : declares) {
                    firstLineLiveMap.put(var, lineCounter + 1);
                    lastLineLiveMap.put(var, lineCounter + 1);
                }
            } else {
                ArrayList<String> declares = tempInstruction.getFloatList();
                for (String var : declares) {
                    firstLineLiveMap.put(var, lineCounter + 1);
                    lastLineLiveMap.put(var, lineCounter + 1);
                }
            }
            lineCounter++;
        }
    }


    private Set<String> typeCheckVirtualRegs(ArrayList<String> currentList) {
        Set<String> tempList = new HashSet<>(currentList);
        for (int i = 0; i < currentList.size(); i++) {
            String variable = currentList.get(i);
            try {
                float myFloat = Float.parseFloat(variable);
                tempList.remove(variable);
            } catch (NumberFormatException e) {
                if (variable.contains("$") || variable.contains("'")) {
                    tempList.remove(variable);
                }
            }
        }
        return tempList;
    }

    public Map<String, Integer> getFirstLineLiveMap() {
        return firstLineLiveMap;
    }

    public Map<String, Integer> getLastLineLiveMap() {
        return lastLineLiveMap;
    }
}
