package basic;

public class Weapon extends Item implements IItem{
    private int damage;

    public Weapon(String name, int weight, int value) {
        super(name, weight, value);
        this.damage = value;
    }

    public void setDamage(int damageMultiplier){
        this.damage *= damageMultiplier;
    }

    @Override
    public String getType() {
        return "Weapon";
    }

    @Override
    public void display() {
        System.out.printf("** Name: %s | Weight: %s | Damage: %s **\n", this.name, this.weight, this.damage);
    }

}