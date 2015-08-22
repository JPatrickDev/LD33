package me.jack.ld33.Level;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Jack on 22/08/2015.
 */
public class Room extends Space {



    public Room(int width, int height, int startX, int startY) {
        this.width = width;
        this.height = height;
        this.tiles = new int[width][height];
        for (int x = 0; x != getWidth(); x++) {
            for (int y = 0; y != getHeight(); y++) {
                if (y == 0 || x == 0 || y == getHeight()-1 || x == getWidth()-1)
                    setTileAt(x, y,2);
                else
                    setTileAt(x, y, 1);
            }
        }
        this.x = startX;
        this.y = startY;
        generateDoors();
        System.out.println(id);
        for (int y = 0; y != getHeight(); y++) {
            String line = "";
            for (int x = 0; x != getWidth(); x++) {

                if (getTileAt(x, y) == 1){
                    line+="_";
                }else{
                    line+="#";
                }
            }
            System.out.println(line);
        }
    }

    public static Random random = new Random();

    private void generateDoors() {
        int minDoors = 2;
        int maxDoors = 3;
        int doorsToGen = random.nextInt((maxDoors - minDoors) + 1) + minDoors;
        System.out.println(doorsToGen);
        boolean leftWallHasDoor = false;
        boolean rightWallHasDoor = false;
        boolean topWallHasDoor = false;
        boolean bottomWallHasDoor = false;

        for (int i = 0; i != maxDoors; i++) {
            boolean generationSuccessful = false;


            while (!generationSuccessful) {


                int wallToChange = random.nextInt(4);
                switch (wallToChange) {
                    case 0:
                        if (!topWallHasDoor) {
                            topWallHasDoor = true;
                            generationSuccessful = true;
                            int randX = random.nextInt(getWidth() - 1) + 1;
                            setTileAt(randX, 0, 1);
                            Door door = new Door(randX,0,Corridor.NORTH,Door.CORRIDOR);
                            doors.add(door);
                        }
                        break;
                    case 1:
                        if (!rightWallHasDoor) {
                            rightWallHasDoor = true;
                            generationSuccessful = true;
                            int randY = random.nextInt(getHeight() - 1) + 1;
                            setTileAt(getWidth() - 1, randY, 1);
                            Door door = new Door(getWidth() - 1, randY,Corridor.EAST,Door.CORRIDOR);
                            doors.add(door);
                        }
                        break;
                    case 2:
                        if (!bottomWallHasDoor) {
                            bottomWallHasDoor = true;
                            generationSuccessful = true;
                            int randX = random.nextInt(getWidth() - 1) + 1;
                            setTileAt(randX, getHeight() - 1, 1);
                            Door door = new Door(randX, getHeight() - 1,Corridor.SOUTH,Door.CORRIDOR);
                            doors.add(door);
                        }
                        break;
                    case 3:
                        if (!leftWallHasDoor) {
                            leftWallHasDoor = true;
                            generationSuccessful = true;
                            int randY = random.nextInt(getHeight() - 1) + 1;
                            setTileAt(0, randY, 1);
                            Door door = new Door(0, randY,Corridor.WEST,Door.CORRIDOR);
                            doors.add(door);
                        }
                        break;
                }

            }
        }
    }



}
