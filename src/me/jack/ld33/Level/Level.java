package me.jack.ld33.Level;

import me.jack.ld33.Entity.*;
import me.jack.ld33.Item.Chest;
import me.jack.ld33.Item.Item;
import me.jack.ld33.Item.Melee.AxeWeapon;
import me.jack.ld33.Item.Melee.DaggerWeapon;
import me.jack.ld33.Item.Ranged.MachineGunWeapon;
import me.jack.ld33.Item.Ranged.PistolWeapon;
import me.jack.ld33.Item.Ranged.ProjectileType;
import me.jack.ld33.Item.RangedWeapon;
import me.jack.ld33.Level.Tile.Tile;
import me.jack.ld33.States.InGameState;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 22/08/2015.
 */
public class Level implements TileBasedMap {


    public ParticleSystem particleSystem = new ParticleSystem(this);
    public static final int TILESIZE = 64;
    private final int width;
    private final int height;

    public int[][] levelTiles = new int[500][500];
    public int[][] topLayer;
    public ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
    public CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();

    public ArrayList<Chest> chests = new ArrayList<Chest>();

    public Camera camera = new Camera(1000, 1000, 64, 800, 600);

    MobPlayer player;


    public Level(int width, int height, int[][] tiles,int[][] topLayer) {
        this.levelTiles = tiles;
        this.topLayer = topLayer;
        this.width = width;
        this.height = height;
        //  camera.calculate(30 * TILESIZE, 30 * TILESIZE);
    }

    public void postGeneration() {
        Random r = new Random();
        boolean found = false;
        int x = -1, y = -1;
        while (!found) {
            x = r.nextInt(width);
            y = r.nextInt(height);
            if (getTileAt(x, y) == 1) {
                found = true;
            }
        }
        player = new MobPlayer(x * TILESIZE, y * TILESIZE);
        player.getWeapons().setSlot(0, new DaggerWeapon());
        player.getWeapons().setSlot(1, new AxeWeapon());
        player.getWeapons().setSlot(2, new PistolWeapon());
        player.ammo.put(ProjectileType.SMALL_BULLET, 20);
        player.getWeapons().setSlot(3, new MachineGunWeapon());
        player.ammo.put(ProjectileType.BULLET, 200);



        for (int xx = 0; xx != width; xx++) {
            for (int yy = 0; yy != height; yy++) {
                Tile tile = Tile.getTile(getTileAt(xx, yy));
                if (tile.isSolid() || topLayer[xx][yy] != 0) {
                    hitboxes.add(new Rectangle(xx * TILESIZE, yy * TILESIZE, TILESIZE, TILESIZE));
                } else {
                    if (getTileAt(xx, yy) == 1 && r.nextInt(50) == 0) {
                        for (int i = 0; i != 1; i++) {
                            spawnHuman(xx * TILESIZE, yy * TILESIZE);
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        // g.scale(0.15f,0.15f);
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
        for (int x = 0; x != levelTiles.length; x++) {
            for (int y = 0; y != levelTiles[0].length; y++) {
                int i = topLayer[x][y];
                Tile tile = Tile.getTile(i);
                //      if((x*TILESIZE) <0 || (x*TILESIZE) > 832)continue;
                // if((y*TILESIZE) <0 || (y*TILESIZE) > 632) continue;

                if(i == 0)
                    continue;
                if (onScreen(x * TILESIZE, y * TILESIZE))
                tile.render((x) * TILESIZE, (y) * TILESIZE, g);
            }
        }

        particleSystem.render(g, 0, 0);
        for (Entity e : entities) {
            e.render(g);
        }

        player.render(g);
        g.resetTransform();
    }

    public void update(float delta) {
        camera.calculate(player.getX(), player.getY());
        for (Entity e : entities) {
            if(e instanceof MobHuman){
                if(!onScreen(e.getX(),e.getY()))
                    continue;
            }
            e.update(this, delta);
        }
        player.update(this, delta);
        if(Mouse.isButtonDown(0)){
            if(player.getWeapons().getWeapon(player.selectedWeaponSlot) instanceof MachineGunWeapon){
              player.attack(this);
            }
        }
        particleSystem.update();
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
        } catch (Exception e) {
            return -1;
        }
    }

    public void setTileAt(int x, int y, int i) {
        try {
            this.levelTiles[x][y] = i;
        } catch (Exception e) {

        }
    }


    public boolean canMove(int newX, int newY, int width, int height, Entity entity) {
        Rectangle rekt = new Rectangle(newX, newY, width, height);
        for (Rectangle rectangle : hitboxes) {
            if (rekt.intersects(rectangle)) return false;
        }
        return true;
    }

    public ArrayList<Entity> entitiesInShape(Shape shape) {
        ArrayList<Entity> foundEntities = new ArrayList<Entity>();
        for (Entity e : entities) {
            org.newdawn.slick.geom.Rectangle rectangle = new org.newdawn.slick.geom.Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            if (shape.intersects(rectangle)) {
                foundEntities.add(e);
            }
        }
        return foundEntities;
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
        return getTileAt(i, i1) != 1;
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        int defaultCost = 20;
        if (i == pathFindingContext.getSourceX() || i1 == pathFindingContext.getSourceY()) {
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

    public void spawnBat(int x, int y) {
        entities.add(new MobBat(x, y));
    }

    public void spawnHuman(int x, int y) {
        System.out.println("Human Spawned");
        entities.add(new MobHuman(x, y));
    }

    public MobPlayer getPlayer() {
        return player;
    }

    public Rectangle getPlayerHitbox() {
        return new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    public void dropItem(Item item, int x, int y) {
        entities.add(new EntityItem(x, y, item));
    }

    public void playerDropItem(Item item){
        int tX = getPlayer().getX()/64;
        int tY = getPlayer().getY()/64;
        ArrayList<Point> possibleDrops = new ArrayList<Point>();

        if(getTileAt(tX - 1,tY) == 1)
            possibleDrops.add(new Point(tX-1,tY));
        if(getTileAt(tX + 1,tY) == 1)
            possibleDrops.add(new Point(tX+1,tY));
        if(getTileAt(tX,tY-1) == 1)
            possibleDrops.add(new Point(tX,tY-1));
        if(getTileAt(tX,tY+1) == 1)
            possibleDrops.add(new Point(tX,tY+1));

        Point dropPoint = possibleDrops.get(MobHuman.random.nextInt(possibleDrops.size()));
        dropItem(item, dropPoint.x * 64, dropPoint.y * 64);
    }


    public int getTopTileAt(int x, int y) {
        try {
            return topLayer[x][y];
        } catch (Exception e) {
            return -1;
        }
    }

    public void setTopTileAt(int x, int y, int i) {
        try {
            this.topLayer[x][y] = i;
        } catch (Exception e) {

        }
    }

    public Chest getChestAt(int x,int y){
        for(Chest chest : chests){
            if(chest.getOwner().x == x && chest.getOwner().y == y){
                return chest;
            }
        }
        return null;
    }

}
