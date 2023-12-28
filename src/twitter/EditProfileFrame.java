package twitter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditProfileFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	protected Connection con = null;

	private InfoJDBC twitterApp;
	private String userId;

	private JPanel contentPane;
	private JButton profileImageButton;
	private JTextField nameTextField, emailTextField, birthTextField, introTextField;
	private String selectedImagePath;
	
	

	public Connection connection;

	public EditProfileFrame(Connection con, String userId) {
		this.con = con;
		this.userId = userId;
		this.setResizable(false); 
        this.setLocationRelativeTo(null);
		initializeUI();
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

		addBackButton();
		addSaveButton();
		addProfileImageSelectionButton();
		addNameComponents();
		addEmailComponents();
		addBirthComponents();
		addIntroComponents();
		addChangePasswordButton();

	}

	private void navigateToHomePage() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ProfileGUI.ProfileGui(con, userId);
			}
		});
	}

	// 뒤로 가기 버튼 메소드
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

	// 뒤로 가기 버튼 로직
	private void handleBackButtonClick() {
		// EditProfileFrame 닫고 HomePageFrame 열기
		EditProfileFrame.this.dispose();
		navigateToHomePage();
	}

	// 저장 버튼 메소드
	private void addSaveButton() {
		JButton saveButton = new JButton("save");
		saveButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setBackground(new Color(29, 160, 241));
		saveButton.setForeground(Color.WHITE);
		saveButton.setBounds(55, 510, 290, 62);
		saveButton.setOpaque(true);
		saveButton.setBorderPainted(false);
		contentPane.add(saveButton);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleSaveButtonClick();
			}
		});
	}

	// 저장 버튼 로직
	@SuppressWarnings("static-access")
	private void handleSaveButtonClick() {
		// 편집된 프로필 정보 가져오기
		String newName = nameTextField.getText();
		String newEmail = emailTextField.getText();
		String newBirth = birthTextField.getText();
		String newProfileImagePath = selectedImagePath; // Use the selected profile image path
		String newBgImagePath = ""; // You can add code here to get the background image path, if applicable
		String newIntro = introTextField.getText();

		// 프로필 이미지가 선택되었는지 확인
		if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
			// TwitterApp에서 편집된 프로필 정보 저장하는 메소드 호출
			twitterApp.editProfileInfo(userId, newName, newBirth, newProfileImagePath, newBgImagePath, newIntro, newEmail);
			// 현재 창 닫기 또는 필요한 다른 작업 수행
			this.dispose();
			navigateToHomePage(); // Call the common navigation method
		} else {
			InfoJDBC.editProfileInfo(userId, newName, newBirth, "../twitter/src/image/profile.jpg", newBgImagePath, newIntro, newEmail);
			this.dispose();
			navigateToHomePage();
		}
	}

	// 프로필 이미지 선택 버튼 추가 메소드
	private void addProfileImageSelectionButton() {
		 ImageIcon originalIcon = new ImageIcon("../twitter/src/image/profile.jpg");
		 Image originalImage = originalIcon.getImage();
		 Image scaledImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		 ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		profileImageButton = new JButton(scaledIcon);
		profileImageButton.setBounds(55, 110, 70, 70);
		contentPane.add(profileImageButton);
		profileImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseProfileImage();
			}
		});
	}

	// 선택된 프로필 이미지를 표시하는 메소드
	private void displayProfileImage(File file) {
		ImageIcon icon = new ImageIcon(file.getAbsolutePath());
		profileImageButton.setIcon(icon);
		selectedImagePath = file.getAbsolutePath();
		JOptionPane.showMessageDialog(this, "Profile image set successfully!");
	}

	// 프로필 이미지 선택 다이얼로그 표시 메소드
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

	// 이름 입력 관련 컴포넌트 추가 메소드
	private void addNameComponents() {
		JLabel userNameLabel = new JLabel("Name");
		userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userNameLabel.setBounds(60, 200, 131, 16);
		contentPane.add(userNameLabel);

		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		nameTextField.setBounds(55, 223, 290, 40);
		contentPane.add(nameTextField);
	}

	// 이메일 입력 관련 컴포넌트 추가 메소드
	private void addEmailComponents() {
		JLabel emailLabel = new JLabel("Email Address");
		emailLabel.setBounds(60, 275, 131, 16);
		contentPane.add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		emailTextField.setBounds(55, 298, 290, 40);
		contentPane.add(emailTextField);
	}

	// 생년월일 입력 관련 컴포넌트 추가 메소드
	private void addBirthComponents() {
		JLabel birthLabel = new JLabel("Birth Date (YYYY-MM-DD)");
		birthLabel.setBounds(60, 350, 200, 16);
		contentPane.add(birthLabel);

		birthTextField = new JTextField();
		birthTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		birthTextField.setBounds(55, 373, 290, 40);
		contentPane.add(birthTextField);
	}

	// 자기소개 입력 관련 컴포넌트 추가 메소드
	private void addIntroComponents() {
		JLabel introLabel = new JLabel("Introduction");
		introLabel.setAlignmentX(0.5f);
		introLabel.setBounds(60, 425, 131, 16);
		contentPane.add(introLabel);

		introTextField = new JTextField();
		introTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		introTextField.setBounds(55, 448, 290, 40);
		contentPane.add(introTextField);
	}

	// 비밀번호 변경 버튼 추가 메소드
	private void addChangePasswordButton() {
		JLabel signUpTextLabel = new JLabel("Want to change password?");
		signUpTextLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		signUpTextLabel.setBounds(28, 585, 210, 16);
		signUpTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(signUpTextLabel);

		JButton signUpButton = new JButton("Change Password >");
		signUpButton.setForeground(new Color(29, 160, 241));
		signUpButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		signUpButton.setBounds(200, 578, 182, 29);
		signUpButton.setOpaque(false);
		signUpButton.setContentAreaFilled(false);
		signUpButton.setBorderPainted(false);
		contentPane.add(signUpButton);

		// 회원가입 버튼에 ActionListener 추가
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 회원가입 버튼 클릭 시 수행할 메소드 호출
				handleChangePasswordButtonClick();
			}
		});
	}

	// 비밀번호 변경 버튼 클릭 시 수행할 메소드
	private void handleChangePasswordButtonClick() {
		// UserInfoFrame 닫고 ChangePasswordFrame 열기
		EditProfileFrame.this.dispose();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create and open the ChangePasswordFrame
				ChangePasswordFrame changePasswordFrame = new ChangePasswordFrame(con, userId);
				changePasswordFrame.setVisible(true);
			}
		});
	}


}