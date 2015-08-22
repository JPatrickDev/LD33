package me.jack.ld33.Level;

import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.Graphics;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Jack on 22/08/2015.
 */
public class Level {


    public HashMap<String, Space> spaceArray = new HashMap<String, Space>();
    public static final int TILESIZE = 32;
    private int[][] levelTiles = new int[1000][1000];
    //public Room activeRoom;

    public static int ROOM_COUNT = 0;
    public static int CORRIDOR_COUNT = 0;

    public Level() {
        Room firstRoom = new Room(UUID.randomUUID().toString(), 10, 10, 0, 0);
        addRoom(firstRoom);
    }


    public void addRoom(Room room) {
        int rX = 0,rY = 0;
        for(int x = room.getX();x!= room.getX() + (room.getWidth());x++){
            for(int y = room.getY();y!= room.getY() + (room.getHeight());y++){
                 levelTiles[x][y] =
                         room.getTileAt(rX,rY);
                System.out.println(room.getTileAt(rX,rY));
                rY++;
            }
            rY = 0;
            rX++;
        }
    }

    public void addCorridor(Corridor corridor) {
        int rX = 0,rY = 0;
        for(int x = corridor.getX();x!= corridor.getX() + (corridor.getWidth());x++){
            for(int y = corridor.getY();y!= corridor.getY() + (corridor.getHeight());y++){
                levelTiles[x][y] =
                        corridor.getTileAt(rX,rY);
                System.out.println(corridor.getTileAt(rX,rY));
                rY++;
            }
            rY = 0;
            rX++;
        }
    }

    public void render(Graphics g) {
        // g.scale(2f,2f);
        // g.translate(128,128);
        for (int x = 0; x != levelTiles.length; x++) {
            for (int y = 0; y != levelTiles[0].length; y++) {
                int i = levelTiles[x][y];
                Tile tile = Tile.getTile(i);
                if((x*TILESIZE) <0 || (x*TILESIZE) > 832)continue;
                if((y*TILESIZE) <0 || (y*TILESIZE) > 632) continue;
                tile.render((x) * TILESIZE, (y) * TILESIZE, g);
            }

        }
    }


    private void renderSpace(Space space, Graphics g) {
        for (int x = 0; x != space.getWidth(); x++) {
            for (int y = 0; y != space.getHeight(); y++) {
                int i = space.getTileAt(x, y);
                Tile tile = Tile.getTile(i);
                tile.render((x + space.getX()) * TILESIZE, (y + space.getY()) * TILESIZE, g);
            }
        }
    }


    public void doorClicked(int tX, int tY) {
       doorSteppedOn(tX,tY);
    }

    public void doorSteppedOn(int x, int y) {
        if (getTileAt(x, y) != 3) {
            System.out.println("That's not a door");
            return;
        } else {
            int facing = doorFacing(x, y);
            System.out.println("Door facing: " + facing);
            Corridor corridor = new Corridor(facing, x, y);
            addCorridor(corridor);
            corridor.init();
        }
    }


    public int doorFacing(int dX, int dY) {
        return 2;
    }

    public int getTileAt(int x, int y) {
        return levelTiles[x][y];
    }

    public void setTileAt(int x, int y, int i) {
        this.levelTiles[x][y] = i;
    }

}
