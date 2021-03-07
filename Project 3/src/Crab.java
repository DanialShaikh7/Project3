import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crab extends ActiveEntity {

    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;

    public static final String QUAKE_KEY = "quake";

    public Crab(String id, Point position, List<PImage> images, int resourceLimit,
                int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeCrabActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> crabTarget = world.findNearest(this.getPosition(), new SeaGrass(this.getId(), this.getPosition(), this.getImages(), resourceLimit, resourceCount, this.getActionPeriod(), this.getAnimationPeriod()));
        long nextPeriod = this.getActionPeriod();

        if (crabTarget.isPresent()) {
            Point tgtPos = crabTarget.get().getPosition();

            if (moveToCrab(world, crabTarget.get(), scheduler)) {
                ActiveEntity quake = tgtPos.createQuake(imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore), nextPeriod);
    }


    public boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = nextPositionCrab(world, target.getPosition());

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


    public Point nextPositionCrab(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get() instanceof Fish)))
        {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get() instanceof Fish))) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

}