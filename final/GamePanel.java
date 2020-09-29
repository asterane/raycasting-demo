import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel {
   private int width;
   private int height;
   private Timer t;
   private BufferedImage image;
   private Graphics buffer;
   private Controls controls;

   private JLayeredPane pane;
   private Display display;
   private Menu menu;
   
   private boolean paused = false;
   
   private double mouseX, mouseY, lastX, lastY;
   private Robot robot;
   private Cursor blankCursor;

   public GamePanel(int w, int h) {
      width = w;
      height = h;
      
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      buffer = image.getGraphics();
      
      controls = new Controls();
      addKeyListener(controls);
      addKeyListener(new Key());
      
      FlowLayout layout = new FlowLayout();
      layout.setVgap(0);
      setLayout(layout);
      
      pane = new JLayeredPane();
      pane.setPreferredSize(new java.awt.Dimension(w, h));
      
      display = new Display(w, h, controls);
      pane.add(display, pane.DEFAULT_LAYER);
      display.setBounds(0, 0, width, height);
      
      menu = new Menu(new MapListener(), new StartListener(),
                        new SaveListener(), new LoadListener());
            
      t = new Timer(5, new TimerListener());
      t.start();
      
      add(pane);
      
      try {
         robot = new Robot();
      } catch (Exception e) {
         return;
      }
      
      BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
      blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                  cursorImg, new Point(0, 0), "blank cursor");
      setCursor(blankCursor);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
   }
   
   private void checkMouse() {
      Point loc;
      try {
         loc = display.getLocationOnScreen();
      } catch (Exception e) {
         loc = getLocationOnScreen();
      }
      try {
         Point pos = getMousePosition();
         mouseX = pos.getX();
         mouseY = pos.getY();
      } catch (Exception e) {
         if(!paused) {
            if(lastX > width / 2) robot.mouseMove((int)loc.getX(), (int)loc.getY()+height/2);
            else robot.mouseMove((int)loc.getX()+width, (int)loc.getY()+height/2);
         }
      }
      if(!paused) {
         lastX = mouseX;
         lastY = mouseY;
      }
   }
   
   private void togglePause() {
      if(!paused) {
         paused = true;
         menu.setBounds(320, 180, 640, 360);
         pane.add(menu, pane.POPUP_LAYER);
         setCursor(Cursor.getDefaultCursor());
         menu.reset();
      } else {
         paused = false;
         pane.remove(menu);
         setCursor(blankCursor);
      }
   }
   
   private class Key extends KeyAdapter {
      public void keyPressed(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePause();
         }
      }
   }
   
   private class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if(!paused) {
            requestFocus();         
            display.frame(mouseX, mouseY);
         } else menu.repaint();
         checkMouse();
         pane.repaint();
         repaint();
      }
   }
   
   private class MapListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (display.setMap(menu.getName()+".txt")) {
            menu.success();
            display.reset();
         } else menu.error();
         display.frame(mouseX, mouseY);
         requestFocus();
      }
   }
   
   private class StartListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         togglePause();
      }
   }
   
   private class SaveListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         display.save();
         requestFocus();
      }
   }
   
   private class LoadListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         display.load();
         display.frame(mouseX, mouseY);
         requestFocus();
      }
   }
}