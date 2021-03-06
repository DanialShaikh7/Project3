import processing.core.PImage;

import java.util.*;
import java.util.stream.Collectors;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;
   private Fish mario;

   private static final String OCTO_KEY = "octo";
   private static final int OCTO_NUM_PROPERTIES = 7;
   private static final int OCTO_ID = 1;
   private static final int OCTO_COL = 2;
   private static final int OCTO_ROW = 3;
   private static final int OCTO_LIMIT = 4;
   private static final int OCTO_ACTION_PERIOD = 5;
   private static final int OCTO_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   public static final String FISH_KEY = "fish";
   private static final int FISH_NUM_PROPERTIES = 5;
   private static final int FISH_ID = 1;
   private static final int FISH_COL = 2;
   private static final int FISH_ROW = 3;
   private static final int FISH_ACTION_PERIOD = 4;
   private static final int FISH_REACH = 1;

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;
   private static final int ATLANTIS_ANIMATION_PERIOD = 70;
   private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

   private static final String SGRASS_KEY = "seaGrass";
   private static final int SGRASS_NUM_PROPERTIES = 5;
   private static final int SGRASS_ID = 1;
   private static final int SGRASS_COL = 2;
   private static final int SGRASS_ROW = 3;
   private static final int SGRASS_ACTION_PERIOD = 4;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String REAPER_KEY = "reaper";
   private static final int REAPER_NUM_PROPERTIES = 6;
   private static final int REAPER_ID = 1;
   private static final int REAPER_COL = 2;
   private static final int REAPER_ROW = 3;
   private static final int REAPER_ACTION_PERIOD = 4;
   private static final int REAPER_ANIMATION_PERIOD = 5;

   private static final int PROPERTY_KEY = 0;

   public int getNumRows() {
      return numRows;
   }

   public int getNumCols() {
      return numCols;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public boolean setMarioPos(int x, int y, EventScheduler scheduler, ImageStore imageStore) {
      try {
         mario.setMarioPos(new Point(mario.getPosition().x + x, mario.getPosition().y + y), this);
         return mario.executeMarioActivity(this, imageStore, scheduler) == true;
      }
      catch (Exception e) {
      }
      return false;
   }

   public void scareEntities(ImageStore imageStore) {
      for (Entity e : entities) {
         if (e instanceof Crab) {
            e.setActionPeriod(e.getActionPeriod()/2);
            e.setAnimationPeriod(e.getAnimationPeriod()/2);
         }
         else if (e instanceof Fish) {
            e.setImages(imageStore.getImageList("mario"));
            e.setAnimationPeriod(1000);
            e.setActionPeriod(1000);
         }
      }
   }

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }


   public boolean parseBackground(String [] properties,
                                  ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseOcto(String [] properties,
                            ImageStore imageStore)
   {
      if (properties.length == OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         Entity entity = Factory.createOctoNotFull(properties[OCTO_ID], pt,
                 imageStore.getImageList(OCTO_KEY),
                 Integer.parseInt(properties[OCTO_LIMIT]),
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]));
         tryAddEntity(entity);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }


   public boolean parseReaper(String [] properties,
                            ImageStore imageStore)
   {
      if (properties.length == REAPER_NUM_PROPERTIES)
      {
         System.out.println("got here");
         Point pt = new Point(Integer.parseInt(properties[REAPER_COL]),
                 Integer.parseInt(properties[REAPER_ROW]));
         Entity entity = Factory.createGrimReaper(properties[REAPER_ID], pt,
                 Integer.parseInt(properties[REAPER_ACTION_PERIOD]),
                 Integer.parseInt(properties[REAPER_ANIMATION_PERIOD]),
                 imageStore.getImageList(REAPER_KEY));
         tryAddEntity(entity);
      }

      System.out.println(properties.length == REAPER_NUM_PROPERTIES);
      return properties.length == REAPER_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = Factory.createObstacle(properties[OBSTACLE_ID], pt,
                 imageStore.getImageList(OBSTACLE_KEY));
         tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseFish(String [] properties, ImageStore imageStore)
   {
      if (properties.length == FISH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                 Integer.parseInt(properties[FISH_ROW]));
         Fish entity = Factory.createFish(properties[FISH_ID], pt,
                 imageStore.getImageList(FISH_KEY),
                 Integer.parseInt(properties[FISH_ACTION_PERIOD]));
         tryAddEntity(entity);
      }

      return properties.length == FISH_NUM_PROPERTIES;
   }

   public boolean parseAtlantis(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));

         Entity entity = Factory.createAtlantis(properties[ATLANTIS_ID], pt,
                 imageStore.getImageList(ATLANTIS_KEY));
         tryAddEntity(entity);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

   public boolean parseSeaGrass(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == SGRASS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                 Integer.parseInt(properties[SGRASS_ROW]));
         Entity entity = Factory.createSeaGrass(properties[SGRASS_ID], pt,
                 Integer.parseInt(properties[SGRASS_ACTION_PERIOD]),
                 imageStore.getImageList(SGRASS_KEY));
         tryAddEntity(entity);
      }

      return properties.length == SGRASS_NUM_PROPERTIES;
   }

   public boolean processLine(String line, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {

         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, imageStore);
            case OCTO_KEY:
               return parseOcto(properties, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, imageStore);
            case FISH_KEY:
               return parseFish(properties, imageStore);
            case ATLANTIS_KEY:
               return parseAtlantis(properties, imageStore);
            case SGRASS_KEY:
               return parseSeaGrass(properties, imageStore);
            case REAPER_KEY:
               return parseReaper(properties, imageStore);
         }
      }

      return false;
   }


   public void tryAddEntity(Entity entity)
   {
      if (isOccupied( entity.getPosition()))
      {

         throw new IllegalArgumentException("position occupied");
      }

      if (entity instanceof Fish) {
         mario = (Fish)entity;
      }
      if (entity instanceof GrimReaper) {
         System.out.println("WE GOT HERE");
      }
      addEntity(entity);
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < numRows &&
              pos.x >= 0 && pos.x < numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds( pos) &&
              getOccupancyCell(pos) != null;
   }



   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
//         for (Entity a : entities) {
//            if (a instanceof Crab) {
//               count++;
//            }
//         }
//         if (count < 15) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
         }


      }

//   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);


         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
         entity.setImages(null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos)) {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
      }
      else {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos,
                                Entity entity)
   {
      occupancy[pos.y][pos.x] = entity;
   }

   public Background getBackgroundCell(Point pos)
   {
      return background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }


   public Optional<Entity> findNearest(Point pos, Entity entityOG) {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities) {
         if (entity.getClass().equals(entityOG.getClass())) {
            ofType.add(entity);
         }
      }
      return pos.nearestEntity(ofType);
   }


   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++)
      {
         for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }


   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line NUMBER FORMAT %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }


}