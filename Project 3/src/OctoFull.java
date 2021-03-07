import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class OctoFull extends Octo {
    private static final int ATLANTIS_ANIMATION_PERIOD = 70;


    public OctoFull(String id, Point position, List<PImage> images, int resourceLimit,
                     int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeOctoFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Atlantis atlantis = new Atlantis(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), ATLANTIS_ANIMATION_PERIOD);

        Optional<Entity> fullTarget = world.findNearest(this.getPosition(),
                atlantis);

        if (fullTarget.isPresent() &&
                moveToFull(world, fullTarget.get(), scheduler)) {

            ((Atlantis)fullTarget.get()).scheduleActions(scheduler, world, imageStore);
                System.out.println("hit");
                // I cant figure out how to get the animation to hit, ive been trying forever and cant figure out what im missing :(

            this.transformFull(world, scheduler, imageStore);

        }
        else {
            Activity act = new Activity(this, world, imageStore, 0);
            scheduler.scheduleEvent(this,
                    act.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity octo = this.getPosition().createOctoNotFull(this.getId(), this.resourceLimit,
                this.getActionPeriod(), this.getAnimationPeriod(), this.getImages());


        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        ActiveEntity o = (ActiveEntity)octo;
        o.scheduleActions(scheduler, world, imageStore);
    }


    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        }
        else {
            Point nextPos = nextPositionOcto(world, target.getPosition());
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

}