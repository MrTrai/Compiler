package graph;

import middleEnd.ReachingDefBuilder;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class CFG {
    protected static int FIRST_LINE_POSITION = 0;
    protected BasicBlock root;
    protected ArrayList<BasicBlock> blocks;
    protected ArrayList<LinkedList<String>> codes;
    protected ReachingDefBuilder reachDef;

    public abstract BasicBlock buildCFG();
    public abstract boolean isBranch(LinkedList<String> line);
    public abstract boolean isGoTo(LinkedList<String> line);
    public abstract boolean isLeader(int lineNum);
    public abstract boolean belongToGotoBranch(String label);
    public abstract boolean isLabel(LinkedList<String> line);


    public CFG(ArrayList<LinkedList<String>> _codes, int firstLine) {
        FIRST_LINE_POSITION = firstLine;
        codes = _codes;
        root = this.buildCFG();
        sortBlocks("asc");
        reachDef = new ReachingDefBuilder(this);
    }

    public CFG(ArrayList<LinkedList<String>> _codes) {
        codes = _codes;
        root = this.buildCFG();
        sortBlocks("asc");
        reachDef = new ReachingDefBuilder(this);
    }

    public void print() {
        printCodes();
        printBlocks();
    }

    public void printCodes() {
        System.out.println("codes");
        for (int i = 0; i < codes.size(); i++) {
            System.out.print("Line " + i + " ");
            System.out.println(codes.get(i));
        }
    }

    private void printBlocks() {
        for (BasicBlock block : blocks) {
            System.out.println("ID " + block.getId());
            System.out.println("Block Codes " + block.getCodes());
            for (BasicBlock succ : block.getSuccessors()) {
                System.out.println("Successor ID: " + succ.getId());
            }
            for (BasicBlock pred : block.getPredecessors()) {
                System.out.println("Predecessor ID: " + pred.getId());
            }
        }
    }


    public abstract int getLineNumOfBranchGotoTarget(int currLineNum);

    public BasicBlock getBlockById(int id) {
        for (BasicBlock block : blocks) {
            if (block.getId() == id) {
                return block;
            }
        }
        return null;
    }

    protected ArrayList<BasicBlock> initBasicBlocks() {
        ArrayList<BasicBlock> blocks = new ArrayList<>();
        LinkedList<String> currLine;
        for (int i = FIRST_LINE_POSITION; i < codes.size(); i++) {
            currLine = codes.get(i);
            if (i == FIRST_LINE_POSITION)
                blocks.add(new BasicBlock(i));
            if (isGoTo(currLine) || isBranch(currLine)) {
                int targetLabelLineNum = getLineNumOfBranchGotoTarget(i);
                if (!containBlockId(blocks, targetLabelLineNum) && targetLabelLineNum != -1) {
                    blocks.add(new BasicBlock(targetLabelLineNum));
                }
            }
            if (isBranch(currLine)) {
                if (!containBlockId(blocks, i + 1))
                    blocks.add(new BasicBlock(i + 1));
            }
        }
        return blocks;
    }

    public boolean containBlockId(ArrayList<BasicBlock> blocks, int id) {
        for (BasicBlock blk : blocks) {
            if (blk.getId() == id)
                return true;
        }
        return false;
    }

    public BasicBlock getRoot() {
        return this.root;
    }

    public ArrayList<BasicBlock> getBlocks() {
        return blocks;
    }

    public LinkedList<String> getCodeByLineNum(int lineNum) {
        int start;
        int end;
        LinkedList<String> code = null;
        for (BasicBlock blk : blocks) {
            start = blk.getId();
            end = blk.getId() + blk.getCodes().size() - 1;
            if (lineNum >= start && lineNum <= end) {
                code = blk.getCodes().get(lineNum - start);
            }
        }
        return code;
    }

    public boolean removeCodeByLineNum(int lineNum) {
        int start;
        int end;
        for (BasicBlock blk : blocks) {
            start = blk.getId();
            end = blk.getId() + blk.getCodes().size() - 1;
            if (lineNum >= start && lineNum <= end) {
                blk.getCodes().set(lineNum - start, null);
                return true;
            }
        }
        return false;
    }

    public ArrayList<LinkedList<String>> constructCodes() {
        sortBlocks("asc");
        ArrayList<LinkedList<String>> codes = new ArrayList<>();
        LinkedList<String> code;
        ArrayList<Integer> removedLines = new ArrayList<>();
        for (BasicBlock blk : blocks) {
            for (int i = 0; i < blk.getCodes().size(); i++) {
                code = blk.getCodes().get(i);
                if (code != null) {
                    codes.add(code);
                } else {
                    removedLines.add(blk.getId() + i);
                }
            }
        }
        return codes;
    }

    public void sortBlocks(String order) {
        if (order.equals("asc")) {
            blocks.sort((o1, o2) -> {
                if (o1.getId() < o2.getId())
                    return -1;
                if (o1.getId() > o2.getId())
                    return 1;
                return 0;
            });
        } else if (order.equals("desc")) {
            blocks.sort((o1, o2) -> {
                if (o1.getId() > o2.getId())
                    return 1;
                if (o1.getId() < o2.getId())
                    return -1;
                return 0;
            });
        }
    }

    public ArrayList<LinkedList<String>> getCodes() {
        return codes;
    }

    public ReachingDefBuilder getReachDef() {
        return reachDef;
    }

    public void initGenKillSet() {

    }

    public void buildReachDef() {

    }

    public void buildDefUseChain() {

    }
}
