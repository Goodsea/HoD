package basic;

public class Armour extends Item implements IItem{
    private int reduceDamage;

    public Armour(String name, int weight, int value){
        super(name, weight, value);
        this.reduceDamage = value;
    }

    @Override
    public String getType() {
        return "Armour";
    }

    @Override
    public void display() {
        System.out.printf("** Name: %s | Weight: %s | ReduceDamage: %s **\n", this.name, this.weight, this.reduceDamage);
    }
}