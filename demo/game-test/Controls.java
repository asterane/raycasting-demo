import java.awt.event.*;

public class Controls implements KeyListener {
   public boolean forward, back, left, right, up, down, lookL, lookR, lookU, lookD;

   public Controls() {}
   
   public void keyPressed (KeyEvent key) {
      if((key.getKeyCode() == KeyEvent.VK_W))
         forward = true;
      if((key.getKeyCode() == KeyEvent.VK_S))
         back = true;
      if((key.getKeyCode() == KeyEvent.VK_A))
         left = true;
      if((key.getKeyCode() == KeyEvent.VK_D))
         right = true;
      if((key.getKeyCode() == KeyEvent.VK_R))
         up = true;
      if((key.getKeyCode() == KeyEvent.VK_F))
         down = true;
      if((key.getKeyCode() == KeyEvent.VK_LEFT))
         lookL = true;
      if((key.getKeyCode() == KeyEvent.VK_RIGHT))
         lookR = true;
      if((key.getKeyCode() == KeyEvent.VK_UP))
         lookU = true;
      if((key.getKeyCode() == KeyEvent.VK_DOWN))
         lookD = true;
   }
   
   public void keyReleased (KeyEvent key) {
      if((key.getKeyCode() == KeyEvent.VK_W))
         forward = false;
      if((key.getKeyCode() == KeyEvent.VK_S))
         back = false;
      if((key.getKeyCode() == KeyEvent.VK_A))
         left = false;
      if((key.getKeyCode() == KeyEvent.VK_D))
         right = false;
      if((key.getKeyCode() == KeyEvent.VK_R))
         up = false;
      if((key.getKeyCode() == KeyEvent.VK_F))
         down = false;
      if((key.getKeyCode() == KeyEvent.VK_LEFT))
         lookL = false;
      if((key.getKeyCode() == KeyEvent.VK_RIGHT))
         lookR = false;
      if((key.getKeyCode() == KeyEvent.VK_UP))
         lookU = false;
      if((key.getKeyCode() == KeyEvent.VK_DOWN))
         lookD = false;
   }
   
   public void keyTyped (KeyEvent key) {}
   
}