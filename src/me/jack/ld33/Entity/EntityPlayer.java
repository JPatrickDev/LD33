package me.jack.ld33.Entity;

import me.jack.ld33.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class EntityPlayer extends Entity {

    Circle attackRadius;
    public EntityPlayer(int x, int y) {
        super(x, y, 32, 32);
        attackRadius = new Circle(x,y,64f);
    }


    @Override
    public void update(Level level, float delta) {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(getX(), getY() - 8, getWidth(), getHeight()))
                addY(-8);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(getX(), getY() + 8, getWidth(), getHeight()))
                addY(8);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(getX() - 8, getY(), getWidth(), getHeight()))
                addX(-8);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(getX() + 8, getY(), getWidth(), getHeight()))
                addX(8);
        }


        attackRadius.setCenterX(getX() + getWidth()/2);
        attackRadius.setCenterY(getY() + getHeight()/2);

    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        graphics.fill(attackRadius);
    }
}
