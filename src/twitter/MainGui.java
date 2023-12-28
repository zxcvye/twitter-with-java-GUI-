package twitter;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

public class MainGui {
	
    public static void mainGUI(Connection con, String id) {
        // Set up the frame
        JFrame frame = new JFrame("Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 720);

        // Set up the content pane
        Container content = frame.getContentPane();
        content.setLayout(null);
        content.setBackground(new Color(230, 230, 230));

        // <<<Top Panel>>>
        JPanel top = new JPanel();
        top.setLayout(null);
        top.setBounds(0, 0, 420, 80);
        top.setBackground(new Color(242, 242, 242));

        // Top - Profile Btn
        ImageIcon iconProfile = new ImageIcon(getValue.getProfile(con, id));
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(25, 23, 37, 37);
        top.add(profileBtn);

        // Top - profile action
        profileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a glass pane with a translucent black color
                JPanel glassPane = new JPanel();
                glassPane.setBackground(new Color(0, 0, 0, 160));
                glassPane.setLayout(null);

                // Set the glass pane to be visible
                frame.setGlassPane(glassPane);
                glassPane.setVisible(true);

                // Create the dialog and panel
                JDialog dialog = new JDialog(frame, "Profile", JDialog.ModalityType.MODELESS);
                JPanel panel = new JPanel();
                dialog.getContentPane().add(panel);
                panel.setLayout(null);
          
            	// Add mouse listener to the glass pane
                glassPane.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Hide the glass pane and dialog when the glass pane is clicked
                        glassPane.setVisible(false);
                        dialog.dispose();
                    }
                });

                // Calculate dialog dimensions and position
                int contentWidth = content.getWidth();
                int contentHeight = content.getHeight();

                int dialogWidth = (int) (contentWidth * 0.85);
                int dialogHeight = (int) (contentHeight * 1);

                int dialogX = frame.getX() + frame.getInsets().left;
                int dialogY = frame.getY() + frame.getInsets().top;

                dialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
                dialog.setUndecorated(true);

                // Profile
                RoundButton profile = new RoundButton(iconProfile);
                profile.setBounds(30, 30, 80, 80);
                panel.add(profile);

                // Name (Note: You need to retrieve the name from the database)
                JLabel name = new JLabel(getValue.getInfo(con, id, "name"));
                name.setLayout(null);
                name.setBounds(profile.getX() + 5, profile.getY() + 82, 100, 40);
                Font NameFont = new Font("Arial", Font.BOLD, 25);
                name.setFont(NameFont);
                panel.add(name, BorderLayout.CENTER);

                // user_id
                JLabel user_id = new JLabel("@" + id);
                Font idFont = new Font("Arial", Font.PLAIN, 13);
                user_id.setFont(idFont);
                user_id.setLayout(null);
                user_id.setForeground(new Color(140, 140, 140));
                user_id.setBounds(name.getX(), name.getY() + 35, 100, 20);
                panel.add(user_id, BorderLayout.CENTER);

                // follower, following cnt
                Font cntFont = new Font("Arial", Font.BOLD, 13);
                Font FollowFont = new Font("Arial", Font.PLAIN, 13);

                JLabel cntFollowing = new JLabel(Integer.toString(FollowJDBC.followingList(con, id))); 
                cntFollowing.setLayout(null);
                cntFollowing.setBounds(name.getX(), name.getY() + 70, 100, 20);
                cntFollowing.setFont(cntFont);

                JLabel following = new JLabel("Following");
                following.setLayout(null);
                following.setBounds(cntFollowing.getX() + 20, cntFollowing.getY(), 100, 20);
                following.setFont(FollowFont);

                JLabel cntFollower = new JLabel(Integer.toString(FollowJDBC.followerList(con, id))); 
                cntFollower.setLayout(null);
                cntFollower.setBounds(following.getX() + 80, following.getY(), 100, 20);
                cntFollower.setFont(cntFont);

                JLabel follower = new JLabel("Follower");
                follower.setLayout(null);
                follower.setBounds(cntFollower.getX() + 20, cntFollower.getY(), 100, 20);
                follower.setFont(FollowFont);

                panel.add(following, BorderLayout.CENTER);
                panel.add(follower, BorderLayout.CENTER);
                panel.add(cntFollowing, BorderLayout.CENTER);
                panel.add(cntFollower, BorderLayout.CENTER);
                
                //button 
                //profile
                ImageIcon profileIcon = new ImageIcon("../twitter/src/image/myProfile.png"); 
                CustomButton profileBtn = new CustomButton("Profile", profileIcon, (int) (contentWidth * 0.85));
                profileBtn.setLayout(null);
                profileBtn.setBounds(0, follower.getY()+30, (int) (contentWidth * 0.85), 70);
                panel.add(profileBtn);
                
                ImageIcon writeIcon = new ImageIcon("../twitter/src/image/post.png"); 
                CustomButton writeBtn = new CustomButton("My Post", writeIcon, (int) (contentWidth * 0.85));
                writeBtn.setLayout(null);
                writeBtn.setBounds(0, follower.getY()+105, (int) (contentWidth * 0.85), 70);
                panel.add(writeBtn);
                
                ImageIcon heartIcon = new ImageIcon("../twitter/src/image/heart.png"); 
                CustomButton heartBtn = new CustomButton("Like Post", heartIcon, (int) (contentWidth * 0.85));
                heartBtn.setLayout(null);
                heartBtn.setBounds(0, follower.getY()+180, (int) (contentWidth * 0.85), 70);
                panel.add(heartBtn);
              
                profileBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	frame.setVisible(false);
                    	ProfileGUI.ProfileGui(con, id);
                    }
                });
                
                writeBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	frame.setVisible(false);
                    	ProfileGUI.ProfileGui(con, id);
                    }
                });
                
                heartBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	frame.setVisible(false);
                    	ProfileGUI.ProfileGui(con, id);
                    }
                });
                
                
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);

                // Add a window listener to the dialog
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Set the glass pane to be invisible when the dialog is closed
                        glassPane.setVisible(false);
                    }
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        dialog.dispose(); // Close the dialog when it loses focus
                    }
                });
            }
        });

        // Top logo
        ImageIcon iconLogo = new ImageIcon("../twitter/src/image/logo.png");
        Image imgLogo = iconLogo.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imgLogo);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(185, 22, 40, 40);
        top.add(logoLabel);

        frame.add(top);

        // <<<middle>>>
        JPanel middle = new JPanel();
        middle.setLayout(null);
        middle.setBounds(0, 80, 405, 525);

        // create JScrollPane
        JScrollPane scroll = new JScrollPane(middle, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 80, 405, 525);
        scroll.setVisible(true);

        // feed
        Feed.feed(con, id);
        int cnt = Feed.feedCnt;
        ArticleCompFrame.yPoint=0;
        for(int i=0; i<cnt; i++) {
        	middle.add(ArticleCompFrame.articleCom(con, Feed.userIdArray.get(i), Feed.articleIdArray.get(i), ArticleCompFrame.yPoint, frame));
        }
         
        middle.setPreferredSize(new Dimension(405, 162 * cnt));
        
        frame.add(scroll);
        middle.revalidate();
        middle.repaint();

        // <<<bottom panel>>>
        JPanel bottom = new JPanel();
        bottom.setLayout(null);
        bottom.setBounds(0, 605, 420, 110);
        bottom.setBackground(new Color(242, 242, 242));
        frame.add(bottom);    
        
        JButton homeBtn = new JButton("HOME");
        homeBtn.setLayout(null);
        homeBtn.setContentAreaFilled(false);
        homeBtn.setForeground(Color.DARK_GRAY);
        homeBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        homeBtn.setBounds(90, 15, 100, 40);
        bottom.add(homeBtn);

        homeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	MainGui.mainGUI(con, id);
            }
        });
        
        // WRITE BTN
        JButton writeBtn = new JButton("WRITE");
        writeBtn.setLayout(null);
        writeBtn.setContentAreaFilled(false);
        writeBtn.setBounds(210, 15, 100, 40);
        writeBtn.setForeground(Color.DARK_GRAY);
        writeBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); 
        bottom.add(writeBtn);

        writeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WriteArticle newArticle = new WriteArticle(con, id);
                newArticle.setVisible(true);
                frame.setVisible(false);
            }
        });

        // Set frame properties
        frame.setVisible(true);
        frame.setResizable(false); // Do not allow frame resizing
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Add a window listener to close the connection when the frame is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}