import processing.core.PImage;

import java.util.List;

public abstract class Octo extends ActiveEntity {


    public Octo(String id, Point position, List<PImage> images,
                 int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public Point nextPositionOcto(WorldModel world, Point destPos) {

        PathingStrategy strategy = new AStarPathingStrategy();
        Point newPos = null;
        try {
            newPos =  strategy.computePath(this.getPosition(), destPos,
                    p -> world.withinBounds(p) && !world.isOccupied(p),
                    (p1, p2) -> p1.adjacent(p2),
                    PathingStrategy.CARDINAL_NEIGHBORS).get(0);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if (newPos == null) {
            return this.getPosition();
        }
        return newPos;
    }

}