package twitter;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.*;

@SuppressWarnings("serial")
public class deleteDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();

	public deleteDialog(Connection con, String user_id, String post_id, String table) {
		

		this.setResizable(false); 
	    this.setLocationRelativeTo(null);
	    
		setSize(450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(47, 53, 60));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Delete post?");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setBounds(40, 32, 150, 50);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(47, 53, 60));
			buttonPane.setBounds(40, 150, 358, 102);
			contentPanel.add(buttonPane);
			{
				JButton okButton = new JButton("Delete");
				okButton.setBackground(new Color(206, 57, 95));
				okButton.setForeground(new Color(255, 255, 255));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				okButton.setBounds(10, 11, 338, 35);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// delete 
						// you can delete post except like_record(foreign constraint) 
						
						LikeJDBC.delete(con, user_id,post_id, table);
						Post.delete(con, user_id, post_id, table);
						
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

						dispose();
					}
				    private void showHomePage(Connection con,String id) {
				    	dispose();
				        EventQueue.invokeLater(new Runnable() {
				            public void run() {
				                try {
				                	System.out.println("Edit-> Home");
				                	MainGui.mainGUI(con, InfoJDBC.id);
				                } catch (Exception e) {
				                    e.printStackTrace();
				                }
				            }
				        });
				    }
				    
				    private void showComment(Connection con,String user_id , String post_id) {
				    	//edit ->cmt
				    	// hight_id = article_id
				    	
				  
				    	dispose();
				        EventQueue.invokeLater(new Runnable() {
				            public void run() {
				                try {
				                	String table = "Comment";
				                	String highId = getValue.getHighId(con, post_id, table);
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
				    
				    private void showChild(Connection con,String user_id , String post_id) {
				    	//edit ->child_cmt
				    	dispose();
				        EventQueue.invokeLater(new Runnable() {
				            public void run() {
				                try {
				                	String comment_id ="";
				                	String article_id = "";
				                	comment_id = getValue.getHighId(con, post_id, "CHILD_CMT");
				                	System.out.println("highId_cmt: "+comment_id);
				                	article_id = getValue.getHighId(con, comment_id,"COMMENT");
				                	System.out.println("highId_article: "+article_id);
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
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setForeground(new Color(255, 255, 255));
				cancelButton.setBackground(new Color(47, 53, 60));
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setBounds(10, 57, 338, 34);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

			
			
		}
		
		JLabel lblNewLabel_1 = new JLabel("the timeline of any accounts that follow you, and from search resurlts.");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(40, 105, 358, 32);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("This can't be undone and it will be removed from your profile, ");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(40, 80, 358, 32);
		contentPanel.add(lblNewLabel_1_1);
	}
	
}
