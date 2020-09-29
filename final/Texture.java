import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public abstract class Texture {
   public int[] pixels;
   public String loc;
   private final int SIZE;
   
   public Texture (String location, int size) {
      loc = location;
      SIZE = size;
      pixels = new int[SIZE * SIZE];
      load();
   }
   
   abstract void load();
   
   public int getSize() {
      return SIZE;
   }
}