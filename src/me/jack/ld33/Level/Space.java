package me.jack.ld33.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 22/08/2015.
 */
public class Space {


    protected int[][] tiles = null;
    protected int width, height, x, y;
    protected String id;
    public ArrayList<Door> doors = new ArrayList<Door>();


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

    public void setId(String id) {
        this.id = id;
    }
}
