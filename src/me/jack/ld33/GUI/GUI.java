package me.jack.ld33.GUI;

import me.jack.ld33.Item.Weapon;
import me.jack.ld33.Level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 22/08/2015.
 */
public class GUI {

    public static void renderHUD(Graphics graphics,Level level){
        graphics.setColor(Color.white);
        graphics.fillRect(0,500,800,100);
        renderWeaponSlots(graphics,level);
    }

    private static void renderWeaponSlots(Graphics graphics,Level level){
        graphics.setColor(Color.black);
        int x = 10,y = 510;
        for(int i = 0;i!= 3;i++){
            graphics.drawRect(x,y,32,32);
            if(level.getPlayer().getWeapons().getWeapon(i) != null){
                Weapon weaponInSlot = level.getPlayer().getWeapons().getWeapon(i);
                graphics.drawImage(weaponInSlot.getIcon(),x+8,y);
                float fillAmount = (weaponInSlot.getCondition()/weaponInSlot.getMaxCondition());
                int pixelsToFill = (int) (32*fillAmount);
                graphics.setColor(Color.red);
                graphics.fillRect(x,y+16,pixelsToFill,8);
                graphics.setColor(Color.black);
            }
            x+=36;
        }
        x = 10;
        y = 546;
        for(int i = 0;i!= 3;i++){
            graphics.drawRect(x,y,32,32);
            x+=36;
        }

        graphics.setColor(Color.white);
    }
}
