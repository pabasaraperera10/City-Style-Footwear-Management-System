package ClassPackage;
import java.sql.*;

public class CategoryClass {

    private String CategoryId;
    private String CategoryName;

    DBConnectionClass connectionClass = new DBConnectionClass();
    Connection connection;

    public CategoryClass() {
        connection = connectionClass.getConnection();
    }

    public CategoryClass(String CategoryId, String CategoryName) {
        this.CategoryId = CategoryId;
        this.CategoryName = CategoryName;
        connection = connectionClass.getConnection();
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    // ✅ FIXED AUTO ID METHOD
    public String AutoCategoryId() {

        String newId = "C001";  // default first ID

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT MAX(CategoryId) FROM CategoryTable");

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                String CID = rs.getString(1);

                if (CID != null) {

                    CID = CID.trim();  // important for NCHAR
                    int id = Integer.parseInt(CID.substring(1));
                    id++;

                    newId = String.format("C%03d", id);
                }
            }

        } catch (Exception e) {
            System.err.println("Error Auto Id " + e);
        }

        return newId;
    }

    // ✅ FIXED INSERT METHOD (PROFESSIONAL WAY)
    public boolean insertCategory() {

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO CategoryTable (CategoryId, CategoryName) VALUES (?, ?)");

            statement.setString(1, getCategoryId());
            statement.setString(2, getCategoryName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.err.println("Error inserting data " + ex);
            return false;
        }
    }
}
