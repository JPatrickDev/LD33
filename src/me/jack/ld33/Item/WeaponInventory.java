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

}
