package me.jack.ld33.Item.Ranged;

/**
 * Created by Jack on 23/08/2015.
 */
public enum ProjectileType {

    SMALL_BULLET(10f,20f);

    private float moveSpeed, damage;

    private ProjectileType(float moveSpeed,float damage){
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getDamage() {
        return damage;
    }
}
