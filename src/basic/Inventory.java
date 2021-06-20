package basic;

import java.util.*;

public class Inventory {
    private int maxWeight = -1;
    private int totalWeight = 0;
    private Map<String, Item> itemMap = new HashMap<String, Item>();

    public Inventory(boolean setMaxWeight){
        if(setMaxWeight == true){
            this.totalWeight = 0;
            this.maxWeight = 1000;
        }
    }

    public Collection<Item> getAll(){
        return this.itemMap.values();
    }

    public Collection<Item> dropAll(){
        Collection<Item> allItems = this.getAll();
        this.itemMap.clear();
        return allItems;
    }

    public void add(String itemID){
        Item toAddItem = new ItemFactory().get_item(itemID);

        if(maxWeight != -1) {
            if (this.totalWeight + toAddItem.getWeight() > this.maxWeight) {
                System.out.println("** The Inventory is full! **");
            } else {
                this.totalWeight += toAddItem.getWeight();
                this.itemMap.put(itemID, toAddItem);
            }
        } else{
            this.itemMap.put(itemID, toAddItem);
        }
    }

    private Map<String, Integer> getFreq(ArrayList<String> types) {
        int n = types.size();
        boolean[] hasSeen = new boolean[n];
        Arrays.fill(hasSeen, false);

        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for (int i = 0; i < n; i++) {
            if (hasSeen[i])
                continue;

            int count = 1;
            for (int j = i + 1; j < n; j++) {
                if (types.get(i) == types.get(j)) {
                    hasSeen[j] = true;
                    count++;
                }
            }

            freqMap.put(types.get(i), count);
        }

        return freqMap;
    }

    private boolean canDrop(String type){
        ArrayList<String> itemTypes = new ArrayList<String>();
        for (Item item: itemMap.values()) {
            itemTypes.add(item.getType());
        }

        Map<String, Integer> freqMap = this.getFreq(itemTypes);

        int countOfType = freqMap.get(type);

        if(countOfType == 1){
            return false;
        }

        return true;
    }

    public void drop(String itemID){
        Item toDropItem = new ItemFactory().get_item(itemID);

        if(this.canDrop(toDropItem.getType())){
            this.totalWeight -= toDropItem.getWeight();
            this.itemMap.remove(itemID);
        } else{
            System.out.println("** This type of item is necessary, you can't drop that! **");
        }
    }

    public void replace(String dropItemID, String addItemID){
        this.add(addItemID);
        this.drop(dropItemID);
    }

    public void display(String header){
        System.out.println("------------------------------------");
        System.out.println("**         " + header + "         **");
        System.out.println("------------------------------------");

        if(this.itemMap.size() == 0){
            System.out.println("** Can't find any item :( **");
        } else {
            for (Map.Entry itemEntry : this.itemMap.entrySet()) {
                Item item = (Item) itemEntry.getValue();
                item.display();
            }
        }

        System.out.println("------------------------------------");
    }

    public void display(){
        System.out.println("---------------------------");
        System.out.println("**         Items         **");
        System.out.println("---------------------------");

        if(this.itemMap.size() == 0){
            System.out.println("** Can't find any item :( **");
        } else {
            for (Map.Entry itemEntry : this.itemMap.entrySet()) {
                Item item = (Item) itemEntry.getValue();
                item.display();
            }
        }

        System.out.println("------------------------------------");
    }
}
