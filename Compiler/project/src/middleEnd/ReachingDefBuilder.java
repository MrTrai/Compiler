package middleEnd;

import graph.BasicBlock;
import graph.CFG;

import java.util.*;

public class ReachingDefBuilder {

    private CFG myCFG;
    private HashMap<String, HashMap<Integer, ArrayList<Integer>>> varDefUseMap;
    private int intListLine;
    private int floatListLine;
    private HashSet<String> paramSet;
    private HashMap<String, String> typeMap;
    private HashSet<Integer> returnSet = new HashSet<>();

    public ReachingDefBuilder(CFG theCFG) {
        myCFG = theCFG;
        intListLine = -1;
        floatListLine = -1;
        paramSet = new HashSet<>();
        varDefUseMap = new HashMap<>();
        typeMap = new HashMap<>();

        buildTheMap();
    }

    private void buildTheMap() {
        HashMap<String, ArrayList<Integer>> rootKickstart = new HashMap<>();
        modifyMap(myCFG.getRoot(), rootKickstart);
        generateInitializerLists();
        /*for (int i = 0; i < myCFG.getCodes().size(); i++) {
            System.out.println(i + " : " + getReachingDefs(i));
        }*/
    }

    private void modifyMap(BasicBlock root, HashMap<String, ArrayList<Integer>> parentOutMap) {
        if (!(BasicBlock.firstContainsSecond(root.getInMap(), parentOutMap)) || (root.getInMap().isEmpty() && parentOutMap.isEmpty())) {
            root.setInMap(parentOutMap);
            ArrayList<LinkedList<String>> codes = root.getCodes();
            //add to varDefUseMap
            int lineCounter = 0;
            for (LinkedList<String> line : codes) {
                //parse for use and defs and add to appropriate maps
                addDefs(root, line, lineCounter);
                lineCounter += 1;
            }
            //modify root most recently used def
            LinkedList<BasicBlock> children = new LinkedList<>();
            children.addAll(root.getSuccessors());
            while (children.size() != 0) {
                BasicBlock child = children.remove();
                if (child.equals(root)) {
                    //System.out.println("IM MY OWN DADDY");
                }
                if (child.getPredecessors().size() == 1) {
                    child.setMostRecentDefMap((root.getMostRecentDefMap()));
                    modifyMap(child, root.getMostRecentDefMap());
                } else if (child.getPredecessors().size() > 1) {
                    child.setMostRecentDefMap(BasicBlock.mergeMostRecentDefs(child.getPredecessors()));
                    modifyMap(child, BasicBlock.mergeMostRecentDefs(child.getPredecessors()));
                }
            }
        }
    }

    private void addDefs(BasicBlock theBlock, LinkedList<String> theLine, int counter) {
        String instruction = theLine.get(0);
        String defVar = null;
        ArrayList<String> useVars = new ArrayList<>();
        //add mappings for parameters
        if (instruction.contains("(")) {
            //System.out.println(instruction);
            String parameters = instruction.substring(instruction.indexOf('(') + 1, instruction.indexOf(')'));
            String[] parametersSplit = parameters.split("\\s|,");
            String tempType = "";
            for (int i = 0; i < parametersSplit.length; i++) {
                if (i % 2 == 1) {
                    defVar = parametersSplit[i];
                    typeMap.put(defVar, tempType);
                    paramSet.add(defVar);
                    ArrayList<Integer> addLineList = new ArrayList<>();
                    addLineList.add(theBlock.getId() + counter);
                    theBlock.addToMostRecentDefMap(defVar, addLineList);
                    defVar = null;
                } else {
                    tempType = parametersSplit[i].replaceAll("\\s", "");
                }
            }
            //add mappings for initializers
        } else if (instruction.equals("int-list") || instruction.equals("float-list")) {
            if (instruction.equals("int-list")) {
                intListLine = theBlock.getId() + counter;
            } else {
                floatListLine = theBlock.getId() + counter;
            }
            for (int i = 1; i < theLine.size(); i++) {
                defVar = theLine.get(i);
                if (instruction.equals("int-list")) {
                    typeMap.put(defVar, "int");
                } else {
                    typeMap.put(defVar, "float");
                }
                ArrayList<Integer> addLineList = new ArrayList<>();
                addLineList.add(theBlock.getId() + counter);
                theBlock.addToMostRecentDefMap(defVar, addLineList);
                defVar = null;
            }
        } else {
            DefUseGroup myGrouping = makeGrouping(theLine);
            defVar = myGrouping.getDefVar();
            useVars = myGrouping.getUseVars();
            //System.out.println("line: " + (theBlock.getLineNum() + counter) + ", defVar: " + defVar + ", useVars: " + useVars);
        }

        if (defVar != null) {
            ArrayList<Integer> newDefLines = new ArrayList<>();
            if (useVars.contains(defVar)) {
                ArrayList<Integer> previousDefs = theBlock.getMostRecentDefMap().get(defVar);
                if (previousDefs != null) {
                    newDefLines.addAll(theBlock.getMostRecentDefMap().get(defVar));
                }
            }
            newDefLines.add(theBlock.getId() + counter);
            theBlock.addToMostRecentDefMap(defVar, newDefLines);
        }

        for (String useVar : useVars) {
            try {
                //figures out if variable or literal value
                Float.valueOf(useVar);
               // System.out.println("Float value of: " + useVar + " = " + Float.valueOf(useVar));
            } catch (NumberFormatException e) {
                //variable

                HashMap<String, ArrayList<Integer>> blockMap = theBlock.getMostRecentDefMap();
                ArrayList<Integer> defListOfVar = blockMap.get(useVar);
                addToMap(useVar, theBlock.getId() + counter, defListOfVar);
            }
        }
    }

