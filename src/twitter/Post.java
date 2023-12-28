package twitter;

import java.sql.*;
import java.time.LocalDateTime;import java.time.format.DateTimeFormatter;

public class Post {
	@SuppressWarnings("resource")
	 //Post.write(con, "123", "B378223EF", "COMMENT");
	public static int write(Connection con, String userId, String content, String highId, String table) {
		 	String query = null;
	        if (highId == null) {
	        	// ARTICLE(attribute = 4)
	        	query = "INSERT INTO "+table+" VALUES(?, ?, ?, ?)";
	        	
	        }else {
	        	//Commnet, child_cmt(attribute = 5)
	        	query = "INSERT INTO "+table+" VALUES(?, ?, ?, ?, ?)";
	        }
	       
	        try (PreparedStatement stmt = con.prepareStatement(query)) {
	        	System.out.println(query);
	        	//user_id setting
	        	stmt.setString(1, userId);
	           	
	        	//comment_id & child_cmt_id (답글의 id) 
	        	String writeId;
	        	String tableVar="";
	        	
	        	switch(table) {
	        	case "ARTICLE":
	        		tableVar="article_id";
	        		break;
	        	case "COMMENT":
	        		tableVar="comment_id";
	        		
	        		break;
	        	case "CHILD_CMT":
	        		tableVar="child_cmt_id";
	        		break;
	        	}
	        	
	        	while(true) {
	            	writeId = generateRandomHex(9); 
	            	try (PreparedStatement check = con.prepareStatement("SELECT * FROM " + table+" WHERE "+tableVar+ "= ?")) {
	            		check.setString(1, writeId);
	            		ResultSet result = check.executeQuery();
	            		if (result.next()==false)   
	            			break;     
	            	}
	            }
	           stmt.setString(2, writeId);
	            
	           //text
	         
	           stmt.setString(3, content);
	           
	            //date time [ upload_date]
	            LocalDateTime now = LocalDateTime.now();
	            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	            stmt.setString(4, formatedNow);

	            //article_id & comment_id (답글을 달려고 하는 ... 글의 id)  
	        		//except- in Article, highID = null
	            if (highId != null) {
	            	stmt.setString(5, highId);
	            }
	            System.out.println(stmt);
	            //execute
	            stmt.executeUpdate();
	           	} catch (SQLException e) {
	           		e.printStackTrace();
	        	}
	        return 0;
	    }
	 

	    private static String generateRandomHex(int length) {
	        String characters = "0123456789ABCDEF";
	        StringBuilder hex = new StringBuilder();
	        for (int i = 0; i < length; i++) {
	            int index = (int) (Math.random() * characters.length());
	            hex.append(characters.charAt(index));
	        }
	        return hex.toString();
	    }
	    
	    //delete
	    // compare write_id(ARTICLE, COMMENT) 
	    public static void delete(Connection con, String id, String write_id,String table) {
       	String tableVar="";
       	switch(table) {
       	case "ARTICLE":
       		tableVar="article_id";
       		break;
       	case "COMMENT":
       		tableVar="comment_id";
       		
       		break;
       	case "CHILD_CMT":
       		tableVar="child_cmt_id";
       		break;
       	} 
	    	String query="DELETE FROM "+table+" WHERE "+tableVar+"=?";
			try (PreparedStatement stmt = con.prepareStatement(query)){
				stmt.setString(1,write_id);
				stmt.executeUpdate();
				
				System.out.println(query);
				System.out.println("Delete "+ write_id);
			 } catch (SQLException e) {
				e.printStackTrace();
			 }
		    
	    }		
 
	     
	    // by modify btn
	    public static void modify(Connection con,String new_text, String user_id, String post_id,String table) {
       	String tableVar="";
       	switch(table) {
       	case "ARTICLE":
       		tableVar="article_id";
       		break;
       	case "COMMENT":
       		tableVar="comment_id";
       		
       		break;
       	case "CHILD_CMT":
       		tableVar="child_cmt_id";
       		break;
       	} 
	    	String query="UPDATE "+table+" SET content_text= ? WHERE "+ tableVar +"= ? AND user_id= ?";
			 try (PreparedStatement stmt = con.prepareStatement(query)){
			 	stmt.setString(1,new_text);
			 	stmt.setString(2,post_id);
			 	stmt.setString(3,user_id);
				stmt.executeUpdate();
			 } catch (SQLException e) {
				e.printStackTrace();
			 }
			
		}

}