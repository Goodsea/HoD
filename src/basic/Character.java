package basic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Character implements ICharacter{
    private String name;
    protected String roomID;
    protected int currentHitPoint;
    protected final int maxHitPoint;
    protected int currentHitDamage;
    protected Inventory inventory = new Inventory(false);

    public Character(String name, int maxHitPoint, int hitDamage, String roomID) {
        this.name = name;
        this.currentHitDamage = hitDamage;
        this.maxHitPoint = maxHitPoint;
        this.currentHitPoint = this.maxHitPoint;
        this.roomID = roomID;
    }

    public Character(int maxHitPoint, int hitDamage, String roomID){
        this.name = "No_Name";
        this.currentHitDamage = hitDamage;
        this.maxHitPoint = maxHitPoint;
        this.currentHitPoint = this.maxHitPoint;
        this.roomID = roomID;
    }

    public Map<String, Item> getBestItems(){
        Collection<Item> allItems = this.inventory.getAll();
        Map<String, Item> bestItems = new HashMap<String, Item>();

        for(Item item : allItems){
            String itemType = item.getType();

            if(bestItems.get(itemType) == null){
                bestItems.put(itemType, item);
            } else {
                int valueInList = bestItems.get(itemType).getValue();
                int valueOfItem = item.getValue();
                if(valueOfItem > valueInList){
                    bestItems.put(itemType, item);
                }
            }
        }

        return bestItems;
    }

    public String getName() {
        return this.name;
    }

    public int getHitPoint() {
        return this.currentHitPoint;
    }

    public int getHitDamage() {
        return this.currentHitDamage;
    }

    public Collection<Item> dropItems(){
        return this.inventory.dropAll();
    }

    public boolean decreaseHitPoint(int hitPoint) {
        if (this.currentHitPoint - hitPoint < 0) {
            this.currentHitPoint = 0;
            return true; // Dead
        }

        this.currentHitPoint -= hitPoint;
        return false; // Alive
    }

}