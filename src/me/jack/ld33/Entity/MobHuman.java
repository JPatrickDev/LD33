package me.jack.ld33.Entity;

import me.jack.ld33.Item.Melee.AxeWeapon;
import me.jack.ld33.Level.Level;
import me.jack.ld33.Particles.SmallBloodParticle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;


import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 22/08/2015.
 */
public class MobHuman extends Mob implements Mover {

    Human_Behaviour currentBehaviour = null;

    public static Image humanSprite;

    public MobHuman(int x, int y) {
        super(x, y, 32, 32);
        if (humanSprite == null) {
            humanSprite = ImageUtil.loadImage("res/human.png");
        }
        currentBehaviour = Human_Behaviour.randomBehaviour();
        currentBehaviour = Human_Behaviour.FLEEING;
        this.health = 20f;
    }

    Point patrol1, patrol2;
    int patrolTo = -1;
    Path patrolPath = null;


    int step = 1;


    Point movingTo;
    Path currentPath;
    public static AStarPathFinder aStarPathFinder;
    public static Random random = new Random();

    @Override
    public void update(Level level, float delta) {

        if (health <= 0) {
            level.entities.remove(this);
            for (int i = 0; i != 25; i++)
                level.particleSystem.addParticle(new SmallBloodParticle(getX(), getY()));
            level.dropItem(new AxeWeapon(),getX(),getY());
            return;
        }

        if (aStarPathFinder == null)
            aStarPathFinder = new AStarPathFinder(level, 100, true);
        switch (currentBehaviour) {
            case STANDING:
                break;
            case PATROLLING:
                break;
            case WANDERING:
                if (movingTo == null) {
                    boolean found = false;
                    while (!found) {
                        System.out.println("Trying");
                        int dX = random.nextInt(5) + 2;
                        int dY = random.nextInt(5) + 2;
                        if (random.nextBoolean()) {
                            dX *= -1;
                        }
                        if (random.nextBoolean()) {
                            dY *= -1;
                        }
                        dX *= 64;
                        dY *= 64;

                        int newX = getX() + dX;
                        int newY = getY() + dY;
                        if (level.getTileAt(newX / 64, newY / 64) == 1) {
                            found = true;
                            movingTo = new Point(newX, newY);
                        }
                    }
                    currentPath = aStarPathFinder.findPath(this, getX() / 64, getY() / 64, (int) movingTo.getX() / 64, (int) movingTo.getY() / 64);
                } else {
                    Path.Step nextStep = currentPath.getStep(step);
                    moveToStep(nextStep, level);

                }

            case FLEEING:
               /* int playerX = level.getPlayer().getX();
                int playerY = level.getPlayer().getY();

                int dX = 0;
                int dY = 0;
                if (playerX > getX()) {
                    dX = -4;
                } else if (playerX < getX()) {
                    dX = 4;
                }

                if (playerY > getY()) {
                    dY = -4;
                } else if (playerY < getY())
                    dY = 4;

                if (level.canMove(getX() + dX, getY(), getWidth(), getHeight(),this)) {
                    addX(dX);
                }
                if (level.canMove(getX(), getY() + dY, getWidth(), getHeight(),this))
                    addY(dY);
                */
                if (currentPath == null) {
                    boolean foundFloor = false;
                    while (!foundFloor) {
                        int rX = random.nextInt(level.getWidth());
                        int rY = random.nextInt(level.getHeight());
                        if (level.getTileAt(rX, rY) == 1) {
                            foundFloor = true;
                            currentPath = aStarPathFinder.findPath(this, getX() / 64, getY() / 64, rX, rY);
                        }
                    }
                }else{
                    try {
                        moveToStep(currentPath.getStep(step), level);
                        moveToStep(currentPath.getStep(step), level);
                    }catch (Exception e){
                        movingTo = null;
                        currentPath = null;
                        step = 1;
                    }
                }

        }

    }

    public void notifyEnterPlayerSight() {
        changeBehaviour(Human_Behaviour.FLEEING);
    }

    public void notifyLeavePlayerSight() {
        changeBehaviour(Human_Behaviour.WANDERING);
    }

    private void changeBehaviour(Human_Behaviour newBehaviour) {
        step = 1;
        currentPath = null;
        movingTo = null;
        this.currentBehaviour = newBehaviour;
    }

    private void moveToStep(Path.Step nextStep, Level level) {
        float xSpeed = ((nextStep.getX() * 64) - getX());
        float ySpeed = ((nextStep.getY() * 64) - getY());
        float factor = (float) (4 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xSpeed *= factor;
        ySpeed *= factor;
        if (level.canMove((int) (getX() + xSpeed), (int) (getY() + ySpeed), 32, 32, this)) {
            addX((int) xSpeed);
            addY((int) ySpeed);
        } else {
            step = 1;
            movingTo = null;
        }
        if (random.nextInt(500) == 0 || step >= currentPath.getLength() - 1) {
            movingTo = null;
            currentPath = null;
            step = 1;
        }
        Rectangle nextStepRect = new Rectangle(nextStep.getX() * 64, nextStep.getY() * 64, 64, 64);
        if (new Rectangle(getX(), getY(), getWidth(), getHeight()).intersects(nextStepRect)) {
            step++;
        }
    }


    @Override
    public void render(Graphics graphics) {

        if (patrol1 != null && patrol2 != null) {
            graphics.setColor(Color.cyan);
            graphics.fillRect((int) patrol1.getX(), (int) patrol1.getY(), 16, 16);
            graphics.fillRect((int) patrol2.getX(), (int) patrol2.getY(), 16, 16);
            graphics.setColor(Color.white);
        }

        //if (currentPath != null)
        //    for (int i = 0; i != currentPath.getLength(); i++) {
        //       Path.Step step = currentPath.getStep(i);
        //          graphics.fillRect(step.getX()*64,step.getY()*64,64,64);
        //   }
        if (movingTo != null)
            graphics.fillRect(movingTo.x, movingTo.y, 64, 64);
     graphics.drawImage(humanSprite,getX(),getY());
    }

}


enum Human_Personality {
    NEUTRAL, AGGRESSIVE, SCARED;
}

enum Human_Behaviour {
    STANDING, PATROLLING, WANDERING, FLEEING;

    private static final List<Human_Behaviour> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Human_Behaviour randomBehaviour() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
