package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Banda;
import model.Satellite;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Date.*;

public class SatelliteDAO {

	   public static void inserisciSatellite(String Nome,Date primaOsservazione,Date termineOperazione,String agenzia, Connection conn) {
		    PreparedStatement stmt=null;

		    try {
				stmt=conn.prepareStatement("INSERT INTO Satellite(Nome,PrimaOsservazione,TermineOperazione,Agenzia) "+
						                   "VALUES (?,?,?,?); ");
				stmt.setString(1, Nome);
				java.sql.Date myFirstDate = new java.sql.Date(primaOsservazione.getTime());
				stmt.setObject(2, myFirstDate);
				java.sql.Date mySecondDate = new java.sql.Date(termineOperazione.getTime());
				stmt.setObject(3, mySecondDate);
				stmt.setString(4, agenzia);
			
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
	   public static void inserisciSatellite(String Nome,Date primaOsservazione,Date termineOperazione,String agenzia) {
		    PreparedStatement stmt=null;
			Connection conn = null;
			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				stmt=conn.prepareStatement("INSERT INTO Satellite(Nome,PrimaOsservazione,TermineOperazione,Agenzia) "+
						                   "VALUES (?,?,?,?); ");
				stmt.setString(1, Nome);
				java.sql.Date myFirstDate = new java.sql.Date(primaOsservazione.getTime());
				stmt.setObject(2, myFirstDate);
				java.sql.Date mySecondDate = new java.sql.Date(termineOperazione.getTime());
				stmt.setObject(3, mySecondDate);
				stmt.setString(4, agenzia);
			
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

	   public static void inserisciSatellite(String Nome, Connection conn) {
		    PreparedStatement stmt=null;

			
			try {
				
				stmt=conn.prepareStatement("INSERT INTO Satellite(Nome,PrimaOsservazione,TermineOperazione,Agenzia) "+
						                   "VALUES (?,?,?,?); ");
				stmt.setString(1, Nome);
				stmt.setNull(2, Types.DATE);
				stmt.setNull(3, Types.DATE);
				stmt.setString(4, null);
			
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
	   public static void inserisciSatellite(String Nome) {
		    PreparedStatement stmt=null;
			Connection conn=null;
			
			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				stmt=conn.prepareStatement("INSERT INTO Satellite(Nome,PrimaOsservazione,TermineOperazione,Agenzia) "+
						                   "VALUES (?,?,?,?); ");
				stmt.setString(1, Nome);
				stmt.setNull(2, Types.DATE);
				stmt.setNull(3, Types.DATE);
				stmt.setString(4, null);
			
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
	   public static void inserisciUtilizza(String satellite, String strumento, Connection conn) {
		    PreparedStatement stmt=null;
	
				
				try {
					
					stmt=conn.prepareStatement("INSERT INTO Utilizza(Satellite, Strumento) "+
							                   "VALUES (?,?); ");
					stmt.setString(1, satellite);
					stmt.setString(2, strumento);

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
	   public static void inserisciUtilizza(String satellite, String strumento) {
		    PreparedStatement stmt=null;
				Connection conn=null;
				
				try {
					Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
					stmt=conn.prepareStatement("INSERT INTO Utilizza(Satellite, Strumento) "+
							                   "VALUES (?,?); ");
					stmt.setString(1, satellite);
					stmt.setString(2, strumento);

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
	   public static void updatePrimaOsservazioneSatellite(String Nome, Date data) {
		    PreparedStatement stmt=null;
			Connection conn=null;
			
			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				stmt=conn.prepareStatement("UPDATE Satellite SET PrimaOsservazione = ? "+
						                   "WHERE Nome = ? ");
				stmt.setString(2, Nome);
				java.sql.Date fDate = new java.sql.Date(data.getTime());
				stmt.setDate(1, fDate);
			
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
	   
	   public static void updateTermineOperazioneSatellite(String Nome, Date data) {
		    PreparedStatement stmt=null;
			Connection conn=null;
			
			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				stmt=conn.prepareStatement("UPDATE Satellite SET TermineOperazione = ? "+
				                           "WHERE Nome = ? ");
				stmt.setString(2, Nome);
				java.sql.Date sDate = new java.sql.Date(data.getTime());
				stmt.setDate(1, sDate);
			
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
	   
	   public static void updateAgenziaSatellite(String Nome,String Agenzia) {
		    PreparedStatement stmt=null;
			Connection conn=null;
			
			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				stmt=conn.prepareStatement("UPDATE Satellite SET Agenzia = ? "+
						                    "WHERE Nome = ? ");
				stmt.setString(2, Nome);
				stmt.setObject(1, Agenzia);
			
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
	   
	   public static ArrayList<Satellite> findSatelliti() {
			ArrayList<Satellite> satelliti = new ArrayList<Satellite>();
			Connection conn = null;
			PreparedStatement stmt = null;

			try {
				Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
				String sql = ("SELECT * " + 
						"FROM Satellite ");

				stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet rs = stmt.executeQuery();
				if (!rs.next()) {
					return satelliti;
				}
				do {
					String ID = rs.getString("Nome");
					Date primaOss = (Date) rs.getObject("PrimaOsservazione");
					Date termineOp = (Date) rs.getObject("TermineOperazione");
					String agenzia= rs.getString("Agenzia");
                    Satellite sat=new Satellite(ID,primaOss,termineOp,agenzia);
                    
					ArrayList<Banda> bande = BandaDAO.findBandeAssociateSatellite(ID);

					
					sat.setBande(bande);

					satelliti.add(sat);

				} while (rs.next());
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
			return satelliti;
		}



	   public static void eliminaSatellite(String nome) {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt = conn.prepareStatement("DELETE FROM satellite WHERE nome = ? ");

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
    
	    
	   public static boolean exists(String Nome, Connection conn) {
		   //per controllare se non vi e' gia' un satellite gi� creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		       
		        boolean check = false;
		        

		        try {
		        	

		            stmt = conn.prepareStatement("SELECT * FROM Satellite where Nome = ? ");
		            
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
		                //if (conn != null)
		                	//conn.close();
		            } catch (SQLException se2) {
		            	se2.printStackTrace();
		            }
		        }     

		        return check;
		    }
	   
	   public static boolean exists(String Nome) {
		   //per controllare se non vi e' gia' un satellite gi� creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		        Connection conn = null;
		        boolean check = false;
		        

		        try {
		        	Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

		            stmt = conn.prepareStatement("SELECT * FROM Satellite where Nome = ? ");
		            
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
		                if (conn != null)
		                	conn.close();
		            } catch (SQLException se2) {
		            	se2.printStackTrace();
		            }
		        }     

		        return check;
		    }
	   
	   public static boolean existsUtilizza(String satellite,String strumento, Connection conn) {
		   //per controllare se non vi e' gia' un satellite gi� creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		 
		        boolean check = false;
		        

		        try {
		        	

		            stmt = conn.prepareStatement("SELECT * FROM Utilizza where Satellite = ? AND Strumento = ? ");
		            
		            stmt.setString(1, satellite);
		            stmt.setString(2, strumento);
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
    
	   public static boolean existsUtilizza(String satellite,String strumento) {
		   //per controllare se non vi e' gia' un satellite gi� creato;
		   //is busy restituisce true se e' gia' esistente un satellite con lo stesso nome
		    	
		    	PreparedStatement stmt = null;
		        Connection conn = null;
		        boolean check = false;
		        

		        try {
		        	Class.forName("org.postgresql.Driver");
					conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

		            stmt = conn.prepareStatement("SELECT * FROM Utilizza where Satellite = ? AND Strumento = ? ");
		            
		            stmt.setString(1, satellite);
		            stmt.setString(2, strumento);
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
		                if (conn != null)
		                	conn.close();
		            } catch (SQLException se2) {
		            	se2.printStackTrace();
		            }
		        }     

		        return check;
		    }



	public static ArrayList<Satellite> getAll() throws SQLException {

		ArrayList<Satellite> listaSatelliti = new ArrayList<Satellite>();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM Satellite");
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				listaSatelliti.add(new Satellite(resultSet.getString("Nome"), resultSet.getDate("PrimaOsservazione"),
						resultSet.getDate("TermineOperazione"), resultSet.getString("Agenzia")));

			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Errore nel caricamento dei satelliti!");
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

		return listaSatelliti;
	}

	public static Satellite getByName(String nome) throws SQLException {
		Satellite satellite = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			stmt = conn.prepareStatement("SELECT * FROM satellite WHERE nome = ? ");

			stmt.setString(1, nome);
			resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				satellite = new Satellite(resultSet.getString("nome"), resultSet.getDate("primaosservazione"), resultSet.getDate("termineoperazione"),
						resultSet.getString("agenzia"));
			}
		}  catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException("Errore nel caricamento del satellite!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if(stmt != null) {
					stmt.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
		return satellite;
	}
	 public static void main(String[] args) throws Exception{
		 ArrayList<Satellite> satelliti = SatelliteDAO.getAll();
		 for(int i = 0;i<satelliti.size();i++) {
			 System.out.println("Satellite :" + satelliti.get(i).getNome());
		 }
		 Satellite s = SatelliteDAO.getByName("Crado");
		 System.out.println("Satellite :" + s.getNome());
		 System.out.println("Agenzia :" + s.getAgenzia());
		 System.out.println("PrimaOsservazione :" + s.getPrimaOsservazione());

	 }
	 
}