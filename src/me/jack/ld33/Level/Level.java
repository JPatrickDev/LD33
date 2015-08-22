package me.jack.ld33.Level;

import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.Graphics;

import java.util.HashMap;

/**
 * Created by Jack on 22/08/2015.
 */
public class Level {


    private HashMap<String, Room> roomArray = new HashMap<String, Room>();
    public static final int TILESIZE = 32;
    private Room activeRoom;


    public Level() {
        activeRoom = new Room("ROOM_1",10,10);
    }


    public void render(Graphics g) {
        g.scale(2f,2f);
        if (activeRoom != null) {
            renderRoom(activeRoom, g);
        }

    }


    private void renderRoom(Room room, Graphics g) {
            for(int x = 0;x!= room.getWidth();x++){
                for(int y = 0;y!= room.getHeight();y++){
                    int i = room.getTileAt(x,y);
                    Tile tile = Tile.getTile(i);
                    tile.render(x * TILESIZE,y*TILESIZE,g);
                }
            }
    }


}
