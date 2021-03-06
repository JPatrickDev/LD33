package me.jack.ld33.Level.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;

/**
 * Created by Jack on 22/08/2015.
 */
public class Tile {

    //TILE IDS

    public static final int StoneFloor = 1;










    public static SpriteSheet tileSpriteSheet = null,altSprites;
    private static HashMap<Integer, Tile> tileLookup = new HashMap<Integer, Tile>();


    public static void initTiles() throws SlickException {
        tileSpriteSheet = new SpriteSheet("res/floor_1_tileset.png", 16, 16);
        altSprites = new SpriteSheet("res/tileset.png", 16, 16);new StoneFloor();
        new StoneWall();
        new StoneWallLeft();
        new StoneWallTop();
        new StoneWallRight();
        new StoneWallBottom();
        new StoneCornerBottomRight();
        new StoneCornerBottomLeft();
        new StoneCornerTopLeft();
        new StoneCornerTopRight();
        new ChestTile();
    }


    private int x, y;
    private String name;
    private boolean solid;
    protected Image tileImage;

    public Tile(int x, int y, String name, boolean solid, int id) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.solid = solid;
        this.tileImage = tileSpriteSheet.getSubImage(x, y).getScaledCopy(4f);
        tileLookup.put(id, this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public boolean isSolid() {
        return solid;
    }

    public void render(int rX, int rY, Graphics graphics) {
        graphics.drawImage(tileImage, rX, rY);
    }

    public static Tile getTile(int i) {
        return tileLookup.get(i);
    }
}
