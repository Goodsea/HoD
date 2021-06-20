package basic;

import java.util.Collection;
import java.util.Map;
import character.Hero;

public class Attack {
    private Hero hero;
    private Character enemy;
    private Room room;

    public Attack(Hero hero, Character enemy, Room room){
        this.hero = hero;
        this.enemy = enemy;
        this.room = room;
    }

    public boolean fight(){
        Map<String, Item> heroItems = this.hero.getBestItems();
        Map<String, Item> enemyItems = this.enemy.getBestItems();

        boolean win = false;
        int heroHitDamage;
        int monsterHitDamage;
        while(true) {
            heroHitDamage = this.hero.getHitDamage() + heroItems.get("Weapon").getValue() - enemyItems.get("Armour").getValue();
            monsterHitDamage = this.enemy.getHitDamage() + enemyItems.get("Weapon").getValue() - heroItems.get("Armour").getValue();

            boolean boolEnemyDead = this.enemy.decreaseHitPoint(heroHitDamage);
            if(boolEnemyDead) {
                win = true;
                break;
            }

            boolean boolHeroDead = this.hero.decreaseHitPoint(monsterHitDamage);
            if(boolHeroDead) {
                win = false;
                break;
            }

        }

        if(win){
            for(Map.Entry<String, Item> itemEntry: enemyItems.entrySet()){
                this.room.dropToFloor(itemEntry.getValue());
            }

            this.room.killMonster(this.enemy);
            System.out.println("** You killed a monster! **");

            if(this.room.getNumOfMonster() == 0){
                System.out.println("** You saved all townspeople **");
                int numOfTP = this.room.getNumOfTownspeople();
                int healPoint = numOfTP * 25;
                this.hero.increaseHitPoint(healPoint);
                System.out.println("** You healed by " + healPoint + " **");
                this.room.rescueTownspeople();
                this.hero.increaseScore(numOfTP);
            }

            this.hero.display();

        }

        return win;
    }


}
