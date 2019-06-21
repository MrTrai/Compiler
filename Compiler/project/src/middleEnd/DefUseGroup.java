package middleEnd;

import java.util.ArrayList;
import java.util.LinkedList;

public class DefUseGroup {

    private String defVar;
    private ArrayList<String> useVars;

    public DefUseGroup(String defVar, ArrayList<String> useVars) {
        this.defVar = defVar;
        this.useVars = useVars;
    }

    public String getDefVar() {
        return defVar;
    }

    public ArrayList<String> getUseVars() {
        return useVars;
    }

    public void setDefVar(String newDefVar) {
        defVar = newDefVar;
    }

    public void setUseVars(ArrayList<String> newUseVars) {
        useVars = newUseVars;
    }
}