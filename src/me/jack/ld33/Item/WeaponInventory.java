package me.jack.ld33.Item;

import java.util.ArrayList;

/**
 * Created by Jack on 22/08/2015.
 */
public class WeaponInventory {

    private Weapon[] weapons = new Weapon[6];

    public Weapon getWeapon(int slot) {
        return weapons[slot];
    }

    public void setSlot(int slot, Weapon weapon) {
        weapons[slot] = weapon;
    }

    public void removeWeapon(Weapon currentOverlay) {
        for(int i = 0;i!= weapons.length;i++){
            if(weapons[i] == currentOverlay)
                weapons[i] = null;
        }
    }

    public int getWeaponPos(Weapon currentOverlay) {
        for(int i = 0;i!= weapons.length;i++){
            if(weapons[i] == currentOverlay)
              return i;
        }
        return -1;
    }
}
