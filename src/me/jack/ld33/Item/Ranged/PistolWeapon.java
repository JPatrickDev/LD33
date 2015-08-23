package me.jack.ld33.Item.Ranged;

import me.jack.ld33.Item.RangedWeapon;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 23/08/2015.
 */
public class PistolWeapon extends RangedWeapon {
    public PistolWeapon() {
        super(weaponIcons.getSprite(0, 1), weaponSprites.getSprite(0, 1), "Pistol", 500, ProjectileType.SMALL_BULLET);
    }
}
