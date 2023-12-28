package twitter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;

public class LikeListFrame {

	
	public static void ArticleLike(Connection con, String user_id, String article_id, String table) {
		JFrame frame = new JFrame("Like List");
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
	    int cnt = 0;
	    switch (table) {
	    case "ARTICLE":
	        cnt=LikeJDBC.likeList(con, article_id, "ARTICLE_LIKE");
	        break;
	    case "COMMENT":
	        cnt=LikeJDBC.likeList(con, article_id, "COMMENT_LIKE");
	        break; 
	    case "CHILD_COMMENT":
	        cnt=LikeJDBC.likeList(con, article_id, "CHILD_CMT_LIKE");
	        break; 
	}
	  
	String temp = "";
	for (int i = 0; i < cnt; i++) {
		System.out.println(getValue.getWriterId(con,LikeJDBC.likeId.get(i)));
	    Component newComponent = listComp.followComp(con, getValue.getWriterId(con,LikeJDBC.likeId.get(i)), LikeJDBC.likeId.get(i), listComp.yPoint, frame);
	    middle.add(newComponent);
	}

	    
	    JLabel tit = new JLabel("Like List");
	    Font titFont = new Font("Arial", Font.BOLD, 17);
	    tit.setFont(titFont);
	    tit.setBounds(160, 13, 200, 50);
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
	        	String arId="";
	        	switch(table) {
	        		case "ARTICLE":
	        			arId=article_id;
	        			break;
	        		case "COMMENT":
	        			arId=getValue.getParentId(con, article_id, "COMMENT");
	        			break;
	        		case "CHILD_CMT":
	        			arId=(getValue.getParentId(con, article_id, "CHILD_CMT"));
	        			break;
	        	}	
	        	CommentFrame cmtF = new CommentFrame(con, user_id, arId);
    	    	cmtF.setVisible(true);
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
	}
}