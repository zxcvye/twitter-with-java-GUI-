package twitter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class EditArticleFrame extends JFrame {
	
	private JPanel contentPane;

	public EditArticleFrame(Connection con, String user_id, String post_id, String table) {
		this.setResizable(false); 
        this.setLocationRelativeTo(null); 
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420,720);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon iconLogo = new ImageIcon("../twitter/src/image/logo.png");
        Image imgLogo = iconLogo.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imgLogo);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(185, 45, 40, 40);
        this.add(logoLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 455, 380, 52);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//Cancel 
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(78, 11, 100, 30);
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setForeground(new Color(76, 158, 235));
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		 btnNewButton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*return main page*/
				switch(table) {
				case "ARTICLE":
					showHomePage(con,user_id);// save
					break;
				case "COMMENT":
					showComment(con,user_id,post_id); //issue
					break;
				case "CHILD_CMT":
					showChild(con,user_id,post_id);
					break;
				}

			}
		});
		panel.add(btnNewButton);

		//content_text
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(16, 130, 370, 304);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JTextArea context_text = new JTextArea("Please enter your text here!");
		
		context_text.setWrapStyleWord(true);
		context_text.setBounds(42, 35, 280, 246);
		panel_1.add(context_text);
		
		//tweet 
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.setBackground(new Color(76, 158, 235));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton_1.setBounds(190, 11, 100, 30);
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newcontent  = context_text.getText();
				if (!newcontent .isEmpty()) {
					// update newcontent
					System.out.println("new content Resaved: " + newcontent );
					Post.modify(con, newcontent, user_id, post_id, table);
					
					// edit -> (1)Homepage,(2) CommentS, (3) ChildCommentS
					switch(table) {
					case "ARTICLE":
						showHomePage(con,user_id);
						break;
					case "COMMENT":
						showComment(con,user_id,post_id);
						break;
					case "CHILD_CMT":
						showChild(con,user_id,post_id);
						break;
					}
					
				}
			
			}
		});
		panel.add(btnNewButton_1);
		
	}
   
	public void showHomePage(Connection con,String id) {
    	dispose();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	System.out.println("Edit-> Home");
                	MainGui.mainGUI(con, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void showComment(Connection con,String user_id , String post_id) {
    	//edit ->cmt
    	// hight_id = article_id

    	dispose();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	String highId = getValue.getHighId(con, post_id, "COMMENT");
                	System.out.println("edit->cmt");
            		CommentFrame cs= new CommentFrame(con,user_id,highId);
            		cs.setSize(420, 720);
			        cs.setLocationRelativeTo(null); // Center the frame
			        cs.setVisible(true);
                	
                	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void showChild(Connection con,String user_id , String post_id) {
    	//edit ->child_cmt
    	dispose();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	String comment_id ="";
                	String article_id = "";
                	comment_id = getValue.getHighId(con, post_id, "CHILD_CMT");
                	article_id = getValue.getHighId(con, comment_id,"COMMENT");
                	System.out.println("edit->child");

            		ChildScreen css= new ChildScreen(con,user_id,article_id,comment_id);
                	
            		css.setSize(420, 720);
			        css.setLocationRelativeTo(null); // Center the frame
			        css.setVisible(true);
                	                	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
