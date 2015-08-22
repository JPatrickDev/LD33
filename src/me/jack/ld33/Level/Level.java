package me.jack.ld33.Level;

import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Jack on 22/08/2015.
 */
public class Level {


    public HashMap<String, Space> spaceArray = new HashMap<String, Space>();
    public static final int TILESIZE = 32;
    private int[][] levelTiles = new int[1000][1000];
    private ArrayList<Door> doors = new ArrayList<Door>();
    //public Room activeRoom;

    public static int ROOM_COUNT = 0;
    public static int CORRIDOR_COUNT = 0;


    public Camera camera = new Camera(1000,1000,32,800,600);


    public Level() {
        Room firstRoom = new Room(10, 10, 500, 500);
        addRoom(firstRoom);
        camera.calculate(500 * TILESIZE,500 * TILESIZE);
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

        for(Door door : room.doors){
            Door correctPlacement = new Door(room.getX() + door.getX(),room.getY() + door.getY(),door.getFacing(),door.getTargetType());
            doors.add(correctPlacement);
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

        for(Door door : corridor.doors){
            Door correctPlacement = new Door(corridor.getX() + door.getX(),corridor.getY() + door.getY(),door.getFacing(),door.getTargetType());
            doors.add(correctPlacement);
        }


    }

    public void render(Graphics g) {
        // g.scale(2f,2f);
         g.translate(-camera.x,-camera.y);
        for (int x = 0; x != levelTiles.length; x++) {
            for (int y = 0; y != levelTiles[0].length; y++) {
                int i = levelTiles[x][y];
                Tile tile = Tile.getTile(i);
          //      if((x*TILESIZE) <0 || (x*TILESIZE) > 832)continue;
               // if((y*TILESIZE) <0 || (y*TILESIZE) > 632) continue;
                if(onScreen(x*TILESIZE,y * TILESIZE))
                tile.render((x) * TILESIZE, (y) * TILESIZE, g);
            }
        }
        for(Door door : doors){
            door.render(g);
        }

        g.resetTransform();
    }

    private boolean onScreen(int x, int y) {


        int ox = (x) -camera.x;
        int oy = (y) - camera.y;

        int rx = (x + 32) -camera.x;
        int ry = (y + 32) - camera.y;

        if(ox > 800)return false;
        if(rx < 0)return false;

        if(oy > 600)return false;
        if(ry < 0)return false;


        return true;
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


    public boolean doorAt(int x,int y){
        for(Door door : doors){
            if(door.getX() == x && door.getY() == y){
                return true;
            }
        }
        return false;
    }

    public Door getDoorAt(int x,int y){
        for(Door door : doors){
            if(door.getX() == x && door.getY() == y){
                return door;
            }
        }
        return null;
    }

    public void doorSteppedOn(int x, int y) {
        if (!doorAt(x,y)) {
            System.out.println("That's not a door");
            return;
        } else {
            Door clickedDoor = getDoorAt(x, y);
            int facing = clickedDoor.getFacing();          System.out.println("Door facing: " + facing);

            if(clickedDoor.getTargetType() == Door.CORRIDOR) {
                Corridor corridor = new Corridor(facing, x, y);
                addCorridor(corridor);
            }else{
                Room room = new Room(10,10,x,y);
                addRoom(room);
            }

            doors.remove(clickedDoor);
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
