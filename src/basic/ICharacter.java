package basic;

import java.util.Map;

public interface ICharacter {
    public Map<String, Item> getBestItems();
    public int getHitPoint();
    public int getHitDamage();
    public boolean decreaseHitPoint(int hitPoint);
}
