import processing.core.PImage;

import java.util.List;

public class Atlantis extends Entity {

    public Atlantis(String id, Point position, List<PImage> images,
                    int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeAtlantisActivity( WorldModel world,
                                         ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);

    }


}

