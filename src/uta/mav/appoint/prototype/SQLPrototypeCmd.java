package uta.mav.appoint.prototype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import uta.mav.appoint.team3fall.singleton.ConfigFileReader;
/*
 * SQLCmd -> implements command and template patterns
 */
public abstract class SQLPrototypeCmd {

	ArrayList<Object> result = new ArrayList<Object>();
	ResultSet res;
	protected Connection conn;
	public ArrayList<Object> getResult(){return result;};
	public abstract void queryDB();
	public abstract void processResult();
	
	public void execute(){
		try{
			connectDB();
			queryDB();
			processResult();
			disconnectDB(); 
		}
		catch(Exception e){
			disconnectDB();
		}
	}
	
	public void connectDB(){
		try
	    {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    String jdbcUrl = "jdbc:mysql://";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_SERVER") + ":";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_PORT") + "/";
	    jdbcUrl += ConfigFileReader.getInstance().getValue("MYSQL_DATABASE");
	    String userid = ConfigFileReader.getInstance().getValue("MYSQL_USER");
	    String password = ConfigFileReader.getInstance().getValue("MYSQL_PASSWORD");
	    conn = DriverManager.getConnection(jdbcUrl,userid,password);
	    }
	    catch (Exception e){
	        System.out.println(e.toString());
	    }
	}
	
	public void disconnectDB(){
		try{
			conn.close();
		}
		catch(Exception e){
			//System.out.println(e);
		}
	}
	
	/**
	 * @param result the result to set
	 */
	public void setResult(ArrayList<Object> result) {
		this.result = result;
	}
}
