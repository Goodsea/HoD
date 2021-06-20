package basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String roomID;
    private String entryRoomID = "-1";
    private String exitRoomID = "-1";
    private String upStairsRoomID = "-1";
    private String downStairsRoomID = "-1";
    private int numOfTownspeople, numOfMonster;

    private Inventory droppedItems = new Inventory(false);
    private ArrayList<Character> enemyCharacters = new ArrayList<Character>();

    public Room(String roomID, int numOfTownspeople, ArrayList<Character> enemyCharacters){
        this.roomID = roomID;
        this.numOfTownspeople = numOfTownspeople;
        this.enemyCharacters = enemyCharacters;
        this.numOfMonster = enemyCharacters.size();
    }

    public String getRoomID() {
        return this.roomID;
    }

    public int getNumOfTownspeople() {
        return this.numOfTownspeople;
    }

    public int getNumOfMonster() {
        return this.numOfMonster;
    }

    public Map<String, String> matchEntranceRoomIDsWkey(){
        Map<String, String> entranceMap = new HashMap<String, String>();

        String[] doors = new String[]{this.entryRoomID, this.exitRoomID};
        String[] stairs = new String[]{this.upStairsRoomID, this.downStairsRoomID};

        int doorCounter = 0;
        for(int i=0; i<doors.length; i++){
            if(doors[i] != "-1"){
                doorCounter += 1;
                String nextRoomID = doors[i];
                entranceMap.put("d" + String.valueOf(doorCounter), nextRoomID);
            }
        }

        int stairCounter = 0;
        for(int i=0; i<stairs.length; i++){
            if(stairs[i] != "-1"){
                stairCounter += 1;
                String nextRoomID = stairs[i];
                entranceMap.put("s" + String.valueOf(stairCounter), nextRoomID);
            }
        }

        return entranceMap;
    }

    public String getNextRoomID(String key) throws IllegalArgumentException{
        Map<String, String> entranceMap = this.matchEntranceRoomIDsWkey();

        if(entranceMap.get(key) == null){
            throw new IllegalArgumentException("Invalid key. Please write interacted object carefully!");
        }

        return entranceMap.get(key);
    }

    public Map<String, Character> matchMonsterWkey(){
        Map<String, Character> monsterMap = new HashMap<String, Character>();

        for(int i=0; i<this.numOfMonster; i++){
            monsterMap.put("m" + String.valueOf(i), this.enemyCharacters.get(i));
        }

        return monsterMap;
    }

    public Character getNextMonster(String key) throws IllegalArgumentException{
        Map<String, Character> monsterMap = this.matchMonsterWkey();

        if(monsterMap.get(key) == null){
            throw new IllegalArgumentException("Invalid key. Please write interacted object carefully!");
        }

        return monsterMap.get(key);
    }

    public void killMonster(Character enemy){
        Map<String, Character> monsterMap = this.matchMonsterWkey();

        int foundIdx = -1;
        for(int i=0; i<this.enemyCharacters.size(); i++){
            if(enemy == this.enemyCharacters.get(i)){
                foundIdx = i;
            }
        }

        this.numOfMonster -= 1;
        this.enemyCharacters.remove(foundIdx);
    }

    public void rescueTownspeople(){
        this.numOfTownspeople = 0;
    }

    public void connectEntry(String roomID){
        this.entryRoomID = roomID;
    }

    public void connectExit(String roomID){
        this.exitRoomID = roomID;
    }

    public void connectUpStairs(String roomID){
        this.upStairsRoomID = roomID;
    }

    public void connectDownStairs(String roomID){
        this.downStairsRoomID = roomID;
    }

    public void dropToFloor(String enemyItem){
        this.droppedItems.add(enemyItem);
    }

    public void dropToFloor(Item enemyItem){
        this.droppedItems.add(enemyItem.getName());
    }

    public void takeFromFloor(String enemyItem){
        this.droppedItems.drop(enemyItem);
    }

    public void display(){
        System.out.println("************************************");
        System.out.println("Room ID: " + roomID);
        System.out.println("************************************");

        // Door & Stairs Information
        System.out.println("------------------------------------");
        System.out.println("**    Door and Stairs    **");
        System.out.println("------------------------------------");

        Map<String, String> entranceMap = this.matchEntranceRoomIDsWkey();
        for(Map.Entry<String, String> roomIDKeyEntry : entranceMap.entrySet()){
            String key = roomIDKeyEntry.getKey();
            String roomID = roomIDKeyEntry.getValue();

            int keyNumber = Integer.parseInt(key.substring(1));

            String type = "";
            if(key.contains("d")){
                type = "Door";
            } else if(key.contains("s")){
                type = "Stair";
            }

            System.out.println("*> " + type + keyNumber + " [Room" + String.valueOf(roomID) + "]: ( " + key +" ) <*");

        }

        // Characters Information
        System.out.println("------------------------------------");
        System.out.println("**    Character Stats    **");
        System.out.println("------------------------------------");

        String monsterStatText = "";
        String townspeopleStatText = "";

        if(this.numOfMonster == 0){
            monsterStatText = "*> There is no Monster <*";
            townspeopleStatText = "*> All Townspeople have been saved <*";
        } else if(this.numOfMonster > 0){
            Map<String, Character> monsterMap = this.matchMonsterWkey();
            for(Map.Entry<String, Character> roomIDKeyEntry : monsterMap.entrySet()){
                String key = roomIDKeyEntry.getKey();
                int keyNumber = Integer.parseInt(key.substring(1));
                monsterStatText += "*> Monster" + String.valueOf(keyNumber) + " (" + key + ") <*\n";
            }
        }

        if(this.numOfTownspeople == 1){
            townspeopleStatText = "*> There is A Townspeople <*";
        } else if(this.numOfTownspeople > 1) {
            townspeopleStatText = "*> There are " + this.numOfTownspeople + " Townspeoples <*";
        }

        System.out.println(monsterStatText);
        System.out.println(townspeopleStatText);

        // Dropped Items Information
        this.droppedItems.display("Room Items");


    }

}
