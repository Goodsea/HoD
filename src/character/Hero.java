package character;

import basic.Character;
import basic.Inventory;
import basic.ItemFactory;

public class Hero extends Character {
    private int score = 0;
    private ItemFactory itemFactory = new ItemFactory();

    public Hero(String name){
        super(name, 100, 5, "1-1");
        this.addItem(itemFactory.chooseRandomWeapon(1));
        this.addItem(itemFactory.chooseRandomArmour(1));
    }

    public void increaseScore(int addScore){
        this.score += addScore;
    }

    public int getScore(){
        return this.score;
    }

    public void addItem(String addItemID){
        this.inventory.add(addItemID);
    }

    public void dropItem(String dropItemID){
        this.inventory.drop(dropItemID);
    }

    public void replaceItem(String dropItemID, String addItemID){
        this.inventory.replace(dropItemID, addItemID);
    }

    public void upgrade(int hitDamage){
        this.currentHitDamage += hitDamage;
    }

    public String getRoomID(){
        return this.roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void increaseHitPoint(int hitPoint){
        if(this.currentHitPoint + hitPoint > this.maxHitPoint){
            this.currentHitPoint = this.maxHitPoint;
        } else {
            this.currentHitPoint += hitPoint;
        }
    }

    public void display(){
        System.out.println("** Hero HP: " + String.valueOf(this.getHitPoint()) + " || Game Score: " + String.valueOf(this.getScore()) + " **");
    }
    public void displayInventory(){
        this.inventory.display("Hero Items");
    }
}
