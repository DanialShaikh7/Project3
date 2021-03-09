public class Activity extends Action {


    public Activity(Entity entity, WorldModel world,
                    ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    public void executeActivityAction(EventScheduler scheduler) {

        if (this.getEntity() instanceof OctoFull) {
            ((OctoFull)(this.getEntity())).executeOctoFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof OctoNotFull) {
            ((OctoNotFull)(this.getEntity())).executeOctoNotFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Fish) {
            ((Fish)(this.getEntity())).executeMarioActivity(this.getWorld(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Crab) {
            ((Crab)(this.getEntity())).executeCrabActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof Quake) {
            ((Quake)(this.getEntity())).executeQuakeActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }
        else if (this.getEntity() instanceof SeaGrass) {
            ((SeaGrass)(this.getEntity())).executeSeaGrassActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }

        else if (this.getEntity() instanceof Atlantis) {
            ((Atlantis) (this.getEntity())).executeAtlantisActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }
        else if (this.getEntity() instanceof GrimReaper) {
            ((GrimReaper) (this.getEntity())).executeReaperActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            "this.getEntity().kind"));
        }
    }


    public Action createActivityAction(WorldModel world,
                                       ImageStore imageStore) {
        return new Activity(this.getEntity(), world, imageStore, 0);
    }

}
