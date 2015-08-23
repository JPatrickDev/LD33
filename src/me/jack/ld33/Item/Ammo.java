package me.jack.ld33.Item;

import me.jack.ld33.Item.Ranged.ProjectileType;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 23/08/2015.
 */
public class Ammo extends Item{

    private ProjectileType type;
    private int amount;
    public Ammo(ProjectileType type, int amount) {
        super(Weapon.weaponIcons.getSprite(0,2), Weapon.weaponSprites.getSprite(0,2), "Ammo");
        this.type = type;
        this.amount = amount;
    }

    public ProjectileType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
