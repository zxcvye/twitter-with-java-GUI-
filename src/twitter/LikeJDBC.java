package twitter;

import java.sql.*;
import java.util.ArrayList;

public class LikeJDBC {
    static ArrayList<String> likeId = new ArrayList<>();

    public static void like(Connection con, String user_id, String post_id, String table) {
        // 이미 좋아요를 눌렀는지 확인
        if (hasLiked(con, user_id, post_id, table)) {
            // 이미 좋아요를 눌렀으면 추가하지 않고 종료
            System.out.println("Already liked!");
            return;
        }

        // 좋아요 추가
        String query = "INSERT INTO " + table + " VALUES(?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user_id);
            stmt.setString(2, post_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasLiked(Connection con, String user_id, String post_id, String table) {
        String tableVar = "";
        switch (table) {
            case "ARTICLE_LIKE":
                tableVar = "article_id";
                break;
            case "COMMENT_LIKE":
                tableVar = "comment_id";
                break;
            case "CHILD_CMT_LIKE":
                tableVar = "child_cmt_id";
                break;
        }

        String query = "SELECT * FROM " + table + " WHERE user_id = ? AND " + tableVar + " = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user_id);
            stmt.setString(2, post_id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // 결과가 있으면 이미 좋아요를 눌렀음
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int likeList(Connection con, String post_id, String table) {
        likeId.clear();
        String tableVar = "";
        int cntLike = 0;
        switch (table) {
            case "ARTICLE_LIKE":
                tableVar = "article_id";
                break;
            case "COMMENT_LIKE":
                tableVar = "comment_id";
                break;
            case "CHILD_CMT_LIKE":
                tableVar = "child_cmt_id";
                break;
        }

        String query = "SELECT user_id FROM " + table + " WHERE " + tableVar + " = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, post_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String likedUserId = rs.getString("user_id");
                likeId.add(likedUserId);
                cntLike++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cntLike;
    }

    public static void delete(Connection con, String id, String write_id, String table) {
        String tableVar = "";
        String like_table = "";
        switch (table) {
            case "ARTICLE":
                like_table = "ARTICLE_LIKE";
                tableVar = "article_id";
                break;
            case "COMMENT":
                like_table = "COMMENT_LIKE";
                tableVar = "comment_id";
                break;
            case "CHILD_CMT":
                like_table = "CHILD_CMT_LIKE";
                tableVar = "child_cmt_id";
                break;
        }

        String query = "DELETE FROM " + like_table + " WHERE " + tableVar + "=?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, write_id);
            stmt.executeUpdate();
            System.out.println(query);
            System.out.println(tableVar + write_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String count(Connection con, String post_id, String table) {
        String c = "";
        String tableVar = "";
        switch (table) {
            case "ARTICLE_LIKE":
                tableVar = "article_id";
                break;
            case "COMMENT_LIKE":
                tableVar = "comment_id";
                break;
        }

        String query = "Select count(*) from " + table + " WHERE " + tableVar + "=?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, post_id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    c = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
