package character;

import basic.Item;
import basic.Character;
import basic.Inventory;

public class Monster extends Character {

    public Monster(int maxHitPoint, int hitDamage, String roomID){
        super(maxHitPoint, hitDamage, roomID);
    }

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

}
