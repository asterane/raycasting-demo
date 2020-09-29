import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
public class GamePanel extends JPanel
{
   private TitleButtons buts;
   private int count;
   private JButton menu;
   private JButton start;
   private JButton control;
   private JButton back;
   public GamePanel(JFrame frame)
   {
      setLayout(null);
      addMouseListener(new Mouse());
      
      menu = new JButton();
      ImageIcon icon = new ImageIcon("Menu Button.png");
      Image image = icon.getImage();
      Image newimg = image.getScaledInstance(400, 75, java.awt.Image.SCALE_SMOOTH);
      ImageIcon fin = new ImageIcon(newimg);
      menu.setIcon(fin);
      menu.setBounds(0, 663, 400, 75);
      menu.addActionListener(new Menu());
      add(menu);
      
      start = new JButton();
      ImageIcon icon2 = new ImageIcon("Start Button.png");
      Image image2 = icon2.getImage();
      Image newimg2 = image2.getScaledInstance(420, 170, java.awt.Image.SCALE_SMOOTH);
      ImageIcon fin2 = new ImageIcon(newimg2);
      start.setIcon(fin2);
      start.setBounds(500, 574, 420, 170);
      start.addActionListener(new Start());
      add(start);
      
      control = new JButton();
      ImageIcon icon3 = new ImageIcon("Control Button.png");
      Image image3 = icon3.getImage();
      Image newimg3 = image3.getScaledInstance(259, 87, java.awt.Image.SCALE_SMOOTH);
      ImageIcon fin3 = new ImageIcon(newimg3);
      control.setIcon(fin3);
      control.setBounds(1000, 645, 259, 87);
      control.addActionListener(new Control());
      add(control);
      
      back = new JButton();
      ImageIcon icon4 = new ImageIcon("Back Button.png");
      Image image4 = icon4.getImage();
      Image newimg4 = image4.getScaledInstance(274, 91, java.awt.Image.SCALE_SMOOTH);
      ImageIcon fin4 = new ImageIcon(newimg4);
      back.setIcon(fin4);
      back.setBounds(948, 640, 274, 91);
      back.addActionListener(new Back());
      back.setVisible(false);
      add(back);
      
      count = 0;
      
      
      
      
   }
   public void paintComponent(Graphics g)
   {
      
      ImageIcon tit = new ImageIcon("Title Screen.jpg");
      ImageIcon menu = new ImageIcon("Menu Screen.jpg");
      ImageIcon control = new ImageIcon("Control Screen.jpg");
      switch(count)
      {
         case 0: g.drawImage(tit.getImage(), 0, 0, getWidth(), getHeight(), this) ; break;
         case 1: g.drawImage(menu.getImage(), 0, 0, getWidth(), getHeight(), this) ; break;
         case 2: g.drawImage(control.getImage(), 0, 0, getWidth(), getHeight(), this) ; break;
      }
        
            
   }
   private class Mouse extends MouseAdapter
   {
      public void mousePressed(MouseEvent e)
      {
         System.out.println(e.getX());
         System.out.println(e.getY());
         System.out.println();
      }
   }
   
   private class Start implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
        Game game = new Game();
      }
   }
   
    private class Control implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         count = 2;
         menu.setVisible(false);
         control.setVisible(false);
         start.setVisible(false);
         back.setVisible(true);
         repaint();
      }
   }
   
    private class Menu implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         count = 1;
         menu.setVisible(false);
         control.setVisible(false);
         start.setVisible(false);
         back.setVisible(true);
         repaint();
      }
   }
   
   private class Back implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         back.setVisible(false);
         start.setVisible(true);
         control.setVisible(true);
         menu.setVisible(true);
         count = 0;
         repaint();
      }
   }

   
    
}