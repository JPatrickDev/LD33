package me.jack.ld33.Level;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class Corridor extends Space{

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;



    private int[][] tiles = null;
    private String id;
    private int facing;
    private int width, height, x, y;



    public Corridor(int facing,int x,int y){
        this.facing = facing;
        this.id = id;
        int dX = 0, dY = 0;
        switch (facing) {
            case NORTH:
                width = 5;
                height = 8;
                this.x = x - 2;
                this.y = y - (height-1);
                dX = 2;
                dY =height-1;
                doors.add(new Door(2,0,NORTH,Door.ROOM));
                break;
            case EAST:
                height = 5;
                width = 8;
                this.x = x;
                this.y = y-2;
                dX = 0;
                dY = 2;
                doors.add(new Door(width-1,2,EAST,Door.ROOM));
                break;
            case SOUTH:
                width = 5;
                height = 8;
                this.x = x-2;
                this.y = y;
                dX = 2;
                dY = 0;
                doors.add(new Door(2,height-1,SOUTH,Door.ROOM));
                break;

            case WEST:
                height = 5;
                width = 8;
                this.x = x-(width-1);
                this.y = y - 2;
                dY = 2;
                dX = width-1;
                doors.add(new Door(0,2,WEST,Door.ROOM));
                break;
        }

        System.out.println(width + ":" + height);
        tiles = new int[width][height];

        for (int xx = 0; xx != getWidth(); xx++) {
            for (int yy = 0; yy != getHeight(); yy++) {
                if (yy != 0 && xx != 0 && yy != getHeight() - 1 && xx != getWidth() - 1)
                    setTileAt(xx, yy,1);
                  else
                    setTileAt(xx, yy, 2);
            }
        }

        setTileAt(dX,dY,1);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
