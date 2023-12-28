package twitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class ChangePasswordFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected Connection con = null;
	private String userId;
	private JPanel contentPane;
	private JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
	public Connection connection;
	private int y=180;
	
	public ChangePasswordFrame(Connection con, String userId) {
		this.connection = con;
		this.userId = userId;
		this.setResizable(false); 
        this.setLocationRelativeTo(null);
		initializeUI();
	}

	// UI 초기화 메소드
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

		addLabels(); // 라벨 추가 메소드 호출
		addPasswordFields(); // 비밀번호 입력 필드 추가 메소드 호출
		addChangePasswordButton(); // 비밀번호 변경 버튼 추가 메소드 호출
		addBackButton(); // 뒤로 가는 메소드 호출
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
		// ChangePasswordFrame 닫고 User's HomePage 열기
		ChangePasswordFrame.this.dispose();
		new EditProfileFrame(con, userId).setVisible(true);
	}

	// 라벨 추가 메소드
	private void addLabels() {
		JLabel currentPasswordLabel = new JLabel("Current Password");
		currentPasswordLabel.setBounds(52, y, 150, 16);
		getContentPane().add(currentPasswordLabel);

		JLabel newPasswordLabel = new JLabel("New Password");
		newPasswordLabel.setBounds(52, y+55, 150, 16);
		getContentPane().add(newPasswordLabel);

		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setBounds(52, y+110, 150, 16);
		getContentPane().add(confirmPasswordLabel);
	}

	// 비밀번호 입력 필드 추가 메소드
	private void addPasswordFields() {
		currentPasswordField = new JPasswordField();
		currentPasswordField.setBounds(202, y, 150, 30);
		getContentPane().add(currentPasswordField);

		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(202, y+55, 150, 30);
		getContentPane().add(newPasswordField);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(202, y+110, 150, 30);
		getContentPane().add(confirmPasswordField);
	}

	// 비밀번호 변경 버튼 추가 메소드
	private void addChangePasswordButton() {
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setForeground(Color.WHITE);
		changePasswordButton.setBackground(new Color(29, 160, 241));
		changePasswordButton.setBorderPainted(false);
		changePasswordButton.setOpaque(true);
		changePasswordButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		changePasswordButton.setBounds(102, y+200, 200, 40);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleChangePasswordButtonClick();
			}
		});
		getContentPane().add(changePasswordButton);
	}

	// 비밀번호 변경 버튼 클릭 시 실행되는 메소드
	private void handleChangePasswordButtonClick() {
		String currentPassword = new String(currentPasswordField.getPassword());
		String newPassword = new String(newPasswordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());

		// 입력값 유효성 검사
		if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
			return;
		}

		// 새 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!newPassword.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(this, "New password and confirm password do not match.");
			return;
		}

		// TwitterApp 클래스의 비밀번호 변경 메소드 호출
		boolean success = InfoJDBC.changePassword(userId, currentPassword, newPassword);

		// 결과에 따라 메시지 출력
		if (success) {
			JOptionPane.showMessageDialog(this, "Password changed successfully.");
			this.dispose();
			ProfileGUI.ProfileGui(con, userId);
		} else {
			JOptionPane.showMessageDialog(this, "Failed to change the password. Please check your current password.");
		}
	}

}
