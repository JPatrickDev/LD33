package me.jack.ld33.Level;

import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 22/08/2015.
 */
public class Door {

    private int x,y;
    private static Image doorImage;
    private int facing;
    private int targetType = -1;

    public static final int ROOM = 1;
    public static final int CORRIDOR = 2;

    public Door(int x, int y, int facing,int targetType) {
        this.x = x;
        this.y = y;
        this.facing = facing;
        this.targetType = targetType;
        if(doorImage == null){
            doorImage = Tile.tileSpriteSheet.getSubImage(2,0).getScaledCopy(2f);
        }
    }

    public void render(Graphics graphics){
        graphics.drawImage(doorImage,x * Level.TILESIZE,y*Level.TILESIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFacing() {
        return facing;
    }

    public int getTargetType() {
        return targetType;
    }
}
