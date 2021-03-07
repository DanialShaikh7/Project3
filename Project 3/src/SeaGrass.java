import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class SeaGrass extends ActiveEntity {


    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;
    public static final int FISH_REACH = 1;


    public SeaGrass(String id, Point position, List<PImage> images,
                    int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    public void executeSeaGrassActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent())
        {
            Fish fish = new Fish(FISH_ID_PREFIX + this.getId(), openPt.get(), imageStore.getImageList(world.FISH_KEY), resourceLimit, resourceCount,
                    FISH_CORRUPT_MIN + ImageStore.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN), this.getAnimationPeriod());


            world.addEntity(fish);
            fish.scheduleActions(scheduler, world, imageStore);
        }

        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore), this.getActionPeriod());
    }

}