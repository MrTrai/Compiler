package middleEnd;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class IR {

    private ArrayList<LinkedList<String>> codeStructure;
    private String code;
    public static String[] assignmentInstr = {"assign", "add", "sub", "mult", "div", "and", "or", "callr", "array_load"};
    public static String[] binaryOps = {"add", "sub", "mult", "div", "and", "or"};
    public static String[] allInstr = {"assign", "add", "sub", "mult", "div", "and", "or", "goto", "breq", "brneq", "brlt",
                                "brgt", "brgeq", "brleq", "return", "call", "callr", "array_store", "array_load"};
    public static String[] initializers = {"int-list:", "float-list:"};


    public IR(String filename) throws IOException {
        codeStructure = buildStructure(extractContent(filename));
        code = updateString(codeStructure);
    }

    public String toString() {
        return code;
    }

    public void setString(String newString) {
        code = newString;
        codeStructure = buildStructure(code);
    }

    public ArrayList<LinkedList<String>> getStructure() {
        return codeStructure;
    }

    public void setCodeStructure(ArrayList<LinkedList<String>> newStruct) {
        codeStructure = newStruct;
        code = updateString(codeStructure);
    }

    public LinkedBlockingQueue<ArrayList<LinkedList<String>>> getCodeQueue() {
        LinkedBlockingQueue<ArrayList<LinkedList<String>>> myQueue = new LinkedBlockingQueue<>();
        ArrayList<LinkedList<String>> miniStructure = new ArrayList<>();
        for (LinkedList<String> list : codeStructure) {
            String head = list.get(0);
            if (head.contains(" ")) {
                if (miniStructure.size() != 0) {
                    myQueue.add(miniStructure);
                    //System.out.println(miniStructure);
                    //System.out.println("----------------------");
                    miniStructure = new ArrayList<>();
                }
                miniStructure.add(list);
            } else {
                miniStructure.add(list);
            }
        }
        //System.out.println(miniStructure);
        //System.out.println("----------------------");
        myQueue.add(miniStructure);
        return myQueue;
    }

    private String extractContent(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("#start_function\n" +
                "void init_arr(int[] a,  int size,  int val):\n" +
                "int-list: i, t\n" +
                "float-list:\n" +
                "    assign, i, 0\n" +
                "    loop:\n" +
                "        array_store, val, a, i\n" +
                "        add, i, i, 1\n" +
                "        brlt, loop, i, size\n" +
                "#end_function");
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append("\n");
        }
        reader.close();
        return content.toString();
    }

    private ArrayList<LinkedList<String>> buildStructure(String theString) {
        ArrayList<LinkedList<String>> newStructure = new ArrayList<>();
        String[] stringArray = theString.split("\n");
        //CLEANING UP FIRST EDITION OF STRUCTURE
        for (String line : stringArray) {
            LinkedList<String> myList = new LinkedList<>();
            if (!line.contains("(")) {
                line = line.replaceAll("\\s","");
            }
            boolean removable = (line.length() >= 1);

            removable = removable && (line.substring(0, 1).equals("#") || (line.substring(0, 1).equals("\n")));
            removable = removable || line.length() == 0;
            //removable = removable || (inStrArray(initializers, line));
            if (!removable) {
                int length = line.length();
                String[] components;
                if (line.contains("int-list") || line.contains("float-list")) {
                    components = line.split(",|\\:");

                } else {
                    components = line.split(",|\\):|\\(");
                }/*
                for (String part : components) {
                    System.out.println(part);
                }*/
                Collections.addAll(myList, components);
                newStructure.add(myList);
            }
        }
        return newStructure;
        /*
        //ACTUAL TEMPORARY ASSIGNMENT INJECTIONS
        int tempNumber = 0;
        ArrayList<LinkedList<String>> finalStructure = new ArrayList<>();
        for (LinkedList<String> list : newStructure) {
            //checking for var initialization
            if (isInitializer(list)) {
                String header = list.remove(); //removes initialization header
                while(list.size() != 0) {
                    String variable = list.remove();
                    LinkedList<String> tempList = new LinkedList<>();
                    tempList.add("assign");
                    tempList.add(variable);
                    //tempList.add("temp" + tempNumber);
                    if (header.contains("f")) {
                        tempList.add("0.");
                    } else {
                        tempList.add("0");
                    }
                    //LinkedList<String> tempList2 = new LinkedList<>();
                    //tempList2.add("assign");
                    //tempList2.add(variable);
                    //tempList2.add("temp" + tempNumber);
                    //tempNumber += 1;
                    finalStructure.add(tempList);
                    //finalStructure.add(tempList2);
                }
             if (isAssigner(list)){
                String theInstruction = list.remove();
                String destination = list.remove();
                LinkedList<String> tempList = new LinkedList<>();
                tempList.add(theInstruction);
                tempList.add("temp" + tempNumber);
                while (list.size() != 0) {
                    String parameter = list.remove();
                    tempList.add(parameter);
                }
                LinkedList<String> tempList2 = new LinkedList<>();
                tempList2.add("assign");
                tempList2.add(destination);
                tempList2.add("temp" + tempNumber);
                tempNumber += 1;
                finalStructure.add(tempList);
                finalStructure.add(tempList2);
            } else {
                finalStructure.add(list);
            }
        }
        //System.out.println(finalStructure);
        return finalStructure;
        */
    }

    private String updateString(ArrayList<LinkedList<String>> newStruct) {
        StringBuilder newCode = new StringBuilder();
        for (LinkedList<String> myList : newStruct) {
            StringBuilder listMerged = new StringBuilder();
            for (String component : myList) {
                listMerged.append(component);
                listMerged.append(",");
            }
            listMerged.deleteCharAt(listMerged.length() - 1);
            listMerged.append("\n");
            newCode.append(listMerged.toString());
        }
        return newCode.toString();
    }

    public static boolean inStrArray(String[] myArray, String value) {
        for (String each : myArray) {
            if (each.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInitializer(LinkedList<String> myList) {
        String instruction = myList.get(0);
        return (!inStrArray(allInstr, instruction) && !instruction.contains("(") && myList.size() > 1);
    }

    public static boolean isAssigner(LinkedList<String> myList) {
        String instruction = myList.get(0);
        if (instruction.equals("assign") && myList.size() == 4) {
            return false;
        } else {
            return (inStrArray(assignmentInstr, instruction));
        }
    }
}