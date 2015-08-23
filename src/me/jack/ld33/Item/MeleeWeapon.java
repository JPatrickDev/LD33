package me.jack.ld33.Item;

import org.newdawn.slick.Image;

/**
 * Created by Jack on 22/08/2015.
 */
public class MeleeWeapon extends Weapon{



    private float attackRadius;

    public MeleeWeapon(Image icon, Image sprite,String name) {
        super(icon, sprite,name);
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

}
