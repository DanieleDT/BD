package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Banda;
import model.Satellite;
import model.Strumento;
import model.Strumento;

import java.sql.*;
import java.util.ArrayList;

public class StrumentoDAO {
  
	
	
	public static void inserisciStrumento(String nome, Connection conn) {
		PreparedStatement stmt=null;
	
		try {

			stmt=conn.prepareStatement("INSERT INTO Strumento(Nome) " +
			                           "VALUES (?) ");
			stmt.setString(1, nome);
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
	public static void inserisciStrumento(String nome) {
		PreparedStatement stmt=null;
		Connection conn=null;
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt=conn.prepareStatement("INSERT INTO Strumento(Nome) " +
			                           "VALUES (?) ");
			stmt.setString(1, nome);
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
	
	
	public static void inserisciIndividua(String strumento, int IDFilamento, Connection conn) {
		PreparedStatement stmt=null;

		try {
			
			stmt=conn.prepareStatement("INSERT INTO IndividuaFilamento(Strumento, Filamento) " +
			                           "VALUES (?,?) ");
			stmt.setString(1, strumento);
			stmt.setInt(2, IDFilamento);

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
	public static void inserisciIndividua(String strumento, int IDFilamento) {
		PreparedStatement stmt=null;
		Connection conn=null;
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt=conn.prepareStatement("INSERT INTO IndividuaFilamento(Strumento, Filamento) " +
			                           "VALUES (?,?) ");
			stmt.setString(1, strumento);
			stmt.setInt(2, IDFilamento);

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
	
	public  static ArrayList<Strumento> findStrumenti() throws Exception {
			ArrayList<Strumento> strumenti = new ArrayList<Strumento>();
			Connection conn = null;
			PreparedStatement stmt = null;

			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				String sql = ("SELECT * " + 
						"FROM strumento");

				stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet rs = stmt.executeQuery();
				if (!rs.next()) {
					return strumenti;
				}
				do {
					String nome = rs.getString("nome");

					Strumento s = new Strumento(nome);
					strumenti.add(s);

				} while (rs.next());
				rs.close();
				stmt.close();
			} catch (SQLException se) {
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
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			return strumenti;
		}
	 
	public static boolean exists(String Nome, Connection conn) {
		   //per controllare se non vi e' gia' un satellite gi� creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		        
		        boolean check = false;
		        

		        try {
		        	

		            stmt = conn.prepareStatement("SELECT * FROM Strumento where Nome = ? ");
		            
		            stmt.setString(1, Nome);
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
	public static boolean exists(String Nome) {
		   //per controllare se non vi e' gia' un satellite gia creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		        Connection conn = null;
		        boolean check = false;
		        

		        try {
		        	Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

		            stmt = conn.prepareStatement("SELECT * FROM Strumento where Nome = ? ");
		            
		            stmt.setString(1, Nome);
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
	 
	public static void updateCattura(Satellite sat, Banda banda, Connection conn) {

	    	PreparedStatement stmt = null;
	     
	        boolean check = false;
	        

	        try {


	            stmt = conn.prepareStatement("INSERT INTO Cattura(Satellite, Banda) " +
	                                         "VALUES (?,?) ");
	            
	            stmt.setObject(1, sat);
	            stmt.setObject(2, banda);
	            stmt.executeUpdate();

	            stmt.close();
	            
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

	 }
	public static void updateCattura(Satellite sat, Banda banda) {

    	PreparedStatement stmt = null;
        Connection conn = null;

        try {
        	Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

            stmt = conn.prepareStatement("INSERT INTO Cattura(Satellite, Banda) " +
                                         "VALUES (?,?) ");
            
            stmt.setObject(1, sat);
            Double band = new Double(banda.getValore());
            stmt.setObject(2, band);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            
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

 }

	public static ArrayList<Strumento> getAll() throws Exception {

		ArrayList<Strumento> listaStrumenti = new ArrayList<Strumento>();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM Strumento");
            ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String nome = rs.getString(1);
				
				Strumento s = new Strumento(nome);
				
                listaStrumenti.add(s);
			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Errore nel caricamento degli strumenti!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				if(stmt != null) {
					stmt.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listaStrumenti;
	}

	public static Strumento getByName(String nome) throws SQLException {
		Strumento s = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM utilizza WHERE strumento = ? ");
			stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
			while (rs.next()) {
				String nomeS = rs.getString(2);
                s=new Strumento(nomeS);
                
			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Errore nel caricamento degli strumenti!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return s;
	}

	public static void eliminaStrumento(String nome) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt = conn.prepareStatement("DELETE FROM Strumento WHERE Nome = ? ");

			stmt.setString(1, nome);
			ResultSet rs=stmt.executeQuery();


			while (rs.next());
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e ) {
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

	public static boolean existsRegistra(String strumento,double banda) {
		//per controllare se non vi e' gia' un satellite giï¿½ creato;
		//is busy restituisce true se e' gia' esistente un satellite con lo stesso nome

		PreparedStatement stmt = null;
		Connection conn = null;
		boolean check = false;


		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM Registra where Strumento = ? AND Banda = ? ");

			stmt.setString(1, strumento);
			stmt.setDouble(2,banda);
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
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}

		return check;
	}

	public static void updateRegistra(Strumento s, Banda banda) {

		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("INSERT INTO Registra(Strumento, Banda) " +
					"VALUES (?,?) ");

			stmt.setString(1, s.getNome());
			Double value = new Double(banda.getValore());
			stmt.setObject(2, value);
			stmt.executeUpdate();

			stmt.close();
			conn.close();


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

	}
	
	public static void main(String[] args) throws Exception{

	    Strumento strumento = StrumentoDAO.getByName("SPIRE");
		
	    System.out.println(strumento.getNome());
	
	}
}