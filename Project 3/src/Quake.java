import processing.core.PImage;

import java.util.List;

public class Quake extends ActiveEntity {

    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public static final String QUAKE_KEY = "quake";

    public Quake(String id, Point position, List<PImage> images,
                 int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeQuakeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

}