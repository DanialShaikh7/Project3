import processing.core.PImage;

import java.util.List;

final class Background
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public List<PImage> getImages() {
      return images;
   }

   public void setImages(List<PImage> images) {
      this.images = images;
   }

   public int getImageIndex() {
      return imageIndex;
   }

   public void setImageIndex(int imageIndex) {
      this.imageIndex = imageIndex;
   }

   public PImage getCurrentImage()
   {
      if (this instanceof Background)
      {
         return ((Background)this).images
                 .get(((Background)this).imageIndex);
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         this));
      }
   }

}