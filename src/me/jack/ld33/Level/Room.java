package me.jack.ld33.Level;

import java.util.Random;

/**
 * Created by Jack on 22/08/2015.
 */
public class Room {

    private int[][] tiles = null;
    private int width, height;
    private String id;

    public Room(String id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.tiles = new int[width][height];
        for (int x = 0; x != getWidth(); x++) {
            for (int y = 0; y != getHeight(); y++) {
                if (y != 0 && x != 0 && y != getWidth() - 1 && x != getHeight() - 1)
                    setTileAt(x, y, 1);
                else
                    setTileAt(x, y, 2);
            }
        }


    }

    public static Random random = new Random();

    private void generateDoors() {
        int minDoors = 1;
        int maxDoors = 4;
        int doorsToGen = random.nextInt(maxDoors) + 1;
        boolean leftWallHasDoor = false;
        boolean rightWallHasDoor = false;
        boolean topWallHasDoor = false;
        boolean bottomWallHasDoor = false;
        for (int i = 0; i != doorsToGen; i++) {

        }
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getId() {
        return id;
    }

    public int getTileAt(int x, int y) {
        return tiles[x][y];
    }

    public void setTileAt(int x, int y, int i) {
        this.tiles[x][y] = i;
    }
}
