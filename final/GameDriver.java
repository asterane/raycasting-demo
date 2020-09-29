import javax.swing.JFrame;
public class GameDriver {
   public static void main(String[] args) { 
      int w = 1280;
      int h = 720;
      JFrame frame = new JFrame("Final Project Demo");
      frame.setSize(w+14, h+38);    // corrects projection plane size to 1280x720
      frame.setLocation(100, 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GamePanel g = new GamePanel(w, h);
      frame.setContentPane(g);
      g.requestFocus();
      frame.setVisible(true);
   }
}