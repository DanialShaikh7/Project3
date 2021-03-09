import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class GrimReaper extends ActiveEntity {

    int count = 0;

    public GrimReaper(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeReaperActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> marioTarget = world.findNearest(this.getPosition(), new Fish(this.getId(), this.getPosition(), this.getImages(), resourceLimit, resourceCount, this.getActionPeriod(), this.getAnimationPeriod()));
        long nextPeriod = this.getActionPeriod();

        if (marioTarget.isPresent()) {
            Point tgtPos = marioTarget.get().getPosition();

            if (moveToReaper(world, marioTarget.get(), scheduler)) {
                System.out.println("You lost! :(");
                int a = 9/0;
            }
        }
        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore), nextPeriod);
    }


    public boolean moveToReaper(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = nextPositionReaper(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


    public Point nextPositionReaper(WorldModel world, Point destPos) {
        if (count == 3) {
            PathingStrategy strategy = new DijkstraPathingStrategy();
            Point newPos = null;
            try {
                newPos = strategy.computePath(this.getPosition(), destPos,
                        p -> world.withinBounds(p) && !world.isOccupied(p),
                        (p1, p2) -> p1.adjacent(p2),
                        PathingStrategy.CARDINAL_NEIGHBORS).get(0);
            } catch (Exception e) {
            }
            finally {
                count = 0;
            }
            if (newPos == null) {
                return this.getPosition();
            }
            return newPos;
        }
        count++;
        return this.getPosition();
    }

}
