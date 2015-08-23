package me.jack.ld33.Item.Ranged;

import me.jack.ld33.Item.RangedWeapon;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 23/08/2015.
 */
public class FlameThrowerWeapon extends RangedWeapon{
    public FlameThrowerWeapon() {
        super(weaponIcons.getSprite(1,2), weaponSprites.getSprite(1,2), "Flamethrower", 1, ProjectileType.FIRE);
    }
}
