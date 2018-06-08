package DAO;

import model.Amministratore;
import model.Utente;
import model.UtenteRegistrato;

import java.sql.*;
import java.util.ArrayList;

public class UtenteDAO {

	public static Utente autenticazioneUtente(String user_id, String password) {
		PreparedStatement stmt=null;
		Connection conn=null;
		Utente u=null;

		try {
			Class.forName("org.postgresql.Driver");

			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt=conn.prepareStatement("SELECT * FROM Utente WHERE UserId = ? AND Password = ? ");
			stmt.setString(1, user_id);
			stmt.setString(2, password);
			ResultSet rs=stmt.executeQuery();

			if(rs.next()) {

				String nome=rs.getString("Nome");
				String cognome=rs.getString("Cognome");
				String email=rs.getString("Email");
				boolean superuser=rs.getBoolean("Superuser");

				if(superuser) {
					u = Amministratore.getInstance(nome, cognome, user_id, email, superuser);
					System.out.println("Sono Amministratore");
				}else {
					u= UtenteRegistrato.getInstance(nome, cognome, user_id, email, superuser);
					System.out.println("Sono Utente Registrato");
				}

			}
			rs.close();
		}catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
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

		return u;
	}



	public Utente trovaUtente(String username) {
		PreparedStatement stmt=null;
		Connection conn=null;
		Utente u=null;

		try {
			Class.forName("org.postgres.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt=conn.prepareStatement("SELECT * FROM Utente WHERE UserId = ? ");
			stmt.setString(1, username);
			ResultSet rs=stmt.executeQuery();

			if(rs.next()) {
				String nome=rs.getString("Nome");
				String cognome=rs.getString("Cognome");
				String email=rs.getString("Email");
				Boolean superuser=rs.getBoolean("Superuser");

				if(superuser) {
					u = Amministratore.getInstance(nome, cognome,username, email, superuser);
				}else {
					u= UtenteRegistrato.getInstance(nome, cognome, username, email, superuser);
				}
			}
			rs.close();
		}catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
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

		return u;
	}
	
	
	

	public static void insertUtente(String nome, String cognome, String user_id, String password, String email, boolean superuser) {
		PreparedStatement stmt=null;
		Connection conn=null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt=conn.prepareStatement("INSERT INTO Utente (UserId,Password,Nome,Cognome,Email,Superuser) "+
			                           "VALUES (?,?,?,?,?,?); " );
			stmt.setString(1, user_id);
			stmt.setString(2, password);
			stmt.setString(3, nome);
			stmt.setString(4, cognome);
			stmt.setString(5, email);
			stmt.setBoolean(6,superuser);
			
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


	 public static boolean isBusy(String UserId) {
		   //per controllare se non vi e' gia' un utente gi� creata;
		   //is busy restituisce true se e' gia' esistente un utente con lo stesso user id
		    	
		    	PreparedStatement stmt = null;
		        Connection conn = null;
		        boolean check = false;
		        

		        try {
		        	Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

		            stmt = conn.prepareStatement("SELECT * FROM Utente where UserId = ? ");
		            
		            stmt.setString(1, UserId);
		            ResultSet rs=stmt.executeQuery();
		            
		            check = (rs.next());
		            //se compare l'utente � gi� presente
		            
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
