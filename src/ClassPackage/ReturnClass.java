package ClassPackage;
import java.sql.*;

public class ReturnClass {
 private String ReturnId;
    private String ShoeId;
    private int Size;
    private int Quantity;
    private String Reason;
    private String Status;
    
    DBConnectionClass connectionClass= new DBConnectionClass();
     Connection connection;
      public ReturnClass()
    {
        connection=connectionClass.getConnection();
    }

    public ReturnClass(String ReturnId, String ShoeId, int Size, int Quantity, String Reason, String Status) {
        this.ReturnId = ReturnId;
        this.ShoeId = ShoeId;
        this.Size = Size;
        this.Quantity = Quantity;
        this.Reason = Reason;
        this.Status = Status;
    }

    public String getReturnId() {
        return ReturnId;
    }

    public void setReturnId(String ReturnId) {
        this.ReturnId = ReturnId;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
   
    public String AutoReturnId() 
    {
        String Return_Id=null;
        try
        {
              PreparedStatement statement=connection.prepareStatement("select ReturnId from ReturnTable");
              ResultSet rs=statement.executeQuery();
              String CID=null;
              while (rs.next())
              {
                CID= rs.getString("ReturnId");
              }
              String x=CID.substring(1);
              int ID = Integer.parseInt(x);
              
              if(ID>0 && ID<=9)
              {
                  ID=ID+1;
                  Return_Id ="R00"+ID;
              }
              else if (ID>=10 && ID<100)
              {
                  ID=ID+1;
                 Return_Id ="R0"+ID;
              }
               else if (ID>=100)
               {
                    ID=ID+1;
                     Return_Id ="R"+ID;
               }
                
            }
        catch(NumberFormatException | SQLException e)
        {
            System.err.println("Error Auto Id"+e);
        }
        return Return_Id;
    }
    
    public ResultSet displayReturns(){

    try{

        PreparedStatement ps = connection.prepareStatement(
        "SELECT * FROM ReturnTable WHERE Status='Pending'");

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println("Return display error "+e);
        return null;
    }
}
    
   public void approveReturn(String id){

    try{

        PreparedStatement ps = connection.prepareStatement(
        "UPDATE ReturnTable SET Status='Approved' WHERE ReturnId=?");

        ps.setString(1,id);

        ps.executeUpdate();

    }catch(Exception e){

        System.out.println("Approve error "+e);
    }
}
   private String getShoeIdByReturnId(String id){
    try{
        PreparedStatement ps = connection.prepareStatement(
            "SELECT ShoeId FROM ReturnTable WHERE ReturnId=?"
        );
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("ShoeId");
        }
    }catch(Exception e){
        System.out.println(e);
    }
    return "";
}

   public void rejectReturn(String id){

    try{

        PreparedStatement ps = connection.prepareStatement(
        "UPDATE ReturnTable SET Status='Rejected' WHERE ReturnId=?");

        ps.setString(1,id);

        ps.executeUpdate();

    }catch(Exception e){

        System.out.println("Reject error "+e);
    }
}
}