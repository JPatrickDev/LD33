package me.jack.ld33.Item.Melee;

import me.jack.ld33.Item.MeleeWeapon;
import org.newdawn.slick.Image;

/**
 * Created by Jack on 22/08/2015.
 */
public class DaggerWeapon extends MeleeWeapon{

    public DaggerWeapon() {
        super(weaponIcons.getSprite(0,0), weaponSprites.getSprite(0,0));
        setAttackRadius(64f);
        setCondition(100f);
        setDamage(5f);
        setMaxCondition(100f);
    }

}