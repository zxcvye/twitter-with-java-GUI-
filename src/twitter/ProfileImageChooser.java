package twitter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProfileImageChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel profileImageLabel;
	private String selectedImagePath; // 선택한 이미지의 경로를 저장할 변수

	
	public ProfileImageChooser() {
		setTitle("Profile Image Chooser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);

		initComponents();
	}

	private void initComponents() {
		profileImageLabel = new JLabel("No Image");
		profileImageLabel.setHorizontalAlignment(JLabel.CENTER);

		JButton chooseImageButton = new JButton("Choose Image");
		chooseImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseImage();
			}
		});

		JButton setProfileButton = new JButton("Set as Profile Image");
		setProfileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setProfileImage();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(chooseImageButton);
		buttonPanel.add(setProfileButton);

		setLayout(new BorderLayout());
		add(profileImageLabel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void chooseImage() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			selectedImagePath = selectedFile.getAbsolutePath();
			displayImage(selectedFile);
		}
	}

	private void displayImage(File file) {
		ImageIcon icon = new ImageIcon(file.getAbsolutePath());
		profileImageLabel.setIcon(icon);
	}

	private void setProfileImage() {
		if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
			// 여기에서 선택한 이미지의 경로를 DB에 저장하는 로직을 추가할 수 있습니다.
			// 데이터베이스에 저장하지 않고 경로만 출력하는 예시
			JOptionPane.showMessageDialog(this, "Profile image set successfully!\nImage Path: " + selectedImagePath);
		} else {
			JOptionPane.showMessageDialog(this, "Please choose an image first.");
		}
	}

}
