package ClassPackage;
import java.sql.*;

public class InventoryClass {
   private String InventoryId;
    private String ShoeId;
    private int Size;
    private int Stock;
    private int RecoderLevel;
    
    DBConnectionClass connectionClass= new DBConnectionClass();
     Connection connection;
      public InventoryClass()
    {
        connection=connectionClass.getConnection();
    }

    public InventoryClass(String InventoryId, String ShoeId, int Size, int Stock,int RecoderLevel) {
        this.InventoryId = InventoryId;
        this.ShoeId = ShoeId;
        this.Size = Size;
        this.Stock = Stock;
        this.RecoderLevel = RecoderLevel ;
    }

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String InventoryId) {
        this.InventoryId = InventoryId;
    }

    public String getShoeId() {
        return ShoeId;
    }

    public void setShoeId(String ShoeId) {
        this.ShoeId = ShoeId;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }
    
    public int getRecoderLevel() {
        return RecoderLevel;
    }

    public void setRecoderLevel(int Stock) {
        this.RecoderLevel = RecoderLevel;
    }
      
       public String AutoInventoryId() 
    {
        String Inventory_Id=null;
        try
        {
              PreparedStatement statement=connection.prepareStatement("select InventoryId from InventoryTable");
              ResultSet rs=statement.executeQuery();
              String CID=null;
              while (rs.next())
              {
                CID= rs.getString("InventoryId");
              }
              String x=CID.substring(1);
              int ID = Integer.parseInt(x);
              
              if(ID>0 && ID<=9)
              {
                  ID=ID+1;
                  Inventory_Id ="I00"+ID;
              }
              else if (ID>=10 && ID<100)
              {
                  ID=ID+1;
                 Inventory_Id ="I0"+ID;
              }
               else if (ID>=100)
               {
                    ID=ID+1;
                     Inventory_Id ="I"+ID;
               }
                
            }
        catch(NumberFormatException | SQLException e)
        {
            System.err.println("Error Auto Id"+e);
        }
        return Inventory_Id;
    }
       
   

    // Get ShoeId by Color (from ShoeTable)
    public String getShoe_Id(String Color) {
        String ShoeId = "";
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT ShoeId FROM ShoeTable WHERE Color = ?"
            );
            statement.setString(1, Color);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ShoeId = rs.getString("ShoeId");
            }
        } catch (SQLException e) {
            System.err.println("Error getting ShoeId: " + e);
        }
        return ShoeId;
    }

    // Get all colors from ShoeTable
    public String[] getColor() {
        String[] Color = new String[50]; // adjust size if needed
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Color FROM ShoeTable");
            ResultSet rs = statement.executeQuery();
            int index = 0;
            while (rs.next()) {
                Color[index] = rs.getString("Color");
                index++;
            }
        } catch (SQLException ex) {
            System.err.println("Error getting colors: " + ex);
        }
        return Color;
    }

    // Display stock with Shoe details (Model, Brand, Color)
    public ResultSet DisplayInventory() {
        try {
            String sql = "SELECT inv.InventoryId, sh.Model, sh.Brand, inv.Size, sh.Color, inv.Stock, inv.RecoderLevel " +
                         "FROM InventoryTable inv " +
                         "INNER JOIN ShoeTable sh ON inv.ShoeId = sh.ShoeId " +
                         "ORDER BY sh.Model, inv.Size";
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error displaying stock: " + ex);
            return null;
        }
    }
    public boolean insertInventory() 
     {
         try
         {
              connection=connectionClass.getConnection();
             PreparedStatement statement=connection.prepareStatement
                     ("insert into InventoryTable values('"+getInventoryId()+"','"+getShoeId()+"','"+getSize()+"','"+getStock()+
                            "','"+getRecoderLevel()+"')");
             
              statement.executeUpdate();
             return true;
             
         }
         catch (SQLException ex)
         {
             System.err.println("Error insering data"+ex);
             return false;
         }
     }
}