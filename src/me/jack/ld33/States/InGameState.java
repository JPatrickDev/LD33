package me.jack.ld33.States;

import me.jack.ld33.GUI.GUI;
import me.jack.ld33.Item.Item;
import me.jack.ld33.Item.Weapon;
import me.jack.ld33.Level.Level;
import me.jack.ld33.Level.LevelGenerator;
import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 22/08/2015.
 */
public class InGameState extends BasicGameState {

    Level level;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.initTiles();
        Weapon.init();


        //   this.level = new Level();
        level = LevelGenerator.generateLevel(50, 50, false);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        level.render(graphics);
        GUI.renderHUD(graphics,level);
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        level.update(i);
        if(level.getPlayer() != null){
            level.getPlayer().angle = (float) -(Math.atan2(level.getPlayer().getX() - (gameContainer.getInput().getMouseX()+level.camera.x), level.getPlayer().getY() - (gameContainer.getInput().getMouseY() + level.camera.y)) * 180 / Math.PI);
        }

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if (button == Input.MOUSE_MIDDLE_BUTTON){
            for(int i = 0;i!= 10;i++){
                //level.spawnBat(000);
            }
        }
        else
            level.getPlayer().attack(level);
    }

    @Override
    public int getID() {
        return 0;
    }

}
