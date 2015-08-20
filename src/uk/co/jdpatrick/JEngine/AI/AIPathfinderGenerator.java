package uk.co.jdpatrick.JEngine.AI;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import uk.co.jdpatrick.JEngine.AI.Movement.MovementType;
import uk.co.jdpatrick.JEngine.AI.Movement.TileBased;
import uk.co.jdpatrick.JEngine.Level.Tile;

/**
 * Created by Jack on 01/12/2014.
 */
public class AIPathfinderGenerator {

    /**
     * The TileBasedMap for the pathfinder to use
     */
    private TileBasedMap map;

    /**
     * The pathFinder to use
     */
    private AStarPathFinder pathFinder = null;

    public AIPathfinderGenerator(TileBasedMap map, AStarPathFinder finder){
        this.map = map;
        this.pathFinder = finder;
    }


    /**
     * Calculate the path to the target
     */
    public AIPathfinder calculatePath(final Point target, final AIEntity owner, MovementType type) {

        AIPathfinder pathfinder = null;
        if (target == null) return null;
        Path path = pathFinder.findPath(owner, owner.getTileX(), owner.getTileY(), (int) target.getX(), (int) target.getY());

        if (path == null) {
            System.out.println("Unable to find path");
            owner.aiComplete();
        } else {
            pathfinder = new AIPathfinder(path, owner, type);
            System.out.println("AIPathfinder created");
        }

        return pathfinder;

    }


}
