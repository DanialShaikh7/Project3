import processing.core.PImage;

import java.util.List;

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

    public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Crab crab = new Crab(this.getId() + Crab.CRAB_ID_SUFFIX,
                /*new Point((int)(Math.random() * 40), (int)(Math.random() * 30))*/this.getPosition(), imageStore.getImageList(CRAB_KEY), resourceLimit, resourceCount,
                this.getActionPeriod() / Crab.CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN + ImageStore.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN));

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

}