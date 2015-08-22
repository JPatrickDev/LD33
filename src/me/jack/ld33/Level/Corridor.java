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

        switch (facing) {
            case NORTH:
                width = 5;
                height = Room.random.nextInt(6) + 4;
                this.x = x - 2;
                this.y = y - height;
                break;
            case EAST:
                height = 5;
                width = 8;
                this.x = x;
                this.y = y-2;
                doors.put(new Point(0,2),"");
                doors.put(new Point(width-1,2),"");
                break;
            case SOUTH:
                width = 5;
                height = 8;
                this.x = x-2;
                this.y = y;
                doors.put(new Point(2,0),"");
                doors.put(new Point(2,height-1),"");
                break;

            case WEST:
                height = 5;
                width = 8;
                this.x = x-width;
                this.y = y - 2;
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

        for(Point point : doors.keySet()){
            setTileAt(point.x,point.y,3);
        }


    }

    public void init() {
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
