package me.jack.ld33.States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 23/08/2015.
 */
public class MainMenuState extends BasicGameState {


    private Image start_game_button = null;
    private Image view_tutorial_button = null;
    private Image logo = null;
    private Image madeBy= null;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.start_game_button = new Image("res/start_game_button.png");
        this.view_tutorial_button = new Image("res/view_tutorial_button.png");
        this.logo = new Image("res/logo.png");
        this.madeBy = new Image("res/madeby.png");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        nextState = -1;
    }

    public int nextState = -1;

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(logo,0,0);
        graphics.drawImage(start_game_button, 200 , 200+ 50);
        graphics.drawImage(view_tutorial_button, 200, 320);
        graphics.drawImage(madeBy,0,400);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (nextState != -1) {
            if(nextState == 0){
                ((InGameState)stateBasedGame.getState(0)).newGame();
            }
            stateBasedGame.enterState(nextState);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (x > 200 && y > 250 && x < 200 + 400 && y < 200 + 100) {
            nextState = 0;
        }
        if (x > 200 && y > 320 && x < 200 + 400 && y < 320 + 50) {
            nextState = 4;
        }
    }

    @Override
    public int getID() {
        return 3;
    }
}
