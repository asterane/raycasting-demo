import java.io.*;
import java.util.*;

public class Map {
   public int[][] map;
   private String loc;
   private int length, width;
   private double startX, startY;
   
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
         startX = infile.nextDouble();
         startY = infile.nextDouble();
         map = new int[width][length];
         for(int r = 0; r < width; r++) {
            for(int c = 0; c < length; c++) {
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
   
   public double getStartX() {
      return startX;
   }
   
   public double getStartY() {
      return startY;
   }
}