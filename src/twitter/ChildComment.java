package twitter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class ChildComment {
	static int yPoint=0;	
    public static Component commentCom(Connection con, String user_id, String child_id, int child_y, JFrame frame) {
        /*icon path */
    	String mypath = getValue.getProfile(con, user_id);
    	String delPath ="../twitter/src/image/delete.png";
    	String editPath ="../twitter/src/image/edit.png";
    	
    	
    	JPanel child_comment = new JPanel();
    	child_comment .setLayout(null);
    	child_comment .setBounds(50, child_y + 10, 328, 130); // commentv , Topm(15),
    	child_comment .setBackground(Color.WHITE);
        
        yPoint=child_comment.getY()+130;
        System.out.println("outputchild_y:"+yPoint);
        
        ImageIcon iconProfile = new ImageIcon(mypath);
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(20, 15, 30, 30);
        child_comment.add(profileBtn);

        JLabel name = new JLabel(getValue.getInfo(con, user_id, "name"));
        name.setLayout(null);
        name.setBounds(profileBtn.getX() + 50, profileBtn.getY() - 5, 100, 40);
        Font NameFont = new Font("Arial", Font.BOLD, 12);
        name.setFont(NameFont);
        child_comment.add(name, BorderLayout.CENTER);

        JLabel user_id_lab = new JLabel("@" + user_id);
        Font idFont = new Font("Arial", Font.PLAIN, 8);
        user_id_lab.setFont(idFont);
        user_id_lab.setLayout(null);
        user_id_lab.setForeground(new Color(140, 140, 140));
        user_id_lab.setBounds(name.getX(), name.getY() + 28, 100, 20);
        child_comment.add(user_id_lab, BorderLayout.CENTER);
        
        //edit Button
       
        ImageIcon iconEdit = new ImageIcon(editPath);
        Image Editimage = iconEdit.getImage();
        Image newEditImage = Editimage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon EditIcon = new ImageIcon(newEditImage);
        JButton edit = new JButton(EditIcon);
        edit.setLayout(null);
        edit.setBounds(275, 12, 20, 20);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);
        child_comment.add(edit);
       
        //delete Button 
        
        ImageIcon iconDelete = new ImageIcon(delPath);
        Image Deleteimage = iconDelete.getImage();
        Image newDeleteImage = Deleteimage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon DeleteIcon = new ImageIcon(newDeleteImage);
        JButton delete = new JButton(DeleteIcon);
        delete.setLayout(null);
        delete.setBounds(295, 12, 20, 20);
        delete.setContentAreaFilled(false);
        delete.setBorderPainted(false);
        child_comment.add(delete);
        
        //BUTTON EVENTs
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	EditArticleFrame editframe= new EditArticleFrame (con,user_id,child_id,"CHILD_CMT");
                editframe.setVisible(true);
            	frame.setVisible(false);
            }});
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteDialog deleteD = new deleteDialog(con, user_id, child_id, "CHILD_CMT");
            	deleteD.setVisible(true);
            }});

        //comment's count in label
        String cont = getValue.getContent(con, child_id,"CHILD_CMT");
        JLabel content = new JLabel(cont);
        content.setBounds(profileBtn.getX() + 10, profileBtn.getY() + 55, 300, 40);
        Font contFont = new Font("돋움", Font.PLAIN, 12);
        content.setFont(contFont);
        child_comment.add(content);
        return child_comment ;
    }
    
}
