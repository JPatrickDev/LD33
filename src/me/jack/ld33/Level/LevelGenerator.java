package me.jack.ld33.Level;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Jack on 22/08/2015.
 */
public class LevelGenerator {

    public static Level generateLevel(int width, int height, boolean testing) {
        int[][] tiles = new int[width][height];
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                tiles[x][y] = 2;
            }
        }
        Level level = new Level(width, height, tiles);

        placeRooms(level);

        outlineRoomsAndCorridors(level);
        if (!testing)
            level.postGeneration();
        return level;

    }

    static Random r = new Random();

    public static void placeRooms(Level level) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        for (int i = 0; i != 10; i++) {
            int width = 5 + r.nextInt(8 - 4 + 1);
            int height = 5 + r.nextInt(8 - 4 + 1);
            int x = r.nextInt(level.getWidth() - width - 1) + 1;
            int y = r.nextInt(level.getHeight() - height - 1) + 1;
            Room room = new Room(x, y, width, height);
            boolean failed = false;
            for (Room r : rooms) {
                if (room.intersects(r)) {
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                createRoom(room, level);
                rooms.add(room);
            }
        }

        Room prev = null;
        for (Room r : rooms) {
            if (prev != null) {
                hCorridor(prev.getCenter().x, r.getCenter().x, prev.getCenter().y, level);
                vCorridor(prev.getCenter().y, r.getCenter().y, r.getCenter().x, level);
            }
            prev = r;
        }
    }

    private static void createRoom(Room room, Level level) {
        for (int x = room.getX(); x != room.getX() + room.getWidth(); x++) {
            for (int y = room.getY(); y != room.getY() + room.getHeight(); y++) {
                level.setTileAt(x, y, 1);
            }
        }
    }

    private static void hCorridor(int prevX, int newX, int y, Level level) {
        if (prevX > newX) {
            for (int x = prevX; x != newX; x--) {
                level.setTileAt(x, y, 1);
            }
        } else if (prevX < newX) {
            for (int x = prevX; x != newX; x++) {
                level.setTileAt(x, y, 1);
            }
        }
    }

    private static void vCorridor(int prevY, int newY, int x, Level level) {
        if (prevY > newY) {
            for (int y = prevY; y != newY; y--) {
                level.setTileAt(x, y, 1);
            }
        } else if (prevY < newY) {
            for (int y = prevY; y != newY; y++) {
                level.setTileAt(x, y, 1);
            }
        }
    }

    private static void outlineRoomsAndCorridors(Level level) {


        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                if (level.getTileAt(x, y) == 1) {
                    if(level.getTileAt(x+1,y+1)==2 && isCorner(x+1,y+1,level)){
                        level.setTileAt(x+1,y+1,7);
                    }
                    if(level.getTileAt(x-1,y+1)==2 && isCorner(x-1,y+1,level)){
                        level.setTileAt(x-1,y+1,8);
                    }
                    if(level.getTileAt(x-1,y-1) == 2 && isCorner(x-1,y-1,level)){
                        level.setTileAt(x-1,y-1,9);
                    }
                    if(level.getTileAt(x+1,y-1) == 2 && isCorner(x+1,y-1,level)){
                        level.setTileAt(x+1,y-1,10);
                    }
                }
            }
        }

        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                if (level.getTileAt(x, y) == 1) {
                    if (level.getTileAt(x - 1, y) == 2) {
                        level.setTileAt(x - 1, y, 3);
                    }
                    if (level.getTileAt(x, y - 1) == 2) {
                        level.setTileAt(x, y - 1, 4);
                    }

                    if (level.getTileAt(x + 1, y) == 2) {
                        level.setTileAt(x + 1, y, 5);
                    }

                    if (level.getTileAt(x, y + 1) == 2) {
                        level.setTileAt(x, y + 1, 6);
                    }


                }
            }
        }


    }

    public static boolean isCorner(int x, int y, Level level) {
        int floorCount = 0;
        int wallCount  = 0;

        int top = level.getTileAt(x,y-1);
        int topLeft = level.getTileAt(x-1,y-1);
        int topRight = level.getTileAt(x+1,y-1);

        int left = level.getTileAt(x-1,y);
        int right = level.getTileAt(x+1,y);

        int bottom = level.getTileAt(x,y+1);
        int bottomLeft = level.getTileAt(x-1,y+1);
        int bottomRight = level.getTileAt(x+1,y+1);

        if(top == 1)floorCount++;
        else wallCount++;

        if(topLeft == 1)floorCount++;
        else wallCount++;

        if(topRight == 1)floorCount++;
        else wallCount++;

        if(left == 1)floorCount++;
        else wallCount++;

        if(right == 1)floorCount++;
        else wallCount++;

        if(bottom == 1)floorCount++;
        else wallCount++;

        if(bottomLeft == 1)floorCount++;
        else wallCount++;

        if(bottomRight == 1)floorCount++;
        else wallCount++;

        if(wallCount == 7 && floorCount == 1){
            System.out.println("Found corner");
            return true;
        }
        return false;
    }

    private static boolean onlyTwo(boolean b1, boolean b2, boolean b3, boolean b4) {
        int total = 0;
        total += booleanToInt(b1);
        total += booleanToInt(b2);
        total += booleanToInt(b3);
        total += booleanToInt(b4);
        return total == 2;
    }

    private static int booleanToInt(boolean input) {
        if (input) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        while (true) {


            int w = 50;
            int h = 50;

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[w * h];

            Level level = generateLevel(w, h, true);
            System.out.println("Level generated");
            for (int x = 0; x != level.levelTiles.length; x++) {
                for (int y = 0; y != level.levelTiles[0].length; y++) {
                    float p = level.levelTiles[x][y];

                    Color c = null;
                    if (p == 1) c = Color.WHITE;
                    if (p == 2) c = Color.BLACK;
                    if (p == 3) c = Color.RED;
                    if (p == 4) c = Color.cyan;
                    if (p == 5) c = Color.green;
                    if (p == 6) c = Color.magenta;
                    if (p == 7 || p == 8 || p == 9 || p == 10) c = Color.pink;
                    pixels[x + y * w] = c.hashCode();

                }
            }


            image.setRGB(0, 0, w, h, pixels, 0, w);

            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(w * 4, h * 4, 0)));
        }
    }


}


