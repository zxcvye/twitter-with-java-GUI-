package twitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


//class for custom button 
@SuppressWarnings("serial")
class CustomButton extends JButton {
 public CustomButton(String text, Icon icon, int width) {
     super(text, icon);

     setOpaque(false);
     setContentAreaFilled(false);
     setBorderPainted(false);

     setFont(new Font("Arial", Font.PLAIN, 18));
     setForeground(new Color(70, 70, 70));

     setIcon(resizeIcon((ImageIcon) icon, 22, 25));
     setIconTextGap(20); 

     setHorizontalAlignment(SwingConstants.LEFT);
     setMargin(new Insets(0, 25, 0, 0));
 }

 private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
     Image img = icon.getImage();
     Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
     return new ImageIcon(resizedImg);
 }
}