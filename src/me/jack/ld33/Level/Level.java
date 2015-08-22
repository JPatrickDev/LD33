package me.jack.ld33.Level;

import me.jack.ld33.Entity.Entity;
import me.jack.ld33.Entity.EntityBat;
import me.jack.ld33.Entity.EntityPlayer;
import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Jack on 22/08/2015.
 */
public class Level implements TileBasedMap {


    public static final int TILESIZE = 64;
    private final int width;
    private final int height;

    public int[][] levelTiles = new int[500][500];

    public ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
    public ArrayList<Entity> entities = new ArrayList<Entity>();

    public Camera camera = new Camera(1000, 1000, 64, 800, 600);

    EntityPlayer player;

    public Level(int width, int height, int[][] tiles) {
        this.levelTiles = tiles;
        this.width = width;
        this.height = height;
      //  camera.calculate(30 * TILESIZE, 30 * TILESIZE);
    }

    public void postGeneration(){
        Random r = new Random();
        boolean found = false;
        int x = -1,y=-1;
        while(!found){
             x = r.nextInt(width);
             y = r.nextInt(height);
            if(getTileAt(x,y) == 1){
                found = true;
            }
        }
        player = new EntityPlayer(x*TILESIZE,y*TILESIZE);


        for(int xx = 0;xx!= width;xx++){
            for(int yy = 0; yy != height;yy++){
                Tile tile = Tile.getTile(getTileAt(xx,yy));
                if(tile.isSolid()){
                    hitboxes.add(new Rectangle(xx*TILESIZE,yy * TILESIZE,TILESIZE,TILESIZE));
                }
            }
        }
    }

    public void render(Graphics g) {
        // g.scale(2f,2f);
        g.translate(-camera.x, -camera.y);
        for (int x = 0; x != levelTiles.length; x++) {
            for (int y = 0; y != levelTiles[0].length; y++) {
                int i = levelTiles[x][y];
                Tile tile = Tile.getTile(i);
                //      if((x*TILESIZE) <0 || (x*TILESIZE) > 832)continue;
                // if((y*TILESIZE) <0 || (y*TILESIZE) > 632) continue;
               // if (onScreen(x * TILESIZE, y * TILESIZE))
                    tile.render((x) * TILESIZE, (y) * TILESIZE, g);
            }
        }

        player.render(g);
        for(Entity e : entities){
            e.render(g);
        }
        g.resetTransform();
    }

    public void update(float delta){
        camera.calculate(player.getX(), player.getY());
        for(Entity e : entities){
            e.update(this,delta);
        }
        player.update(this, delta);
    }
    private boolean onScreen(int x, int y) {


        int ox = (x) - camera.x;
        int oy = (y) - camera.y;

        int rx = (x + 32) - camera.x;
        int ry = (y + 32) - camera.y;

        if (ox > 800) return false;
        if (rx < 0) return false;

        if (oy > 600) return false;
        if (ry < 0) return false;


        return true;
    }


    public int doorFacing(int dX, int dY) {
        return 2;
    }

    public int getTileAt(int x, int y) {
        try {
            return levelTiles[x][y];
        }catch (Exception e){
            return -1;
        }
    }

    public void setTileAt(int x, int y, int i) {

        try {
        this.levelTiles[x][y] = i;
        }catch (Exception e){

        }
    }


    public boolean canMove(int newX,int newY,int width,int height){
        Rectangle rekt = new Rectangle(newX,newY,width,height);
        for(Rectangle rectangle: hitboxes){
            if(rekt.intersects(rectangle))return false;
        }
        return true;
    }




    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public void pathFinderVisited(int i, int i1) {

    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
      return false;
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        int defaultCost = 20;
        if(i == pathFindingContext.getSourceX() || i1 == pathFindingContext.getSourceY()){
            return 0;
        }
        return defaultCost;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void spawnBat() {
            entities.add(new EntityBat(player.getX(),player.getY()));
    }
}
