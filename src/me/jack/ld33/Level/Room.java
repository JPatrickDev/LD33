package me.jack.ld33.Level;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class Room {

    private Rectangle rectangle;
    private Point center = null;
    private int x, y, width, height;

    public Room(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
        center = new Point(x + (width / 2), y + (height / 2));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public boolean intersects(Room room) {
        return room.getRectangle().intersects(rectangle);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

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

    public Point getCenter() {
        return center;
    }
}
