package me.jack.ld33.Entity;

/**
 * Created by Jack on 22/08/2015.
 */
public abstract class Mob extends Entity{

    public float health = 0;
    public Mob(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
