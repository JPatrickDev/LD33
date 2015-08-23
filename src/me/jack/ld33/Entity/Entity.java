package me.jack.ld33.Entity;

import me.jack.ld33.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 22/08/2015.
 */
public abstract class Entity {
    private int x, y, width, height;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics graphics) {

        graphics.fillRect(x, y, width, height);
    }

    public abstract void update(Level level,float delta);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }
}
