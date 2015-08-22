package me.jack.ld33.States;

import me.jack.ld33.Level.Level;
import me.jack.ld33.Level.Room;
import me.jack.ld33.Level.Tile.Tile;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.UUID;

/**
 * Created by Jack on 22/08/2015.
 */
public class InGameState extends BasicGameState {

    Level level;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Tile.initTiles();


        this.level = new Level();
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

        int tX = (x)/32;
        int tY = (y)/32;
      //  if(tX < 0 || tX >=10 || tY < 0 || tY >= 10)return;
        //((Room)level.spaceArray.get(Level.firstUUID)).doorSteppedOn(tX,tY,level);
       level.doorClicked(tX,tY);
    }

    @Override
    public int getID() {
        return 0;
    }

}
