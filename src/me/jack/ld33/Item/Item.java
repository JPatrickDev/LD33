package me.jack.ld33.Item;

import org.newdawn.slick.Image;

/**
 * Created by Jack on 22/08/2015.
 */
public class Item {

    private Image icon;
    private Image sprite;
    private String name;
    public Item(Image icon, Image sprite,String name) {
        this.icon = icon;
        this.sprite = sprite;
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public Image getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }
}
