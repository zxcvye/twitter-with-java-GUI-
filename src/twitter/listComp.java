package twitter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class listComp {

	static int yPoint=0;
	
	public static Component followComp(Connection con, String user_id, String target_id, int y, JFrame parentFrame) {
		JPanel comp = new JPanel();
		comp.setLayout(null);
        comp.setBounds(10, y + 10, 368, 90);
        comp.setBackground(Color.WHITE);
        
        yPoint=comp.getY()+90;
        
        ImageIcon iconProfile = new ImageIcon(getValue.getProfile(con, target_id));
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(20, 16, 55, 55);
        comp.add(profileBtn);
        
        JLabel name = new JLabel(getValue.getInfo(con, target_id, "name"));
        name.setLayout(null);
        name.setBounds(profileBtn.getX()+78, 17, 100, 40);
        Font NameFont = new Font("Arial", Font.BOLD, 22);
        name.setFont(NameFont);
        comp.add(name, BorderLayout.CENTER);
        
        JLabel user_id_lab = new JLabel("@" + target_id);
        Font idFont = new Font("Arial", Font.PLAIN, 11);
        user_id_lab.setFont(idFont);
        user_id_lab.setLayout(null);
        user_id_lab.setForeground(new Color(140, 140, 140));
        user_id_lab.setBounds(name.getX(), name.getY() + 31, 100, 20);
        comp.add(user_id_lab, BorderLayout.CENTER);
        
        JButton followBtn = new JButton("follow");
        followBtn.setLayout(null);
        followBtn.setBounds(name.getX()+150, 28, 100, 30);
        followBtn.setBackground(new Color(0, 172, 238));
        followBtn.setForeground(Color.WHITE);
        comp.add(followBtn, BorderLayout.CENTER);
        

        JButton unfollowBtn = new JButton("unfollow");
        unfollowBtn.setLayout(null);
        unfollowBtn.setBounds(name.getX()+150, 28, 100, 30);
        comp.add(unfollowBtn, BorderLayout.CENTER);
        
        LineBorder lineBorder = new LineBorder(Color.LIGHT_GRAY, 1, true);
        followBtn.setBorder(lineBorder);
        unfollowBtn.setBorder(lineBorder);
              
        
        if (FollowJDBC.alreadyFollow(con, user_id, target_id)) {
            followBtn.setVisible(false);
            unfollowBtn.setVisible(true);
        } else {
            followBtn.setVisible(true);
            unfollowBtn.setVisible(false);
        }
        
        followBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FollowJDBC.follow(con, user_id, target_id);
                followBtn.setVisible(false);
                unfollowBtn.setVisible(true);
            }
        });
        
        unfollowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	FollowJDBC.unfollow(con, user_id, target_id);
            	followBtn.setVisible(true);
                unfollowBtn.setVisible(false);
            }
        });
        
        if(user_id==target_id) {
        	followBtn.setVisible(false);
        	unfollowBtn.setVisible(false);
        }
        
        unfollowBtn.setContentAreaFilled(false);
        
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProfileGUI.targetGUI(con, user_id, target_id);
                parentFrame.setVisible(false);
            }
        });
        
        return comp;
	}
}
