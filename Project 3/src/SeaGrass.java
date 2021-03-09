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
            Point here;
            while (true) {
                here = new Point((int)(Math.random() * 40), (int)(Math.random() * 30));
                if (world.getOccupancyCell(here) == null) {
                    break;
                }
            }
            Crab cow = new Crab(this.getId() + Crab.CRAB_ID_SUFFIX,
                here, imageStore.getImageList(Fish.CRAB_KEY), resourceLimit, resourceCount,
                this.getActionPeriod() / Crab.CRAB_PERIOD_SCALE,
                Fish.CRAB_ANIMATION_MIN + ImageStore.rand.nextInt(Fish.CRAB_ANIMATION_MAX - Fish.CRAB_ANIMATION_MIN));


            world.addEntity(cow);
            cow.scheduleActions(scheduler, world, imageStore);
        }

        Activity act = new Activity(this, world, imageStore, 0);
        scheduler.scheduleEvent(this,
                act.createActivityAction(world, imageStore), this.getActionPeriod());
    }

}