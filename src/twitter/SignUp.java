package twitter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SignUp extends JFrame {

	private InfoJDBC twitterApp;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton profileImageButton;
	private JTextField userIdTextField, passwordTextField, nameTextField, birthTextField, introTextField, emailTextField;
	private String selectedImagePath;

	public SignUp(InfoJDBC twitterApp) {
		this.twitterApp = twitterApp;
		initializeUI();
		this.setResizable(false);
        this.setLocationRelativeTo(null); 
	}

	private void initializeUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon iconLogo = new ImageIcon("../twitter/src/image/logo.png");
        Image imgLogo = iconLogo.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imgLogo);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(185, 14, 40, 40);
        this.add(logoLabel);

		addProfileImageSelectionButton();
		addUserIdComponents();
		addPasswordComponents();
		addNameComponents();
		addEmailComponents();
		addBirthComponents();
		addIntroComponents();
		addSignInButton();
		addBackButton();
	}
	
	private void addBackButton() {
		ImageIcon iconPrev = new ImageIcon("../twitter/src/image/prev.png");
        Image Previmage = iconPrev.getImage();
        Image newPrevImage = Previmage.getScaledInstance(40, 17, Image.SCALE_SMOOTH);
        ImageIcon PrevIcon = new ImageIcon(newPrevImage);
        JButton PrevBtn = new JButton(PrevIcon);
        PrevBtn.setLayout(null);
        PrevBtn.setContentAreaFilled(false);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setBounds(10, 20, 50, 18);
        PrevBtn.setBorder(null);
        PrevBtn.setFocusPainted(false);
       		contentPane.add(PrevBtn);
		PrevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleBackButtonClick();
			}
		});
	}

	private void handleBackButtonClick() {
		SignUp.this.dispose();
		new LoginFrame(twitterApp).setVisible(true);
	}

	private void addProfileImageSelectionButton() {
		
		 ImageIcon originalIcon = new ImageIcon("../twitter/src/image/profile.jpg");
		 Image originalImage = originalIcon.getImage();
		 Image scaledImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		 ImageIcon scaledIcon = new ImageIcon(scaledImage);

		 profileImageButton = new JButton(scaledIcon); 
		 profileImageButton.setBounds(55, 75, 70, 70);
		 contentPane.add(profileImageButton);
		 profileImageButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            chooseProfileImage();
		        }
		    });
	}

	private void addUserIdComponents() {
		JLabel userIdLabel = new JLabel("User ID");
		userIdLabel.setBounds(55, 163, 127, 16);
		contentPane.add(userIdLabel);

		userIdTextField = new JTextField();
		userIdTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		userIdTextField.setBounds(55, 181, 285, 40);
		contentPane.add(userIdTextField);
	}

	private void addPasswordComponents() {
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(55, 230, 131, 16);
		contentPane.add(passwordLabel);

		passwordTextField = new JPasswordField();
		passwordTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		passwordTextField.setBounds(55, 248, 290, 40);
		contentPane.add(passwordTextField);
	}

	private void addNameComponents() {
		JLabel userNameLabel = new JLabel("Name");
		userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userNameLabel.setBounds(55, 297, 131, 16);
		contentPane.add(userNameLabel);

		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		nameTextField.setBounds(55, 315, 290, 40);
		contentPane.add(nameTextField);
	}

	private void addEmailComponents() {
		JLabel emailLabel = new JLabel("Email Address");
		emailLabel.setBounds(53, 364, 131, 16);
		contentPane.add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		emailTextField.setBounds(55, 382, 290, 40);
		contentPane.add(emailTextField);
	}

	private void addBirthComponents() {
		JLabel birthLabel = new JLabel("Birth Date (YYYY-MM-DD)");
		birthLabel.setBounds(53, 431, 200, 16);
		contentPane.add(birthLabel);

		birthTextField = new JTextField();
		birthTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		birthTextField.setBounds(55, 449, 290, 40);
		contentPane.add(birthTextField);
	}

	private void addIntroComponents() {
		JLabel introLabel = new JLabel("Introduction");
		introLabel.setAlignmentX(0.5f);
		introLabel.setBounds(53, 498, 131, 16);
		contentPane.add(introLabel);

		introTextField = new JTextField();
		introTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		introTextField.setBounds(55, 516, 290, 40);
		contentPane.add(introTextField);
	}

	private void addSignInButton() {
		JButton loginButton = new JButton("Sign In");
		loginButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.setBackground(new Color(29, 160, 241));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBounds(55, 580, 290, 62);
		loginButton.setOpaque(true);
		loginButton.setBorderPainted(false);
		contentPane.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSignInButtonClick();
			}
		});
	}

	private void handleSignInButtonClick() {
        String user_id = userIdTextField.getText();
        String password = passwordTextField.getText();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String birth = birthTextField.getText();
        String profileImagePath = selectedImagePath;
        String bgImagePath = null;
        String intro = introTextField.getText();

        if (profileImagePath != null && !profileImagePath.isEmpty()) {
            InfoJDBC.signUpAndRegisterInfo(user_id, password, name, birth, profileImagePath, bgImagePath, intro, email);
            JOptionPane.showMessageDialog(this, "Signed up successfully. Please log in again!");
            closeSignUpFrame();

            new LoginFrame(twitterApp).setVisible(true);
        } else {
            profileImagePath = null;
            InfoJDBC.signUpAndRegisterInfo(user_id, password, name, birth, profileImagePath, bgImagePath, intro, email);
            JOptionPane.showMessageDialog(this, "Signed up successfully. Please log in again!");
            closeSignUpFrame();
            new LoginFrame(twitterApp).setVisible(true);
        }

        // 아이디or이메일 같을 경우 가입 제한 & 다시 signUp창으로 돌아가는 것

    }
	private void closeSignUpFrame() {
		this.dispose();
	}

	private void chooseProfileImage() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			displayProfileImage(selectedFile);
		}
	}

	private void displayProfileImage(File file) {
		ImageIcon icon = new ImageIcon(file.getAbsolutePath());
		profileImageButton.setIcon(icon);
		selectedImagePath = file.getAbsolutePath();
		JOptionPane.showMessageDialog(this, "Profile image set successfully!");
	}

}
