package me.jack.ld33.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Jack on 22/08/2015.
 */
public class Weapon extends Item{

    public static SpriteSheet weaponIcons = null;
    public static SpriteSheet weaponSprites = null;

    public static void init() throws SlickException {
        weaponIcons = new SpriteSheet("res/weapon_icons.png",16,16);
        weaponSprites = new SpriteSheet("res/weapon_sprites.png",32,32);
    }
    private float damage, condition,maxCondition;
    public Weapon(Image icon, Image sprite) {
        super(icon, sprite);
    }

    public float getDamage() {
        return damage;
    }

    public float getCondition() {
        return condition;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setCondition(float condition) {
        this.condition = condition;
    }

    public float getMaxCondition() {
        return maxCondition;
    }

    public void setMaxCondition(float maxCondition) {
        this.maxCondition = maxCondition;
    }
}
