package twitter;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class listFrame {
	
	public static JFrame frameList(String title, int cnt) {
		JFrame frame = new JFrame(title);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 720);
        
        JPanel top = new JPanel();
        top.setLayout(null);
        top.setBounds(0, 0, 420, 80);
        top.setBackground(new Color(242, 242, 242));
        
        JPanel middle = new JPanel();
        middle.setLayout(null);
        middle.setBounds(0, 80, 405, 525);
        
        JPanel bottom = new JPanel();
        bottom.setLayout(null);
        bottom.setBounds(0, 605, 420, 110);
        bottom.setBackground(new Color(242, 242, 242));
        frame.add(bottom); 

        JScrollPane scroll = new JScrollPane(middle, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 80, 405, 525);
        scroll.setVisible(true);
        
        
        middle.revalidate();
        middle.repaint();
        frame.add(scroll);
        frame.add(top);
        frame.add(bottom);
        frame.setVisible(true);
        frame.setResizable(false); // Do not allow frame resizing
        frame.setLocationRelativeTo(null);
        
        //사이즈 조절
        middle.setPreferredSize(new Dimension(405, 162 * cnt));
        
		return frame;
	}
}
