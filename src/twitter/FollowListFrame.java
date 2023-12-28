package twitter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class FollowListFrame {

	public static void FollowingList(Connection con, String user_id, String target_id) {
		JFrame frame = new JFrame("Following List");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 720);
        
        JPanel top = new JPanel();
        top.setLayout(null);
        top.setBounds(0, 0, 420, 80);
        top.setBackground(new Color(242, 242, 242));
        
        JPanel middle = new JPanel();
        middle.setLayout(null);
        middle.setBounds(0, 80, 405, 635);        

        JScrollPane scroll = new JScrollPane(middle, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 80, 405, 635);
        scroll.setVisible(true);
        
        listComp.yPoint=0;
        int cnt=FollowJDBC.followingList(con, target_id);
        for(int i=0; i<cnt; i++) {
        	Component newComponent = listComp.followComp(con, target_id, FollowJDBC.followingList.get(i), listComp.yPoint, frame);
        	middle.add(newComponent);
        }
        
        JLabel tit = new JLabel("Following List");
        Font titFont = new Font("Arial", Font.BOLD, 17);
        tit.setFont(titFont);
        tit.setBounds(140, 13, 200, 50);
        tit.setForeground(new Color(127,127,127));
        top.add(tit);
        
        ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
        Image Previmage = iconPrev.getImage();
        Image newPrevImage = Previmage.getScaledInstance(38, 16, Image.SCALE_SMOOTH);
        ImageIcon PrevIcon = new ImageIcon(newPrevImage);
        JButton PrevBtn = new JButton(PrevIcon);
        PrevBtn.setLayout(null);
        PrevBtn.setContentAreaFilled(false);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setBounds(-30, 12, 120, 50);
        PrevBtn.setBorder(null);
        PrevBtn.setFocusPainted(false);
        
        PrevBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ProfileGUI.ProfileGui(con, user_id); 
            	frame.setVisible(false);
            }
        });

        top.add(PrevBtn);
        
        middle.revalidate();
        middle.repaint();
        frame.add(scroll);
        frame.add(top);
        frame.setVisible(true);
        frame.setResizable(false); 
        frame.setLocationRelativeTo(null);
        
        middle.setPreferredSize(new Dimension(405, 100 * cnt));
        
        frame.setResizable(false); 
	    frame.setLocationRelativeTo(null);
        
	}
	
	public static void FollowerList(Connection con, String user_id, String target_id) {
		
		JFrame frame = new JFrame("Follower List");
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
        
        listComp.yPoint=0;
        int cnt=FollowJDBC.followerList(con, target_id);
        for(int i=0; i<cnt; i++) {
        	Component newComponent = listComp.followComp(con, target_id, FollowJDBC.followerList.get(i), listComp.yPoint, frame);
        	middle.add(newComponent);
        }
        
        JLabel tit = new JLabel("Follower List");
        Font titFont = new Font("Arial", Font.BOLD, 17);
        tit.setFont(titFont);
        tit.setBounds(140, 13, 200, 50);
        tit.setForeground(new Color(127,127,127));
        top.add(tit);
        
        ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
        Image Previmage = iconPrev.getImage();
        Image newPrevImage = Previmage.getScaledInstance(38, 16, Image.SCALE_SMOOTH);
        ImageIcon PrevIcon = new ImageIcon(newPrevImage);
        JButton PrevBtn = new JButton(PrevIcon);
        PrevBtn.setLayout(null);
        PrevBtn.setContentAreaFilled(false);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setBounds(-30, 12, 120, 50);
        PrevBtn.setBorder(null);
        PrevBtn.setFocusPainted(false);
        
        PrevBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ProfileGUI.targetGUI(con, user_id, target_id); 
            	frame.setVisible(false);
            }
        });

        top.add(PrevBtn);
        
        middle.revalidate();
        middle.repaint();
        frame.add(scroll);
        frame.add(top);
        frame.add(bottom);
        frame.setVisible(true);
        frame.setResizable(false); 
        frame.setLocationRelativeTo(null);
        
        middle.setPreferredSize(new Dimension(405, 100 * cnt));
        
        frame.setResizable(false); 
	    frame.setLocationRelativeTo(null);
	}
}
