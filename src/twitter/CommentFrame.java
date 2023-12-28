package twitter;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.sql.Connection;
import javax.swing.*;

@SuppressWarnings("serial")
public class CommentFrame extends JFrame {

	private JPanel contentPane;
	private int y=23;
	
	public CommentFrame(Connection con, String user_id,String article_id) {
		

		this.setResizable(false); 
	    this.setLocationRelativeTo(null);
		
		ArticleCompFrame.yPoint = 0;// to refresh, initial y_point
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 252, 252));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//<Top Panel>
		JPanel top = new JPanel();
		top.setBackground(new Color(252, 252, 252));
		top.setLayout(null);
		top.setBounds(0, 0, 420, 80);
		contentPane.add(top);
		
		//Top - (1)BackMainGui
		ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
        Image Previmage = iconPrev.getImage();
        Image newPrevImage = Previmage.getScaledInstance(38, 16, Image.SCALE_SMOOTH);
        ImageIcon PrevIcon = new ImageIcon(newPrevImage);
        JButton PrevBtn = new JButton(PrevIcon);
        PrevBtn.setLayout(null);
        PrevBtn.setContentAreaFilled(false);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setBounds(-17, 15, 120, 50);
        PrevBtn.setBorder(null);
        PrevBtn.setFocusPainted(false);
        top.add(PrevBtn);
        
		PrevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainGui.mainGUI(con, InfoJDBC.id);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Comment");
		lblNewLabel.setLayout(null);
		lblNewLabel.setBounds(160, y, 130, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		top.add(lblNewLabel);
		
		//<<Bottom Panel>>
		JPanel bottom = new JPanel();
		bottom.setLayout(null);
		bottom.setBackground(new Color(252, 252, 252));
		bottom.setBounds(0, 602, 420, 60);
		contentPane.add(bottom);
		
		//Bottom (2) content
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(232, 232, 232));
		textArea.setBounds(18, 18, 290, 40);
		bottom.add(textArea);
		
		/*Post comment*/
		JButton Tweet = new JButton("Post");
		Tweet.setBounds(320, 18, 67, 40);
		bottom.add(Tweet);
		/*tweet action*/
		Tweet.setForeground(new Color(252, 252, 252));
		Tweet.setFont(new Font("Tahoma", Font.BOLD, 13));
		Tweet.setBackground(new Color(76, 158, 235));
		
		Tweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = textArea.getText();
				if(!content.isEmpty()) {
					Post.write(con, InfoJDBC.id, content, article_id, "COMMENT");
					textArea.setText("");
					dispose();
					CommentFrame cs= new CommentFrame(con,user_id,article_id);
					cs.setSize(420, 720);
			        cs.setLocationRelativeTo(null); // Center the frame
			        cs.setVisible(true);
				}  else {
		            JOptionPane.showMessageDialog(null, "Please enter your comment.");
		        }
			}
		});
		
		
		//<<middle Panel>>
		JPanel middle = new JPanel();
		middle.setBackground(new Color(232, 232, 232));
		middle.setBounds(0, 80, 405, 525);
		//contentPane.add(middle);
		middle.setLayout(null);
		
	    // create JScrollPane
        JScrollPane scroll = new JScrollPane(middle, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 80, 405, 525);
        scroll.setVisible(true);
        contentPane.add(scroll);
        
        //middle - Article
		int init_y = 0;
		middle.add(Article_cmt.articleCom(con,user_id,article_id,init_y,this));
		
		//middle- Comment
		int cmt_y = Article_cmt.yPoint;//10(m)+170 = 180

		Comment.yPoint =0;
		Comment.yPoint +=cmt_y;
		Feed.post_feed(con, article_id, "COMMENT");
		int cmt_cnt = Feed.postCnt;
		Feed.postCnt=0;
		for(int i=0;i<cmt_cnt;i++) {
			JPanel bar1 = new JPanel();
    		bar1.setBackground(new Color(128, 128, 128));
    		bar1.setBounds(10, Comment.yPoint+15, 5, 150);
    		middle.add(bar1);
        	middle.add(Comment.commentCom(con, Feed.post_userIdArray.get(i), Feed.postIdArray.get(i), Comment.yPoint, this));
        	System.out.print(Feed.post_userIdArray.get(i));
		}
		cmt_cnt = cmt_cnt+1;
		middle.setPreferredSize(new Dimension(405, 175 * cmt_cnt));
		contentPane.add(scroll);
        middle.revalidate();
        middle.repaint();
        
        setResizable(false);
        setLocationRelativeTo(null); 
		
	}
		
}
