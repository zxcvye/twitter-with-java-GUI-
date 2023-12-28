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


public class Article_cmt {
	static int yPoint=0;
	
    public static Component articleCom(Connection con, String user_id, String article_id, int y, JFrame frame) {
        /*icon path */
    	
    	String mypath = getValue.getProfile(con, user_id);
    	String RheartPath = "../twitter/src/image/articleHeart_red.png";
    	String cmtPath = "../twitter/src/image/comment.png";
    	String heartPath ="../twitter/src/image/articleHeart.png";
    	String delPath ="../twitter/src/image/delete.png";
    	
    	JPanel article = new JPanel();
        article.setLayout(null);
        article.setBounds(10, y + 10, 368, 170);
        article.setBackground(Color.WHITE);
        
        yPoint=article.getY()+170; // to see more content detail
        
        
        ImageIcon iconProfile = new ImageIcon(mypath);
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
        
        ImageIcon iconEdit = new ImageIcon("../twitter/src/image/edit.png");
        Image Editimage = iconEdit.getImage();
        Image newEditImage = Editimage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon EditIcon = new ImageIcon(newEditImage);
        JButton edit = new JButton(EditIcon);
        edit.setLayout(null);
        edit.setBounds(310, 12, 20, 20);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);
        article.add(edit);
        
       
        //delete Button 
        
        ImageIcon iconDelete = new ImageIcon(delPath);
        Image Deleteimage = iconDelete.getImage();
        Image newDeleteImage = Deleteimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon DeleteIcon = new ImageIcon(newDeleteImage);
        JButton delete = new JButton(DeleteIcon);
        delete.setLayout(null);
        delete.setBounds(335, 12, 20, 20);
        delete.setContentAreaFilled(false);
        delete.setBorderPainted(false);
        article.add(delete);
        
        //heart
      
        ImageIcon iconHeart = new ImageIcon(heartPath);
        Image Heartimage = iconHeart.getImage();
        Image newHeartImage = Heartimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartIcon = new ImageIcon(newHeartImage);
        JButton heart = new JButton(HeartIcon);
        heart.setLayout(null);
        heart.setBounds(20, 140, 20, 20);
        heart.setContentAreaFilled(false);
        heart.setBorderPainted(false);
        article.add(heart);
        
        //comment

        ImageIcon iconCmt = new ImageIcon(cmtPath);
        Image cmtimage = iconCmt.getImage();
        Image newCmtImage = cmtimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon CmtIcon = new ImageIcon(newCmtImage);
        JButton comment = new JButton(CmtIcon);
        comment.setLayout(null);
        comment.setBounds(heart.getX()+50, 141, 20, 20);
        comment.setContentAreaFilled(false);
        comment.setBorderPainted(false);
        article.add(comment);
        
        //heart-red
        
        ImageIcon iconHeartRed = new ImageIcon(RheartPath);
        Image HeartRedimage = iconHeartRed.getImage();
        Image newHeartRedImage = HeartRedimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartRedIcon = new ImageIcon(newHeartRedImage);
        JButton heartRed = new JButton(HeartRedIcon);
        heartRed.setLayout(null);
        heartRed.setBounds(20, 140, 20, 20);
        heartRed.setContentAreaFilled(false);
        heartRed.setBorderPainted(false);
        article.add(heartRed);
        heartRed.setVisible(false);
        
        //Cnt like
        int intcntLike = LikeJDBC.likeList(con, article_id, "ARTICLE_LIKE");
        JLabel cntLike = new JLabel(Integer.toString(intcntLike));
        Font likeFont = new Font("Arial", Font.PLAIN, 11);
        cntLike.setFont(likeFont);
        cntLike.setBounds(heart.getX()+30,135,50,30);
        article.add(cntLike); 
        
        cntLike.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LikeListFrame.ArticleLike(con, user_id, article_id, "ARTICLE");
                frame.setVisible(false);
            }
        });
        
        //Cnt Comment
        JLabel cntCmt = new JLabel(Integer.toString(getValue.getComment(con, article_id,"ARTICLE")));
        cntCmt.setFont(likeFont);
        cntCmt.setBounds(comment.getX()+30,135,50,30);
        article.add(cntCmt); 
        

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteDialog deleteD = new deleteDialog(con, user_id, article_id, "ARTICLE");
            	deleteD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            	deleteD.setVisible(true);
            }});
        heart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	heart.setVisible(false);
            	heartRed.setVisible(true);   
            	LikeJDBC.like(con, user_id, article_id,"ARTICLE_LIKE");
            	int intcntLike=LikeJDBC.likeList(con, article_id,"ARTICLE_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            }});
        heartRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	heartRed.setVisible(false);
            	heart.setVisible(true);
            	LikeJDBC.delete(con, user_id, article_id,"ARTICLE");
            	System.out.println(user_id + article_id);
            	int intcntLike=LikeJDBC.likeList(con, article_id,"ARTICLE_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            }});
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	EditArticleFrame editframe= new EditArticleFrame(con,user_id,article_id,"ARTICLE");
                editframe.setVisible(true);
                frame.setVisible(false);
            }});
        
                
        String cont = getValue.getContent(con, article_id,"ARTICLE");
        JLabel content = new JLabel(cont);
        content.setBounds(profileBtn.getX() + 10, profileBtn.getY() + 55, 300, 40);
        Font contFont = new Font("돋움", Font.PLAIN, 16);
        content.setFont(contFont);
        article.add(content);

        return article;
    }
    
}
