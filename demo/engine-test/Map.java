import java.io.*;
import java.util.*;

public class Map {
   public int[][] map;
   private String loc;
   private int length, width;
   
   public Map (String location) {
      loc = location;
      load();
   }
   
   private void load() {
      Scanner infile = null;
      try {
         infile = new Scanner(new File(loc));
         length = infile.nextInt();
         width = infile.nextInt();
         map = new int[length][width];
         for(int r = 0; r < length; r++) {
            for(int c = 0; c < width; c++) {
               map[r][c] = infile.nextInt();
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public int getLength() {
      return length;
   }
   
   public int getWidth() {
      return width;
   }
}