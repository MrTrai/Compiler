package backEnd;

import java.util.ArrayList;
import java.util.LinkedList;

public class MockData {

    private ArrayList<Instruction> mockData;

    public MockData() {
        mockData = new ArrayList<>();
        LinkedList<String> myList;
        String myString;
        Instruction myInstruction;

        myString = "int fib(int n):";
        myList = new LinkedList<>();
        myList.add("int");
        myList.add("fib");
        myList.add("int");
        myList.add("n");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 0);
        mockData.add(myInstruction);

        myString = "int-list: t1, t2, x, r";
        myList = new LinkedList<>();
        myList.add("int_list:");
        myList.add("t1");
        myList.add("t2");
        myList.add("x");
        myList.add("r");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 1);
        mockData.add(myInstruction);

        myString = "float_list:";
        myList = new LinkedList<>();
        myList.add("float_list:");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 2);
        mockData.add(myInstruction);

        myString = "bne $at, $0, if_label0";
        myList = new LinkedList<>();
        myList.add("bne");
        myList.add("$at");
        myList.add("$0");
        myList.add("if_label0");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 3);
        mockData.add(myInstruction);

        myString = "add r, n, $0";
        myList = new LinkedList<>();
        myList.add("add");
        myList.add("r");
        myList.add("n");
        myList.add("$0");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 4);
        mockData.add(myInstruction);

        myString = "j end";
        myList = new LinkedList<>();
        myList.add("j");
        myList.add("end");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 5);
        mockData.add(myInstruction);

        myString = "if_label0:";
        myList = new LinkedList<>();
        myList.add("if_label0:");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 6);
        mockData.add(myInstruction);

        myString = "sub n, n, 1";
        myList = new LinkedList<>();
        myList.add("sub");
        myList.add("n");
        myList.add("n");
        myList.add("1");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 7);
        mockData.add(myInstruction);

        myString = "callr, t1, fib, n";
        myList = new LinkedList<>();
        myList.add("callr");
        myList.add("t1");
        myList.add("fib");
        myList.add("n");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 8);
        mockData.add(myInstruction);

        myString = "sub x, n, 1";
        myList = new LinkedList<>();
        myList.add("sub");
        myList.add("x");
        myList.add("n");
        myList.add("1");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 9);
        mockData.add(myInstruction);

        myString = "callr, t2, fib, x";
        myList = new LinkedList<>();
        myList.add("callr");
        myList.add("t2");
        myList.add("fib");
        myList.add("x");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 10);
        mockData.add(myInstruction);

        myString = "add r, t1, t2";
        myList = new LinkedList<>();
        myList.add("add");
        myList.add("r");
        myList.add("t1");
        myList.add("t2");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 11);
        mockData.add(myInstruction);

        myString = "end:";
        myList = new LinkedList<>();
        myList.add("end:");
        myInstruction = new Instruction(myList, myString, InstructionType.MIPS, 12);
        mockData.add(myInstruction);

        myString = "return, r";
        myList = new LinkedList<>();
        myList.add("return");
        myList.add("r");
        myInstruction = new Instruction(myList, myString, InstructionType.TIGER, 13);
        mockData.add(myInstruction);
    }

    public ArrayList<Instruction> getInstructions() {
        return mockData;
    }
}