    private void addToMap(String varName, int useLine, ArrayList<Integer> newDefList) {
        if (!varDefUseMap.containsKey(varName)) {
            varDefUseMap.put(varName, new HashMap<>());
        }
        HashMap<Integer, ArrayList<Integer>> innerMap = varDefUseMap.get(varName);
        innerMap.put(useLine, newDefList);
        varDefUseMap.put(varName, innerMap);
    }

    public void displayMap() {
        ArrayList<String> keyList = new ArrayList<>();
        keyList.addAll(varDefUseMap.keySet());
        for (String variable : keyList) {
            System.out.println(variable);
            System.out.println(varDefUseMap.get(variable));
        }
        System.out.println(varDefUseMap);
    }

    public ArrayList<Integer> getReachingDefs(int lineNumber) {
        returnSet = new HashSet<>();
        ArrayList<Integer> returnArray = new ArrayList<>();
        HashSet<Integer> myReturnSet = getAllReachingDefs(lineNumber);
        //returnSet.remove(lineNumber);
        returnArray.addAll(myReturnSet);
        Collections.sort(returnArray);
        return (returnArray);
    }

    private HashSet<Integer> getAllReachingDefs(int lineNumber) {
        LinkedList<String> instruction = myCFG.getCodeByLineNum(lineNumber);
        DefUseGroup tempGrouping = makeGrouping(instruction);
        //def, uses, current line number
        ArrayList<String> useVars = tempGrouping.getUseVars();
        for (String useVar : useVars) {
            HashMap<Integer, ArrayList<Integer>> temp = varDefUseMap.get(useVar);
            if (temp != null) {
                ArrayList<Integer> importantDefs = temp.get(lineNumber);
                if (importantDefs != null) {
                    //System.out.println("LINE:" + lineNumber + " DEFS: " + importantDefs);
                    for (Integer definition : importantDefs) {

                        if (!returnSet.contains(definition)) {
                            returnSet.add(definition);
                            returnSet.addAll(getAllReachingDefs(definition));
                            //System.out.println("RETURN SET: " + returnSet);
                        }
                    }
                }
            }
        }
        //returnSet.add(lineNumber);
        return returnSet;
    }

    private DefUseGroup makeGrouping(LinkedList<String> theLine) {
        int compIndex = 0;
        String instruction = theLine.get(compIndex);
        compIndex += 1;
        String defVar = null;
        ArrayList<String> useVars = new ArrayList<>();
        if (instruction.equals("assign")) {
            //WHEN INSTRUCTION == ASSIGN
            defVar = theLine.get(compIndex);
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
        } else if (IR.inStrArray(IR.binaryOps, instruction)) {
            //WHEN INSTRUCTION == BINARY OPERATIONS
            defVar = theLine.get(compIndex);
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
        } else if (instruction.substring(0, 1).equals("b") && !instruction.contains(":")) {
            //WHEN INSTRUCTION == BRANCH
            //useVars.add(theLine.get(compIndex));
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
        } else if (instruction.equals("return")) {
            //WHEN INSTRUCTION == RETURN
            useVars.add(theLine.get(compIndex));
        } else if (instruction.contains("call")) {
            //WHEN CALLING A FUNCTION
            if (instruction.equals("callr")) {
                defVar = theLine.get(compIndex);
                compIndex += 2;
            } else {
                compIndex += 1;
            }
            while (compIndex < theLine.size()) {
                useVars.add(theLine.get(compIndex));
                compIndex += 1;
            }
        } else if (instruction.equals("array_store")) {
            //ARRAY STORING
            useVars.add(theLine.get(compIndex));
        } else if (instruction.equals("array_load")) {
            //ARRAY LOADING
            defVar = theLine.get(compIndex);
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
            compIndex += 1;
            useVars.add(theLine.get(compIndex));
        }

        ArrayList<String> actualUseVars = new ArrayList<>();
        for (String useVar : useVars) {
            try {
                //figures out if variable or literal value
                Float.valueOf(useVar);
            } catch (NumberFormatException e) {
                //variable
                actualUseVars.add(useVar);
            }
        }
        return (new DefUseGroup(defVar, actualUseVars));
    }

    private static BasicBlock getCommonElement(Iterable<BasicBlock> first, Iterable<BasicBlock> second) {
        for (BasicBlock each : first) {
            for (BasicBlock other : second) {
                if (each.equals(other)) {
                    return each;
                }
            }
        }
        return null;
    }

    private void generateInitializerLists() {
        ArrayList<LinkedList<String>> allCodes = myCFG.getCodes();
        for (int i = 0; i < allCodes.size(); i++) {
            LinkedList<String> line = allCodes.get(i);
            if (line.contains("int-list")) {
                intListLine = i;
            } else if (line.contains("float-list")) {
                floatListLine = i;
            } else if (!line.get(0).contains("(")) {
                if (IR.isAssigner(line)) {
                    String destVar = line.get(1);
                    if (destVar.contains("temp")) {
                        LinkedList<String> nextLine = allCodes.get(i + 1);
                        String realVar = nextLine.get(1);
                        typeMap.put(destVar, typeMap.get(realVar));
                    }
                }
            }
        }
        for (String varName : typeMap.keySet()) {
            String varType = typeMap.get(varName);
            int listLine = -1;
            if (varType.equals("float")) {
                listLine = floatListLine;
            } else if (varType.equals("int")) {
                listLine = intListLine;
            }
            if (listLine != -1) {
                LinkedList<String> lineOfCode = allCodes.get(listLine);
                if (!lineOfCode.contains(varName) && !paramSet.contains(varName)) {
                    allCodes.get(listLine).add(varName);
                }
            }
        }

    }
}
