import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {
   private JLabel header;
   
   private JPanel select;
   private JLabel map;
   private JTextField name;
   private JButton change;
   private JLabel status;
   
   private JButton start;
   
   public Menu(ActionListener mapListener, ActionListener startListener) {
      BorderLayout layout = new BorderLayout();
      layout.setVgap(20);
      setLayout(layout);
      setBackground(new Color(0, 0, 0, 180));
      
      header = new JLabel("Pause Menu", SwingConstants.CENTER);
      header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
      header.setForeground(Color.WHITE);
      add(header, BorderLayout.NORTH);
      
      start = new JButton("Resume");
      start.addActionListener(startListener);
      
      select = new JPanel(new FlowLayout());
      select.setBackground(new Color(0, 0, 0, 0));
      map = new JLabel("Enter Map Name", SwingConstants.CENTER);
      map.setForeground(Color.WHITE);
      name = new JTextField("", 10);
      change = new JButton("Select");
      change.addActionListener(mapListener);
      status = new JLabel("", SwingConstants.CENTER);
      status.setForeground(Color.WHITE);
      
      select.add(map);
      select.add(name);
      select.add(change);
      select.add(status);
      
      add(select, BorderLayout.WEST);
      add(start, BorderLayout.SOUTH);
   }
   
   public String getName() {
      return name.getText();
   }
   
   public void success() {
      name.setText("");
      status.setText("Success!");
   }
   
   public void error() {
      name.setText("");
      status.setText("Try Again!");
   }
   
   public void reset() {
      name.setText("");
      status.setText("");
   }
}