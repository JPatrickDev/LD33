package me.jack.ld33.Item.Ranged;

import me.jack.ld33.Item.RangedWeapon;

/**
 * Created by Jack on 23/08/2015.
 */
public class HumanPistolWeapon extends RangedWeapon {
    public HumanPistolWeapon() {
        super(weaponIcons.getSprite(0, 1), weaponSprites.getSprite(0, 1), "Pistol", 500, ProjectileType.HUMAN_BULLET);
    }
}
