import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Texture {
   public int[] pixels;
   private String loc;
   private final int SIZE;
   
   public Texture (String location, int size) {
      loc = location;
      SIZE = size;
      pixels = new int[SIZE * SIZE];
      load();
   }
   
   private void load() {
      try {
         BufferedImage image = ImageIO.read(new File(loc));
         int w = image.getWidth();
         int h = image.getHeight();
         image.getRGB(0, 0, w, h, pixels, 0, w); // takes color data from each pixel and stores in an array
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public int getSize() {
      return SIZE;
   }
}