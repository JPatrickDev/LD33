package me.jack.ld33.Level.Tile;

/**
 * Created by Jack on 23/08/2015.
 */
public class ChestTile extends Tile{

    public ChestTile() {
        super(0, 0, "ChestTile", true,11);
        this.tileImage = altSprites.getSubImage(0,0).getScaledCopy(4f);
    }
}

