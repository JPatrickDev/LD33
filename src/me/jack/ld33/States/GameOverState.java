package me.jack.ld33.States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 23/08/2015.
 */
public class GameOverState extends BasicGameState{

    public static boolean causeOfEnd = false;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if(causeOfEnd) {
            graphics.drawString("GAME OVER", 50, 50);
            graphics.drawString("After 5 rounds, you managed to kill " + InGameState.totalHumanKills + " humans and " + InGameState.totalBatKills + " bats.", 50, 65);
            graphics.drawString("This gives you a final score of " + ((InGameState.totalHumanKills * 4) + InGameState.totalBatKills * 2 + 500), 50, 80);
            graphics.drawString("Thank you for playing", 50, 95);
            graphics.drawString("Press enter to return to main menu", 50, 110);
        }else{
            graphics.drawString("GAME OVER", 50, 50);
            graphics.drawString("YOU DIED!", 50, 65);
            graphics.drawString("After " +  InGameState.numberOfRounds +  " rounds, you managed to kill " + InGameState.totalHumanKills + " humans and " + InGameState.totalBatKills + " bats.", 50, 80);
            graphics.drawString("This gives you a final score of " + ((InGameState.totalHumanKills * 4) + InGameState.totalBatKills * 2), 50, 95);
            graphics.drawString("Thank you for playing", 50, 110);
            graphics.drawString("Press enter to return to main menu", 50, 125);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 2;
    }
}
