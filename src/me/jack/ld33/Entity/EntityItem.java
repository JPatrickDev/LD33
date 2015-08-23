package me.jack.ld33.Entity;

import me.jack.ld33.Item.Ammo;
import me.jack.ld33.Item.Item;
import me.jack.ld33.Item.Weapon;
import me.jack.ld33.Level.Level;
import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by Jack on 23/08/2015.
 */
public class EntityItem extends Entity {

    private Item item;

    public EntityItem(int x, int y, Item item) {
        super(x, y, 32, 32);
        this.item = item;
    }

    @Override
    public void update(Level level, float delta) {
        Rectangle player = level.getPlayerHitbox();
        Rectangle itemHitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
        if (itemHitbox.intersects(player)) {
            if (item instanceof Weapon) {
                if (level.getPlayer().canPickupWeapon((Weapon) item) != -1) {
                    level.getPlayer().getWeapons().setSlot(level.getPlayer().canPickupWeapon((Weapon) item), (Weapon) item);
                    level.entities.remove(this);
                }
            } else if (item instanceof Ammo) {
                Ammo ammo = (Ammo) item;
                level.getPlayer().addAmmo(ammo.getAmount(),ammo.getType());
                level.entities.remove(this);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(item.getSprite(), getX(), getY());
    }

}
