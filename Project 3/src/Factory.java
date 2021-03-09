
import processing.core.PImage;

import java.util.List;

public class Factory {


    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    public static OctoNotFull createOctoNotFull(String id, Point position, int resourceLimit, int actionPeriod,
                                                  int animationPeriod,
                                                  List<PImage> images) {
        return new OctoNotFull(id, position, images, 0,
                resourceLimit, 0, actionPeriod, animationPeriod);

    }



    public static OctoFull createMinerFull(String id, Point position, int resourceLimit, int actionPeriod, int animationPeriod,
                                            List<PImage> images) {
        return new OctoFull( id, position, images, 0,
                resourceLimit, actionPeriod,
                animationPeriod);
    }

    public static Obstacle createObstacle(String id, Point position, List<PImage> images) {
        return new Obstacle(id, position, images,0);
    }

    public static Fish createFish(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Fish(id, position, images, 0, actionPeriod);
    }


    public static Crab createCrab(
            String id,
            int actionPeriod,
            Point position,
            int animationPeriod,
            List<PImage> images)
    {
        return new Crab(id, position, images, 0, actionPeriod, animationPeriod);

    }

    public static Quake createQuake(Point position, List<PImage> images) {
        return new Quake(QUAKE_ID, position, images,0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Entity createSeaGrass(
            String id, Point position, int actionPeriod, List<PImage> images) {
        return new SeaGrass(id, position, images, 0, actionPeriod);
    }

    public static Entity createAtlantis(String id, Point position,List<PImage> images) {
        return new Atlantis(id, position, images,0);
    }

    public static Action createActivityAction(ActiveEntity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore);}


    public static Animation createAnimationAction(Animation entity, int repeatCount) {
        return new Animation(entity, repeatCount); }

}