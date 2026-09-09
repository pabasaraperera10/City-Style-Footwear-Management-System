package ClassPackage;
import java.sql.*;

public class ShoeClass {
    private String ShoeId;
    private String Model;
    private String Brand;
    private String CategoryId;
    private String Color;
    private int Price;
    
    DBConnectionClass connectionClass= new DBConnectionClass();
     Connection connection;
      public ShoeClass()
    {
        connection=connectionClass.getConnection();
    }

    public ShoeClass(String ShoeId, String Model, String Brand, String CategoryId, String Color, int Price) {
         this();
        
        this.ShoeId = ShoeId;
        this.Model = Model;
        this.Brand = Brand;
        this.CategoryId = CategoryId;
        this.Color = Color;
        this.Price = Price;
    }

    public String getShoeId() {
        return ShoeId;
    }

    public void setShoeId(String ShoeId) {
        this.ShoeId = ShoeId;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
      
      public String AutoShoeId() {
    String CID = null;

    try {
        String sql = "SELECT TOP 1 ShoeId FROM ShoeTable ORDER BY ShoeId DESC";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            CID = rs.getString("ShoeId");
        }

        // If table empty
        if (CID == null) {
            return "S001";
        }

        // Remove first letter (S)
        int number = Integer.parseInt(CID.substring(1));
        number++;

        return "S" + String.format("%03d", number);

    } catch (Exception e) {
        System.err.println("Auto ID Error: " + e);
        return "S001";   // safety return
    }
}

     
     public String getCategory_Id(String CategoryName)
     {
         String CategoryId="";
         try
         {
             PreparedStatement statement=connection.prepareStatement
                      ("select CategoryiD from CategoryTable Where CategoryName='"+CategoryName+"'");
              ResultSet rs=statement.executeQuery();
              
              while (rs.next())
              {
                  CategoryId=rs.getString("CategoryId");
              }
         }
         catch(SQLException e)
         {
             System.err.println("Error category ID:"+e);
                     }
         return CategoryId;
     }
     public String[] getCategoryName()
     {
         String CategoryName[]=new String[10];
         try
         {
              PreparedStatement statement=connection.prepareStatement
                      ("select CategoryName from CategoryTable");
              ResultSet rs=statement.executeQuery();
              int index=0;
              while (rs.next())
              {
                  CategoryName[index]= rs.getString("CategoryName");
                  index++;
              }
         }
         catch (SQLException ex)
         {
             System.err.println("Error CategoryName "+ex);
         }
             return CategoryName;
              }
        public boolean insertShoe() {
    try {
        String sql = "INSERT INTO ShoeTable VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, getShoeId());
        statement.setString(2, getModel());
        statement.setString(3, getBrand());
        statement.setString(4, getCategoryId());
        statement.setString(5, getColor());
        statement.setInt(6, getPrice());

        statement.executeUpdate();
        return true;

    } catch (SQLException ex) {
        System.err.println("Error inserting data " + ex);
        return false;
    }
}

         
         public ResultSet DisplayShoe()
         {
             try
             {
                 PreparedStatement statement=connection.prepareStatement("Select * from ShoeTable");
                 ResultSet rs=statement.executeQuery();
                 return rs;
             }
             catch(SQLException ex)
                 {
             System.err.println("Error display shoe"+ex);
             return null;
         }
         }
             
          public ResultSet SearchShoes(String ShoeId)
         {
             try
             {
                 PreparedStatement statement=connection.prepareStatement("Select * from ShoeTable where ShoeId='"+ShoeId+ "'");
                 ResultSet rs=statement.executeQuery();
                 return rs;
             }
             catch(SQLException ex)
                 {
             System.err.println("Error display shoe"+ex);
             return null;
         }
             
             
         }
          public ResultSet SearchShoebyCategory(String CategoryId)
         {
             try
             {
                 PreparedStatement statement=connection.prepareStatement("Select * from ShoeTable where CategoryId='"+CategoryId+ "'");
                 ResultSet rs=statement.executeQuery();
                 return rs;
             }
             catch(SQLException ex)
                 {
             System.err.println("Error display shoe"+ex);
             return null;
         }
}
          
          
     public String[] SelectCategoryId()
     {
         String CategoryName[]=new String[10];
         try
         {
             PreparedStatement statement=connection.prepareStatement
                      ("select CategoryID from CategoryTable");
              ResultSet rs=statement.executeQuery();
              int index=0;
              while (rs.next())
              {
                  CategoryName[index]=rs.getString("CategoryId");
                  index++;
              }
         }
         catch(SQLException e)
         {
             System.err.println("Error Category Id:"+e);
                     }
         return CategoryName;
     }
     public String[] SelectShoeId()
{
    String[] ids = new String[100];
    int i = 0;

    try
    {
        String sql = "SELECT ShoeId FROM ShoeTable";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            ids[i] = rs.getString("ShoeId");
            i++;
        }
    }
    catch(Exception e)
    {
        System.out.println("Error loading Shoe IDs " + e);
    }

    return ids;
}
}