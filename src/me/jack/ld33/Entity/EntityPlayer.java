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
                addY(-8);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                addY(8);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
             addX(-8);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
               addX(8);
            }




    }

}
