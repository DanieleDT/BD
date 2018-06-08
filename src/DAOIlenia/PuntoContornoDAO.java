package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PuntoContornoDAO {
	
	 public static boolean exists(double longitudine, double latitudine,Connection conn) {
		  
	    	PreparedStatement stmt = null;
	       
	        boolean check = false;
	        

	        try {
	       

	            stmt = conn.prepareStatement("SELECT * FROM PuntoContorno WHERE Longitudine = ? "
	            					+ "AND Latitudine = ? ");
	            
	            stmt.setDouble(1, longitudine);
	            stmt.setDouble(2, latitudine);
	            ResultSet rs=stmt.executeQuery();
	            
	            check = (rs.next());
	            
	            rs.close();
	            
	        }catch (SQLException se) {  
	            se.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }  finally {
	            try {
	                if (stmt != null)
	                    stmt.close();
	               // if (conn != null)
	                //	ConnectionManagerDB.closeConnection(conn);
	            } catch (SQLException se2) {
	            	se2.printStackTrace();
	            }
	        }    
	        return check;
	    }

	public static void creaPuntoContorno(double longitudine, double latitudine,Connection conn) {
			PreparedStatement stmt=null;
		
			
			try {

				stmt=conn.prepareStatement("INSERT INTO PuntoContorno(Longitudine, Latitudine) "+
						                   "VALUES (?,?) ");
				stmt.setDouble(1, longitudine);
				stmt.setDouble(2, latitudine);
								
				stmt.executeUpdate();

	            stmt.close();
	          

			}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
	            try {
	                if (stmt != null)
	                    stmt.close();

	            } catch (SQLException se2) {
	            	se2.printStackTrace();
	            }
			}
	}
}
