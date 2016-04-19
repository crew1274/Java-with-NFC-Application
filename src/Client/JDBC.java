package Client;

import java.sql.*;
import gnu.io.*;
import java.io.*;
public class JDBC {
	private static  String ip="140.116.39.225";
	private static String dbname ="NFC";
	private static String account="nfc_user";
	private static String password="50150";
	public static String[][] the_new=new String[30][20];;
  private Connection con = null; //Database objects
  //�s��object
  private Statement stat = null;
  //����,�ǤJ��sql������r��
  private ResultSet rs = null;
  //���G��
  private PreparedStatement pst = null;
  //����,�ǤJ��sql���w�x���r��,�ݭn�ǤJ�ܼƤ���m
  //���Q��?�Ӱ��Х�
  private String dropdbSQL = "checkout";
  private String selectSQL = "select * from checkout ";
  /*private static String createdbSQL = "CREATE TABLE nfc.checkout("+"serial VARCHAR(7)"+",year INTEGER"+",month INTEGER"+",day INTEGER"+",hr INTEGER"+
		    "  , min    INTEGER " +
		    "  , sec    INTEGER )" ;*/

  private String insertdbSQL = "insert into checkout('serial','year','month','day','hr','min','sec')";

  //private static String selectSQL = "select * from User where id='546' ";

  public boolean connect(String ip ,String dbname,String account,String password)
  {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Connect:"+ip+":"+dbname+"...");
      con = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/"+dbname,account,password);}
    catch(ClassNotFoundException e)
    {System.out.println("DriverClassNotFound :"+e.toString());
     return false;}//���i��|����sqlexception
    catch(SQLException x) {System.out.println("Exception :"+x.toString());
      return false;}
    System.out.println("Successful connect database!");
	return true;
  }
  //�إ�table���覡
  //�i�H�ݬ�Statement���ϥΤ覡
  public void createTable(String c)
  {
    try
    {stat = con.createStatement();
     stat.executeUpdate(c);}
    catch(SQLException e)
    {System.out.println("CreateDB Exception :" + e.toString());}
    finally
    {close();}
  }
  //�s�W���
  //�i�H�ݬ�PrepareStatement���ϥΤ覡
  // private String insertdbSQL = "insert into User(id,day,passwd) "
	      // "select ifNULL(max(id),0)+1,?,? FROM User";
  public void insertTable(String uid,String stage , String time)
  {
    try
    {
      pst = con.prepareStatement("insert into checkout(uid,stage,time) VALUES(?,?,?)");
      pst.setString(1,uid);
      pst.setString(2,stage);
      pst.setString(3,time);
      pst.executeUpdate();
    }
    catch(SQLException e)
    {
      System.out.println("InsertDB Exception :" + e.toString());
    }
    finally
    {
      System.out.println("Insert done!");
      close();
    }
  }
  public void insertTable_2(String uid,String blood_u ,String blood_d , String time)
  {
    try
    {
      pst = con.prepareStatement("insert into blood(uid,blood_u,blood_d,time) VALUES(?,?,?,?)");
      pst.setString(1,uid);
      pst.setString(2,blood_u);
      pst.setString(3,blood_d);
      pst.setString(4,time);
      pst.executeUpdate();
    }
    catch(SQLException e)
    {
      System.out.println("InsertDB Exception :" + e.toString());
    }
    finally
    {
      System.out.println("Insert done!");
      close();
    }
  }
  //�d�߸��
  //�i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡

  public String SelectTable(String  uid)
  {
	  String b = "";
    try
    {
      stat = con.createStatement();
      rs = stat.executeQuery("select *,RPAD(stage,16,' ') from checkout where uid ='"+uid+"' ORDER BY time DESC  LIMIT 5");
      //System.out.println("ID\t\tName\t\tPASSWORD");
      while(rs.next())
      {
       b=b+rs.getString("uid")+"  "+rs.getString("RPAD(stage,16,' ')")+"  "+rs.getString("time")+"\n";
      }
    }
    catch(SQLException e)
    {
      System.out.println("DropDB Exception :" + e.toString());
    }
    finally
    {
      close();
    }
	return b;
  }
  public String SelectTable_2(String  uid)
  {
	  int i=0;
	  String b = "";
    try
    {
      stat = con.createStatement();
      rs = stat.executeQuery("SELECT * FROM blood WHERE uid = '"+uid+"' group by DATE_FORMAT(time, '%Y-%m-%d-%h') ORDER BY time DESC  LIMIT 3");
      //System.out.println("ID\t\tName\t\tPASSWORD");
      while(rs.next())
      {
       b=b+rs.getString("uid")+"  "+rs.getString("blood_u")+"/"+rs.getString("blood_d")+"  "+rs.getString("time")+"\n";
           the_new[i][0]=rs.getString("time");
    	   the_new[i][1]=rs.getString("blood_u")+"/"+rs.getString("blood_d");
    	   i++;
      }
    }
    catch(SQLException e)
    {
      System.out.println("DropDB Exception :" + e.toString());
    }
    finally
    {
      close();
    }
	return b;
  }


  public void checkdb(String c)
  {
    try
    {
      stat = con.createStatement();
      rs = stat.executeQuery(c);
      System.out.println("ID\tName\tPASSWORD");
      while(rs.next())
      {
        System.out.println(rs.getInt("id")+"\t\t"+
            rs.getString("name")+"\t\t"+rs.getString("passwd"));
      }
    }
    catch(SQLException e)
    {
      System.out.println("DropDB Exception :" + e.toString());
    }
    finally
    {
      close();
    }
  }
  //����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object
  //�_�h�b����Timeout��,�i��|��Connection poor�����p
  public void close()
  {
    try
    {
      if(rs!=null)
      {
        rs.close();
        rs = null;
      }
      if(stat!=null)
      {
        stat.close();
        stat = null;
      }
      if(pst!=null)
      {
        pst.close();
        pst = null;
      }

    }
    catch(SQLException e)
    {
      System.out.println("Close Exception :" + e.toString());
    }
  }


  public static void main(String[] args)
  {
    //���ݬݬO�_���`
    JDBC test = new JDBC();
    if(test.connect(ip,dbname,account,password))
    {System.out.println("connect database!");}
    else
    {System.out.println("can't connect database!");
     return;}
    //test.dropTable();
    //test.createTable(createdbSQL);
    //test.insertTable("yku", "12356");
    //test.insertTable("yku2", "7890");
    System.out.println(test.SelectTable("1111"));
  }
}
