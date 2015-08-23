package me.jack.ld33.Item;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 23/08/2015.
 */
public class Chest {

    private ArrayList<Item> items = new ArrayList<Item>();

    private Point owner;

    public Chest(Point owner){
        this.owner = owner;
    }
    public void addItem(Item item){
        items.add(item);
    }

    public Item getItem(int slot){
        try {
            return items.get(slot);
        }catch (Exception e){
            return null;
        }
    }

    public Point getOwner() {
        return owner;
    }

    public void removeItem(Item selected) {
        items.remove(selected);
    }
}
