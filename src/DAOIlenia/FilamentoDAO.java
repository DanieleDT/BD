package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Filamento;
import model.Segmento;
import model.Stella;

public class FilamentoDAO {
   
	    public static void creaFilamento(int id, String nome, double flusso, double densita, double tempMedia, double ell, double contrasto,Connection conn) {
	        PreparedStatement stmt=null;
		
	    	try {
		    	
			    stmt=conn.prepareStatement("INSERT INTO Filamento(ID, Nome, FlussoTotale, DensitaMedia, TemperaturaMedia, Ellitticita, Contrasto) "+
					                   "VALUES (?,?,?,?,?,?,?); ");
    			stmt.setInt(1, id);
	    		stmt.setString(2, nome);
		    	stmt.setDouble(3, flusso);
			    stmt.setDouble(4, densita);
    			stmt.setDouble(5, tempMedia);
	    		stmt.setDouble(6, ell);
		    	stmt.setDouble(7, contrasto);
			
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
	
        public static void creaFilamento(int id,Connection conn) {
	        //modifica i null delle stelle
            PreparedStatement stmt=null;
		
		    try {

			    stmt=conn.prepareStatement("INSERT INTO Filamento(ID, Nome, FlussoTotale, DensitaMedia, TemperaturaMedia, Ellitticita, Contrasto) "+
					                   "VALUES (?,?,?,?,?,?,?); ");
			    stmt.setInt(1, id);
	    		stmt.setString(2, null);
		    	stmt.setNull(3, java.sql.Types.DOUBLE);
			    stmt.setNull(4, java.sql.Types.DOUBLE);
		    	stmt.setNull(5, java.sql.Types.DOUBLE );
			    stmt.setNull(6, java.sql.Types.DOUBLE );
			    stmt.setNull(7, java.sql.Types.DOUBLE);
			
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


        public static int numeroFilamentiInDataBase()throws Exception{
	        PreparedStatement stmt=null;
	        int trovati=0;
	        Connection conn=null;
	   
		    try {
			    Class.forName("org.postgresql.Driver");
			    conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			    stmt=conn.prepareStatement("SELECT count(distinct ID) "+
			                           "FROM Filamento ");

    			ResultSet rs = stmt.executeQuery();
	    		if(!rs.next()) {
		    		return trovati;
			    }
			
                trovati = rs.getInt(1);
                rs.close();
                stmt.close();
                conn.close();

	    	}catch(SQLException se){
		    	se.printStackTrace();
		    }
		    return trovati;
        }


        public static void aggiornaFilamento(int id, String nome, double flusso, double densita, double tempMedia, double ell, double contrasto,Connection conn) {
	        PreparedStatement stmt=null;
		    
		
		    try {

			    stmt=conn.prepareStatement("UPDATE Filamento "+
			                           "SET Nome = ?, FlussoTotale = ?, DensitaMedia = ?, TemperaturaMedia = ?, "+
			                           "Ellitticita = ?, Contrasto = ? "+
					                   "WHERE ID = ? ");
			
			    stmt.setString(1, nome);
			    stmt.setDouble(2, flusso);
			    stmt.setDouble(3, densita);
			    stmt.setDouble(4, tempMedia);
			    stmt.setDouble(5, ell);
			    stmt.setDouble(6, contrasto);
			    stmt.setInt(7, id);
			
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


        public static void aggiornaContornoFilamento(int id, double lon, double lat,Connection conn) {
	        PreparedStatement stmt=null;
	
	        try {
		  
		        stmt=conn.prepareStatement("INSERT INTO ComposizioneContorno (Filamento, Longitudine, Latitudine) "
				   					+ "VALUES (?,?,?) ");
		        stmt.setInt(1,id);
		        stmt.setDouble(2, lon);
		        stmt.setDouble(3, lat);
		   
		        stmt.executeUpdate();
		   
	            }catch (SQLException se) {
                    System.out.println("Rilevato un valore ridondante: [" + id + " ," + lon + " ," + lat + "]");
                } catch (Exception e) {
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


	   public static ArrayList<Double> calcolaCentroideContornoId(int id) throws Exception{
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   ArrayList<Double> centroide=new ArrayList<Double>();
		   try {
			   Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT AVG(Longitudine) as LongitudineC,AVG(Latitudine) as LatitudineC "+
			                              "FROM ComposizioneContorno "+
					                      "WHERE Filamento = ? ");
			   stmt.setInt(1,id);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return centroide;
			   }
			   do {
				   Double longitudineC=rs.getDouble("LongitudineC");
				   Double latitudineC=rs.getDouble("LatitudineC");
				   centroide.add(longitudineC);
				   centroide.add(latitudineC);
			   }while (rs.next());
				 rs.close();
				 stmt.close();
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
		   return centroide;
	   }


	   /*la seguente funzione controlla se � gi� presente un filamento con un dato id*/
	   public static boolean exists(int ID,Connection conn) {
		  
		    	PreparedStatement stmt = null;
		        boolean check = false;
		        

		        try {
		        

		        	
		            stmt = conn.prepareStatement("SELECT * FROM Filamento where ID = ? ");
		            
		            stmt.setInt(1, ID);
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
		
		            } catch (SQLException se2) {
		            	se2.printStackTrace();
		            }
		        }    
		        return check;
	   }


	   public static boolean existsId(int ID) {
			  
	    	PreparedStatement stmt = null;
	        Connection conn = null;
	        boolean check = false;
	        

	        try {
	        	Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

	        	
	            stmt = conn.prepareStatement("SELECT * FROM Filamento where ID = ? ");
	            
	            stmt.setInt(1, ID);
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


	   public static boolean existsNome(String nome) {
			  
	    	PreparedStatement stmt = null;
	        Connection conn = null;
	        boolean check = false;
	        

	        try {
	        	Class.forName("org.postgresql.Driver");
				conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

	        	
	            stmt = conn.prepareStatement("SELECT * FROM Filamento where Nome = ? ");
	            
	            stmt.setString(1, nome);
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

  
	   public static boolean existsComposizioneContorno(int ID,double longitudine,double latitudine,Connection conn) {
			  
	    	PreparedStatement stmt = null;
	        boolean check = false;
	        

	        try {
	        	

	            stmt = conn.prepareStatement("SELECT * FROM ComposizioneContorno where Filamento = ? AND Longitudine = ? AND Latitudine = ? ");
	            
	            stmt.setInt(1, ID);
	            stmt.setDouble(2, longitudine);
	            stmt.setDouble(3, latitudine);
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

	            } catch (SQLException se2) {
	            	se2.printStackTrace();
	            }
	        }    
	        return check;
	   }


	   public static ArrayList<Double> calcolaCentroideContornoNome(String nome) throws Exception{
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   ArrayList<Double> centroide=new ArrayList<Double>();
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT AVG(Longitudine) as LongitudineC,AVG(Latitudine) as LatitudineC "+
			                              "FROM ComposizioneContorno join Filamento ON ComposizioneContorno.Filamento=Filamento.ID  "+
					                      "WHERE Nome = ? ");
			   stmt.setString(1, nome);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return centroide;
			   }
			   do {
				   Double longitudineC=rs.getDouble("LongitudineC");
				   Double latitudineC=rs.getDouble("LatitudineC");
				   centroide.add(longitudineC);
				   centroide.add(latitudineC);
			     }while (rs.next());
				 rs.close();
				 stmt.close();
		   }catch (SQLException se) {
				se.printStackTrace();
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
		   return centroide;
		}

	   
	   public static ArrayList<Double> calcolaMinimoMassimoPosizioniLat(int id) throws Exception{
			  
		   ArrayList<Double> minMaxLat=new ArrayList<Double>();
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT min(Latitudine) as minimoLatitudine, max(Latitudine) as massimoLatitudine " +
			                              "FROM ComposizioneContorno "+
					                      "WHERE Filamento = ? ");
			   stmt.setInt(1,id);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return minMaxLat;
			   }
			   do {
				   Double minlatitudine=rs.getDouble("minimoLatitudine");
				   Double maxlongitudine=rs.getDouble("massimoLatitudine");
				   minMaxLat.add(minlatitudine);
				   minMaxLat.add(maxlongitudine);
			     }while (rs.next());
				 rs.close();
				 stmt.close();
		   }catch (SQLException se) {
				se.printStackTrace();
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
		   return minMaxLat;
	   }


	   public static ArrayList<Double> calcolaMinimoMassimoPosizioniLong(int id) throws Exception{
		  
		   ArrayList<Double> minMaxLong=new ArrayList<Double>();
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT min(Longitudine) as minimoLongitudine , max(Longitudine) as massimoLongitudine " +
			                              "FROM ComposizioneContorno "+
					                      "WHERE Filamento = ? ");
			   stmt.setInt(1,id);
			   
			   ResultSet rs= stmt.executeQuery();
			   if(!rs.next()) {
				   return minMaxLong;
			   }
			   do {
				   Double minlongitudine=rs.getDouble("minimoLongitudine");
				   Double maxlongitudine=rs.getDouble("massimoLongitudine");
				   minMaxLong.add(minlongitudine);
				   minMaxLong.add(maxlongitudine);
			     }while (rs.next());
				 rs.close();
				 stmt.close();
		   }catch (SQLException se) {
				se.printStackTrace();
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
		   return minMaxLong;
	   }


	   public static ArrayList<Double> calcolaMinimoMassimoPosizioniLatNome(String nome) throws Exception{
			  
		   ArrayList<Double> minMaxLat=new ArrayList<Double>();
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT min(Latitudine) as minimoLatitudine, max(Latitudine) as massimoLatitudine " +
			                              "FROM ComposizioneContorno join Filamento on ComposizioneContorno.Filamento=Filamento.ID "+
					                      "WHERE Nome = ? ");
			   stmt.setString(1,nome);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return minMaxLat;
			   }
			   do {
				   Double minlatitudine=rs.getDouble("minimoLatitudine");
				   Double maxlongitudine=rs.getDouble("massimoLatitudine");
				   minMaxLat.add(minlatitudine);
				   minMaxLat.add(maxlongitudine);
			     }while (rs.next());
				 rs.close();
				 stmt.close();
		   }catch (SQLException se) {
				se.printStackTrace();
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
		   return minMaxLat;
	   }


	   public static ArrayList<Double> calcolaMinimoMassimoPosizioniLongNome(String nome) throws Exception{
		  
		   ArrayList<Double> minMaxLong=new ArrayList<Double>();
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT min(Longitudine) as minimoLongitudine, max(Longitudine) as massimoLongitudine " +
                       "FROM ComposizioneContorno join Filamento on ComposizioneContorno.Filamento=Filamento.ID "+
	                      "WHERE Nome = ? ");
			   stmt.setString(1,nome);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return minMaxLong;
			   }
			   do {
				   Double minlongitudine=rs.getDouble("minimoLongitudine");
				   Double maxlongitudine=rs.getDouble("massimoLongitudine");
				   minMaxLong.add(minlongitudine);
				   minMaxLong.add(maxlongitudine);
			     }while (rs.next());
				 rs.close();
				 stmt.close();
		   }catch (SQLException se) {
				se.printStackTrace();
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
		   return minMaxLong;
	   }


