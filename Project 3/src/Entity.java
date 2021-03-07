import processing.core.PImage;

import java.util.List;


public abstract class Entity {

   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;
   public int resourceLimit;
   public int resourceCount;
   private int actionPeriod;
   private int animationPeriod;

   public Entity(String id, Point position, List<PImage> images,
                 int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = imageIndex;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }

   public void setId(String newId) {
      this.id = newId;
   }

   public String getId() {
      return id;
   }

   public void nextImage() {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }

   public void setPosition(Point newPos) {
      this.position = newPos;
   }

   public Point getPosition() {
      return position;
   }

   public void setImages(List<PImage> newImages) {
      this.images = newImages;
   }

   public List<PImage> getImages() {
      return images;
   }

   public void setResourceLimit(int newResLim) {
      this.resourceLimit = newResLim;
   }

   public int getResourceLimit() {
      return resourceLimit;
   }

   public void setResourceCount(int newResCt) {
      this.resourceCount = newResCt;
   }

   public int getResourceCount() {
      return resourceCount;
   }

   public void setActionPeriod(int newActPer) {
      this.actionPeriod = newActPer;
   }

   public int getActionPeriod() {
      return actionPeriod;
   }

   public void setAnimationPeriod(int newAnimPer) {
      this.animationPeriod = newAnimPer;
   }

   public int getAnimationPeriod() {
      return animationPeriod;
   }

   public PImage getCurrentImage()
   {
      return (this).images.get((this).imageIndex);
   }

   public void scheduleActions(EventScheduler scheduler,
                               WorldModel world, ImageStore imageStore) {
      Activity act = new Activity(this, world, imageStore, 0);
      Animation anim = new Animation(this, world, imageStore, 0);
      scheduler.scheduleEvent(this, act.createActivityAction(world, imageStore),
              this.getActionPeriod());
      scheduler.scheduleEvent(this, anim.createAnimationAction(0),
              this.getAnimationPeriod());
   }
}
