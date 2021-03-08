import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Point
{
   public final int x;
   public final int y;
   public double g;
   public double f;

   private final String QUAKE_ID = "quake";
   private final int QUAKE_ACTION_PERIOD = 1100;
   private final int QUAKE_ANIMATION_PERIOD = 100;
   public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   private static final int ATLANTIS_ANIMATION_PERIOD = 70;


   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
      int g = 0;
      int f = 0;
   }

   public void setG(double g) {
      this.g = g;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
              ((Point)other).x == this.x &&
              ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }


   public Obstacle createObstacle(String id, List<PImage> images)
   {
      return new Obstacle(id, this, images, 0, 0, 0, 0);
   }

   public Fish createFish(String id, int actionPeriod,
                        List<PImage> images)
   {
      return new Fish(id, this, images, 0, 0, actionPeriod, 0);
   }

   public Quake createQuake(List<PImage> images)
   {
      return new Quake(QUAKE_ID, this, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public SeaGrass createSgrass(String id, int actionPeriod,
                          List<PImage> images)
   {
      return new SeaGrass(id, this, images, 0, 0, actionPeriod, 0);
   }


   public Atlantis createAtlantis(String id,
                                      List<PImage> images)
   {
      return new Atlantis(id, this, images, 0, 0, 0, ATLANTIS_ANIMATION_PERIOD);
   }

   public OctoFull createOctoFull(String id, int resourceLimit,
                                    int actionPeriod, int animationPeriod,
                                    List<PImage> images)
   {
      return new OctoFull(id, this, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public OctoNotFull createOctoNotFull(String id, int resourceLimit,
                                          int actionPeriod, int animationPeriod,
                                          List<PImage> images)
   {
      return new OctoNotFull(id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }


   public Optional<Entity> nearestEntity(List<Entity> entities)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPosition(), this);

         for (Entity other : entities) {
            int otherDistance = distanceSquared(other.getPosition(), this);

            if (otherDistance < nearestDistance) {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public boolean adjacent(Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }

   public void calculateF(Point end) {
      f = Math.abs(end.x-this.x) + Math.abs(end.y - this.y) + g;
   }




}