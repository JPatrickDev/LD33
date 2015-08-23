package me.jack.ld33.Item.Melee;

import me.jack.ld33.Item.MeleeWeapon;

/**
 * Created by Jack on 23/08/2015.
 */
public class AxeWeapon extends MeleeWeapon {

    public AxeWeapon() {
        super(weaponIcons.getSprite(1,0), weaponSprites.getSprite(1,0),"Axe");
        setAttackRadius(128f);
        setCondition(100f);
        setDamage(15f);
        setMaxCondition(100f);
    }

}
