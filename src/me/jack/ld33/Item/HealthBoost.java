package me.jack.ld33.Item;

import me.jack.ld33.Item.Ranged.ProjectileType;

/**
 * Created by Jack on 23/08/2015.
 */
public class HealthBoost extends Item {


    private int amount;

    public HealthBoost(int amount) {
        super(Weapon.weaponIcons.getSprite(0, 3), Weapon.weaponSprites.getSprite(0, 3), "Health boost");
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
