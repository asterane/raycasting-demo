import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Wall extends Texture {
   
   public Wall (String location, int size) {
      super(location, size);
   }
   
   public void load() {
      try {
         BufferedImage image = ImageIO.read(new File(loc));
         int w = image.getWidth();
         int h = image.getHeight();
         image.getRGB(0, 0, w, h, pixels, 0, w); // takes color data from each pixel and stores in an array
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}