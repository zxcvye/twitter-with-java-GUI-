package twitter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;



public class ArticleCompFrame {
	static int yPoint=0;
	
    public static Component articleCom(Connection con, String user_id, String article_id, int y, JFrame frame) {
        JPanel article = new JPanel();
        article.setLayout(null);
        article.setBounds(10, y + 10, 368, 150);
        article.setBackground(Color.WHITE);
        
        yPoint=article.getY()+150;
        
        ImageIcon iconProfile = new ImageIcon(getValue.getProfile(con, user_id));
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(20, 15, 40, 40);
        article.add(profileBtn);

        JLabel name = new JLabel(getValue.getInfo(con, user_id, "name"));
        name.setLayout(null);
        name.setBounds(profileBtn.getX() + 50, profileBtn.getY() - 5, 100, 40);
        Font NameFont = new Font("Arial", Font.BOLD, 17);
        name.setFont(NameFont);
        article.add(name, BorderLayout.CENTER);

        JLabel user_id_lab = new JLabel("@" + user_id);
        Font idFont = new Font("Arial", Font.PLAIN, 11);
        user_id_lab.setFont(idFont);
        user_id_lab.setLayout(null);
        user_id_lab.setForeground(new Color(140, 140, 140));
        user_id_lab.setBounds(name.getX(), name.getY() + 28, 100, 20);
        article.add(user_id_lab, BorderLayout.CENTER);
        
        //heart
        ImageIcon iconHeart = new ImageIcon("../twitter/src/image/articleHeart.png");
        Image Heartimage = iconHeart.getImage();
        Image newHeartImage = Heartimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartIcon = new ImageIcon(newHeartImage);
        JButton heart = new JButton(HeartIcon);
        heart.setLayout(null);
        heart.setBounds(20, 120, 20, 20);
        heart.setContentAreaFilled(false);
        heart.setBorderPainted(false);
        article.add(heart);
        
        //comment
        ImageIcon iconCmt = new ImageIcon("../twitter/src/image/comment.png");
        Image cmtimage = iconCmt.getImage();
        Image newCmtImage = cmtimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon CmtIcon = new ImageIcon(newCmtImage);
        JButton comment = new JButton(CmtIcon);
        comment.setLayout(null);
        comment.setBounds(heart.getX()+50, 121, 20, 20);
        comment.setContentAreaFilled(false);
        comment.setBorderPainted(false);
        article.add(comment);
        
        //heart-red
        ImageIcon iconHeartRed = new ImageIcon("../twitter/src/image/articleHeart_red.png");
        Image HeartRedimage = iconHeartRed.getImage();
        Image newHeartRedImage = HeartRedimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartRedIcon = new ImageIcon(newHeartRedImage);
        JButton heartRed = new JButton(HeartRedIcon);
        heartRed.setLayout(null);
        heartRed.setBounds(20, 120, 20, 20);
        heartRed.setContentAreaFilled(false);
        heartRed.setBorderPainted(false);
        article.add(heartRed);
        heartRed.setVisible(false);
        
        //Cnt like
        int intcntLike = LikeJDBC.likeList(con, article_id, "ARTICLE_LIKE");
        JLabel cntLike = new JLabel(Integer.toString(intcntLike));
        Font likeFont = new Font("Arial", Font.PLAIN, 11);
        cntLike.setFont(likeFont);
        cntLike.setBounds(heart.getX()+30,115,50,30);
        article.add(cntLike); 
        
        
        //Cnt Comment
        LikeJDBC.likeList(con, article_id, "ARTICLE_LIKE");
        JLabel cntCmt = new JLabel(Integer.toString(getValue.getComment(con, article_id, "ARTICLE")));
        cntCmt.setFont(likeFont);
        cntCmt.setBounds(comment.getX()+30,115,50,30);
        article.add(cntCmt); 
  
        
        //BUTTON EVENT
        heart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	heart.setVisible(false);
            	heartRed.setVisible(true);   
            	LikeJDBC.like(con, InfoJDBC.id, article_id,"ARTICLE_LIKE");
            	int intcntLike=LikeJDBC.likeList(con, article_id,"ARTICLE_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            }});
        comment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("main->comment");
            	CommentFrame cs = new CommentFrame(con,user_id,article_id);
		        cs.setVisible(true);
		       
            }});
        heartRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	heartRed.setVisible(false);
            	heart.setVisible(true);
            	LikeJDBC.delete(con, InfoJDBC.id, article_id,"ARTICLE");
            	int intcntLike=LikeJDBC.likeList(con, article_id,"ARTICLE_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            }});
        article.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	frame.setVisible(false);
                CommentFrame screen = new CommentFrame(con, user_id, article_id);
                screen.setVisible(true);
            }
        });
        
        String cont = getValue.getContent(con, article_id, "ARTICLE");
        JLabel content = new JLabel(cont);
        content.setBounds(profileBtn.getX() + 10, profileBtn.getY() + 55, 300, 40);
        Font contFont = new Font("돋움", Font.PLAIN, 16);
        content.setFont(contFont);
        article.add(content);

        return article;
        
    }
}
