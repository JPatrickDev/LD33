package me.jack.ld33.GUI;

import me.jack.ld33.Item.*;
import me.jack.ld33.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by Jack on 22/08/2015.
 */
public class GUI {


    public static void renderHUD(Graphics graphics, Level level) {
        graphics.setColor(Color.white);
        graphics.fillRect(0, 500, 800, 100);
        renderWeaponSlots(graphics, level);

        if (renderingWeaponOverlay)
            renderWeaponInfoOverlay(graphics, currentOverlay);
        if (isRenderingChestGUI)
            renderChestGUI(graphics, currentChest);
    }

    static Rectangle slotOne = new Rectangle(10, 510, 32, 32);
    static Rectangle slotTwo = new Rectangle(46, 510, 32, 32);
    static Rectangle slotThree = new Rectangle(82, 510, 32, 32);
    static Rectangle slotFour = new Rectangle(10, 546, 32, 32);
    static Rectangle slotFive = new Rectangle(46, 546, 32, 32);
    static Rectangle slotSix = new Rectangle(82, 546, 32, 32);

    private static void renderWeaponSlots(Graphics graphics, Level level) {
        graphics.setColor(Color.black);
        int x = 10, y = 510;
        for (int i = 0; i != 3; i++) {
            graphics.drawRect(x, y, 32, 32);
            if (level.getPlayer().getWeapons().getWeapon(i) != null) {
                Weapon weaponInSlot = level.getPlayer().getWeapons().getWeapon(i);
                graphics.drawImage(weaponInSlot.getIcon(), x + 8, y);
                float fillAmount = (weaponInSlot.getCondition() / weaponInSlot.getMaxCondition());
                int pixelsToFill = (int) (32 * fillAmount);
                graphics.setColor(Color.red);
                graphics.fillRect(x, y + 16, pixelsToFill, 8);
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
        x = 10;
        y = 546;
        for (int i = 3; i != 6; i++) {
            graphics.drawRect(x, y, 32, 32);
            if (level.getPlayer().getWeapons().getWeapon(i) != null) {
                Weapon weaponInSlot = level.getPlayer().getWeapons().getWeapon(i);
                graphics.drawImage(weaponInSlot.getIcon(), x + 8, y);
                float fillAmount = (weaponInSlot.getCondition() / weaponInSlot.getMaxCondition());
                int pixelsToFill = (int) (32 * fillAmount);
                graphics.setColor(Color.red);
                graphics.fillRect(x, y + 16, pixelsToFill, 8);
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
                }
            }
        }
        //272, 150 + 3 * 64, 256, 50
        if(xx > 272 && yy > 150+3*64 && xx < 272+256 && yy < 200+(3*64)){
            currentChest = null;
            isRenderingChestGUI = false;
        }
        return false;
    }

    private static int calculatePercentage(Weapon weapon) {
        return (int) ((weapon.getCondition() / weapon.getMaxCondition()) * 100);
    }

    public static Chest currentChest;
    public static boolean isRenderingChestGUI = false;

    public static void renderChestGUI(Graphics graphics, Chest chest) {
        graphics.fillRect(272, 150, 256, 192);
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
        graphics.setColor(Color.red);
        graphics.fillRect(272, 150 + 3 * 64, 256, 50);
        graphics.setColor(Color.black);
        graphics.drawString("Exit chest",272,150+3*64);
        graphics.setColor(Color.white);
    }
}
