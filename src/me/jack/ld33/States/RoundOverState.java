package me.jack.ld33.States;

import me.jack.ld33.Level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 23/08/2015.
 */
public class RoundOverState extends BasicGameState{


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        wait = 5000;
    }

    long wait = 5000;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        //TODO Center all this
        graphics.drawString("ROUND OVER",50,50);
        graphics.drawString("You killed " + Level.humansKilled + " humans and " + Level.batsKilled + " bats",50,65);
        graphics.drawString("NEXT ROUND STARTS IN: " + wait/1000,50,80);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        wait-=i;

        if(wait <= 0){
            stateBasedGame.enterState(0);
        }
    }

    @Override
    public int getID() {
        return 1;
    }
}
