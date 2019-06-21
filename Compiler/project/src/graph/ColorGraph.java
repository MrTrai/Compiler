package graph;

import backEnd.RegisterAllocator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ColorGraph {

    private ArrayList<ColorNode> myNodes;
    private int largestCliqueSize;
    private ArrayList<String> registersForAllocation;
    private Map<String, String> virtualToPhysicalMap;
    //private ArrayList<String> myArrayNames;
    private ArrayList<String> mySpillList;
    private int myAllocationType;

    public ColorGraph(ArrayList<ColorNode> _myNodes, Map<String, String> alreadyMadeAllocation, ArrayList<String> arrayNames, int allocationType) {
        myNodes = _myNodes;
        myAllocationType = allocationType;
        //myArrayNames = arrayNames;
        mySpillList = new ArrayList<>(arrayNames);
        virtualToPhysicalMap = new HashMap<>(alreadyMadeAllocation);
        //registersForAllocation = new ArrayList<>(Arrays.asList(RegisterAllocator.kernelRegs));
        registersForAllocation = new ArrayList<>(
                Arrays.asList(RegisterAllocator.tempRegs));
        registersForAllocation.addAll(Arrays.asList(RegisterAllocator.savedRegs));
        generateGlobalAllocation();
    }

    private void generateGlobalAllocation() {
        setNodeNeighbors();
        findLargestClique();
        virtualToPhysicalMapping();

    }

    private void setNodeNeighbors() {
        for (int i = 0; i < myNodes.size(); i++) {
            ColorNode firstNode = myNodes.get(i);
            for (int j = 0; j < myNodes.size(); j++) {
                if (i != j) {
                    ColorNode secondNode = myNodes.get(j);
                    boolean areNeighbors = false;
                    if (secondNode.getFirstLiveLine() >= firstNode.getFirstLiveLine() &&
                            secondNode.getFirstLiveLine() <= firstNode.getLastLiveLine()) {
                        areNeighbors = true;
                    }
                    if (!areNeighbors) {
                        if (secondNode.getLastLiveLine() >= firstNode.getFirstLiveLine() &&
                                secondNode.getLastLiveLine() <= firstNode.getLastLiveLine()) {
                            areNeighbors = true;
                        }
                    }
                    if (areNeighbors) {
                        if (!firstNode.getNeighbors().contains(secondNode)) {
                            firstNode.addNeighbor(secondNode);
                        }
                        if (!secondNode.getNeighbors().contains(firstNode)) {
                            secondNode.addNeighbor(firstNode);
                        }
                    }
                }
            }
        }
    }

    private void findLargestClique() {
        int largestSize = 0;
        for (int i = 0; i < myNodes.size(); i++) {
            int myCliqueSize = findMyCliqueSize(myNodes.get(i));
            if (myCliqueSize > largestSize) {
                largestSize = myCliqueSize;
            }
        }
        largestCliqueSize = largestSize;
    }

    private int findMyCliqueSize(ColorNode theNode) {
        ArrayList<ColorNode> myNeighbors = new ArrayList<>(theNode.getNeighbors());
        ArrayList<ColorNode> neighborIntersection = new ArrayList<>(theNode.getNeighbors());
        for (ColorNode neighbor : myNeighbors) {
            neighborIntersection.retainAll(neighbor.getNeighbors());
            neighborIntersection.add(neighbor);
        }
        neighborIntersection.add(theNode);
        //System.out.println(neighborIntersection.size());
        return neighborIntersection.size();
    }

    public int getLargestCliqueSize() {
        return largestCliqueSize;
    }

    public boolean isSpillingRequired() {
        return (largestCliqueSize > registersForAllocation.size());
    }

    public int getNumOfAllocationRegs() {
        return registersForAllocation.size();
    }

    private void virtualToPhysicalMapping() {
        //Map<String, String> quickMapping = new HashMap<>();
        if (myAllocationType == 3) {
            for (int i = 0; i < myNodes.size(); i++) {
                ColorNode theNode = myNodes.get(i);
                String nodeReg = theNode.getMyVirtualReg();
                if (!virtualToPhysicalMap.keySet().contains(nodeReg)) {
                    boolean foundMapping = false;
                    for (String physicalReg : registersForAllocation) {
                        boolean alreadyTaken = false;
                        for (ColorNode neighbor : theNode.getNeighbors()) {
                            if (physicalReg.equals(virtualToPhysicalMap.get(neighbor.getMyVirtualReg()))) {
                                alreadyTaken = true;
                            }
                        }
                        if (!alreadyTaken) {
                            virtualToPhysicalMap.put(nodeReg, physicalReg);
                            theNode.setMyPhysicalReg(physicalReg);
                            foundMapping = true;
                            break;
                        }
                    }
                    if (!foundMapping) {
                        //this should only happen when we're spilling
                        mySpillList.add(nodeReg);
                    }
                }
            }
        } else if (myAllocationType == 2) {
            int regCounter = 0;
            for (ColorNode theNode : myNodes) {
                String nodeReg = theNode.getMyVirtualReg();
                if (!virtualToPhysicalMap.keySet().contains(nodeReg)) {
                    if (regCounter < registersForAllocation.size()) {
                        String physReg = registersForAllocation.get(regCounter);
                        virtualToPhysicalMap.put(nodeReg, physReg);
                        regCounter++;
                    } else {
                        mySpillList.add(nodeReg);
                    }
                }
            }
        }
    }

    private ColorNode findShareVirtualReg(ColorNode theNode) {
        //find the neighbor that has the least amount of lines left to live
        int lineDif = 0;
        ColorNode theFriend = null;
        for (int i = 0; i < theNode.getNeighbors().size(); i++) {
            ColorNode neighbor = theNode.getNeighbors().get(i);
            int startLine = theNode.getFirstLiveLine();
            int endLine = neighbor.getLastLiveLine();
            int tempDif = endLine - startLine;
            if (theFriend == null) {
                theFriend = neighbor;
                lineDif = tempDif;
            } else if (tempDif < lineDif) {
                theFriend = neighbor;
                lineDif = tempDif;
            }
        }
        return theFriend;
    }

    public Map<String, String> getVirtualToPhysicalMap() {
        return virtualToPhysicalMap;
    }

    public ArrayList<String> getSpillList() {
        return mySpillList;
    }

}
