import basic.Attack;
import basic.Character;
import basic.LevelManager;
import basic.Room;
import character.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final int NUM_OF_LEVELS = 16;

        final String logDir = "logs";
        final String historyFilePath = logDir + "/history.txt";
        final String bestScoresFilePath = logDir + "/highScores.txt";

        Scanner scanner = new Scanner(System.in);
        LevelManager levelManager = new LevelManager();

        // Welcome User
        System.out.println("***** Welcome to the Hero of the Dungeons *****");
        System.out.println("** Good to Know: Any time you write 'quit', game will be terminated! **\n");
        System.out.println("** Actions **");
        System.out.println("** Move to another room: 'go DOOR_OR_STAIR_ID' **");
        System.out.println("** Attack to enemy: 'attack ENEMY_ID' **");
        System.out.println("** Pick item from ground: 'take ITEM_NAME' **");
        System.out.println("** Drop item to ground: 'drop ITEM_NAME' **");
        System.out.println("** Replace item: 'replace DROP_ITEM_NAME TAKE_ITEM_NAME' **");


        // Check records
        boolean fdone;

        File logsF = new File(logDir);
        if(!logsF.isDirectory()) {
            logsF.mkdir();
        }

        File historyF = new File(historyFilePath);
        if(!historyF.exists()) {
            try {
                fdone = historyF.createNewFile();
                if(fdone){
                    System.out.println("** OOPS! History of the end game hasn't been found! Empty record has been created! **");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        // Nickname lists
        ArrayList<String> allNicknames = new ArrayList<String>();
        Scanner historyFReader = null;
        try {
            historyFReader = new Scanner(historyF);

            while (historyFReader.hasNextLine()) {
                String data = historyFReader.nextLine();
                String nickname = data.split("-")[0];
                allNicknames.add(nickname);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create Hero
        String heroName = null;
        boolean apropriateNickname = false;
        while(!apropriateNickname) {
            System.out.println("Please choose a nickname:");
            heroName = scanner.next();
            if(heroName.equals("quit")){
                System.out.println("Quitting..");
                System.exit(0);
            } else if(!allNicknames.contains(heroName)){
                apropriateNickname = true;
            } else{
                System.out.println("The nickname is already taken! Choose another one.");
            }
        }

        Hero hero = new Hero(heroName);

        long timeInit = System.currentTimeMillis();
        outer1 : for(int level=1; level<=NUM_OF_LEVELS; level++){
            Map<String, Room> map = levelManager.generateMap(level);

            int numOfMonsters = -1;
            boolean heroDead = false;

            outer2 : while((numOfMonsters!=0) || (heroDead)){
                String currentRoomID = hero.getRoomID();
                Room room = map.get(currentRoomID);
                room.display();
                System.out.println();
                hero.displayInventory();

                String userAction;
                boolean apropriateUserAction = false;
                outer3 : while(!apropriateUserAction) {
                    System.out.println("Action:");
                    userAction = scanner.nextLine();

                    if(userAction.equals("quit")){
                        System.out.println("Quitting..");
                        break outer1;
                    }

                    else if(userAction.contains("go")){
                        if(userAction.split("\\s+").length != 2){
                            System.out.println("Please specify door or stair id");
                            continue;
                        }

                        String roomKey = userAction.split(" ")[1];

                        String roomID = null;
                        try {
                            roomID = room.getNextRoomID(roomKey);
                        } catch (IllegalArgumentException e){
                            System.out.println("Invalid door or stair id.");
                            continue;
                        }

                        if(roomID != null){
                            hero.setRoomID(roomID);
                            apropriateUserAction = true;
                        }

                    }

                    else if(userAction.contains("attack")){
                        if(userAction.split("\\s+").length != 2){
                            System.out.println("Please specify enemy id");
                            continue;
                        }

                        String enemyKey = userAction.split(" ")[1];

                        Character enemy = null;
                        try {
                            enemy = room.getNextMonster(enemyKey);
                        } catch (IllegalArgumentException e){
                            System.out.println("Invalid enemy id.");
                            continue;
                        }

                        if(enemy != null){
                            Attack attack = new Attack(hero, enemy, room);
                            boolean win = attack.fight();

                            if(win == false){
                                heroDead = true;
                            }

                            apropriateUserAction = true;
                        }
                    }

                    else if(userAction.contains("take")){
                        if(userAction.split("\\s+").length != 2){
                            System.out.println("Please specify item name");
                            continue;
                        }

                        String itemName = userAction.split(" ")[1];

                        hero.addItem(itemName);
                        room.takeFromFloor(itemName);
                        apropriateUserAction = true;
                    }

                    else if(userAction.contains("drop")){
                        if(userAction.split("\\s+").length != 2){
                            System.out.println("Please specify item name");
                            continue;
                        }

                        String itemName = userAction.split(" ")[1];

                        hero.dropItem(itemName);
                        room.dropToFloor(itemName);
                        apropriateUserAction = true;
                    }

                    else if(userAction.contains("replace")){
                        if(userAction.split("\\s+").length != 3){
                            System.out.println("Please specify drop-item-name and take-item-name");
                            continue;
                        }

                        String dropItemName = userAction.split(" ")[1];
                        String takeItemName = userAction.split(" ")[2];

                        hero.addItem(takeItemName);
                        room.takeFromFloor(takeItemName);

                        hero.dropItem(dropItemName);
                        room.dropToFloor(dropItemName);
                        apropriateUserAction = true;
                    }

                    else{
                        System.out.println("Invalid action type! Please try again.");
                    }

                    numOfMonsters = levelManager.getTotalNumOfEnemy(map);
                }

            }


            hero.upgrade(level*2);
            hero.setRoomID(String.valueOf(level+1) + "-1");
        }
        long timeFinish = System.currentTimeMillis();

        try {
            FileWriter historyFWriter = new FileWriter(historyFilePath, true);
            historyFWriter.write(heroName + "-" + String.valueOf((timeFinish-timeInit)/1000) + "-" + String.valueOf(hero.getScore()) + "\n");
            historyFWriter.close();
            System.out.println("** Your score has been recorded! **");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
