import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TitleButtons extends JPanel
{
   JFrame frame1;
   JFrame newFrame;
   public TitleButtons(JFrame frame)
   {
      /*frame1 = frame;
      JButton start = new JButton("Start");
      JButton controls = new JButton("Controls");
      JButton menu = new JButton("Menu");
      start.addActionListener(new StartListener());
      controls.addActionListener(new ControlListener());
      menu.addActionListener(new MenuListener());
      add(controls);
      add(start);
      add(menu);*/
   }
   private class StartListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
        
      }
   }
   
    private class ControlListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         /*frame1.setVisible(false);
         if(newFrame == null)
         {
            newFrame = new JFrame();
            newFrame.setVisible(true);
            newFrame.setLocation(100, 100);
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setContentPane(new Game());
            newFrame.setSize(300, 300);
         }
         else
            newFrame.setVisible(true);*/
      }
   }
   
    private class MenuListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         /*frame1.setVisible(false);
         if(newFrame == null)
         {
            newFrame = new JFrame();
            newFrame.setVisible(true);
            newFrame.setLocation(100, 100);
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setContentPane(new Game());
            newFrame.setSize(300, 300);
         }
         else
            newFrame.setVisible(true);*/
      }
   }
}