	   public static int numeroSegmentiFilamentoId(int Id) throws Exception {
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   int segmenti=0;
	       try {
	    	   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	    	   stmt=conn.prepareStatement("SELECT count(distinct ID) as segmenti "+
	    			                      "FROM Segmento " +
	    			                      "WHERE Segmento.Filamento = ? ");
	    	   stmt.setInt(1, Id);
	    	   ResultSet rs=stmt.executeQuery();
	    	   if(!rs.next()) {
	    		   segmenti=0;
	    	   }
	    	   segmenti=rs.getInt("segmenti");
	       }
	       catch (SQLException se) {
				se.printStackTrace();
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
	       return segmenti;
	   }


	   public static int numeroSegmentiFilamentoNome(String Nome) throws Exception {
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   int segmenti=0;
	       try {
	    	   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	    	   stmt=conn.prepareStatement("SELECT count(distinct Segmento.ID) as segmenti "+
	    			                      "FROM Segmento join Filamento on Segmento.Filamento = Filamento.ID " +
	    			                      "WHERE Nome = ? ");
	    	   stmt.setString(1, Nome);
	    	   
	    	   ResultSet rs=stmt.executeQuery();
	    	   if(!rs.next()) {
	    		   segmenti=0;
	    	   }
	    	   segmenti=rs.getInt("segmenti");
	       }
	       catch (SQLException se) {
				se.printStackTrace();
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
	       return segmenti;
	   }
	   
	  
	   public static ArrayList<Filamento> trovaFilamentiNumeroSegmenti(int minimo,int massimo) throws Exception{
		   
		   PreparedStatement stmt1=null;
		   PreparedStatement stmt2=null;
		   Connection conn=null;
		   ArrayList<Filamento> filamenti=new ArrayList<Filamento>();
	       try {
	    	   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt1=conn.prepareStatement("CREATE VIEW NumeroSegmentiFilamento(Numero,Filamento) as " +
		               "SELECT count(distinct Segmento.ID) , Segmento.Filamento " +
				       "FROM Segmento " + 
		               "GROUP BY Segmento.Filamento ") ;
			   stmt1.executeUpdate();
			   stmt1.close();
	    	   stmt2=conn.prepareStatement(" SELECT distinct(Segmento.Filamento) ,Filamento.Nome,Filamento.FlussoTotale,Filamento.TemperaturaMedia,Filamento.DensitaMedia,Filamento.Contrasto,Filamento.Ellitticita " +
	    			   "FROM Segmento JOIN NumeroSegmentiFilamento on Segmento.Filamento = NumeroSegmentiFilamento.Filamento  JOIN Filamento on Segmento.Filamento = Filamento.ID "+
	    			   " WHERE Numero > ? and Numero < ? ");
	    	   stmt2.setInt(1, minimo);
	    	   stmt2.setInt(2, massimo);
	    	   ResultSet rs=stmt2.executeQuery();
			   if(!rs.next()) {
				   return filamenti;
			   }
			   do {
				   int ID=rs.getInt(1);
				   String nome=rs.getString(2);
				   double flussoTotale=rs.getDouble(3);
				   double temperaturaMedia=rs.getDouble(4);
				   double densitaMedia=rs.getDouble(5);
				   double contrasto = rs.getDouble(6);
				   double ellitticita = rs.getDouble(7);
				   Filamento fil = new Filamento(ID,nome,flussoTotale,temperaturaMedia,densitaMedia,contrasto,ellitticita);
				   filamenti.add(fil);
			     }while (rs.next());
				 rs.close();
				 stmt2.close();
				 
				 stmt2= conn.prepareStatement("DROP VIEW NumeroSegmentiFilamento");
				 stmt2.executeUpdate();
				 stmt2.close();
				 
				 
	    	   
	       }
	       catch (SQLException se) {
				se.printStackTrace();
	       } finally {
				try {
					
					if (conn != null)
						conn.close();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
	       }
	       return filamenti;
	   }
	  //query numero 6
    public static ArrayList<Filamento> ricercaFilamentoContrastoEllitticita(double contrasto,double ellitticitaMin,double ellitticitaMax) throws Exception{
		   
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   ArrayList<Filamento> filamenti=new ArrayList<Filamento>();
	       try {
	    	   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	    	   stmt=conn.prepareStatement("SELECT * "+
	    			                      "FROM Filamento " +
	    			                      "WHERE  Contrasto > ? AND  ? < Ellitticita AND Ellitticita < ? ");
	    	   stmt.setDouble(1,contrasto);
	    	   stmt.setDouble(2, ellitticitaMin);
	    	   stmt.setDouble(3, ellitticitaMax);
	    	   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return filamenti;
			   }
			   do {
				   int id = rs.getInt("ID");
				   String nome=rs.getString("Nome");
				   Double flussoTot = rs.getDouble("FlussoTotale");
				   Double densitaMedia = rs.getDouble("DensitaMedia");
				   Double temperaturaMedia = rs.getDouble("TemperaturaMedia");
				   Double con = rs.getDouble("Contrasto");
				   Double ellitticita = rs.getDouble("Ellitticita");
				   Filamento fil = new Filamento(id,nome,flussoTot,densitaMedia,temperaturaMedia,con,ellitticita);
				   filamenti.add(fil);
			   }while (rs.next());
			        rs.close();
				    stmt.close();
	    	   
	       } catch (SQLException se) {
				se.printStackTrace();
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
	       return filamenti;
	}


    //requisito 8
	public static ArrayList<Filamento> trovaFilamentiArea(int tipo, double R,double Lon,double Lat) throws Exception{

		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		Connection conn=null;
		ArrayList<Filamento> filamenti=new ArrayList<Filamento>();
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

			switch(tipo) {
				case 0:

					stmt1=conn.prepareStatement("CREATE VIEW PuntiContornoCerchio(Longitudine,Latitudine) as " +
							"SELECT Longitudine,Latitudine "+
							"FROM PuntoContorno " +
							"WHERE SQRT(POWER((SELECT CAST(PuntoContorno.Longitudine as DOUBLE PRECISION) - "+ Lat +" ),2)+POWER((SELECT CAST(PuntoContorno.Latitudine as DOUBLE PRECISION)- " + Lon + "),2)) < " +R );

					stmt1.executeUpdate();
					stmt1.close();

					stmt1=conn.prepareStatement( "SELECT distinct Filamento.ID,Filamento.Nome,Filamento.FlussoTotale,Filamento.TemperaturaMedia,Filamento.DensitaMedia,Filamento.Contrasto,Filamento.Ellitticita "+
							" FROM ComposizioneContorno join Filamento on ComposizioneContorno.Filamento = Filamento.ID "+
							" WHERE (Longitudine,Latitudine) in "+
							" (SELECT Longitudine,Latitudine "+
							"FROM PuntiContornoCerchio  ) " );
					ResultSet rs1 = stmt1.executeQuery();
					if(!rs1.next()) {
						stmt1.close();
						stmt3=conn.prepareStatement("DROP VIEW PuntiContornoCerchio " );
						stmt3.executeUpdate();
						stmt3.close();
						return filamenti;
					}
					do {
						int ID=rs1.getInt("ID");
						String nome=rs1.getString("Nome");
						double flussoTotale=rs1.getDouble("FlussoTotale");
						double temperaturaMedia=rs1.getDouble("TemperaturaMedia");
						double densitaMedia=rs1.getDouble("DensitaMedia");
						double contrasto = rs1.getDouble("Contrasto");
						double ellitticita = rs1.getDouble("Ellitticita");
						Filamento fil = new Filamento(ID,nome,flussoTotale,temperaturaMedia,densitaMedia,contrasto,ellitticita);
						filamenti.add(fil);

					}while (rs1.next());
					rs1.close();
					stmt1.close();
					stmt3=conn.prepareStatement("DROP VIEW PuntiContornoCerchio " );
					stmt3.executeUpdate();
					stmt3.close();


				case 1:

					stmt2=conn.prepareStatement("CREATE VIEW PuntiContornoQuadrato(Longitudine,Latitudine) as  "+
							" SELECT Longitudine,Latitudine "+
							" FROM PuntoContorno "+
							" WHERE (SELECT(CAST(PuntoContorno.Longitudine as DOUBLE PRECISION))) < "+ (Lon + R/2)+" AND (SELECT(CAST(PuntoContorno.Longitudine as DOUBLE PRECISION))) > "+ (Lon-R/2)+
							" AND (SELECT(CAST(PuntoContorno.Latitudine as DOUBLE PRECISION))) < " + (Lat+R/2) +" AND (SELECT(CAST(PuntoContorno.Latitudine as DOUBLE PRECISION))) > "+ (Lat-R/2) );

					stmt2.executeUpdate();
					stmt2.close();
					stmt2=conn.prepareStatement( "SELECT distinct Filamento.ID,Filamento.Nome,Filamento.FlussoTotale,Filamento.TemperaturaMedia,Filamento.DensitaMedia,Filamento.Contrasto,Filamento.Ellitticita "+
							" FROM ComposizioneContorno join Filamento on ComposizioneContorno.Filamento = Filamento.ID "+
							" WHERE (Longitudine,Latitudine) in "+
							" (SELECT Longitudine,Latitudine "+
							"FROM PuntiContornoQuadrato  ) " );
					ResultSet rs2 = stmt2.executeQuery();
					if(!rs2.next()) {
						stmt2.close();
						stmt3=conn.prepareStatement("DROP VIEW PuntiContornoQuadrato " );
						stmt3.executeUpdate();
						stmt3.close();
						return filamenti;

					}
					do {
						int ID=rs2.getInt("ID");
						String nome=rs2.getString("Nome");
						double flussoTotale=rs2.getDouble("FlussoTotale");
						double temperaturaMedia=rs2.getDouble("TemperaturaMedia");
						double densitaMedia=rs2.getDouble("DensitaMedia");
						double contrasto = rs2.getDouble("Contrasto");
						double ellitticita = rs2.getDouble("Ellitticita");
						Filamento fil = new Filamento(ID,nome,flussoTotale,temperaturaMedia,densitaMedia,contrasto,ellitticita);
						filamenti.add(fil);

					}while (rs2.next());
					rs2.close();
					stmt2.close();
					stmt3= conn.prepareStatement("DROP VIEW PuntiContornoQuadrato " );
					stmt3.executeUpdate();
					stmt3.close();

			}

		}catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
		return filamenti;

	}




	//per la query 10,selezionare i filamenti, i cui punti di contorno si trovano all'interno del rettangolo.
    
        public static ArrayList<Integer> selezionaFilamentiInRettangolo(double f,double g,double lonCentro,double latCentro) throws Exception{
        	ArrayList<Integer> filamentiTrovati = new ArrayList<Integer>();
        	PreparedStatement stmt=null;
     	    Connection conn=null;
     	   
            try {
                Class.forName("org.postgresql.Driver");
                conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
             
                stmt=conn.prepareStatement("CREATE VIEW PuntiContornoRettangolo(Longitudine,Latitudine) as  "+
                        " SELECT Longitudine,Latitudine "+
                        " FROM PuntoContorno "+
                        " WHERE (SELECT(CAST(PuntoContorno.Longitudine as DOUBLE PRECISION))) < "+ (lonCentro + f/2)+" AND (SELECT(CAST(PuntoContorno.Longitudine as DOUBLE PRECISION))) > "+ (lonCentro - f/2)+
                        " AND (SELECT(CAST(PuntoContorno.Latitudine as DOUBLE PRECISION))) < " + (latCentro+g/2) +" AND (SELECT(CAST(PuntoContorno.Latitudine as DOUBLE PRECISION))) > "+ (latCentro-g/2) );
     		    
                stmt.executeUpdate();
     		    stmt.close();
                stmt=conn.prepareStatement(
            		          "SELECT distinct Filamento.ID,Filamento.Nome,Filamento.FlussoTotale,Filamento.TemperaturaMedia,Filamento.DensitaMedia,Filamento.Contrasto,Filamento.Ellitticita "+
                              " FROM ComposizioneContorno join Filamento on ComposizioneContorno.Filamento = Filamento.ID "+
                              " WHERE (Longitudine,Latitudine) in "+ 
		                      " (SELECT Longitudine,Latitudine "+
                              "FROM PuntiContornoRettangolo  ) " );
      	   
      	        ResultSet rs=stmt.executeQuery();
     		    if(!rs.next()) {
     		    	stmt.close();
					stmt=conn.prepareStatement("DROP VIEW PuntiContornoRettangolo " );
					stmt.executeUpdate();
					stmt.close();
     			   return filamentiTrovati;
     		    }
     		    do {
     			   int IdFil=rs.getInt(1);
     			   filamentiTrovati.add(IdFil);
     		    }while (rs.next());
     	            rs.close();
     	            stmt.close();
     	        stmt=conn.prepareStatement("DROP VIEW PuntiContornoRettangolo ");
     	        stmt.executeUpdate();
     	        stmt.close();
                
     	        }catch (SQLException se) {
     				se.printStackTrace();
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
     			return filamentiTrovati;
	    }
        
    public static void main(String[] args) throws Exception{
    	
    	int trovati = FilamentoDAO.numeroFilamentiInDataBase();
    	System.out.println(trovati);
  
		
    }	
	
}
