package basic;

import item.armour.*;
import item.weapon.*;

import java.util.Map;
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;

public class ItemFactory {
    private Random random = new Random(System.currentTimeMillis());

    private Map<String, Item> weapons = new HashMap<String, Item>();
    private Map<String, Item> armours = new HashMap<String, Item>();

    public ItemFactory(){
        // Weapons
        this.weapons.put("SmallAxe", new SmallAxe());
        this.weapons.put("Axe", new Axe());
        this.weapons.put("BroadAxe", new BroadAxe());

        this.weapons.put("ShortBow", new ShortBow());
        this.weapons.put("LongBow", new LongBow());
        this.weapons.put("CompositeBow", new CompositeBow());

        this.weapons.put("Dagger", new Dagger());
        this.weapons.put("LongSword", new LongSword());
        this.weapons.put("ShortSword", new ShortSword());

        // Armours
        this.armours.put("LightArmour", new LightArmour());
        this.armours.put("MediumArmour", new MediumArmour());
        this.armours.put("HeavyArmour", new HeavyArmour());
    }

    public String chooseRandomWeapon(int level){
        String[] weakWeapons = {"SmallAxe", "ShortBow", "Dagger"};
        String[] mediumWeapons = {"Axe", "LongBow", "LongSword"};
        String[] strongWeapons = {"BroadAxe", "CompositeBow", "ShortSword"};

        String[] weaponArray = {"null"};
        if(0 < level && level <= 5){
            weaponArray = weakWeapons;
        }

        if(5 < level && level <= 10){
            weaponArray = mediumWeapons;
        }

        if(10 < level){
            weaponArray = strongWeapons;
        }

        int idx = this.random.nextInt(weaponArray.length);
        return weaponArray[idx];
    }

    public String chooseRandomArmour(int level){
        String[] weakArmours = {"LightArmour"};
        String[] mediumArmours = {"MediumArmour"};
        String[] strongArmours = {"HeavyArmour"};

        String[] armourArray = {"null"};
        if(0 < level && level <= 5){
            armourArray = weakArmours;
        }

        if(5 < level && level <= 10){
            armourArray = mediumArmours;
        }

        if(10 < level){
            armourArray = strongArmours;
        }

        int idx = this.random.nextInt(armourArray.length);
        return armourArray[idx];
    }

    public Item get_item(String itemID) throws IllegalArgumentException{
        boolean hasFound = false;

        Item item = new Item("DefaultItem", 0, 0);
        if(this.weapons.containsKey(itemID)){
            hasFound = true;
            item = this.weapons.get(itemID);
        }

        if(this.armours.containsKey(itemID)){
            hasFound = true;
            item = this.armours.get(itemID);
        }

        if(!hasFound){
            throw new IllegalArgumentException("Item hasn't been found!");
        }

        return item;
    }
}
