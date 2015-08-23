package me.jack.ld33.Entity;

import me.jack.ld33.Item.*;
import me.jack.ld33.Item.Ranged.ProjectileType;
import me.jack.ld33.Level.Level;
import me.jack.ld33.States.InGameState;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jack on 22/08/2015.
 */
public class MobPlayer extends Mob {

    Circle attractRadius;
    Circle stopRadius;

    WeaponInventory weaponInventory = new WeaponInventory();
    public int selectedWeaponSlot = 0;

    public static SpriteSheet playerSprites;

    public HashMap<ProjectileType, Integer> ammo = new HashMap<ProjectileType, Integer>();

    private int facing = 0;//NORTH = 0,EAST = 1,SOUTH = 2,EAST = 3
    private Image[] facingImages = new Image[4];
    public MobPlayer(int x, int y) {
        super(x, y, 32, 32);
        if (playerSprites == null) {
            try {
                playerSprites = new SpriteSheet("res/player.png",32,32);
                facingImages[0] = playerSprites.getSprite(1,0);
                facingImages[1] = playerSprites.getSprite(0,1);
                facingImages[2] = playerSprites.getSprite(0,0);
                facingImages[3] = playerSprites.getSprite(1,1);
            } catch (SlickException e) {
                e.printStackTrace();
            }

        }
        this.health = 50f;
        this.maxHealth = 50f;
        attractRadius = new Circle(x, y, 256f);
        stopRadius = new Circle(x, y, 128f);
    }


    public float angle;

    @Override
    public void update(Level level, float delta) {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (level.canMove(getX(), getY() - 8, getWidth(), getHeight(), this)) {
                facing =0;
                addY(-8);
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (level.canMove(getX(), getY() + 8, getWidth(), getHeight(), this)){
                facing = 2;
                addY(8);
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (level.canMove(getX() - 8, getY(), getWidth(), getHeight(), this)){
                facing = 3;
                addX(-8);
            }

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (level.canMove(getX() + 8, getY(), getWidth(), getHeight(), this)) {
                facing = 1;
                addX(8);
            }
        }


        attractRadius.setCenterX(getX() + getWidth() / 2);
        attractRadius.setCenterY(getY() + getHeight() / 2);
        stopRadius.setCenterX(getX() + getWidth() / 2);
        stopRadius.setCenterY(getY() + getHeight() / 2);

    }


    public void attack(Level level) {
        Weapon weapon = weaponInventory.getWeapon(selectedWeaponSlot);

        if (weapon == null) {
            System.out.println("No weapon equipped");
            return;
        }
        if (weapon instanceof MeleeWeapon)
            meleeAttack(level, (MeleeWeapon) weapon);
        else if (weapon instanceof RangedWeapon) {
            rangedAttack(level, (RangedWeapon) weapon);
        }
    }

    private void rangedAttack(Level level, RangedWeapon weapon) {
        weapon.fire(InGameState.mX + level.camera.x, InGameState.mY + level.camera.y, level, this);
    }


    private void meleeAttack(Level level, MeleeWeapon meleeWeapon) {
        meleeWeapon.setCondition(meleeWeapon.getCondition() - 1);
        attractRadius.setRadius(meleeWeapon.getAttackRadius());
        ArrayList<Entity> inRange = level.entitiesInShape(attractRadius);
        for (Entity e : inRange) {
            if (e instanceof Mob) {
                ((Mob) e).health -= meleeWeapon.getDamage();
            }
        }
        if (meleeWeapon.getCondition() <= 0 && meleeWeapon.getMaxCondition() != 0) {
            weaponInventory.setSlot(selectedWeaponSlot, null);
        }
    }


    @Override
    public void render(Graphics graphics) {
        //   graphics.fill(attractRadius);
        graphics.drawImage(facingImages[facing], getX(), getY());


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

    public void addAmmo(int ammoToAdd, ProjectileType type) {
        if (ammo.containsKey(type)) {
            ammo.put(type, ammo.get(type) + ammoToAdd);
        } else {
            ammo.put(type, ammoToAdd);
        }
    }

    public void addHealth(HealthBoost boost) {
        health += boost.getAmount();
        if (health > maxHealth)
            health = maxHealth;
    }
}
