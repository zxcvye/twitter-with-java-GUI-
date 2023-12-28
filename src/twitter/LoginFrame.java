package twitter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {


	private InfoJDBC twitterApp;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userIdTextField;

	// 패스워드 입력을 받을 필드
	private JPasswordField passwordField;

	// 로그인 프레임 생성자
	public LoginFrame(InfoJDBC twitterApp) {
		this.twitterApp = twitterApp;
		// UI 초기화 메소드 호출
		initializeUI();
		this.setResizable(false); // Do not allow frame resizing
        this.setLocationRelativeTo(null); // Center the frame on the screen
	}

	private void initializeUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 720);

		// 컨텐트 팬 초기화
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// UI 구성 요소 추가
		addImageLabel();
		addTitleLabel();
		addUserIdComponents();
		addPasswordComponents();
		addLoginButton();
		addSignUpComponents();
	}

	// 이미지 레이블 추가 메소드
	private void addImageLabel() {
		ImageIcon iconLogo = new ImageIcon("../twitter/src/image/logo.png");
        Image Logoimage = iconLogo.getImage();
        Image newLogoImage = Logoimage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon LogoIcon = new ImageIcon(newLogoImage);
        JButton LogoBtn = new JButton(LogoIcon);
        LogoBtn.setLayout(null);
        LogoBtn.setContentAreaFilled(false);
        LogoBtn.setBorderPainted(false);
        LogoBtn.setBounds(144, 150, 131, 100);
        LogoBtn.setFocusPainted(false);
        contentPane.add(LogoBtn);
	}

	// 타이틀 레이블 추가 메소드
	private void addTitleLabel() {
		JLabel loginLabel = new JLabel("Log in to Twitter");
		loginLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		loginLabel.setBounds(110, 320, 193, 40);
		contentPane.add(loginLabel);
	}

	// 사용자 아이디 입력 관련 컴포넌트 추가 메소드
	private void addUserIdComponents() {
		JLabel userIdLabel = new JLabel("User ID");
		userIdLabel.setBounds(68, 383, 61, 16);
		contentPane.add(userIdLabel);

		userIdTextField = new JTextField();
		userIdTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		userIdTextField.setBounds(65, 405, 270, 35);
		contentPane.add(userIdTextField);
	}

	// 패스워드 입력 관련 컴포넌트 추가 메소드
	private void addPasswordComponents() {
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(68, 450, 61, 16);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		passwordField.setBounds(65, 472, 270, 35);
		contentPane.add(passwordField);
	}

	// 로그인 버튼 추가 메소드
	private void addLoginButton() {
		JButton loginButton = new JButton("Log In");
		loginButton.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.setBackground(new Color(29, 160, 241));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBounds(55, 528, 290, 62);
		loginButton.setOpaque(true);
		loginButton.setBorderPainted(false);
		contentPane.add(loginButton);

		// 로그인 버튼에 ActionListener 추가
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 로그인 버튼 클릭 시 수행할 메소드 호출
				handleLoginButtonClick();
			}
		});
	}

	// 회원가입 관련 컴포넌트 추가 메소드
	private void addSignUpComponents() {
		JLabel signUpTextLabel = new JLabel("Don't have an account?");
		signUpTextLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		signUpTextLabel.setBounds(55, 605, 210, 16);
		signUpTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(signUpTextLabel);

		JButton signUpButton = new JButton("Sign Up >");
		signUpButton.setForeground(new Color(29, 160, 241));
		signUpButton.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		signUpButton.setBounds(220, 599, 117, 29);
		signUpButton.setBorderPainted(false);
		signUpButton.setOpaque(false); 
		signUpButton.setFocusPainted(false);
		signUpButton.setOpaque(false);
		signUpButton.setContentAreaFilled(false);
		signUpButton.setBorderPainted(false);
		signUpButton.setFocusPainted(false);
		contentPane.add(signUpButton);

		// 회원가입 버튼에 ActionListener 추가
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 회원가입 버튼 클릭 시 수행할 메소드 호출
				handleSignUpButtonClick();
			}
		});
	}

	// 로그인 버튼 클릭 시 수행할 메소드
	private void handleLoginButtonClick() {
		// 사용자가 입력한 아이디와 비밀번호 가져오기
		String userId = userIdTextField.getText();
		String password = new String(passwordField.getPassword());

		// TwitterApp 인스턴스에서 로그인 메서드 호출
		if (InfoJDBC.loginUser(userId, password)) {
			// 로그인에 성공하면 현재 로그인 프레임 닫고 홈페이지 프레임 열기
			closeLoginFrame();
			MainGui.mainGUI(Main.con, userId);
		} else {
			// 로그인 실패 시 메시지 출력
			JOptionPane.showMessageDialog(this, "Incorrect user ID or password. Please try again.");
		}
	}

	private void handleSignUpButtonClick() {
		// 회원가입 버튼 클릭 시 사용자 정보 입력 창으로 이동
		closeLoginFrame();
		SignUp signUpFrame = new SignUp(twitterApp);
		signUpFrame.setVisible(true);
	}

	// 현재 로그인 프레임을 닫는 메소드
	private void closeLoginFrame() {
		this.dispose();
	}

}