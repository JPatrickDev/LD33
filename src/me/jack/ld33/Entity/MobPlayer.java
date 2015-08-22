package me.jack.ld33.Entity;

import me.jack.ld33.Level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 22/08/2015.
 */
public class MobPlayer extends Mob {

    Circle attackRadius;
    public MobPlayer(int x, int y) {
        super(x, y, 32, 32);
        this.health = 50f;
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


    public void attack(Level level){
        ArrayList<Entity> inRange = level.entitiesInShape(attackRadius);
        for(Entity e : inRange){
            if(e instanceof Mob){
                System.out.println("Found mob in range");
                ((Mob)e).health-=5;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
      //  graphics.fill(attackRadius);
    }
}
