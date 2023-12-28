package twitter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//사용법!!!
//ImageIcon imageName = new ImageIcon("../twitter/src/image/profile.jpg"); 이미지 경로 넣으면 되는데 저렇게 image폴더에 넣으세요! 
//RoundButton variableName = new RoundButton(imageName); 변수명 알아서
//variableName.setBounds(x,y,width,height); 위치, 크기 조절 
//frame.add(variableName); panel이면 panel, frame이면 frame에 add! 

@SuppressWarnings("serial")
class RoundButton extends JButton {
    private Image originalIcon;

    public RoundButton(ImageIcon icon) {
        super("");
        setContentAreaFilled(false);
        this.originalIcon = icon.getImage();
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }

        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        Image clippedImage = clipToRound(originalIcon);
        if (clippedImage != null) {
            int x = (getWidth() - clippedImage.getWidth(this)) / 2;
            int y = (getHeight() - clippedImage.getHeight(this)) / 2;
            g.drawImage(clippedImage, x, y, this);
        }

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        // Do nothing for the border
    }

    Shape shape;

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    private Image clipToRound(Image original) {
        if (original == null) {
            return null;
        }

        int diameter = Math.min(getWidth(), getHeight());
        BufferedImage clippedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = clippedImage.createGraphics();
        g2d.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2d.drawImage(original, 0, 0, diameter, diameter, null);
        g2d.dispose();

        return clippedImage;
    }
}

