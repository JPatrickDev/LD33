package me.jack.ld33;

import me.jack.ld33.States.GameOverState;
import me.jack.ld33.States.InGameState;
import me.jack.ld33.States.MainMenuState;
import me.jack.ld33.States.RoundOverState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 22/08/2015.
 */
public class LD33Game extends StateBasedGame {

    public LD33Game(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        gameContainer.setUpdateOnlyWhenVisible(false);
        gameContainer.setAlwaysRender(true);

        this.addState(new InGameState());
        this.addState(new MainMenuState());
        this.addState(new GameOverState());
        this.addState(new RoundOverState());
    }
}
