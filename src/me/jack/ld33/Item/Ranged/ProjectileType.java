package me.jack.ld33.Item.Ranged;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jack on 23/08/2015.
 */
public enum ProjectileType {

    SMALL_BULLET(10f, 20f, new Point(0, 2)), BULLET(20f, 30f, new Point(1, 3)), FIRE(40f, 40f, new Point(0, 4)), HUMAN_BULLET(10f, 2f, new Point(0, 2));

    private float moveSpeed, damage;
    private Point ammoSprite;

    private ProjectileType(float moveSpeed, float damage, Point ammoSprite) {
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        this.ammoSprite = ammoSprite;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getDamage() {
        return damage;
    }


    public Point getAmmoSprite() {
        return ammoSprite;
    }

    public static Random random = new Random();

    public static ProjectileType randomProjectile() {
        return ProjectileType.values()[random.nextInt(ProjectileType.values().length)];
    }
}
