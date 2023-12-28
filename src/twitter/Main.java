package twitter;

import java.sql.*;

public class Main {
	
	public static Connection con = null;
	
    public static void main(String[] args) throws ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/twitter";
            String user = "root";
            String password = "password";
            try {
				con = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
            InfoJDBC twitterApp = new InfoJDBC(con);
            LoginFrame frame = new LoginFrame(twitterApp);
            frame.setVisible(true);
    }
}
            
       
       
        


    




