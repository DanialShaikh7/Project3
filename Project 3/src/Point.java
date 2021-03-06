import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Point
{
   public final int x;
   public final int y;
   private double g;
   private double f;


   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
      int g = 0;
      int f = 0;
   }

   public double getG() {
      return g;
   }

   public double getF() {
      return f;
   }

   public int getX(){return x;}
   public int getY(){return y;}

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