package me.jack.ld33.States;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 24/08/2015.
 */
public class TutorialState extends BasicGameState {

    private Image tutorialImage;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        tutorialImage = new Image("res/tutorial.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(tutorialImage,0,0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                stateBasedGame.enterState(3);
    }

    @Override
    public int getID() {
        return 4;
    }
}
