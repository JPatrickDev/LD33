package me.jack.ld33.Entity;

import me.jack.ld33.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Vector2f;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class EntityPlayer extends Entity {

    public EntityPlayer(int x, int y) {
        super(x, y, 32, 32);
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


    }

}
