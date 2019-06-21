package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class TigerCFG extends CFG {
    public TigerCFG(ArrayList<LinkedList<String>> _codes) {
        super(_codes);
    }

    public TigerCFG(ArrayList<LinkedList<String>> _codes, int firstLine) {
        super(_codes, firstLine);
    }

    @Override
    public BasicBlock buildCFG() {
        blocks = initBasicBlocks();
        LinkedList<String> currLine;
        BasicBlock currBlock = null;
        BasicBlock block;
        for (int i = FIRST_LINE_POSITION; i < codes.size(); i++) {
            currLine = codes.get(i);
            if (isLeader(i)) {
                block = getBlockById(i);
                if (currBlock != null && block != null) {
                    currBlock.addSuccessor(block);
                    block.addPredecessor(currBlock);
                }
                currBlock = block;
            }
            if (this.isGoTo(currLine)) {
                currBlock.getCodes().add(currLine);
                block = getBlockById(
                        getLineNumOfBranchGotoTarget(i)
                );
                if (block != null && currBlock != null) {
                    currBlock.addSuccessor(block);
                    block.addPredecessor(currBlock);
                }

            } else if (this.isBranch(currLine)) {
                currBlock.getCodes().add(currLine);

                block = getBlockById(
                        getLineNumOfBranchGotoTarget(i)
                );
                if (block != null && currBlock != null) {
                    currBlock.addSuccessor(block);
                    block.addPredecessor(currBlock);
                }

                block = getBlockById(i + 1); // branch successor line
                if (block != null && currBlock != null) {
                    currBlock.addSuccessor(block);
                    block.addPredecessor(currBlock);
                }

            } else {
                currBlock.getCodes().add(currLine);
            }
        }

        return getBlockById(FIRST_LINE_POSITION); // first block (Root)
    }

    @Override
    public boolean isLabel(LinkedList<String> line) {
        return line.getFirst().contains(":");
    }

    @Override
    public boolean isGoTo(LinkedList<String> line) {
        return line.getFirst().equals("goto");
    }

    @Override
    public boolean isBranch(LinkedList<String> line) {
        String opName = line.getFirst();
        ArrayList<String> ops = new ArrayList<>(Arrays.asList(
                "breq", "brneq", "brlt", "brgt", "brgeq", "brleq"
        ));
        return ops.contains(opName);
    }

    @Override
    public boolean isLeader(int lineNum) {
        if (lineNum == FIRST_LINE_POSITION)
            return true;
        LinkedList<String> currLine = codes.get(lineNum);
        LinkedList<String> prevLine = codes.get(lineNum - 1);

        boolean isGotoBranchTarget = false;
        boolean isSuccessorOfBranch = isBranch(prevLine);

        if (isLabel(currLine)) {
            String label = currLine.getFirst();
            label = label.substring(0, label.length() - 1);
            if (belongToGotoBranch(label)) {
                isGotoBranchTarget = true;
            }
        }

        return isGotoBranchTarget || isSuccessorOfBranch;
    }

    @Override
    public int getLineNumOfBranchGotoTarget(int currLineNum) {
        LinkedList<String> line = codes.get(currLineNum);
        int targetLabelLineNum = -1;
        String labelTarget = line.get(1);
        for (int i = FIRST_LINE_POSITION; i < codes.size(); i++) {
            if (i != currLineNum) {
                LinkedList<String> eachLine = codes.get(i);
                if (isLabel(eachLine)) {
                    String label = eachLine.get(0);
                    if (label.substring(0, label.length() - 1).equals(labelTarget)) {
                        targetLabelLineNum = i;
                    }
                }
            }
        }
        return targetLabelLineNum;
    }

    @Override
    public boolean belongToGotoBranch(String label) {
        for (LinkedList<String> line : codes) {
            if (isBranch(line) || isGoTo(line)) {
                String target = line.get(1);
                if (label.equals(target)) {
                    return true;
                }
            }
        }
        return false;
    }
}
