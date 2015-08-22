package me.jack.ld33.States;

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


     //   this.level = new Level();
        level = LevelGenerator.generateLevel(100,100);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        level.render(graphics);
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);

        int tX = (x + level.camera.x)/32;
        int tY = (y + level.camera.y)/32;
      //  if(tX < 0 || tX >=10 || tY < 0 || tY >= 10)return;
        //((Room)level.spaceArray.get(Level.firstUUID)).doorSteppedOn(tX,tY,level);
    }

    @Override
    public int getID() {
        return 0;
    }

}
