import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Zombie extends Texture {
   public double xPos, yPos;

   public Zombie (double x, double y) {
      super("res/zombie.png", 400);
      
      xPos = x;
      yPos = y;
   }
   
   public void load() {
      try {
         BufferedImage image = ImageIO.read(new File(loc));
         int w = 400;
         int h = 400;
         image.getRGB(0, 0, w, h, pixels, 0, w); // takes color data from each pixel and stores in an array
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}