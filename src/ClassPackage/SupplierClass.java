package ClassPackage;
import java.sql.*;

public class SupplierClass {
    private String SupplierId;
    private String SupplierName;
    private String Contact;
    private String Address; 
    
    DBConnectionClass connectionClass= new DBConnectionClass();
     Connection connection;
      public SupplierClass()
    {
        connection=connectionClass.getConnection();
    }

    public SupplierClass(String SupplierId, String SupplierName, String Contact, String Address) {
        this.SupplierId = SupplierId;
        this.SupplierName = SupplierName;
        this.Contact = Contact;
        this.Address = Address;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String SupplierId) {
        this.SupplierId = SupplierId;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
      
     public String AutoSupplierId() 
    {
        String Supplier_Id=null;
        try
        {
              PreparedStatement statement=connection.prepareStatement("select SupplierId from SupplierTable");
              ResultSet rs=statement.executeQuery();
              String CID=null;
              while (rs.next())
              {
                CID= rs.getString("SupplierId");
              }
              String x=CID.substring(1);
              int ID = Integer.parseInt(x);
              
              if(ID>0 && ID<=9)
              {
                  ID=ID+1;
                  Supplier_Id ="N00"+ID;
              }
              else if (ID>=10 && ID<100)
              {
                  ID=ID+1;
                 Supplier_Id ="N0"+ID;
              }
               else if (ID>=100)
               {
                    ID=ID+1;
                     Supplier_Id ="N"+ID;
               }
                
            }
        catch(NumberFormatException | SQLException e)
        {
            System.err.println("Error Auto Id"+e);
        }
        return Supplier_Id;
    }
     public boolean insertSupplier() 
     {
         try
         {
              connection=connectionClass.getConnection();
             PreparedStatement statement=connection.prepareStatement
                     ("insert into SupplierTable values('"+getSupplierId()+"','"+getSupplierName()+"','"+getContact()+"','"+getAddress()+"')");
             
              statement.executeUpdate();
             return true;
             
         }
         catch (SQLException ex)
         {
             System.err.println("Error insering data"+ex);
             return false;
         }
             
         } 
     
    
    
      public ResultSet DisplaySupplier()
         {
             try
             {
                 PreparedStatement statement=connection.prepareStatement("Select * from SupplierTable");
                 ResultSet rs=statement.executeQuery();
                 return rs;
             }
             catch(SQLException ex)
                 {
             System.err.println("Error display supplier"+ex);
             return null;
         }
         }
}