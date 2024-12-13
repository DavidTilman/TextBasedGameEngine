package org.uob.a2.gameobjects;

import java.util.ArrayList;

/**
 * Represents the player in the game, including their name, inventory, and equipment.
 * 
 * <p>
 * The player can carry items and equipment, interact with the game world, and perform
 * actions using their inventory or equipment.
 * </p>
 */
public class Player {
    private final String name;
    private final ArrayList<Item> inventory = new ArrayList<>();
    private final ArrayList<Equipment> equipment = new ArrayList<>();

    public Player() {
        this.name = null;
    }

    public Player(String name) {
        this.name = name;
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public Equipment getEquipment(String name) {
        return equipment.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Equipment getEquipmentById(String id) {
        return equipment.stream().filter(x -> x.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Item getItem(String name) {
        return inventory.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Item getItemById(String id) {
        return inventory.stream().filter(item -> item.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }

    public boolean hasEquipment(String name) {
        return getEquipment(name) != null;
    }

    public boolean hasEquipmentById(String id) {
        return getEquipmentById(id) != null;
    }

    public boolean hasItem(String name) {
        return getItem(name) != null;
    }

    public boolean hasItemById(String id) {
        return getItemById(id) != null;
    }

    public void removeItem(String name) {
        inventory.removeIf(x -> x.getName().equalsIgnoreCase(name));
    }

    public void removeItemById(String id) {
        inventory.removeIf(x -> x.getId().equalsIgnoreCase(id));
    }

    public void removeEquipment(String name) {
        equipment.removeIf(x -> x.getName().equalsIgnoreCase(name));
    }

    /**
     * Returns a string representation of the player's current state, including their name,
     * inventory, and equipment descriptions.
     *
     * @return a string describing the player, their inventory, and equipment
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Player Name: " + this.name + "\nInventory:\n");
        for (Item i : this.inventory) {
            out.append("- ").append(i.getDescription()).append("\n");
        }
        out.append("Equipment:\n");
        for (Equipment e : this.equipment) {
            out.append("- ").append(e.getDescription()).append("\n");
        }
        return out.toString();
    }
}
