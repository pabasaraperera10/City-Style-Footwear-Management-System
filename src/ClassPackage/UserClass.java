package ClassPackage;

import java.sql.*;

public class UserClass {

    private String userId;
    private String password;
    private String userType;

    DBConnectionClass connectionClass = new DBConnectionClass();

    // Constructor for login
    public UserClass(String userId, String password) {
        this.userId = userId.trim();
        this.password = password.trim();
    }

    // Constructor for insert
    public UserClass(String userId, String password, String userType) {
        this.userId = userId.trim();
        this.password = password.trim();
        this.userType = userType.trim();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    // 🔐 LOGIN VALIDATION
    public boolean validLogin() {

        boolean status = false;

        try (Connection con = connectionClass.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT UserType FROM UserTable WHERE UserId = ? AND Password = ?")) {

            ps.setString(1, userId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userType = rs.getString("UserType");
                status = true;
            }

        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }

        return status;
    }

    // ➕ INSERT USER
    public boolean insertUser() {

        try (Connection con = connectionClass.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO UserTable (UserId, Password, UserType) VALUES (?, ?, ?)")) {

            ps.setString(1, userId);
            ps.setString(2, password);
            ps.setString(3, userType);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Insert Error: " + e.getMessage());
            return false;
        }
    }

    // 🔢 AUTO USER ID
    public String autoUserId() {

        String newId = "U001";

        try (Connection con = connectionClass.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT UserId FROM UserTable ORDER BY UserId DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("UserId"); // U005
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                newId = String.format("U%03d", num);
            }

        } catch (Exception e) {
            System.out.println("Auto ID Error: " + e.getMessage());
        }

        return newId;
    }
}
