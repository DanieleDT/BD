package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Filamento;
import model.Segmento;
import model.Stella;

public class StellaDAO {

    public static void creaStella(int id, String nome, double longitudine, double latitudine, double flusso, String tipo,Connection conn) {
	    PreparedStatement stmt=null;
		
		
		try {
			
			stmt=conn.prepareStatement("INSERT INTO Stella(ID, NomeSorgente, Longitudine, Latitudine, FlussoVal, Tipologia, Filamento) "+
					                   "VALUES (?,?,?,?,?,?,?); ");
			stmt.setInt(1, id);
			stmt.setString(2, nome);
			stmt.setDouble(3, longitudine);
			stmt.setDouble(4, latitudine);
			stmt.setDouble(5, flusso);
			stmt.setString(6, tipo);
			stmt.setObject(7, null);
			
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
	
   public static void aggiornaStella(int id, String nome, double longitudine, double latitudine, double flusso, String tipo) {
	    PreparedStatement stmt=null;
		Connection conn=null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			stmt=conn.prepareStatement("UPDATE Stella "+
			                           "SET NomeSorgente = ?,Longitudine = ?, Latitudine = ?, FlussoVal = ?, "+
			                           "Tipologia = ?, Filamento = ? "+
					                   "WHERE ID = ? ");
			
			stmt.setString(1, nome);
			stmt.setDouble(2, longitudine);
			stmt.setDouble(3, latitudine);
			stmt.setDouble(4, flusso);
			stmt.setString(5,tipo);
			stmt.setObject(6, null);
			stmt.setInt(7, id);
			
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
  
   
   public static boolean exists(int IDStella,Connection conn) {
	   PreparedStatement stmt = null;
      
       boolean check = false;
       

       try {
       	

           stmt = conn.prepareStatement("SELECT * FROM Stella WHERE ID = ? ");
           
			stmt.setInt(1, IDStella);
		
			
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
   public static boolean existsId(int IDStella) {
	   PreparedStatement stmt = null;
       Connection conn = null;
       boolean check = false;
       

       try {
       	    Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

           stmt = conn.prepareStatement("SELECT * FROM Stella WHERE ID = ? ");
           
			stmt.setInt(1, IDStella);
		
			
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
   
   public static ArrayList<Stella> trovaStelleInDB() throws Exception{
	   PreparedStatement stmt=null;
	   Connection conn=null;
	   ArrayList<Stella> stelle=new ArrayList<Stella>();
	   try {
		   Class.forName("org.postgresql.Driver");
		   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
		   stmt=conn.prepareStatement("SELECT * "+
		                              "FROM Stella ");
		 
		   ResultSet rs=stmt.executeQuery();
		   if(!rs.next()) {
			   return stelle;
		   }
		   do {
			   int id = rs.getInt(1);
			   String sorg = rs.getString(2);
			   double lon=rs.getDouble(3);
			   double lat = rs.getDouble(4);
			   double flussoVal = rs.getDouble(5);
			   String tipo = rs.getString(6);
			   Stella s = new Stella(id,sorg,lon,lat,flussoVal,tipo);
			   stelle.add(s);
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
		return stelle;   
   }
   
 //Query requisito 9
   public static ArrayList<Object> trovaStelleInFilamento(int id, int count, int numCiclo) throws Exception{
     PreparedStatement stmt=null;
     Connection conn1=null;
     Connection conn2=null;

     int offset_valore = count*numCiclo;
     ArrayList<Object> stelleFinal = new ArrayList<>();
     ArrayList<Stella> stelleTrovate = new ArrayList<>();
     ArrayList<Stella> stars = new ArrayList<>();
     
     ArrayList<ArrayList<Object>> stelle = new ArrayList<ArrayList<Object>>();
     ArrayList<double[]> puntiContorno = new ArrayList<double[]>();
     
     try {
      Class.forName("org.postgresql.Driver");
      conn1=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
      /*Selezioniamo tutti i punti del contorno relativi a quel filamento*/
      stmt=conn1.prepareStatement("SELECT Longitudine,Latitudine "+
                            "FROM ComposizioneContorno  "+
                            "WHERE ComposizioneContorno.Filamento = ? ") ;
     
      stmt.setInt(1,id);
      
      ResultSet rs1=stmt.executeQuery();
      
      while(rs1.next()){
     	  
          double[] punto = new double[2];
          double LongitudinePunto = rs1.getDouble("Longitudine");
          double LatitudinePunto = rs1.getDouble("Latitudine");
          punto[0]=LongitudinePunto;
          punto[1]=LatitudinePunto;
          puntiContorno.add(punto);
       }



         rs1.close();
 	 stmt.close();
 	 conn1.close();
 	 
 	 conn2=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
 	 stmt=conn2.prepareStatement("SELECT ID,NomeSorgente,Longitudine,Latitudine,FlussoVal,Tipologia "+
 	                              "FROM Stella " );
 	 
      ResultSet rs2=stmt.executeQuery();
         while(rs2.next()){
 		
          int ID = rs2.getInt("ID");
          String nomeSorgente = rs2.getString("NomeSorgente");
          double LongitudineStella = rs2.getDouble("Longitudine");
          double LatitudineStella = rs2.getDouble("Latitudine");
          double FlussoVal = rs2.getDouble("FlussoVal");
          String Tipologia = rs2.getString("Tipologia");
          
          ArrayList<Object> infoStella = new ArrayList<Object>();
          infoStella.add(ID);
          infoStella.add(nomeSorgente);
          infoStella.add(LongitudineStella);
          infoStella.add(LatitudineStella);
          infoStella.add(FlussoVal);
          infoStella.add(Tipologia);
          stelle.add(infoStella);
          
      }


      for (int z = numCiclo*count; z < numCiclo*count+numCiclo; z++) {
        for(int i=0;i<stelle.size();i++) {
             double sum = 0;
             for (int j = 0; j < puntiContorno.size() - 1; j++) {

                double numeratore = ((puntiContorno.get(j)[0] - (double) stelle.get(i).get(2)) * (puntiContorno.get(j + 1)[1] - (double) stelle.get(i).get(3))) - ((puntiContorno.get(j)[1] - (double) stelle.get(i).get(3)) * (puntiContorno.get(j + 1)[0] - (double) stelle.get(i).get(2)));
                double denominatore = ((puntiContorno.get(j)[0] - (double) stelle.get(i).get(2)) * (puntiContorno.get(j + 1)[0] - (double) stelle.get(i).get(2))) - ((puntiContorno.get(j)[1] - (double) stelle.get(i).get(3)) * (puntiContorno.get(j + 1)[1] - (double) stelle.get(i).get(3)));
                double value = (double) Math.atan(numeratore / denominatore);
                sum = sum + value;
            }


            if (Math.abs(sum) >= 0.01) {
                Stella s = new Stella((int) stelle.get(i).get(0), (String) stelle.get(i).get(1), (double) stelle.get(i).get(2), (double) stelle.get(i).get(3), (double) stelle.get(i).get(4), (String) stelle.get(i).get(5));
                stelleTrovate.add(s);
            }


        }
        stars.add(stelleTrovate.get(z));

         }
         stelleFinal.add(stars);
         stelleFinal.add(stelleTrovate.size());

     }catch (SQLException se) {
 		se.printStackTrace();
     } finally {
 		try {
 			if (stmt != null)
 				stmt.close();
 			if (conn1 != null)
 				conn1.close();
 		} catch (SQLException se2) {
 			se2.printStackTrace();
 		}
 	}

      return stelleFinal;
 }
 	
   
   public static boolean existsStellaInFilamento(Stella s,int id) throws Exception{
	    PreparedStatement stmt=null;
	    Connection conn=null;
	    
	   
	    double[] infoStella = new double[2];
	    infoStella[0]=s.getLongitudine();
	    infoStella[1]=s.getLatitudine();
	    
	    ArrayList<double[]> puntiContorno = new ArrayList<double[]>();
	    boolean check = false;
	    
	    try {
	     Class.forName("org.postgresql.Driver");
	     conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	     stmt=conn.prepareStatement("SELECT Longitudine,Latitudine "+
	                                "FROM ComposizioneContorno "+
	                                "WHERE ComposizioneContorno.Filamento = ? ") ;
	     
	     stmt.setInt(1, id);
	     ResultSet rs1=stmt.executeQuery();
	     while(rs1.next()){
	    	  
	         double[] punto = new double[2];
	         double LongitudinePunto = rs1.getDouble(1);
	         double LatitudinePunto = rs1.getDouble(2);
	         punto[0]=LongitudinePunto;
	         punto[1]=LatitudinePunto;
	         puntiContorno.add(punto);
	      }
	     rs1.close();
		 stmt.close();
		 
		
		 double sum=0;
	     for(int i=0;i<puntiContorno.size()-1;i++){
	    		double numeratore = ((puntiContorno.get(i)[0]-s.getLongitudine())*(puntiContorno.get(i+1)[1]-s.getLatitudine()))-((puntiContorno.get(i)[1]-s.getLatitudine())*(puntiContorno.get(i+1)[0]-s.getLongitudine()));
	        	double denominatore = ((puntiContorno.get(i)[0]-s.getLongitudine())*(puntiContorno.get(i+1)[0]-s.getLongitudine()))-((puntiContorno.get(i)[1]-s.getLatitudine())*(puntiContorno.get(i+1)[1]-s.getLatitudine()));
	            double value = (double)Math.atan(numeratore/denominatore);
	            sum=sum+value;
	     
	     }
	     
	     if(Math.abs(sum)>=0.01) {
	    	    /*System.out.println("entrato qui");*/
	    		check=true;
        }
	     else {
	    	/* System.out.println("entrato nell'else");*/
	    	 check=false;
	     }
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
	     return check;
	}

       //Query requisito 12
	   public static ArrayList<Object> posizioneStellaSpinaDorsaleFilamento(int fil,Stella stella)throws Exception{
		   
		   
		   
		   Connection conn=null;
		   PreparedStatement stmt1 = null;
		   
		   ArrayList<double[]> puntiSpinaDorsale = new ArrayList<double[]>();
		   ArrayList<Double> distanzeStellaPuntiSD = new ArrayList<Double>();
		   ArrayList<Object> distanzaMinimaStellaSD = new ArrayList<Object>();
		   
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   
			   stmt1=conn.prepareStatement( "CREATE VIEW SpinaDorsale(longitudineP,latitudineP) as "+
			   		                        "SELECT Longitudine,Latitudine " + 
		                                    "FROM Filamento join Segmento on Filamento.ID = Segmento.Filamento "+
		                                    " JOIN PuntoSegmento on Segmento.ID = PuntoSegmento.Segmento " + 
		                                    " WHERE Filamento.ID = "+ fil +" AND Segmento.Tipo = " + "'S'".toString());
			   
			   
		       stmt1.executeUpdate();
			   stmt1.close();
			   stmt1=conn.prepareStatement("SELECT SpinaDorsale.longitudineP,SpinaDorsale.latitudineP "+
			                               "FROM SpinaDorsale ");
			   ResultSet r1 = stmt1.executeQuery();
                
			   while(r1.next()){
				 
			        double[] coordinatePuntoSpinaDorsale = new double[2];
			        double LongitudinePunto = r1.getDouble(1);
			        double LatitudinePunto = r1.getDouble(2);
                    coordinatePuntoSpinaDorsale[0]=LongitudinePunto;
                    coordinatePuntoSpinaDorsale[1]=LatitudinePunto;
			        puntiSpinaDorsale.add(coordinatePuntoSpinaDorsale);
			         
			     };
			   
			  
			   
		       for(int i=0; i<puntiSpinaDorsale.size();i++) {
					   double distanzaStellaPunto=(double) Math.sqrt(Math.pow(((double)puntiSpinaDorsale.get(i)[0]-(double)stella.getLongitudine()),2)+Math.pow((double)puntiSpinaDorsale.get(i)[1]-(double)stella.getLatitudine(),2));
					   distanzeStellaPuntiSD.add(distanzaStellaPunto);	
					   
			   }
		       
		       double distanzaMinima = distanzeStellaPuntiSD.get(0);
		       for(int j=1;j<distanzeStellaPuntiSD.size();j++) {
		    	   if(distanzeStellaPuntiSD.get(j)<distanzaMinima) {
		    		   distanzaMinima=distanzeStellaPuntiSD.get(j);
		    	   }
		       }
		       distanzaMinimaStellaSD.add(stella);
			   distanzaMinimaStellaSD.add(distanzaMinima);
			   r1.close();
			   stmt1.close();
			   stmt1=conn.prepareStatement("DROP VIEW SpinaDorsale ");
			   stmt1.executeUpdate();
			   stmt1.close();
			   conn.close();
		   }catch (SQLException se) {
	   		se.printStackTrace();
			if(conn!=null)
				conn.close();
	       }
	       
	       return distanzaMinimaStellaSD;
		   
	   }
	   //Metodo che potrebbe essere utile nella 10
	   public static int trovaNumeroStelleInDB() throws Exception{
		   int numeroStelle=0;
		   PreparedStatement stmt=null;
		   Connection conn=null;
		  
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT count(*) as NumeroStelle "+
			                              "FROM Stella ");
					                      
			   ResultSet rs=stmt.executeQuery();
			   numeroStelle = rs.getInt("NumeroStelle");
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
		   return numeroStelle;
	   }
	   
	   //QUESTO METODO SERVE NELLA QUERY 10
	   public static int trovaStelleTipo(String tipo) throws Exception{
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   int stelle = 0;
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   stmt=conn.prepareStatement("SELECT count(distinct Stella.ID) "+
			                              "FROM Stella "+
					                      "WHERE Tipologia = ?");
			   stmt.setString(1,tipo);
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return stelle;
			   }
			   do {
				   stelle = rs.getInt(1);

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
			return stelle;   
	   }
	   
	 
	  

	   
	   
	   public static ArrayList<Stella> trovaStelleInRettangolo(double baseR,double altezzaR,double lonCentro,double latCentro) throws Exception{
		   PreparedStatement stmt=null;
		   Connection conn=null;
		   ArrayList<Stella> stelle=new ArrayList<Stella>();
		   try {
			   Class.forName("org.postgresql.Driver");
			   conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
			   
			   stmt=conn.prepareStatement("SELECT * "+
			           "FROM Stella "+
					   "WHERE (SELECT CAST(Stella.Longitudine as DOUBLE PRECISION)) > " + (lonCentro-(baseR/2)) + " AND (SELECT CAST(Stella.Longitudine as DOUBLE PRECISION)) < "+ (lonCentro+(baseR/2)) +
					   "AND  (SELECT CAST(Stella.Latitudine as DOUBLE PRECISION)) > " + (latCentro-(altezzaR/2)) + " AND (SELECT CAST(Stella.Latitudine as DOUBLE PRECISION)) < "+ (latCentro+(altezzaR/2)));
			   
			   ResultSet rs=stmt.executeQuery();
			   if(!rs.next()) {
				   return stelle;
			   }
			   do {
				   int id=rs.getInt("ID");
				   String nomeS=rs.getString("NomeSorgente");
				   double longitudine = rs.getDouble("Longitudine");
				   double latitudine = rs.getDouble("Latitudine");
				   double flussoVal = rs.getDouble("FlussoVal");
				   String tipologia = rs.getString("Tipologia");
				   int filamento = rs.getInt("Filamento");
				   Stella s = new Stella(id,nomeS,longitudine,latitudine,flussoVal,tipologia,filamento);
				   stelle.add(s);
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
			return stelle;  
	   }


    public static void main(String[] args) throws Exception{

        ArrayList<Object> s = StellaDAO.trovaStelleInFilamento(45, 3, 20);
        System.out.println(s);

    }
       

	   
}

