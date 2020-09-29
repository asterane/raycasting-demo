import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Display extends JPanel {

   private Texture wood = new Texture("res/wood.jpg", 512);
   private Texture stone = new Texture("res/stone.jpg", 512);
   private Texture redbrick = new Texture("res/redbrick.jpg", 512);
   private Texture bluebrick = new Texture("res/bluebrick.jpg", 512);

   private int width;
   private int height;
   private String location;
   private Thread thread;
   private boolean running;
   private Map map;
   private BufferedImage image;
   private Graphics buffer;
   private ArrayList<Texture> textures;
   private Timer t;
   public Camera camera;
   public Screen screen;
   public int[] pixels;
   
   public Robot robot;
  
   double mouseX;
   double mouseY;
   double lastX;
   double lastY;
   
   private boolean fire;
   
   ImageIcon gun = new ImageIcon("res/gun.png");
   ImageIcon flash = new ImageIcon("res/flash.png");
   ImageIcon target = new ImageIcon("res/target.png");
   
   public Display(int w, int h, String loc) {
      width = w;
      height = h;
      location = loc;
      
      map = new Map(location);
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      buffer = image.getGraphics();
      
      textures = new ArrayList<Texture>();
      textures.add(wood);
      textures.add(stone);
      textures.add(redbrick);
      textures.add(bluebrick);
      
      camera = new Camera (4.5, 4.5, 1, 0, 0, -.66, 0.12, Math.PI/120);
      screen = new Screen (map, textures, w, h);
      pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
      
      setBackground(Color.BLACK);
      addKeyListener(camera);
      addMouseListener(new Mouse());
      lastX = width / 2.0;
      lastY = height / 2.0;
      
      t = new Timer(5, new Listener());
      t.start();
      
      try {
         robot = new Robot();
      } catch (Exception e) {
         return;
      }
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(gun.getImage(), 330, 400, null);
   }
   
   public void update() {
      if(getMousePosition() != null) {
         try {
            mouseX = getMousePosition().getX();
            mouseY = getMousePosition().getY();
         } catch (Exception e) {
            if(lastX > width / 2) robot.mouseMove((int)getLocationOnScreen().getX(), (int)getLocationOnScreen().getY()+height/2);
            else robot.mouseMove((int)getLocationOnScreen().getX()+width, (int)getLocationOnScreen().getY()+height/2);
         }
      } else {
         if(lastX > width / 2) robot.mouseMove((int)getLocationOnScreen().getX(), (int)getLocationOnScreen().getY()+height/2);
         else robot.mouseMove((int)getLocationOnScreen().getX()+width, (int)getLocationOnScreen().getY()+height/2);
      }
      double dir = -(mouseX / width) * 360;
      camera.setDegrees(dir);
      lastX = mouseX;
      lastY = mouseY;
   }
   
   private class Mouse extends MouseAdapter {
      public void mousePressed(MouseEvent e) {
         fire = true;
      }
      public void mouseReleased(MouseEvent e) {
         fire = false;
      }  
   }
   
   private class Listener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         requestFocus();
         screen.update(camera, pixels);
         camera.update(map.map);
         update();
         if(fire) buffer.drawImage(flash.getImage(), 430, 300, null);
         buffer.drawImage(target.getImage(), (width/2)-210, (height/2)-210, null);
         repaint();
      }
   }
}