package twitter;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Feed {
	static int feedCnt=0;
	static int myFeedCnt=0;
	static int likeFeedCnt=0;
	static ArrayList<String> userIdArray = new ArrayList<String> (); 
	static ArrayList<String> articleIdArray = new ArrayList<String> ();
	static ArrayList<String> myArticleIdArray = new ArrayList<String> ();
	static ArrayList<String> likeArticleIdArray = new ArrayList<String> ();
	static int postCnt=0; // same feedCnt
	static ArrayList<String> postIdArray = new ArrayList<String> ();
	static ArrayList<String> post_userIdArray= new ArrayList<String> ();
	
	public static void feed(Connection con, String Id) {
		//add
		feedCnt=0;
		userIdArray.clear();
		articleIdArray.clear();
		
		String article_id="";
		String user_id="";
		String query="SELECT article_id, user_id FROM article WHERE user_id = ? OR user_id IN (SELECT follow_id FROM follow WHERE user_id = ?) ORDER BY upload_date DESC";
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1, Id);
			stmt.setString(2, Id);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            article_id = rs.getString("article_id");
	            user_id=rs.getString("user_id");
	            userIdArray.add(user_id);
	            articleIdArray.add(article_id);
	            feedCnt++;
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void myFeed(Connection con, String Id) {
		myFeedCnt=0;
		myArticleIdArray.clear();
		likeFeedCnt=0;
		likeArticleIdArray.clear();
		
		String myArticleId = "";
		String query="SELECT article_id FROM ARTICLE WHERE user_id = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1, Id);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            myArticleId = rs.getString("article_id");
	            myArticleIdArray.add(myArticleId);
	            myFeedCnt++;
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void likeFeed(Connection con, String Id) {
		likeFeedCnt=0;
		likeArticleIdArray.clear();
		myFeedCnt=0;
		myArticleIdArray.clear();
		
		String likeArticleId="";
		String query = "SELECT article_id FROM article_like WHERE user_id = ?";
		
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1, Id);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            likeArticleId = rs.getString("article_id");
	            likeArticleIdArray.add(likeArticleId);
	            likeFeedCnt++;
	            System.out.println(likeArticleId);
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void post_feed(Connection con, String highId, String table) {
		//initial-> if not, double issue
		postCnt=0; //comment cnt
		postIdArray.clear(); //comment_id
		post_userIdArray.clear(); 
		
		String tableVar = "";
		String HtableVar = "";
		switch(table) {
		case "COMMENT":
			tableVar = "comment_id";
			HtableVar = "article_id";
			break;
		case "CHILD_CMT":
			tableVar = "child_cmt_id";
			HtableVar = "comment_id";
			break;
		}
		
		String post_id="";
		String user_id="";
		String query="SELECT "+tableVar+", user_id FROM "+ table+" WHERE "+HtableVar +" = ? ORDER BY upload_date DESC";
		try (PreparedStatement stmt = con.prepareStatement(query)){
			stmt.setString(1, highId);
			ResultSet rs= stmt.executeQuery();	
			while (rs.next()) {
	            post_id = rs.getString(tableVar);
	            user_id=rs.getString("user_id");
	            post_userIdArray.add(user_id);
	            postIdArray.add(post_id);
	            postCnt++;
	            System.out.println("******"+user_id);
	        }
			System.out.println("post_feed:"+ postCnt);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}





