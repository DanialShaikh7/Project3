public abstract class Action {

   private Entity entity;
   private WorldModel world;
   private ImageStore imageStore;
   private int repeatCount;

   public Action(Entity entity, WorldModel world,
                 ImageStore imageStore, int repeatCount) {
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   protected abstract void executeAction(EventScheduler scheduler);

   public Entity getEntity() {
      return entity;
   }

   public void setEntity(Entity newEnt) {
      this.entity = newEnt;
   }

   public WorldModel getWorld() {
      return world;
   }

   public void setWorld(WorldModel newWM) {
      this.world = newWM;
   }

   public ImageStore getImageStore() {
      return imageStore;
   }

   public void setImageStore(ImageStore newIS) {
      this.imageStore = newIS;
   }

   public int getRepeatCount() {
      return repeatCount;
   }

   public void setRepeatCount(int newRC) {
      this.repeatCount = newRC;
   }

}