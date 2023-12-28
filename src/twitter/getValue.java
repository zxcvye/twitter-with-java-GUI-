package twitter;

import java.sql.*;

public class getValue {
	
	public static String getParentId(Connection con, String id, String type) {
		String query="";
		String val="";
		switch (type) {
		case "COMMENT":
			query="SELECT article_id FROM COMMENT WHERE comment_id = ?";
			break;
		case "CHILD_CMT":
			query="SELECT a.article_id FROM child_cmt cc JOIN comment c ON cc.comment_id = c.comment_id JOIN article a ON c.comment_id = a.comment_id WHERE cc.child_cmt_id = ? ";
			break;
	}
		try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setString(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                val = rs.getString("article_id");
            }
            else 
           	 return "error";
           } catch (SQLException e) {
            e.printStackTrace();
        }
		return val;
	}
	
	
	public static String getInfo(Connection con, String id, String type) {
		String val="";
		String query="";
		switch (type) {
			case "name":
				query="SELECT name FROM user_info WHERE user_id = ?";
				break;
			case "comment":
				query="SELECT comment FROM user_info WHERE user_id = ?";
				break;
			case "email":
				query="SELECT email FROM user_info WHERE user_id = ?";
				break;
		}
		 try (PreparedStatement pstm = con.prepareStatement(query)) {
             pstm.setString(1, id);
             ResultSet rs = pstm.executeQuery();
             if (rs.next()) {
                 val = rs.getString(type);
             }
             else 
            	 return "error";
            } catch (SQLException e) {
             e.printStackTrace();
         }
		return val;
	}		
	
	public static String getContent(Connection con, String post_id, String table) {
	    String val = "";
	    String tableVar = "";
	    switch (table) {
	        case "ARTICLE":
	            tableVar = "article_id";
	            break;
	        case "COMMENT":
	            tableVar = "comment_id";
	            break;
	        case "CHILD_CMT":
	            tableVar = "child_cmt_id";
	            break;
	    }
	    String query = "";
	    if ("ARTICLE".equals(table)) {
	        query = "SELECT content_text FROM article WHERE article_id = ?";
	    } else {
	        query = "SELECT content_text FROM " + table + " WHERE " + tableVar + " = ?";
	    }
	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, post_id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            val = rs.getString("content_text");
	        } else {
	            return "error";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return val;
	}

	
	public static int getComment(Connection con, String post_id, String table) {
		int cnt=0; 
    	String query= "";
    	if(table == "ARTICLE") {
    		query="SELECT content_text FROM COMMENT WHERE article_id = ?";
    	}else if(table=="COMMENT") {
    		query="Select content_text FROM CHILD_CMT WHERE comment_id = ?";
    	}	
		try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setString(1, post_id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                cnt++;
            }
    		System.out.println("cnt: "+ cnt);
           } catch (SQLException e) {
            e.printStackTrace();
        }

		return cnt;
	}
	
	public static String getProfile(Connection con, String user_id) {
	    String val = "";

	    String query = "SELECT profile_img FROM user_info WHERE user_id = ?";
	    
	    try (PreparedStatement pstm = con.prepareStatement(query)) {
	        pstm.setString(1, user_id);

	        try (ResultSet rs = pstm.executeQuery()) {
	            if (rs.next()) {
	                val = rs.getString("profile_img");
	                if (val == null) {
	                    val = "../twitter/src/image/profile.jpg";
	                }
	            }
	        }

	    } catch (SQLException e) {
	        // Handle the exception appropriately, log it or rethrow a custom exception
	        e.printStackTrace();
	    }

	    return val;
	}
	
	public static String getBg(Connection con, String user_id) {
	    String val = "";

	    String query = "SELECT bg_img FROM user_info WHERE user_id = ?";
	    
	    try (PreparedStatement pstm = con.prepareStatement(query)) {
	        pstm.setString(1, user_id);
	        try (ResultSet rs = pstm.executeQuery()) {
	            if (rs.next()) {
	                val = rs.getString("bg_img");
	                if (val == null) {
	                    val = "../twitter/src/image/bgimg.png";
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return val;
	}
	
	public static String getHighId(Connection con, String post_id,String table) {
		String highId="";
		String HtableVar = "";
    	String tableVar="";
    	switch(table) {
    	case "COMMENT":
    		tableVar="comment_id";
    		HtableVar = "article_id";
    		break;
    	case "CHILD_CMT":
    		tableVar="child_cmt_id";
    		HtableVar = "comment_id";
    		break;
    	} 
    	String query="SELECT "+ HtableVar+" FROM " +table+" WHERE "+ tableVar+" = ?";					
		 try (PreparedStatement stmt = con.prepareStatement(query)){
		 	stmt.setString(1,post_id);
		 	ResultSet rs = stmt.executeQuery();
		 	if(rs.next()) {
		 		highId=rs.getString(HtableVar);
		 	}else {// Handle the case where no highId is found
	            highId = "";
		 		
		 	}
		 	
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 return highId;
	}
	
	public static String getWriterId(Connection con, String article_id) {
		 String val = "";
		    String query = "SELECT user_id FROM ARTICLE WHERE article_id = ?";
		    
		    try (PreparedStatement pstm = con.prepareStatement(query)) {
		        pstm.setString(1, article_id);
		        try (ResultSet rs = pstm.executeQuery()) {
		            if (rs.next()) {
		                val = rs.getString("user_id");
		                System.out.print(val);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return val;
		}
	
}
