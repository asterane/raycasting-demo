import javax.swing.*;

public class GamePanelDriver
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("xd");
      frame.setSize(1250,1250);
      frame.setLocation(0,0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new GamePanel(frame));
      frame.setVisible(true);
   }
}