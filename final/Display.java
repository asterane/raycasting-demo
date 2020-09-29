import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Display extends JPanel {

   private Wall wood = new Wall("res/wood.jpg", 512);
   private Wall stone = new Wall("res/stone.jpg", 512);
   private Wall redbrick = new Wall("res/redbrick.jpg", 512);
   private Wall bluebrick = new Wall("res/bluebrick.jpg", 512);

   private int width;
   private int height;
   private String location;
   private Thread thread;
   private boolean running;
   private Map map;
   private BufferedImage image;
   private Graphics buffer;
   private ArrayList<Wall> textures;
   public Controls controls;
   public Camera camera;
   public Screen screen;
   public int[] pixels;
   
   public Robot robot;
   
   private boolean fire;
   
   ImageIcon gun = new ImageIcon("res/gun.png");
   ImageIcon flash = new ImageIcon("res/flash.png");
   ImageIcon target = new ImageIcon("res/target.png");
   
   public Display(int w, int h, Controls c) {
      width = w;
      height = h;
      
      map = new Map("map/default.txt");
      controls = c;
      
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      buffer = image.getGraphics();
      
      textures = new ArrayList<Wall>();
      textures.add(wood);
      textures.add(stone);
      textures.add(redbrick);
      textures.add(bluebrick);
      
      camera = new Camera (map.getStartX(), map.getStartY(), 1, 0, 0, -.66, 0.12, Math.PI/120);
      screen = new Screen (map, textures, w, h);
      pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
      
      addMouseListener(new Mouse());
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
   }

   private class Mouse extends MouseAdapter {
      public void mousePressed(MouseEvent e) {
         fire = true;
      }
      public void mouseReleased(MouseEvent e) {
         fire = false;
      }  
   }
   
   public boolean setMap(String m) {
      File folder = new File("map");
      File[] listOfFiles = folder.listFiles();

      for (int i = 0; i < listOfFiles.length; i++) {
         if (m.equals(listOfFiles[i].getName())) {
            map = new Map("map/"+m);
            return true;
         }
      }
      return false;
   }
   
   public void reset() {
      camera = new Camera (map.getStartX(), map.getStartY(), 1, 0, 0, -.66, 0.12, Math.PI/120);
      screen = new Screen (map, textures, width, height);
   }
   
   public boolean save() {
      PrintStream outfile;
      try {
         outfile = new PrintStream(new FileOutputStream("save.txt"));
      } catch(Exception e) {
         return false;
      }
      
      outfile.println(map.getLoc());
      outfile.println(camera.getX()+"");
      outfile.println(camera.getY()+"");
      outfile.println(camera.getXDir()+"");
      outfile.println(camera.getYDir()+"");
      outfile.println(camera.getXPlane()+"");
      outfile.println(camera.getYPlane()+"");
      
      return true;
   }
   
   public boolean load() {
      Scanner infile = null;
      try {
         infile = new Scanner(new File("save.txt"));
      } catch(Exception e) {
         return false;
      }
      
      map = new Map(infile.next());
      camera = new Camera(infile.nextDouble(), infile.nextDouble(), infile.nextDouble(),
                           infile.nextDouble(), infile.nextDouble(), infile.nextDouble(),
                           0.12, Math.PI/120);
      screen = new Screen(map, textures, width, height);
   
      return true;
   }
   
   public void frame(double mx, double my) {
		screen.update(camera, pixels);
      camera.update(map.map, controls);
      
      double dir = -(mx / width) * 360;
      camera.setDegrees(dir);
      
      if(fire) buffer.drawImage(flash.getImage(), 430, 300, null);
      buffer.drawImage(target.getImage(), (width/2)-210, (height/2)-210, null);
      buffer.drawImage(gun.getImage(), 330, 400, null);
      
      repaint();
   }
}