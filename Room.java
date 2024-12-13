package org.uob.a2.gameobjects;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a room in the game, which is a type of {@code GameObject}.
 * 
 * <p>
 * Rooms can have items, equipment, features, and exits. They also manage navigation
 * and interactions within the game world.
 * </p>
 */
public class Room extends GameObject {
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Equipment> equipments = new ArrayList<>();
    private final ArrayList<Exit> exits = new ArrayList<>();
    private final ArrayList<Feature> features = new ArrayList<>();
    private Position position = null;

    public Room() {
        super();
    }

    public Room(String id, String name, String description, boolean hidden) {
        super(id, name, description, hidden);
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }

    public void addExit(Exit exit) {
        this.exits.add(exit);
    }

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<GameObject> getAll() {
        Stream.Builder<GameObject> builder = Stream.builder();
        items.forEach(builder::add);
        equipments.forEach(builder::add);
        exits.forEach(builder::add);
        features.forEach(builder::add);
        return builder.build().collect(Collectors.toCollection(ArrayList::new));
    }

    public Equipment getEquipment(String id) {
        return equipments.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public Equipment getEquipmentByName(String name) {
        return equipments.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public Exit getExit(String id) {
        return exits.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public Exit getExitByName(String name) {
        return exits.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public Feature getFeature(String id) {
        return features.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public Feature getFeatureByName(String name) {
        return features.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public Item getItem(String id) {
        return items.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public Item getItemByName(String name) {
        return items.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean hasEquipment(String name) {
        return getEquipmentByName(name) != null;
    }

    public boolean hasEquipmentById(String id) {
        return getEquipment(id) != null;
    }

    public boolean hasItem(String name) {
        return getItemByName(name) != null;
    }

    public boolean hasItemById(String id) {
        return getItem(id) != null;
    }

    public boolean hasFeature(String name) {
        return getFeatureByName(name) != null;
    }

    public boolean hasFeatureById(String id) {
        return getFeature(id) != null;
    }

    public boolean hasExit(String name) {
        return getExit(name) != null;
    }

    public void removeItem(String itemName) {
        items.removeIf(e -> e.getId().equalsIgnoreCase(itemName));
    }

    public void removeEquipment(String equipmentName) {
        equipments.removeIf(e -> e.getId().equalsIgnoreCase(equipmentName));
    }

    public boolean canMove(String direction) {
        ArrayList<String> directions = exits.stream().filter(x->!x.hidden).map(GameObject::getName).collect(Collectors.toCollection(ArrayList::new));
        return directions.contains(direction);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns a string representation of the room, including its contents and description.
     *
     * @return a string describing the room
     */
    @Override
    public String toString() {
        StringBuilder out =
                new StringBuilder(
                        "[" + id + "] Room: " + name + "\n"
                        + "Description: " + description + "\n"
                        + "In the room there is: ");
        for (Item i : this.items) {
            out.append(i).append('\n');
        }
        for (Equipment e : this.equipments) {
            out.append(e).append('\n');
        }
        for (Feature f : this.features) {
            out.append(f).append('\n');
        }
        for (Exit e : this.exits) {
            out.append(e).append('\n');
        }
        out.append('\n');
        return out.toString();
    }
}
