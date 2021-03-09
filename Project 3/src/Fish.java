import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Fish extends ActiveEntity {

    public static final String CRAB_KEY = "crab";
    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;

    public Fish(String id, Point position, List<PImage> images, int resourceLimit,
                int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);

    }

//    public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//
//        Crab crab = new Crab(this.getId() + Crab.CRAB_ID_SUFFIX,
//                /*new Point((int)(Math.random() * 40), (int)(Math.random() * 30))*/this.getPosition(), imageStore.getImageList(CRAB_KEY), resourceLimit, resourceCount,
//                this.getActionPeriod() / Crab.CRAB_PERIOD_SCALE,
//                CRAB_ANIMATION_MIN + ImageStore.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN));
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(crab);
//        crab.scheduleActions(scheduler, world, imageStore);
//    }

    public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> Target = world.findNearest(this.getPosition(),
                new Crab(this.getId(), this.getPosition(), this.getImages(), 0, 0, this.getActionPeriod(), 0));

        if (!Target.isPresent())
        {
            Activity act = new Activity(this, world, imageStore, 0);
            scheduler.scheduleEvent(this,
                    act.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }


    public boolean moveMario(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.setResourceCount(this.getResourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        return false;
    }

}