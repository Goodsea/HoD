package basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import character.Monster;

public class LevelManager {
    private Random random = new Random(System.currentTimeMillis());
    private ItemFactory itemFactory = new ItemFactory();

    public int generateCorridorLength(int level){
        int corridorLength = this.random.nextInt((int) Math.sqrt(level) * 3);
        return corridorLength;
    }

    public int generateNumOfCorridor(int level){
        int numOfCorridor = this.random.nextInt((int) Math.sqrt(level) * 3);
        return numOfCorridor;
    }

    public int generateNumOfTownspeople(int level){
        int maxTownspeople = 2;

        if(10 < level && level <= 20){
            maxTownspeople = 3;
        }

        if(20 < level && level <= 30){
            maxTownspeople = 4;
        }

        if(level > 30){
            maxTownspeople = 5;
        }

        int numOfTownspeople = this.random.nextInt(maxTownspeople);
        return numOfTownspeople;
    }

    public Monster generateMonster(String roomID){
        int level = Integer.valueOf(roomID.split("-")[0]);

        int maxHitPoint = level*20;
        int hitDamage = level*5;
        Monster monster = new Monster(maxHitPoint, hitDamage, roomID);

        Inventory monsterInventory = new Inventory(false);
        monsterInventory.add(this.itemFactory.chooseRandomWeapon(level));
        monsterInventory.add(this.itemFactory.chooseRandomArmour(level));

        monster.setInventory(monsterInventory);

        return monster;
    }

    public ArrayList<Room> createCorridor(int level, int corridorLength, int order){
        int numOfTownspeople;

        ArrayList<Room> corridor = new ArrayList<Room>();
        for(int i=1; i <= corridorLength; i++){
            String roomID = String.valueOf(level) + "-" + String.valueOf(order+i);
            numOfTownspeople = generateNumOfTownspeople(level);

            ArrayList<Character> enemyCharacters = new ArrayList<Character>();
            for(int j=0; j < numOfTownspeople*3; j++){
                Monster monster = generateMonster(roomID);
                enemyCharacters.add(monster);
            }

            Room room = new Room(roomID, numOfTownspeople, enemyCharacters);

            if(i > 1){
                Room prev_room = corridor.get(i-2);
                prev_room.connectExit(roomID);
                room.connectEntry(prev_room.getRoomID());
            }

            corridor.add(room);
        }

        return corridor;
    }

    public ArrayList<ArrayList<Room>> connectCorridors(ArrayList<ArrayList<Room>> corridors){
        ArrayList<ArrayList<Room>> connectedCorridors = new ArrayList<ArrayList<Room>>();

        int prevStairIdx = -1;
        for(int i=0; i<corridors.size(); i++){
            ArrayList<Room> currentCorridor = corridors.get(i);

            ArrayList<Room> newCorridor = new ArrayList<Room>();
            int stairIdx = this.random.nextInt(currentCorridor.size());

            for(int j=0; j<currentCorridor.size(); j++){
                Room current_room = currentCorridor.get(j);

                if(j == stairIdx) {
                    if (i != corridors.size() - 1) {
                        ArrayList<Room> next_corridor = corridors.get(i + 1);
                        Room next_room = next_corridor.get(stairIdx);
                        current_room.connectDownStairs(next_room.getRoomID());
                    }
                }

                if(j == prevStairIdx) {
                    if (prevStairIdx != -1) {
                        ArrayList<Room> prev_corridor = corridors.get(i - 1);
                        Room prev_room = prev_corridor.get(prevStairIdx);
                        current_room.connectUpStairs(prev_room.getRoomID());
                    }
                }

                newCorridor.add(current_room);
            }

            prevStairIdx = stairIdx;
            connectedCorridors.add(newCorridor);
        }

        return connectedCorridors;
    }

    public Map<String, Room> generateMap(int level){
        int corridorLength = this.random.nextInt(level) + 3;
        int numOfCorridors = this.random.nextInt(level) + 3;

        ArrayList<ArrayList<Room>> corridors = new ArrayList<ArrayList<Room>>();
        for(int i=0; i<=numOfCorridors; i++){
            ArrayList<Room> corridor = this.createCorridor(level, corridorLength, i*corridorLength);
            corridors.add(corridor);
        }

        ArrayList<ArrayList<Room>> connectedCorridors = this.connectCorridors(corridors);

        Map<String, Room> map = new HashMap<String, Room>();
        for(int i=0; i<connectedCorridors.size(); i++){
            ArrayList<Room> corridor = connectedCorridors.get(i);
            for(int j=0; j<corridor.size(); j++){
                Room room = corridor.get(j);
                map.put(room.getRoomID(), room);
            }
        }

        return map;
    }

    public int getMaxRoomNo(Map<String, Room> map){
        int maxRoomNo = 0;
        for(Map.Entry<String, Room> mapEntry: map.entrySet()){
            int roomNo = Integer.valueOf(mapEntry.getKey().split("-")[1]);
            if(roomNo > maxRoomNo){
                maxRoomNo = roomNo;
            }
        }

        return maxRoomNo;
    }

    public int getTotalNumOfEnemy(Map<String, Room> map){
        int totalEnemy = 0;
        for(Map.Entry<String, Room> mapEntry: map.entrySet()){
            totalEnemy += mapEntry.getValue().getNumOfMonster();
        }

        return totalEnemy;
    }

}
