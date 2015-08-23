package me.jack.ld33.GUI;

import me.jack.ld33.Item.*;
import me.jack.ld33.Level.Level;
import me.jack.ld33.States.InGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import uk.co.jdpatrick.JEngine.Image.ImageUtil;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class GUI {


    public static org.newdawn.slick.Image weapon_slot = null;
    public static Image HUD_BG = null;
    public static Image CHEST_BG = null;
    public static Image BACK_BUTTON = null;
    public static void renderHUD(Graphics graphics, Level level) {
        if(weapon_slot == null){
            weapon_slot = ImageUtil.loadImage("res/weapon_slot.png");
            HUD_BG = ImageUtil.loadImage("res/HUD_BG.png");
            CHEST_BG = ImageUtil.loadImage("res/chest_background.png");
            BACK_BUTTON = ImageUtil.loadImage("res/back_button.png");
        }
        graphics.setColor(Color.white);
        graphics.drawImage(HUD_BG,0,500);
        graphics.setColor(Color.black);
        graphics.drawString("Time left: " + InGameState.timeTaken / 1000, 600, 550);
        graphics.setColor(Color.white);
        renderWeaponSlots(graphics, level);

        if (renderingWeaponOverlay)
            renderWeaponInfoOverlay(graphics, currentOverlay);
        if (isRenderingChestGUI)
            renderChestGUI(graphics, currentChest);
    }

    static Rectangle slotOne = new Rectangle(16, 515, 32, 32);
    static Rectangle slotTwo = new Rectangle(52, 515, 32, 32);
    static Rectangle slotThree = new Rectangle(88, 515, 32, 32);
    static Rectangle slotFour = new Rectangle(16, 546+6, 32, 32);
    static Rectangle slotFive = new Rectangle(52, 546+6, 32, 32);
    static Rectangle slotSix = new Rectangle(88, 546+6, 32, 32);

    private static void renderWeaponSlots(Graphics graphics, Level level) {
        graphics.setColor(Color.black);
        int x = 16, y = 510+5;
        for (int i = 0; i != 3; i++) {
           graphics.drawImage(weapon_slot,x,y);
            if (level.getPlayer().getWeapons().getWeapon(i) != null) {
                Weapon weaponInSlot = level.getPlayer().getWeapons().getWeapon(i);
                graphics.drawImage(weaponInSlot.getIcon().getScaledCopy(1.5f), x + 8, y);
                if(weaponInSlot.getMaxCondition() != 0) {
                    float fillAmount = (weaponInSlot.getCondition() / weaponInSlot.getMaxCondition());
                    int pixelsToFill = (int) (32 * fillAmount);
                    graphics.setColor(Color.red);
                    graphics.fillRect(x, y + 24, pixelsToFill, 8);
                    graphics.setColor(Color.black);
                }
                if(weaponInSlot instanceof RangedWeapon){
                    String ammoCount = level.getPlayer().ammo.get(((RangedWeapon) weaponInSlot).getProjectileType()) + "";
                    if(!ammoCount.equalsIgnoreCase("null"))
                    graphics.drawString( ammoCount,x,y+20);
                }
                if (level.getPlayer().selectedWeaponSlot == i) {
                    graphics.setColor(Color.red);
                    graphics.drawRect(x, y, 32, 32);
                    graphics.setColor(Color.black);
                }
            }
            x += 36;
        }
        x = 16;
        y = 546 + 5+1;
        for (int i = 3; i != 6; i++) {
            graphics.drawImage(weapon_slot, x, y);
            if (level.getPlayer().getWeapons().getWeapon(i) != null) {
                Weapon weaponInSlot = level.getPlayer().getWeapons().getWeapon(i);
                graphics.drawImage(weaponInSlot.getIcon().getScaledCopy(1.5f), x + 8, y);
                float fillAmount = (weaponInSlot.getCondition() / weaponInSlot.getMaxCondition());
                int pixelsToFill = (int) (32 * fillAmount);
                graphics.setColor(Color.red);
                graphics.fillRect(x, y + 24, pixelsToFill, 8);
                graphics.setColor(Color.black);
                if(weaponInSlot instanceof RangedWeapon){
                    graphics.drawString(level.getPlayer().ammo.get(((RangedWeapon)weaponInSlot).getProjectileType()) + "",x,y+20);
                }
                if (level.getPlayer().selectedWeaponSlot == i) {
                    graphics.setColor(Color.red);
                    graphics.drawRect(x, y, 32, 32);
                    graphics.setColor(Color.black);
                }
            }
            x += 36;
        }

        graphics.setColor(Color.white);
    }


    public static boolean renderingWeaponOverlay = true;
    public static Weapon currentOverlay = null;
    static int width = 180;
    static int height = 200;
    static int x = 400 - (width / 2);
    static int y = 300 - (height / 2);

    static Rectangle closeButton = new Rectangle((x + width) - 32, y, 32, 32);
    static Rectangle equipButton = new Rectangle(x, 335, width, 30);
    static Rectangle dropButton = new Rectangle(x, 370, width, 30);

    public static void renderWeaponInfoOverlay(Graphics graphics, Weapon selected) {
        if (currentOverlay != selected) {
            currentOverlay = selected;
        }
        if (currentOverlay == null) {
            renderingWeaponOverlay = false;
            return;
        }
        graphics.fillRect(x, y, width, height);
        graphics.drawImage(selected.getSprite(), x, y);
        graphics.setColor(Color.black);
        graphics.drawString(selected.getName(), x + 32, y);

        graphics.drawString("Type: " + Weapon.getType(selected), x, y + 28);
        graphics.drawString("Damage: " + selected.getDamage(), x, y + 46);
        graphics.drawString("Condition: " + calculatePercentage(selected) + "%", x, y + 64);

        if (selected instanceof MeleeWeapon) {
            MeleeWeapon meleeWeapon = (MeleeWeapon) selected;
            graphics.drawString("Attack range: " + meleeWeapon.getAttackRadius() / 64, x, y + 82);
        }

        graphics.setColor(Color.red);
        graphics.fillRect(x, 335, width, 30);
        graphics.setColor(Color.black);
        graphics.drawString("Equip weapon", x, 335);

        graphics.setColor(Color.red);
        graphics.fillRect(x, 370, width, 30);
        graphics.setColor(Color.black);
        graphics.drawString("Drop weapon", x, 370);

        graphics.fillRect((x + width) - 32, y, 32, 32);
    }


    public static boolean mouseClick(int button, int xx, int yy, Level level) {
        if (renderingWeaponOverlay) {
            if (xx > x && xx < x + width && yy > y && yy < y + height) {
                System.out.println("Clicking inisde GUI");
                if (closeButton.contains(xx, yy)) {
                    renderingWeaponOverlay = false;
                    currentOverlay = null;
                }
                if (dropButton.contains(xx, yy)) {
                    level.playerDropItem(currentOverlay);
                    level.getPlayer().getWeapons().removeWeapon(currentOverlay);
                    currentOverlay = null;
                    renderingWeaponOverlay = false;
                }
                if (equipButton.contains(xx, yy)) {
                    level.getPlayer().equip(currentOverlay);
                    currentOverlay = null;
                    renderingWeaponOverlay = false;
                }

            }
            return true;
        }
        if (yy > 500 && !renderingWeaponOverlay) {
            if (slotOne.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(0) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(0);
                    renderingWeaponOverlay = true;
                }
            } else if (slotTwo.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(1) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(1);
                    renderingWeaponOverlay = true;
                }
            } else if (slotThree.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(2) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(2);
                    renderingWeaponOverlay = true;
                }
            } else if (slotFour.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(3) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(3);
                    renderingWeaponOverlay = true;
                }
            } else if (slotFive.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(4) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(4);
                    renderingWeaponOverlay = true;
                }
            } else if (slotSix.contains(xx, yy)) {
                if (level.getPlayer().getWeapons().getWeapon(4) != null) {
                    currentOverlay = level.getPlayer().getWeapons().getWeapon(4);
                    renderingWeaponOverlay = true;
                }
            }

            return true;
        }
        if (xx > 272 && yy > 150 && xx < 272 + 256 && yy < 150 + 192 && isRenderingChestGUI) {
            System.out.println("Clicked inside chest");
            int relX = (xx - 272) / 64;
            int relY = (yy - 150) / 64;
            Item selected = currentChest.getItem(relX + relY * 4);
            if (selected != null) {
                if (selected instanceof Weapon) {
                    Weapon weapon = (Weapon) selected;
                    int i = level.getPlayer().canPickupWeapon(weapon);
                    if (i != -1) {
                        level.getPlayer().getWeapons().setSlot(i, weapon);//TODO SOUND EFFECT IF CAN'T FIT
                        currentChest.removeItem(selected);
                    }
                }else if(selected instanceof Ammo){
                    Ammo ammo = (Ammo)selected;
                    level.getPlayer().addAmmo(ammo.getAmount(),ammo.getType());
                    currentChest.removeItem(selected);
                }
                return true;
            }
        }
        //272, 150 + 3 * 64, 256, 50
        if(xx > 272 && yy > 150+3*64 && xx < 272+256 && yy <150+3*64 + 32){
            currentChest = null;
            isRenderingChestGUI = false;
            return true;
        }
        return false;
    }

    private static int calculatePercentage(Weapon weapon) {
        return (int) ((weapon.getCondition() / weapon.getMaxCondition()) * 100);
    }

    public static Chest currentChest;
    public static boolean isRenderingChestGUI = false;

    public static void renderChestGUI(Graphics graphics, Chest chest) {
        graphics.drawImage(CHEST_BG, 272, 150);
        int rows = 3;
        int columns = 4;
        graphics.setColor(Color.black);
        for (int x = 0; x != columns; x++) {
            for (int y = 0; y != rows; y++) {
                graphics.drawRect(272 + (x * 64), 150 + (y * 64), 64, 64);
                if (chest.getItem(x + y * columns) != null) {
                    Item item = chest.getItem(x + y * columns);
                    graphics.drawImage(item.getSprite().getScaledCopy(2f), 272 + (x * 64), 150 + (y * 64));
                    //   graphics.drawString(item.getName(),200 + (x*100),150+64 + (y*100));
                }
            }
        }
        graphics.drawImage(BACK_BUTTON, 272, 150 + 3 * 64);
        graphics.setColor(Color.white);
    }
}
