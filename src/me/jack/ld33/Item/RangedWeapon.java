package me.jack.ld33.Item;

import me.jack.ld33.Entity.Entity;
import me.jack.ld33.Entity.Mob;
import me.jack.ld33.Entity.MobHuman;
import me.jack.ld33.Entity.MobPlayer;
import me.jack.ld33.Item.Ranged.ProjectileType;
import me.jack.ld33.Level.Level;
import org.newdawn.slick.Image;

import java.awt.*;

/**
 * Created by Jack on 23/08/2015.
 */
public class RangedWeapon extends Weapon {

    private ProjectileType projectileType = null;
    private long lastShot;
    private int shotDelay;

    public RangedWeapon(Image icon, Image sprite, String name, int shotDelay,ProjectileType projectileType) {
        super(icon, sprite, name);
        this.projectileType = projectileType;
        this.shotDelay = shotDelay;
    }

    public void fire(int tX, int tY, Level level, Mob user) {
        if(lastShot == 0)
            lastShot = System.currentTimeMillis();
        else{
            long timeBetween = System.currentTimeMillis() - lastShot;
            System.out.println(timeBetween);
            if(timeBetween < shotDelay){
                return;
            }
        }
        if(user instanceof MobPlayer){
            System.out.println(level.getPlayer().ammo.get(projectileType));
            if(level.getPlayer().ammo.get(projectileType) <= 0)return; //TODO SOUND EFFECT?
            level.getPlayer().ammo.put(projectileType,level.getPlayer().ammo.get(projectileType)-1);
        }
        float xSpeed = ((tX) - user.getX());
        float ySpeed = ((tY) - user.getY());
        float factor = (float) (20 / Math
                .sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xSpeed = xSpeed * factor;
        ySpeed = ySpeed * factor;
        level.entities.add(new Projectile(xSpeed, ySpeed, user.getX(), user.getY(), projectileType));
        lastShot = System.currentTimeMillis();
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }
}

class Projectile extends Entity {
    private static Image sprite;
    private float vX, vY;
    private ProjectileType type;

    public Projectile(float vX, float vY, int x, int y, ProjectileType type) {
        super(x, y, 4, 4);
        this.vX = vX;
        this.vY = vY;
        this.type = type;
    }

    public static Image getSprite() {
        return sprite;
    }

    public float getvX() {
        return vX;
    }

    public float getvY() {
        return vY;
    }

    @Override
    public void update(Level level, float delta) {
        if (level.canMove((int) (getX() + vX), (int) (getY() + vY), getWidth(), getHeight(), this)) {
            addY((int) vY);
            addX((int) vX);
        } else {
            level.entities.remove(this);
        }

        Rectangle me = new Rectangle(getX(), getY(), getWidth(), getHeight());
        for (Entity e : level.entities) {
            if (!(e instanceof MobHuman)) continue;

            Rectangle hRect = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            if(me.intersects(hRect)){
                MobHuman mh = (MobHuman) e;
                mh.health-= type.getDamage();
                level.entities.remove(this);
            }
        }
    }

    public ProjectileType getType() {
        return type;
    }
}