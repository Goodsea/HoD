package basic;

public class Item {
    protected String name;
    protected int weight;
    protected int value;

    public Item(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getType() {
        return "Default_Item";
    }

    public void display() {
        System.out.printf("* Name: %s  Weight: %s  Value: %s *\n", this.name, this.weight, this.value);
    }
}