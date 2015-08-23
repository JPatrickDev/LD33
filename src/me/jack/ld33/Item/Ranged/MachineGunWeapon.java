package me.jack.ld33.Item.Ranged;

import me.jack.ld33.Item.RangedWeapon;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 23/08/2015.
 */
public class MachineGunWeapon extends RangedWeapon{
    public MachineGunWeapon() {
        super(weaponIcons.getSprite(1,1),weaponSprites.getSprite(1,1), "Machine Gun",50, ProjectileType.BULLET);
        setCondition(750f);
        setMaxCondition(750f);
    }
}
