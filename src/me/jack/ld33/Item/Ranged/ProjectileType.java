package me.jack.ld33.Item.Ranged;

import java.util.Random;

/**
 * Created by Jack on 23/08/2015.
 */
public enum ProjectileType {

    SMALL_BULLET(10f, 20f), BULLET(20f, 30f), FIRE(40f,40f);

    private float moveSpeed, damage;

    private ProjectileType(float moveSpeed, float damage){
        this.moveSpeed = moveSpeed;
        this.damage = damage;
        }

public float getMoveSpeed() {
        return moveSpeed;
        }

public float getDamage() {
        return damage;
        }

    public static Random random = new Random();
    public static ProjectileType randomProjectile() {
        return ProjectileType.values()[random.nextInt(ProjectileType.values().length)];
    }
}
