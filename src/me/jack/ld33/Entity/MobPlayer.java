package me.jack.ld33.Entity;

import me.jack.ld33.Item.MeleeWeapon;
import me.jack.ld33.Item.Ranged.ProjectileType;
import me.jack.ld33.Item.RangedWeapon;
import me.jack.ld33.Item.Weapon;
import me.jack.ld33.Item.WeaponInventory;
import me.jack.ld33.Level.Level;
import me.jack.ld33.States.InGameState;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 22/08/2015.
 */
public class MobPlayer extends Mob {

    Circle attackRadius;

    WeaponInventory weaponInventory = new WeaponInventory();
    public int selectedWeaponSlot = 0;

    public static Image playerSprite;

    public HashMap<ProjectileType,Integer> ammo = new HashMap<ProjectileType, Integer>();

    public MobPlayer(int x, int y) {
        super(x, y, 32, 32);
        if (playerSprite == null) {
            playerSprite = ImageUtil.loadImage("res/player.png");
            playerSprite.setCenterOfRotation(16, 16);
        }
        this.health = 50f;
        attackRadius = new Circle(x, y, 64f);
    }


    public float angle;

    @Override
    public void update(Level level, float delta) {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(getX(), getY() - 8, getWidth(), getHeight(), this))
                addY(-8);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(getX(), getY() + 8, getWidth(), getHeight(), this))
                addY(8);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(getX() - 8, getY(), getWidth(), getHeight(), this))
                addX(-8);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(getX() + 8, getY(), getWidth(), getHeight(), this))
                addX(8);
        }


        attackRadius.setCenterX(getX() + getWidth() / 2);
        attackRadius.setCenterY(getY() + getHeight() / 2);

    }


    public void attack(Level level) {
        Weapon weapon = weaponInventory.getWeapon(selectedWeaponSlot);

        if (weapon == null) {
            System.out.println("No weapon equipped");
            return;
        }
        if (weapon instanceof MeleeWeapon)
            meleeAttack(level, (MeleeWeapon) weapon);
        else if(weapon instanceof RangedWeapon){
            rangedAttack(level,(RangedWeapon)weapon);
        }
    }

    private void rangedAttack(Level level, RangedWeapon weapon) {
        weapon.fire(InGameState.mX + level.camera.x,InGameState.mY + level.camera.y,level,this);
    }


    private void meleeAttack(Level level, MeleeWeapon meleeWeapon) {
        meleeWeapon.setCondition(meleeWeapon.getCondition() - 1);
        attackRadius.setRadius(meleeWeapon.getAttackRadius());
        ArrayList<Entity> inRange = level.entitiesInShape(attackRadius);
        for (Entity e : inRange) {
            if (e instanceof Mob) {
                ((Mob) e).health -= meleeWeapon.getDamage();
            }
        }
        if (meleeWeapon.getCondition() <= 0) {
            weaponInventory.setSlot(selectedWeaponSlot, null);
        }
    }


    @Override
    public void render(Graphics graphics) {
        //   graphics.fill(attackRadius);
        graphics.drawImage(playerSprite, getX(), getY());


    }

    public WeaponInventory getWeapons() {
        return weaponInventory;
    }

    public int canPickupWeapon(Weapon item) {
        int pos = -1;
        for (int i = 0; i != 6; i++) {
            if (getWeapons().getWeapon(i) == null) {
                pos = i;
                return pos;
            }
        }
        return -1;
    }

    public void equip(Weapon currentOverlay) {
        int pos = weaponInventory.getWeaponPos(currentOverlay);
        if (pos != -1) {
            selectedWeaponSlot = pos;
        }
    }

    public void addAmmo(int ammoToAdd, ProjectileType type){
        if(ammo.containsKey(type)){
            ammo.put(type,ammo.get(type)+ammoToAdd);
        }else{
            ammo.put(type,ammoToAdd);
        }
    }

}
