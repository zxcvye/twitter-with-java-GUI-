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
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class WriteArticle extends JFrame {
	
	private JPanel contentPane;

	public WriteArticle(Connection con,String user_id) {
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
				MainGui.mainGUI(con, user_id);
				dispose();
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
		JButton btnNewButton_1 = new JButton("Tweet");
		btnNewButton_1.setBackground(new Color(76, 158, 235));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton_1.setBounds(190, 11, 100, 30);
		btnNewButton_1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = context_text.getText();
				if (!content.isEmpty()) {
					tweet_article(con,user_id,content);
					context_text.setText("");
					MainGui.mainGUI(con, user_id);
					dispose();
				}
			
			}
		});
		panel.add(btnNewButton_1);
		
	}
   
    // tweet Article
    public static void tweet_article(Connection con,String user_id,String content) {
        // Save the content to the database or perform necessary actions
        Post.write(con, user_id, content, null, "ARTICLE");
    }

}
