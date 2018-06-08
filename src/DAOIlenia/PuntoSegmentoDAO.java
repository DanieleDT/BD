package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PuntoSegmentoDAO {

	public static void creaPuntoSegmento(double longitudine, double latitudine, int numeroProgressivo, double flussoMisurato, int IDSegmento,Connection conn) {
		
		PreparedStatement stmt=null;

		
		try {
			
			stmt=conn.prepareStatement("INSERT INTO PuntoSegmento(Longitudine, Latitudine, NumeroProgressivo, FlussoMisurato, Segmento) "+
					                   "VALUES (?,?,?,?,?); ");
			stmt.setDouble(1, longitudine);
			stmt.setDouble(2, latitudine);
			stmt.setInt(3, numeroProgressivo);
			stmt.setDouble(4, flussoMisurato);
			stmt.setInt(5, IDSegmento);
		
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
	
	public static boolean exists(double longitudine, double latitudine,Connection conn) {
		PreparedStatement stmt = null;
    
        boolean check = false;
        

        try {
  

            stmt = conn.prepareStatement("SELECT * FROM PuntoSegmento WHERE Longitudine = ? AND Latitudine = ? ");
            
			stmt.setDouble(1, longitudine);
			stmt.setDouble(2, latitudine);
			
			ResultSet rs=stmt.executeQuery();
            
            check = (rs.next());
            //se compare il satellite � gi� presente
            
            rs.close();
            
        }catch (SQLException se) {
            // Errore durante l'apertura della connessione
            se.printStackTrace();
        } catch (Exception e) {
            // Errore nel loading del driver
            e.printStackTrace();
        }  finally {
            try {
                if (stmt != null)
                    stmt.close();
             
            } catch (SQLException se2) {
            	se2.printStackTrace();
            }
        }     

        return check;
	}
}
