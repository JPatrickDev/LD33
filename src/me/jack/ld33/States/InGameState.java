package me.jack.ld33.States;

import me.jack.ld33.GUI.GUI;
import me.jack.ld33.Item.Chest;
import me.jack.ld33.Item.Item;
import me.jack.ld33.Item.Weapon;
import me.jack.ld33.Level.Level;
import me.jack.ld33.Level.LevelGenerator;
import me.jack.ld33.Level.Tile.Tile;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

/**
 * Created by Jack on 22/08/2015.
 */
public class InGameState extends BasicGameState {

    Level level;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.initTiles();
        Weapon.init();
        level = LevelGenerator.generateLevel(50, 50, false);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        level.render(graphics);
        GUI.renderHUD(graphics, level);
    }


    long time = 0;

    public static Random random = new Random();

    public static int mX, mY;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        Input ii = gameContainer.getInput();
        mX = ii.getMouseX();
        mY = ii.getMouseY();

        if (!GUI.isRenderingChestGUI && !GUI.renderingWeaponOverlay) {
            level.update(i);
            if (level.getPlayer() != null) {
                level.getPlayer().angle = (float) -(Math.atan2(level.getPlayer().getX() - (gameContainer.getInput().getMouseX() + level.camera.x), level.getPlayer().getY() - (gameContainer.getInput().getMouseY() + level.camera.y)) * 180 / Math.PI);
            }
            time += i;

            if (time > 500) {
                for (int x = 0; x != level.getWidth(); x++) {
                    for (int y = 0; y != level.getHeight(); y++) {
                        if (level.getTileAt(x, y) == 1 && random.nextInt(500) == 0) {
                            level.spawnHuman(x * 64, y * 64);
                        }
                    }
                }
                time = 0;
            }
        }

    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        switch (key) {
            case Keyboard.KEY_1:
                level.getPlayer().selectedWeaponSlot = 0;
                break;
            case Keyboard.KEY_2:
                level.getPlayer().selectedWeaponSlot = 1;
                break;
            case Keyboard.KEY_3:
                level.getPlayer().selectedWeaponSlot = 2;
                break;
            case Keyboard.KEY_4:
                level.getPlayer().selectedWeaponSlot = 3;
                break;
            case Keyboard.KEY_5:
                level.getPlayer().selectedWeaponSlot = 4;
                break;
            case Keyboard.KEY_6:
                level.getPlayer().selectedWeaponSlot = 5;
                break;
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if (button == Input.MOUSE_MIDDLE_BUTTON) {
            for (int i = 0; i != 1; i++) {
                level.spawnHuman(level.getPlayer().getX(), level.getPlayer().getY());
            }
        } else {
            if (!GUI.mouseClick(button, x, y, level)) {
                if (level.topLayer[(x + level.camera.x) / 64][(y + level.camera.y) / 64] == 0)
                    level.getPlayer().attack(level);
                else {
                    Chest chest = level.getChestAt((x + level.camera.x) / 64, (y + level.camera.y) / 64);
                    System.out.println(String.valueOf(chest == null));
                    GUI.currentChest = chest;
                    GUI.isRenderingChestGUI = true;
                }

            }

        }
    }

    @Override
    public int getID() {
        return 0;
    }

}
