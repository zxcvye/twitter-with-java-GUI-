package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoJDBC {

	// 필드
	public static String id;
	private static Connection connection;
	private static String loggedInUserId; // 추가: 로그인한 사용자의 ID를 추적하기 위한 필드

	// 생성자
	public InfoJDBC(Connection con) {
		InfoJDBC.connection=con;
	}

	// 사용자 등록 및 정보 등록 메소드
	public static void signUpAndRegisterInfo(String user_id, String password, String name, String birth,
			String profileImagePath, String bgImagePath, String comment, String email) {
			// 사용자 아이디 중복 확인
			String checkQuery = "SELECT user_id FROM USER WHERE user_id = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
				checkStmt.setString(1, user_id);
				ResultSet rs = checkStmt.executeQuery();

				if (rs.next()) {
					System.out.println("User ID already exists. Please try again."); // 중복처리
				} else {
					// 회원가입 쿼리
					String insertQueryUser = "INSERT INTO USER (user_id, password) VALUES (?, ?)";
					String insertQueryUserInfo = "INSERT INTO USER_INFO (user_id, name, birth, profile_img, bg_img, comment, email) VALUES (?, ?, ?, ?, ?, ?, ?)";

					try (PreparedStatement insertStmtUser = connection.prepareStatement(insertQueryUser);
							PreparedStatement insertStmtUserInfo = connection.prepareStatement(insertQueryUserInfo)) {

						// USER 테이블에 아이디와 패스워드 추가
						insertStmtUser.setString(1, user_id);
						insertStmtUser.setString(2, password);

						int rowsAffectedUser = insertStmtUser.executeUpdate();

						// USER_INFO 테이블에 아이디와 추가 정보 추가
						insertStmtUserInfo.setString(1, user_id);
						insertStmtUserInfo.setString(2, name);
						insertStmtUserInfo.setString(3, birth);
						insertStmtUserInfo.setString(4, profileImagePath);
						insertStmtUserInfo.setString(5, bgImagePath);
						insertStmtUserInfo.setString(6, comment);
						insertStmtUserInfo.setString(7, email);

						int rowsAffectedUserInfo = insertStmtUserInfo.executeUpdate();

						if (rowsAffectedUser > 0 && rowsAffectedUserInfo > 0) {
							System.out.println("User registered successfully!");
						} else {
							System.out.println("User registration failed. Please try again.");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	

	// 프로필 정보 수정 메소드
	public static void editProfileInfo(String userId, String newName, String newBirth, String newProfileImagePath,
			String newBgImagePath, String newComment, String newEmail) {

			String selectQuery = "SELECT * FROM USER_INFO WHERE user_id = ?";
			try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
				selectStmt.setString(1, userId);
				ResultSet rs = selectStmt.executeQuery();

				if (rs.next()) {

					// 변경된 정보로 수정
					String updateQuery = "UPDATE USER_INFO SET name = ?, birth = ?, profile_img = ?, bg_img = ?, comment = ?, email = ? WHERE user_id = ?";
					try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
						updateStmt.setString(1, newName.isEmpty() ? rs.getString("name") : newName);
						updateStmt.setString(2, newBirth.isEmpty() ? rs.getString("birth") : newBirth);
						updateStmt.setString(3,
								newProfileImagePath.isEmpty() ? rs.getString("profile_img") : newProfileImagePath);
						updateStmt.setString(4, newBgImagePath.isEmpty() ? rs.getString("bg_img") : newBgImagePath);
						updateStmt.setString(5, newComment.isEmpty() ? rs.getString("comment") : newComment);
						updateStmt.setString(6, newEmail.isEmpty() ? rs.getString("email") : newEmail);
						updateStmt.setString(7, userId);

						int rowsAffected = updateStmt.executeUpdate();

						if (rowsAffected > 0) {
							System.out.println("Profile information updated successfully!");
						} else {
							System.out.println("Profile information update failed. Please try again.");
						}
					}
				} else {
					System.out.println("User not found.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	

	// 사용자 로그인 메소드
	public static boolean loginUser(String user_id, String password) {
		try {
			// 사용자 아이디와 비밀번호 매칭 확인
			String checkQuery = "SELECT user_id FROM user WHERE user_id = ? AND password = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
				checkStmt.setString(1, user_id);
				checkStmt.setString(2, password);

				ResultSet rs = checkStmt.executeQuery();
				boolean loginSuccess = rs.next();

				if (loginSuccess) {
					loggedInUserId = user_id;
					id=user_id;// 로그인에 성공하면 현재 로그인한 사용자 ID를 설정
				}

				return loginSuccess;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: User registration failed.");
			return false;
		}
	}

	// 유저 확인 메서드
	public boolean isUserIdExists(String user_id) {
			String checkQuery = "SELECT user_id FROM user WHERE user_id = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
				checkStmt.setString(1, user_id);
				ResultSet rs = checkStmt.executeQuery();
				return rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} 
	

	// 현재 로그인한 사용자의 ID를 반환하는 메소드
	public String getLoggedInUserId() {
		return loggedInUserId;
	}

	// 유저 이름을 가져오는 메소드
	public String getUserName(String userId) {
			String selectQuery = "SELECT name FROM USER_INFO WHERE user_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, userId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getString("name");
					} else {
						return null; // 사용자가 존재하지 않을 경우 null 반환
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} 
	

	// 프로필 이미지 경로를 가져오는 메소드 추가
	public String getProfileImagePath(String userId) {
			String selectQuery = "SELECT profile_image FROM USER_INFO WHERE user_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, userId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getString("profile_image");
					} else {
						return null; // 사용자가 존재하지 않을 경우 null 반환
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
	// 비밀번호 변경 메소드
	public static boolean changePassword(String user_id, String currentPassword, String newPassword) {
		if (loginUser(user_id, currentPassword)) {

				// 비밀번호 업데이트 쿼리
				String updateQuery = "UPDATE USER SET password = ? WHERE user_id = ?";
				try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
					updateStmt.setString(1, newPassword);
					updateStmt.setString(2, user_id);

					int rowsAffected = updateStmt.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("Password changed successfully!");
						return true; // 변경 성공
					} else {
						System.out.println("Password change failed. Please try again.");
						return false; // 변경 실패
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		 else {
			System.out.println("Incorrect user_id or password. Please try again.");
			return false; // 변경 실패
		}
		return false;
	}

}