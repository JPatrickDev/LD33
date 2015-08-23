package me.jack.ld33.Particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import uk.co.jdpatrick.JEngine.Particle.Particle;
import uk.co.jdpatrick.JEngine.Particle.ParticleSystem;

import java.util.Random;

/**
 * Created by Jack on 22/08/2015.
 */
public class SmallBloodParticle extends Particle {

    /**
     * Create a new Particle
     *
     * @param x Starting X coordinate
     * @param y Starting Y coordinate
     */
    public SmallBloodParticle(int x, int y) {
        super(x, y);
        xx = x;
        yy = y;

        Random random = new Random();
        xa = random.nextGaussian() * 2;
        ya = random.nextGaussian() * 5;
    }

    @Override
    public void render(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.red);
        g.fillRect(x, y, 4, 4);
        g.setColor(Color.white);
    }

    int i = 0;

    @Override
    public void update(ParticleSystem system) {
        i++;
        if (i > 10) {
            xa = 0;
            ya = 0;
            return;
        }
        if (system.level.canMove((int) (x + xa), (int) (y + ya), 4, 4,null)) {
            x += xa;
            y += ya;
        }
    }
}
