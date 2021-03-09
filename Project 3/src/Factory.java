
import processing.core.PImage;

import java.util.List;

public class Factory {


    private static String QUAKE_ID = "quake";
    private static int QUAKE_ACTION_PERIOD =1100;
    private static int QUAKE_ANIMATION_PERIOD =100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    private static final int ATLANTIS_ANIMATION_PERIOD = 70;

    public static OctoNotFull createOctoNotFull(String id, Point position,List<PImage> images, int resourceLimit, int actionPeriod,
                                                  int animationPeriod) {
        return new OctoNotFull(id, position, images, 0,
                resourceLimit,  actionPeriod, animationPeriod);

    }



    public static OctoFull createOctoFull(String id, Point position, int resourceLimit, int actionPeriod, int animationPeriod,
                                            List<PImage> images) {
        return new OctoFull( id, position, images, 0,
                resourceLimit, actionPeriod,
                animationPeriod);
    }

    public static Obstacle createObstacle(String id, Point position,List<PImage> images) {
        return new Obstacle(id, position, images, 0, 0, 0, 0);
    }

    public static Fish createFish(
            String id, Point position,List<PImage> images,int actionPeriod )
    {
        return new Fish(id, position, images, 0, 0, actionPeriod, 0);
    }


    public static Crab createCrab(
            String id,
            int actionPeriod,
            Point position,
            int animationPeriod,
            List<PImage> images)
    {
        return new Crab(id, position, images, 0, 0, actionPeriod, animationPeriod);

    }

    public static Quake createQuake(Point position, List<PImage> images) {
        return new Quake(QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Entity createSeaGrass(
            String id, Point position, int actionPeriod, List<PImage> images) {
        return new SeaGrass(id, position, images, 0, 0, actionPeriod, 0);
    }

    public static Entity createAtlantis(String id, Point position, List<PImage> images){
        return new Atlantis(id, position, images, 0, 0, 0, ATLANTIS_ANIMATION_PERIOD);
    }

    public static GrimReaper createGrimReaper(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new GrimReaper(id, position, images, 0, 0, actionPeriod,animationPeriod);
    }

}