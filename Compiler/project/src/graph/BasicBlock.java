package graph;

import backEnd.Instruction;

import java.util.*;


public class BasicBlock {
    private int id;
    private ArrayList<LinkedList<String>> codes;
    private ArrayList<BasicBlock> predecessors;
    private ArrayList<BasicBlock> successors;
    private HashMap<String, ArrayList<Integer>> mostRecentDefMap;
    private HashMap<String, ArrayList<Integer>> inMap;
    private static ArrayList<BasicBlock> visitedAncestors = new ArrayList<>();

    //back end stuff
    private Map<String, String> myRegisterAllocation = new HashMap<>();
    private ArrayList<String> spilledRegs = new ArrayList<>();
    private ArrayList<Instruction> myInstructions = new ArrayList<>();

    public BasicBlock(int _id) {
        id = _id;
        codes = new ArrayList<>();
        predecessors = new ArrayList<>();
        successors = new ArrayList<>();
        mostRecentDefMap = new HashMap<>();
        inMap = new HashMap<>();
    }


    public void setId(int _id) {
        this.id = _id;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<BasicBlock> getPredecessors() {
        return predecessors;
    }

    public ArrayList<BasicBlock> getSuccessors() {
        return successors;
    }

    public HashMap<String, ArrayList<Integer>> getInMap() {
        return inMap;
    }

    public void setInMap(HashMap<String, ArrayList<Integer>> newInMap) {
        inMap = newInMap;
    }

    public ArrayList<BasicBlock> getAncestors() {
        visitedAncestors = new ArrayList<>();
        return getAncestorsHelper();
    }
    private ArrayList<BasicBlock> getAncestorsHelper() {
        ArrayList<BasicBlock> parents = getPredecessors();
        ArrayList<BasicBlock> finalResult = new ArrayList<>();
        if (parents.size() != 0) {
            for (BasicBlock parent : parents) {
                if (!visitedAncestors.contains(parent)) {
                    visitedAncestors.add(parent);
                    finalResult.addAll(parent.getAncestorsHelper());
                }
            }
        }
        finalResult.add(this);
        return finalResult;
    }

    public void addSuccessor(BasicBlock block) {
        boolean exist = false;
        for (BasicBlock eachBlk : successors) {
            if (eachBlk.getId() == block.getId())
                exist = true;
        }
        if (!exist)
            successors.add(block);
    }

    public void addPredecessor(BasicBlock block) {
        boolean exist = false;
        for (BasicBlock eachBlk : predecessors) {
            if (eachBlk.getId() == block.getId())
                exist = true;
        }
        if (!exist)
            predecessors.add(block);
    }

    public ArrayList<LinkedList<String>> getCodes() {
        return codes;
    }

    public void setMostRecentDefMap(HashMap<String, ArrayList<Integer>> incoming) {
        mostRecentDefMap = incoming;
    }


    public HashMap<String, ArrayList<Integer>> getMostRecentDefMap() {
        return mostRecentDefMap;
    }

    public static boolean mapsAreSame(HashMap<String, ArrayList<Integer>> first,
                                      HashMap<String, ArrayList<Integer>> second) {
        if (first == null || second == null) {
            if (first == null && second == null) {
                return true;
            } else {
                return false;
            }
        }
        if (first.size() != second.size()) {
            return false;
        }
        for (String varName : first.keySet()) {
            if (!second.containsKey(varName)) {
                return false;
            }
            for (Integer value : first.get(varName)) {
                if (!second.get(varName).contains(value)) {
                    return false;
                }
            }
        }
        for (String varName : second.keySet()) {
            if (!first.containsKey(varName)) {
                return false;
            }
            for (Integer value : second.get(varName)) {
                if (!first.get(varName).contains(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean firstContainsSecond(HashMap<String, ArrayList<Integer>> first,
                                      HashMap<String, ArrayList<Integer>> second) {
        if (first == null) {
            return (second == null);
        }
        if (first.size() < second.size()) {
            return false;
        }
        for (String varName : second.keySet()) {
            if (!first.containsKey(varName)) {
                return false;
            }
            for (Integer value : second.get(varName)) {
                if (!first.get(varName).contains(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addToMostRecentDefMap(String variable, ArrayList<Integer> lineNums) {
        ArrayList<Integer> myArray = new ArrayList<>();
        /*if (!mostRecentDefMap.containsKey(variable)) {
            myArray = new ArrayList<>();
        } else {
            myArray = mostRecentDefMap.get(variable);
        }*/
        myArray.addAll(lineNums);
        mostRecentDefMap.put(variable, myArray);
    }

    public void setCodes(ArrayList<LinkedList<String>> codes) {
        this.codes = codes;
    }

    public static HashMap<String, ArrayList<Integer>> mergeMostRecentDefs(ArrayList<BasicBlock> blockList) {
        HashMap<String, ArrayList<Integer>> finalMap = new HashMap<>();
        HashSet<String> variableNames = new HashSet<>();
        for (BasicBlock block : blockList) {
            variableNames.addAll(block.getMostRecentDefMap().keySet());
        }
        for (String variable : variableNames) {
            HashSet<Integer> defSet = new HashSet<>();
            for (BasicBlock block : blockList) {
                if (block.getMostRecentDefMap().containsKey(variable)) {
                    defSet.addAll(block.getMostRecentDefMap().get(variable));
                }
            }
            ArrayList<Integer> defArray = new ArrayList<>(defSet);
            finalMap.put(variable, defArray);
        }
        return finalMap;
    }

    public void setMyRegisterAllocation(Map<String, String> myRegisterAllocation) {
        this.myRegisterAllocation = myRegisterAllocation;
    }

    public void setSpilledRegs(ArrayList<String> spilledRegs) {
        this.spilledRegs = spilledRegs;
    }

    public void setMyInstructions(ArrayList<Instruction> newInstructions) {
        myInstructions = newInstructions;
    }

    public Map<String, String> getMyRegisterAllocation() {
        return myRegisterAllocation;
    }

    public ArrayList<String> getSpilledRegs() {
        return spilledRegs;
    }

    public ArrayList<Instruction> getInstructions() {
        return myInstructions;
    }
}
