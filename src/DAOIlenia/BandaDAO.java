package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.*;


public class BandaDAO {

	
		public static ArrayList<Banda> findBandeAssociateSatellite(String ID){
		    
	        PreparedStatement stmt = null;
	        Connection conn = null;

	        ArrayList<Banda> bande = new ArrayList<>();
	        
	       try {

	    	    Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	            
	            stmt = conn.prepareStatement("SELECT * FROM Cattura WHERE Satellite = ? ");
                stmt.setString(1,ID);
	            ResultSet rs = stmt.executeQuery();

	            while(rs.next()){

	                Banda a = new Banda(rs.getDouble("Valore"));
	                bande.add(a);

	            };


	            rs.close();

	        } catch (SQLException se) {
	            // Errore durante l'apertura della connessione
	            se.printStackTrace();
	        } catch (Exception e) {
	            // Errore nel loading del driver
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null)
	                    stmt.close();
	                if (conn != null)
	                	conn.close();
	            } catch (SQLException se2) {
	            	se2.printStackTrace();
	            }
	        }      
	        return bande;
	    }
		
		public static ArrayList<Banda> findBandeAssociateStrumento(String ID){
		    

	        PreparedStatement stmt = null;
	        Connection conn = null;

	        ArrayList<Banda> bande = new ArrayList<>();
	        
	       try {

	    	    Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	            stmt = conn.prepareStatement("SELECT * FROM Registra WHERE Strumento = ? ");
                stmt.setString(1,ID);
	            ResultSet rs = stmt.executeQuery();

	            while(rs.next()){

	                Banda a = new Banda(rs.getDouble("banda"));
	                bande.add(a);

	            };


	            rs.close();

	       } catch (SQLException se) {
	            // Errore durante l'apertura della connessione
	            se.printStackTrace();
	       } catch (Exception e) {
	            // Errore nel loading del driver
	            e.printStackTrace();
	       } finally {
	            try {
	                if (stmt != null)
	                    stmt.close();
	                if (conn != null)
	                	conn.close();
	            } catch (SQLException se2) {
	            	se2.printStackTrace();
	            }
	       }
	        return bande;
	    }
		
	  public static void inserisciBanda(Double valore) {
				PreparedStatement stmt=null;
				Connection conn=null;
				try {
					Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
					stmt=conn.prepareStatement("INSERT INTO Banda(Valore) " +
					                           "VALUES (?) ");
					stmt.setDouble(1,valore);
					stmt.executeUpdate();

		            stmt.close();
		            conn.close();
					 
				}catch(SQLException se){
					se.printStackTrace();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
		            try {
		                if (stmt != null)
		                    stmt.close();
		                if (conn != null)
		                	conn.close();
		            } catch (SQLException se2) {
		            	se2.printStackTrace();
		            }
				}
		}


	public static boolean exists(Double valore) {

		PreparedStatement stmt = null;
		Connection conn = null;
		boolean check = false;


		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM Banda where Valore = ? ");

			stmt.setDouble(1, valore);
			ResultSet rs=stmt.executeQuery();

			check = (rs.next());

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
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}

		return check;
	}



	  
	  
}
	

