package org.uob.a2.gameobjects;

import java.util.ArrayList;

public class Equipment extends GameObject implements Usable {
    static ArrayList<CombineInfo> combinations = new ArrayList<CombineInfo>();

    public static void addCombineInfo(CombineInfo info) {
        combinations.add(info);
    }

    private UseInformation useInformation;

    public Equipment(String id, String name, String description, boolean hidden, UseInformation useInformation) {
        super(id, name, description, hidden);
        this.useInformation = useInformation;
    }
    public UseInformation getUseInformation() {
       return useInformation;
   }

    public void setUseInformation(UseInformation useInformation) {
       this.useInformation = useInformation;
   }

    public String use(GameObject target, GameState gameState) {
        if (useInformation.isUsed()) {
            return "You have already used " + getName();
        }
        if (!useInformation.getTarget().equals(target.getId())) {
            return "Invalid use target";
        }

        CombineInfo info = getCombineInfo();
        if (info != null && info.combineRequired && !info.combined) {
            return info.notCombinedMsg;
        }

        useInformation.setUsed(true);
        Room currentRoom = gameState.getMap().getCurrentRoom();
        if (currentRoom.hasFeatureById(useInformation.getResult())) {
            currentRoom.getFeature(useInformation.getResult()).setHidden(false);
        } else if (currentRoom.hasItemById(useInformation.getResult())) {
            currentRoom.getItem(useInformation.getResult()).setHidden(false);
        } else  if (currentRoom.hasEquipmentById(useInformation.getResult())) {
            currentRoom.getEquipment(useInformation.getResult()).setHidden(false);
        } else if (currentRoom.hasFeatureById(useInformation.getResult())) {
            currentRoom.getFeature(useInformation.getResult()).setHidden(false);
        } else if (currentRoom.hasExit(useInformation.getResult())) {
            currentRoom.getExit(useInformation.getResult()).setHidden(false);
        }
        Score.useItem();
        return useInformation.getMessage();
    }

    private CombineInfo getCombineInfo(String targetId) {
        return combinations.stream()
                .filter(x->x.item1.equals(this.id) && x.item2.equals(targetId)
                        || x.item1.equals(targetId) && x.item2.equals(this.name)).findFirst().orElse(null);
    }

    private CombineInfo getCombineInfo() {
        return combinations.stream()
                .filter(x->x.item1.equals(this.id)
                        || x.item2.equals(this.id)).findFirst().orElse(null);
    }

    public String combine(String target, Player player) {
        CombineInfo combineInfo = getCombineInfo(target);
        if (combineInfo != null) {
            if (combineInfo.combined) {
                return "You have already combined " + name + " with " + target;
            }
            combineInfo.combined = true;
            player.removeItemById(target);
            Score.combineItems();
            return "You combined " + name + " with " + target + " successfully.";
        } else {
            return "You cannot combine " + name + " with " + target;
        }
    }

    /**
     * Returns a string representation of this equipment, including the attributes inherited from {@code GameObject}
     * and the associated use information.
     *
     * @return a string describing the equipment
     */
    @Override
    public String toString() {
        return super.toString() + ", useInformation=" + useInformation;
    }
}
