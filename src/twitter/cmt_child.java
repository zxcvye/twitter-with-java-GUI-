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

//one "Comment" in ChildComment Screen 
public class cmt_child {
	static int yPoint=0;
	
    public static Component cmtCom(Connection con, String user_id, String comment_id, int y, JFrame frame) {
        /*icon path */
    	
    	String mypath = getValue.getProfile(con, user_id);
    	String RheartPath = "../twitter/src/image/articleHeart_red.png";
    	String cmtPath = "../twitter/src/image/comment.png";
    	String heartPath ="../twitter/src/image/articleHeart.png";
    	String delPath ="../twitter/src/image/delete.png";
    	
    	JPanel comment = new JPanel();
    	comment.setLayout(null);
    	comment.setBounds(30, y + 15, 348, 150); // 50 , +10(m), 328,150
    	comment.setBackground(Color.WHITE);
        
        yPoint=comment.getY()+150;
        
        
        ImageIcon iconProfile = new ImageIcon(mypath);
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(20, 15, 30, 30);
        comment.add(profileBtn);

        JLabel name = new JLabel(getValue.getInfo(con, user_id, "name"));
        name.setLayout(null);
        name.setBounds(profileBtn.getX() + 50, profileBtn.getY() - 5, 100, 40);
        Font NameFont = new Font("Arial", Font.BOLD, 15);
        name.setFont(NameFont);
        comment.add(name, BorderLayout.CENTER);

        JLabel user_id_lab = new JLabel("@" + user_id);
        Font idFont = new Font("Arial", Font.PLAIN, 8);
        user_id_lab.setFont(idFont);
        user_id_lab.setLayout(null);
        user_id_lab.setForeground(new Color(140, 140, 140));
        user_id_lab.setBounds(name.getX(), name.getY() + 28, 100, 20);
        comment.add(user_id_lab, BorderLayout.CENTER);
        
        
        
        ImageIcon iconDelete = new ImageIcon(delPath);
        Image Deleteimage = iconDelete.getImage();
        Image newDeleteImage = Deleteimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon DeleteIcon = new ImageIcon(newDeleteImage);
        JButton delete = new JButton(DeleteIcon);
        delete.setLayout(null);
        delete.setBounds(315, 12, 20, 20);
        delete.setContentAreaFilled(false);
        delete.setBorderPainted(false);
        comment.add(delete);
        
        //heart
      
        ImageIcon iconHeart = new ImageIcon(heartPath);
        Image Heartimage = iconHeart.getImage();
        Image newHeartImage = Heartimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartIcon = new ImageIcon(newHeartImage);
        JButton heart = new JButton(HeartIcon);
        heart.setLayout(null);
        heart.setBounds(20, 120, 20, 20);
        heart.setContentAreaFilled(false);
        heart.setBorderPainted(false);
        comment.add(heart);
        
        //child_comment

        ImageIcon iconCmt = new ImageIcon(cmtPath);
        Image cmtimage = iconCmt.getImage();
        Image newCmtImage = cmtimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon CmtIcon = new ImageIcon(newCmtImage);
        JButton child_comment = new JButton(CmtIcon);
        child_comment.setLayout(null);
        child_comment.setBounds(heart.getX()+50, 121, 20, 20);
        child_comment.setContentAreaFilled(false);
        child_comment.setBorderPainted(false);
        comment.add(child_comment );
        
        //heart-red
        
        ImageIcon iconHeartRed = new ImageIcon(RheartPath);
        Image HeartRedimage = iconHeartRed.getImage();
        Image newHeartRedImage = HeartRedimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon HeartRedIcon = new ImageIcon(newHeartRedImage);
        JButton heartRed = new JButton(HeartRedIcon);
        heartRed.setLayout(null);
        heartRed.setBounds(20, 120, 20, 20);
        heartRed.setContentAreaFilled(false);
        heartRed.setBorderPainted(false);
        comment.add(heartRed);
        heartRed.setVisible(false);
        
        //Cnt like
        int intcntLike = LikeJDBC.likeList(con, comment_id, "COMMENT_LIKE");
        JLabel cntLike = new JLabel(Integer.toString(intcntLike));
        Font likeFont = new Font("Arial", Font.PLAIN, 8);
        cntLike.setFont(likeFont);
        cntLike.setBounds(heart.getX()+30,115,50,30);
        comment.add(cntLike); 
        
        cntLike.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LikeListFrame.ArticleLike(con, user_id, comment_id, "CHILD_CMT");
                frame.setVisible(false);
            }
        });
        
        //Cnt child_comment
        LikeJDBC.likeList(con, comment_id, "COMMENT_LIKE");
        JLabel cntCmt = new JLabel(Integer.toString(getValue.getComment(con, comment_id,"COMMENT")));
        
        cntCmt.setFont(likeFont);
        cntCmt.setBounds(comment.getX()+70,115,50,30);
        comment.add(cntCmt); 
        
        //**********************************************************************
        //BUTTON EVENT 
        // in comment_cmt , Can't use Edit
        /*
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//comment->edit -> comment 
            	
            	Editcomment editframe= new Editcomment(con,user_id,comment_id,"comment");
                editframe.setVisible(true);
            	
            }});
        */
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//삭제로 넘어가는 거 여기에 구현
            	// comment delete -> mainGui
            	deleteDialog deleteD = new deleteDialog(con, user_id, comment_id, "COMMNET");
            	deleteD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            	deleteD.setVisible(true);
            }});
        heart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//하트 누르면 색 바뀌고 취소할 수 있는 버튼으로 바뀜 
            	heart.setVisible(false);
            	heartRed.setVisible(true);   
            	LikeJDBC.like(con, user_id, comment_id,"comment_LIKE");
            	System.out.println(user_id + comment_id);
            	int intcntLike=LikeJDBC.likeList(con, comment_id,"comment_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            	//라이크 수 증가 여기에 구현 
            }});
//        child_comment.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            	//글 모두 보기 & 코멘트 창 넘어가는 거 여기에 구현
//            	ChildScreen csc = new ChildScreen();
//            }});
        heartRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	heartRed.setVisible(false);
            	heart.setVisible(true);
            	LikeJDBC.delete(con, user_id, comment_id,"comment");
            	System.out.println(user_id + comment_id);
            	int intcntLike=LikeJDBC.likeList(con, comment_id,"comment_LIKE");
            	cntLike.setText(Integer.toString(intcntLike));
            	//라이크 수 감소 여기에 구현
            }});
                
        String cont = getValue.getContent(con, comment_id,"COMMENT");
        JLabel content = new JLabel(cont);
        content.setBounds(profileBtn.getX() + 10, profileBtn.getY() + 55, 300, 40);
        Font contFont = new Font("돋움", Font.PLAIN, 12);
        content.setFont(contFont);
        comment.add(content);

        return comment;
    }
    
}
