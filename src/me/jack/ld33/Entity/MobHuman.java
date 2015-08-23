package me.jack.ld33.Entity;

import me.jack.ld33.Item.Ammo;
import me.jack.ld33.Item.HealthBoost;
import me.jack.ld33.Item.Melee.AxeWeapon;
import me.jack.ld33.Item.Ranged.HumanPistolWeapon;
import me.jack.ld33.Item.Ranged.PistolWeapon;
import me.jack.ld33.Item.Ranged.ProjectileType;
import me.jack.ld33.Level.Level;
import me.jack.ld33.Particles.SmallBloodParticle;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;


import java.awt.Point;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class MobHuman extends Mob implements Mover {

    Human_Behaviour currentBehaviour = null;

    public int spritePos;
    public static ArrayList<Image> humanImages = new ArrayList<Image>();
    public static SpriteSheet humans = null;

    private boolean aggressive = false;

    private HumanPistolWeapon weapon;
    public MobHuman(int x, int y) {
        super(x, y, 32, 32);
        if (humans == null) {
            try {
                humans = new SpriteSheet("res/human_selection.png", 32, 32);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            for(int xx = 0;xx!= 2;xx++){
                for(int yy = 0;yy!= 2;yy++){
                    humanImages.add(humans.getSprite(xx,yy));
                }
            }

        }
        spritePos = random.nextInt(humanImages.size());
        currentBehaviour = Human_Behaviour.randomBehaviour();
        currentBehaviour = Human_Behaviour.FLEEING;
        this.health = 20f;
        this.maxHealth = 20f;
        if(random.nextInt(3) == 0) {
            weapon = new HumanPistolWeapon();
            aggressive = true;
        }
    }

    Point patrol1, patrol2;
    int patrolTo = -1;
    Path patrolPath = null;


    private Color randColour() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }

    int step = 1;


    Point movingTo;
    Path currentPath;
    public
    AStarPathFinder aStarPathFinder;
    public static Random random = new Random();

    @Override
    public void update(Level level, float delta) {
        if (health <= 0) {
            level.entities.remove(this);
            for (int i = 0; i != 25; i++)
                level.particleSystem.addParticle(new SmallBloodParticle(getX(), getY()));
            if(random.nextInt(10) == 0)
            level.dropItem(new Ammo(ProjectileType.randomProjectile(), random.nextInt(10) + 20), getX(), getY());
            if(random.nextInt(50) == 0){
                level.dropItem(new HealthBoost(random.nextInt(15) + 5),getX(),getY());
            }
            level.humansAlive--;
            level.humansKilled++;
            return;
        }

        if(aggressive){

            Rectangle me = new Rectangle(getX(),getY(),getWidth(),getHeight());
            if(me.intersects(level.getPlayer().attractRadius)){
                if(random.nextInt(35) == 0){
                    weapon.fire(level.getPlayer().getX(),level.getPlayer().getY(),level,this);
                }
                if(!me.intersects(level.getPlayer().stopRadius)) {
                    if (getX() > level.getPlayer().getX()) {
                        if(level.canMove(getX() -4,getY(),getWidth(),getHeight(),this))
                        addX(-4);
                    }
                    if (getX() < level.getPlayer().getX()) {
                        if(level.canMove(getX()+ 4,getY(),getWidth(),getHeight(),this))
                        addX(4);
                    }
                    if (getY() > level.getPlayer().getY()) {
                        if(level.canMove(getX(),getY() -4,getWidth(),getHeight(),this))
                        addY(-4);
                    }
                    if (getY() < level.getPlayer().getY()) {
                        if(level.canMove(getX(),getY() + 4,getWidth(),getHeight(),this))
                        addY(4);
                    }
                }
                return;
            }
        }

        if (aStarPathFinder == null)
            aStarPathFinder = new AStarPathFinder(level, 20, true);
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
                } else {
                    try {
                        moveToStep(currentPath.getStep(step), level);
                    } catch (Exception e) {
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
      /*  float xSpeed = ((nextStep.getX() * 64) - getX());
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
            currentPath = null;
        }
        if (random.nextInt(250) == 0) {
            movingTo = null;
            currentPath = null;
            step = 1;
        }*/
        if (getX() > nextStep.getX() * 64) {
            if(level.canMove(getX()-4,getY(),getWidth(),getHeight(),this))
            addX(-4);
            else {
                step = 1;
                currentPath = null;
                movingTo = null;
            }

        }
        if (getX() < nextStep.getX() * 64) {
            if(level.canMove(getX()+4,getY(),getWidth(),getHeight(),this))
            addX(4);
            else {
                step = 1;
                currentPath = null;
                movingTo = null;
            }
        }

        if (getY() > nextStep.getY() * 64) {
            if(level.canMove(getX(),getY()-4,getWidth(),getHeight(),this))
            addY(-4);
            else {
                step = 1;
                currentPath = null;
                movingTo = null;
            }
        }
        if (getY() < nextStep.getY() * 64) {
            if(level.canMove(getX(),getY()+4,getWidth(),getHeight(),this))
            addY(4);
            else {
                step = 1;
                currentPath = null;
                movingTo = null;
            }
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

        /*if (currentPath != null)
            for (int i = 0; i != currentPath.getLength(); i++) {
               Path.Step step = currentPath.getStep(i);
                 graphics.fillRect(step.getX()*64,step.getY()*64,64,64);
          }
        if (movingTo != null)
            graphics.fillRect(movingTo.x, movingTo.y, 64, 64);
            */
        graphics.drawImage(humanImages.get(spritePos), getX(), getY());
    }

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
