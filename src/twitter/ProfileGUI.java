package twitter;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ProfileGUI {
    // Define a list to store dynamically added components
    private static List<Component> dynamicComponents = new ArrayList<>();

    public static void ProfileGui(Connection con, String id) {
        // Set up frame
        JFrame frame = new JFrame("Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 720);

        // Create a layered pane
        JLayeredPane all = new JLayeredPane();
        all.setLayout(null);
        all.setBounds(0, 0, 405, 635);

        JScrollPane scroll = new JScrollPane(all, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 0, 405, 635);
        scroll.setVisible(true);

        // bg img
        ImageIcon iconbg = new ImageIcon(getValue.getBg(con, id));
        JLabel bgimgLabel = new JLabel(iconbg);
        bgimgLabel.setBounds(0, 0, 405, 120);
        all.add(bgimgLabel, JLayeredPane.DEFAULT_LAYER);

        
        // profile img
        ImageIcon iconProfile = new ImageIcon(getValue.getProfile(con, id));
        RoundButton profileBtn = new RoundButton(iconProfile);
        profileBtn.setBounds(24, 85, 70, 70);
        all.add(profileBtn, JLayeredPane.MODAL_LAYER);
        
        ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
        Image Previmage = iconPrev.getImage();
        Image newPrevImage = Previmage.getScaledInstance(38, 16, Image.SCALE_SMOOTH);
        ImageIcon PrevIcon = new ImageIcon(newPrevImage);
        JButton PrevBtn = new JButton(PrevIcon);
        PrevBtn.setLayout(null);
        PrevBtn.setContentAreaFilled(false);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setBounds(-30, 5, 120, 50);
        PrevBtn.setBorder(null);
        PrevBtn.setFocusPainted(false);
        
        PrevBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MainGui.mainGUI(con, id);
            	frame.setVisible(false);
            }
        });

        all.add(PrevBtn, JLayeredPane.MODAL_LAYER);

        // name
        JLabel name = new JLabel(getValue.getInfo(con, id, "name"));
        name.setLayout(null);
        name.setBounds(profileBtn.getX() + 11, profileBtn.getY() + 77, 100, 30);
        Font NameFont = new Font("Arial", Font.BOLD, 23);
        name.setFont(NameFont);
        name.setForeground(new Color(30, 30, 30));
        all.add(name);

        // edit profile button
        ImageIcon iconSet = new ImageIcon("../twitter/src/image/set.png");
        RoundButton setBtn = new RoundButton(iconSet);
        setBtn.setBounds(360, name.getY() - 22, 28, 22);
        all.add(setBtn);
        
        //SET ACTION 
        setBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new EditProfileFrame(con, id).setVisible(true);
            	frame.setVisible(false);
            }
        });

        // follower following
        // follower, following cnt
        Font cntFont = new Font("Arial", Font.BOLD, 13);
        Font FollowFont = new Font("Arial", Font.PLAIN, 13);

        JLabel cntFollowing = new JLabel(Integer.toString(FollowJDBC.followingList(con, id)));
        cntFollowing.setLayout(null);
        cntFollowing.setBounds(name.getX(), name.getY() + 30, 100, 20);
        cntFollowing.setFont(cntFont);

        JLabel following = new JLabel("Following");
        following.setLayout(null);
        following.setBounds(cntFollowing.getX() + 20, cntFollowing.getY(), 100, 20);
        following.setFont(FollowFont);
        
        JLabel cntFollower = new JLabel(Integer.toString(FollowJDBC.followerList(con, id)));
        cntFollower.setLayout(null);
        cntFollower.setBounds(following.getX() + 70, following.getY(), 100, 20);
        cntFollower.setFont(cntFont);

        JLabel follower = new JLabel("Follower");
        follower.setLayout(null);
        follower.setBounds(cntFollower.getX() + 20, cntFollower.getY(), 100, 20);
        follower.setFont(FollowFont);
        
        JPanel cardPanel = new JPanel();
        cardPanel.setBounds(0,0,405,720);
        cardPanel.setBackground(Color.RED);
        
        //follolist, following list 
        
        following.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	frame.setVisible(false);
                FollowListFrame.FollowingList(con, id, id);
            }
        });
        
        
        follower.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	frame.setVisible(false);
            	FollowListFrame.FollowerList(con, id, id);
            }
        });

        all.add(following, BorderLayout.CENTER);
        all.add(follower, BorderLayout.CENTER);
        all.add(cntFollowing, BorderLayout.CENTER);
        all.add(cntFollower, BorderLayout.CENTER);

        // comment
        JLabel comment = new JLabel(getValue.getInfo(con, id, "comment"));
        comment.setLayout(null);
        comment.setBounds(name.getX(), follower.getY() + 24, 500, 30);
        Font cmtFont = new Font("돋움", Font.PLAIN, 13);
        comment.setFont(cmtFont);
        all.add(comment, BorderLayout.CENTER);

        JButton myPost = new JButton("My Post");
        myPost.setLayout(new FlowLayout());
        myPost.setBounds(0, 290, 200, 50);
        myPost.setBackground(new Color(232, 232, 232));
        myPost.setOpaque(false);
        myPost.setBorderPainted(false);
        all.add(myPost);

        myPost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear dynamically added components
                removeAllDynamicComponents(all);

                Feed.myFeed(con, id);
                ArticleCompFrame.yPoint = 350;

                // Add new components
                for (int i = 0; i < Feed.myFeedCnt; i++) {
                    Component newComponent = ArticleCompFrame.articleCom(con, id, Feed.myArticleIdArray.get(i), ArticleCompFrame.yPoint, frame);
                    all.add(newComponent);
                    dynamicComponents.add(newComponent);
                    all.setPreferredSize(new Dimension(405, 350 + (Feed.myFeedCnt * 162)));
                }

                // Revalidate and repaint to reflect changes
                all.revalidate();
                all.repaint();
            }
        });


        JButton likePost = new JButton("Like Post");
        likePost.setLayout(new FlowLayout());
        likePost.setBounds(200, 290, 210, 50);
        likePost.setBackground(new Color(232, 232, 232));
        likePost.setOpaque(false);
        likePost.setBorderPainted(false);
        all.add(likePost);

        likePost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear dynamically added components
                removeAllDynamicComponents(all);

                Feed.likeFeed(con, id);
                ArticleCompFrame.yPoint = 350;

                // Add new components
                for (int i = 0; i < Feed.likeFeedCnt; i++) {
                    Component newComponent = ArticleCompFrame.articleCom(con, id, Feed.likeArticleIdArray.get(i), ArticleCompFrame.yPoint, frame);
                    all.add(newComponent);
                    dynamicComponents.add(newComponent);
                    all.setPreferredSize(new Dimension(405, 350 + (Feed.likeFeedCnt * 162)));
                }

                // Revalidate and repaint to reflect changes
                all.revalidate();
                all.repaint();
            }
        });

        all.setPreferredSize(new Dimension(405, 720));
        
        frame.add(scroll);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    
    public static void targetGUI(Connection con, String user_id, String target_id) {
            // Set up frame
            JFrame frame = new JFrame("Profile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(420, 720);

            // Create a layered pane
            JLayeredPane all = new JLayeredPane();
            all.setLayout(null);
            all.setBounds(0, 0, 405, 635);

            JScrollPane scroll = new JScrollPane(all, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setBounds(0, 0, 405, 635);
            scroll.setVisible(true);

            // bg img
            ImageIcon iconbg = new ImageIcon(getValue.getBg(con, user_id));
            JLabel bgimgLabel = new JLabel(iconbg);
            bgimgLabel.setBounds(0, 0, 405, 120);
            all.add(bgimgLabel, JLayeredPane.DEFAULT_LAYER);
            
            // profile img
            ImageIcon iconProfile = new ImageIcon(getValue.getProfile(con,target_id));
            RoundButton profileBtn = new RoundButton(iconProfile);
            profileBtn.setBounds(24, 85, 70, 70);
            all.add(profileBtn, JLayeredPane.MODAL_LAYER);
            
            ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
            Image Previmage = iconPrev.getImage();
            Image newPrevImage = Previmage.getScaledInstance(38, 16, Image.SCALE_SMOOTH);
            ImageIcon PrevIcon = new ImageIcon(newPrevImage);
            JButton PrevBtn = new JButton(PrevIcon);
            PrevBtn.setLayout(null);
            PrevBtn.setContentAreaFilled(false);
            PrevBtn.setBorderPainted(false);
            PrevBtn.setBounds(-30, 5, 120, 50);
            PrevBtn.setBorder(null);
            PrevBtn.setFocusPainted(false);
            
            PrevBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MainGui.mainGUI(con, user_id);
                	frame.setVisible(false);
                }
            });

            all.add(PrevBtn, JLayeredPane.MODAL_LAYER);

            // name
            JLabel name = new JLabel(getValue.getInfo(con, target_id, "name"));
            name.setLayout(null);
            name.setBounds(profileBtn.getX() + 11, profileBtn.getY() + 77, 100, 30);
            Font NameFont = new Font("Arial", Font.BOLD, 23);
            name.setFont(NameFont);
            name.setForeground(new Color(30, 30, 30));
            all.add(name);        

            JButton followBtn = new JButton("follow");
            followBtn.setLayout(null);
            followBtn.setBounds(280,name.getY() - 22, 100, 30);
            followBtn.setBackground(new Color(0, 172, 238));
            followBtn.setForeground(Color.WHITE);
            all.add(followBtn, BorderLayout.CENTER);
            

            JButton unfollowBtn = new JButton("unfollow");
            unfollowBtn.setLayout(null);
            unfollowBtn.setBounds(280, name.getY() - 22, 100, 30);
            all.add(unfollowBtn, BorderLayout.CENTER);
            
            LineBorder lineBorder = new LineBorder(Color.LIGHT_GRAY, 1, true);
            followBtn.setBorder(lineBorder);
            unfollowBtn.setBorder(lineBorder);
            
            if(user_id==target_id) {
            	followBtn.setVisible(false);
            	unfollowBtn.setVisible(false);
            }
                  
            if (FollowJDBC.alreadyFollow(con, user_id, target_id)) {
                followBtn.setVisible(false);
                unfollowBtn.setVisible(true);
            } else {
                followBtn.setVisible(true);
                unfollowBtn.setVisible(false);
            }
            
            followBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FollowJDBC.follow(con, user_id, target_id);
                    followBtn.setVisible(false);
                    unfollowBtn.setVisible(true);
                }
            });
            unfollowBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	FollowJDBC.unfollow(con, user_id, target_id);
                	followBtn.setVisible(true);
                    unfollowBtn.setVisible(false);
                }
            });
            
            unfollowBtn.setContentAreaFilled(false);
            
            

            // follower following
            // follower, following cnt
            Font cntFont = new Font("Arial", Font.BOLD, 13);
            Font FollowFont = new Font("Arial", Font.PLAIN, 13);

            JLabel cntFollowing = new JLabel(Integer.toString(FollowJDBC.followingList(con, target_id)));
            cntFollowing.setLayout(null);
            cntFollowing.setBounds(name.getX(), name.getY() + 30, 100, 20);
            cntFollowing.setFont(cntFont);

            JLabel following = new JLabel("Following");
            following.setLayout(null);
            following.setBounds(cntFollowing.getX() + 20, cntFollowing.getY(), 100, 20);
            following.setFont(FollowFont);
            
            JLabel cntFollower = new JLabel(Integer.toString(FollowJDBC.followerList(con, target_id)));
            cntFollower.setLayout(null);
            cntFollower.setBounds(following.getX() + 70, following.getY(), 100, 20);
            cntFollower.setFont(cntFont);

            JLabel follower = new JLabel("Follower");
            follower.setLayout(null);
            follower.setBounds(cntFollower.getX() + 20, cntFollower.getY(), 100, 20);
            follower.setFont(FollowFont);
            
            JPanel cardPanel = new JPanel();
            cardPanel.setBounds(0,0,405,720);
            cardPanel.setBackground(Color.RED);
            
            
            frame.setVisible(true);
            //follolist, following list 
            
            following.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	frame.setVisible(false);
                    FollowListFrame.FollowingList(con,user_id, target_id);
                }
            });
            
            
            follower.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	frame.setVisible(false);
                	FollowListFrame.FollowerList(con, user_id, target_id);
                }
            });

            all.add(following, BorderLayout.CENTER);
            all.add(follower, BorderLayout.CENTER);
            all.add(cntFollowing, BorderLayout.CENTER);
            all.add(cntFollower, BorderLayout.CENTER);

            // comment
            JLabel comment = new JLabel(getValue.getInfo(con, target_id, "comment"));
            comment.setLayout(null);
            comment.setBounds(name.getX(), follower.getY() + 24, 500, 30);
            Font cmtFont = new Font("돋움", Font.PLAIN, 13);
            comment.setFont(cmtFont);
            all.add(comment, BorderLayout.CENTER);

            JButton myPost = new JButton("My Post");
            myPost.setLayout(new FlowLayout());
            myPost.setBounds(0, 290, 200, 50);
            myPost.setBackground(new Color(232, 232, 232));
            myPost.setOpaque(false);
            myPost.setBorderPainted(false);
            all.add(myPost);

            myPost.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear dynamically added components
                    removeAllDynamicComponents(all);

                    Feed.myFeed(con, target_id);
                    ArticleCompFrame.yPoint = 350;

                    // Add new components
                    for (int i = 0; i < Feed.myFeedCnt; i++) {
                        Component newComponent = ArticleCompFrame.articleCom(con, target_id, Feed.myArticleIdArray.get(i), ArticleCompFrame.yPoint, frame);
                        all.add(newComponent);
                        dynamicComponents.add(newComponent);
                        all.setPreferredSize(new Dimension(405, 350 + (Feed.myFeedCnt * 162)));
                    }

                    // Revalidate and repaint to reflect changes
                    all.revalidate();
                    all.repaint();
                }
            });


            JButton likePost = new JButton("Like Post");
            likePost.setLayout(new FlowLayout());
            likePost.setBounds(200, 290, 210, 50);
            likePost.setBackground(new Color(232, 232, 232));
            likePost.setOpaque(false);
            likePost.setBorderPainted(false);
            all.add(likePost);

            likePost.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Clear dynamically added components
                    removeAllDynamicComponents(all);

                    Feed.likeFeed(con, target_id);
                    ArticleCompFrame.yPoint = 350;

                    // Add new components
                    for (int i = 0; i < Feed.likeFeedCnt; i++) {
                        Component newComponent = ArticleCompFrame.articleCom(con, target_id, Feed.likeArticleIdArray.get(i), ArticleCompFrame.yPoint, frame);
                        all.add(newComponent);
                        dynamicComponents.add(newComponent);
                        all.setPreferredSize(new Dimension(405, 350 + (Feed.likeFeedCnt * 162)));
                    }

                    // Revalidate and repaint to reflect changes
                    all.revalidate();
                    all.repaint();
                }
            });

            all.setPreferredSize(new Dimension(405, 720));
            
            frame.add(scroll);
            
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
        }
    

    // Helper method to remove dynamically added components
    private static void removeAllDynamicComponents(JLayeredPane all) {
        for (Component component : dynamicComponents) {
            all.remove(component);
        }
        dynamicComponents.clear();
    }
    
    
}
