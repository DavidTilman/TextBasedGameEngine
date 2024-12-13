package org.uob.a2.gameobjects;

public class CombineInfo {
    public String item1;
    public String item2;
    public String notCombinedMsg;
    public boolean combineRequired;
    public boolean combined;

    public CombineInfo(String item1, String item2, String notCombinedMsg, boolean combineRequired) {
        this.item1 = item1;
        this.item2 = item2;
        this.notCombinedMsg = notCombinedMsg;
        this.combineRequired = combineRequired;
        this.combined = false;
    }
}
