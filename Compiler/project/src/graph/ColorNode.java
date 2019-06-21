package graph;

import java.util.ArrayList;

public class ColorNode {

    private int firstLiveLine;
    private int lastLiveLine;
    private String myVirtualReg;
    private ArrayList<ColorNode> neighbors;
    private String myPhysicalReg;

    public ColorNode(String reg, int first, int last) {
        myVirtualReg = reg;
        firstLiveLine = first;
        lastLiveLine = last;
        neighbors = new ArrayList<>();
        myPhysicalReg = "";
    }

    public int getFirstLiveLine() {
        return firstLiveLine;
    }

    public int getLastLiveLine() {
        return lastLiveLine;
    }

    public String getMyVirtualReg() {
        return myVirtualReg;
    }

    public ArrayList<ColorNode> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<ColorNode> newNeighbors) {
        neighbors = newNeighbors;
    }

    public void addNeighbor(ColorNode newNeighbor) {
        neighbors.add(newNeighbor);
    }

    public String toString() {
        String myString = myVirtualReg + " " + firstLiveLine + " " + lastLiveLine;
        myString += " NEIGHBORS: ";
        for (ColorNode neighbor : neighbors) {
            myString += (neighbor.getMyVirtualReg() + ", ");
        }
        myString += ("||| " + myPhysicalReg);
        return myString;
    }

    public void setMyPhysicalReg(String newReg) {
        myPhysicalReg = newReg;
    }
}
