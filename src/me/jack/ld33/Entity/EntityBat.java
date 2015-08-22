package me.jack.ld33.Entity;

import me.jack.ld33.Level.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

import java.util.Random;

/**
 * Created by Jack on 22/08/2015.
 */
public class EntityBat extends Entity {

    float xVel,yVel;

    public static final Random random = new Random();
    public static Image batSprite = null;
    public EntityBat(int x, int y) {
        super(x, y, 24, 24);
        if(batSprite == null){
            batSprite = ImageUtil.loadImage("res/bat.png");
        }
        xVel = (float) (random.nextGaussian()*2);
        yVel = (float) (random.nextGaussian()*2);
        if(random.nextBoolean()){
            xVel *=-1;
        }
        if(random.nextBoolean()){
            yVel *=-1;
        }
    }

    @Override
    public void update(Level level, float delta) {
        if(level.canMove((int)(getX()+xVel),(int)(getY()+yVel),getWidth(),getHeight())) {
            addX((int) xVel);
            addY((int) yVel);
        }else{
            xVel *=-1;
            yVel *=-1;
        }

        if(random.nextInt(30) == 0){
            xVel = random.nextFloat()*5;
            yVel = random.nextFloat()*5;
            if(random.nextBoolean()){
                xVel *=-1;
            }
            if(random.nextBoolean()){
                yVel *=-1;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(batSprite,getX(),getY());
    }
}
