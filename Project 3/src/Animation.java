public class Animation extends Action {

    public Animation(Entity entity, WorldModel world,
                     ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        executeAnimationAction(scheduler);
    }

    public void executeAnimationAction(EventScheduler scheduler){
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            if (this.getEntity() instanceof OctoFull) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((OctoFull)(this.getEntity())).getAnimationPeriod());
            }
            else if (this.getEntity() instanceof OctoNotFull) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((OctoNotFull)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Fish) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Fish)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Crab) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Crab)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Quake) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Quake)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof SeaGrass) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((SeaGrass)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof Atlantis) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((Atlantis)(this.getEntity())).getAnimationPeriod());
            }

            else if (this.getEntity() instanceof GrimReaper) {
                scheduler.scheduleEvent(this.getEntity(),
                        createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
                        ((GrimReaper)(this.getEntity())).getAnimationPeriod());
            }

            else {
                throw new UnsupportedOperationException(
                        String.format("executeActivityAction not supported for %s",
                                "this.getEntity().kind"));
            }
        }
    }


    public Action createAnimationAction(int repeatCount) {
        return new Animation(this.getEntity(), null, null, repeatCount);
    }
}