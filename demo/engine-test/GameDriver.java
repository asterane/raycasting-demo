import javax.swing.JFrame;
public class GameDriver {
   public static void main(String[] args) { 
      JFrame frame = new JFrame("Final Project Demo");
      frame.setSize(1294, 758);    // corrects projection plane size to 1280x720
      frame.setLocation(100, 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Display g = new Display(1280, 720, "map/map2.txt");
      frame.setContentPane(g);
      g.requestFocus();
      frame.setVisible(true);
   }
}