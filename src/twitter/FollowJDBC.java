package twitter;

import java.sql.*;
import java.util.ArrayList;

public class FollowJDBC {
	
	static ArrayList<String> followerList = new ArrayList<String>();
	static ArrayList<String> followingList = new ArrayList<String>();
	
	public static void follow(Connection con, String user_id, String follow_id) {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO FOLLOW VALUES (?, ?)"); ) {
            stmt.setString(1, user_id);
            stmt.setString(2, follow_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void unfollow(Connection con, String user_id, String follow_id) {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM follow WHERE user_id = ? AND follow_id = ?"); ) {
            stmt.setString(1, user_id);
            stmt.setString(2, follow_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static int followingList(Connection con, String user_id) {
		followingList.clear();
		int cnt=0;
		String query="SELECT follow_id FROM FOLLOW WHERE user_id = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1,user_id);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            String Follow = rs.getString("follow_id");
	            followingList.add(Follow);
	            cnt++;
	        }
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public static int followerList(Connection con, String user_id) { //type은 follower또는 following
		followerList.clear();
		int cnt=0;
		String query="SELECT user_id FROM FOLLOW WHERE follow_id = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1,user_id);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            String Follow = rs.getString("user_id");
	            followerList.add(Follow);
	            cnt++;
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public static boolean alreadyFollow(Connection con, String user_id, String target_id) {
	    String query = "SELECT follow_id FROM FOLLOW WHERE user_id = ? AND follow_id = ?";
	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, user_id);
	        stmt.setString(2, target_id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next(); // If there is at least one matching row, return true
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle the exception appropriately for your application
	    }

	    return false;
	}
}