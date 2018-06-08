package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Filamento;
import model.Segmento;

public class SegmentoDAO {

	public static void creaSegmento(int IDFilamento, int IDSegmento, String tipo,Connection conn) {
		
		PreparedStatement stmt=null;
	
		
		try {

			stmt=conn.prepareStatement("INSERT INTO Segmento(ID, Filamento, Tipo) "+
					                   "VALUES (?,?,?) ");
			stmt.setInt(1, IDSegmento);
			stmt.setInt(2, IDFilamento);
			stmt.setString(3, tipo);
		
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

	public static boolean exists(int IDFilamento, int IDSegmento, String tipo,Connection conn) {
		PreparedStatement stmt = null;

        boolean check = false;
        

        try {
        

            stmt = conn.prepareStatement("SELECT * FROM Segmento WHERE ID = ?  ");
            
			stmt.setInt(1, IDSegmento);
			
			
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
	public static boolean existsSegmentoInFilamento(Segmento seg, Filamento fil) throws Exception{
		PreparedStatement stmt = null;
        Connection conn = null;
        boolean check = false;
        

        try {
        	Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");

            stmt = conn.prepareStatement("SELECT *  "+
            		                     "FROM Segmento " +
            		                     "WHERE Segmento.ID = ? AND Segmento.Filamento = ?");
            
			stmt.setInt(1,seg.getIdSegmento());
			stmt.setInt(2, fil.getId());
			
			
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
	
	//Query requisito 11
    public static ArrayList<Double> distanzaVerticiContorno(Segmento seg,Filamento fil) throws Exception{
		   
		   PreparedStatement stmt1=null;
		   PreparedStatement stmt2=null;
		   PreparedStatement stmt3=null;
		   
		   Connection conn1=null;
		   
		   
		   double[][] coordinateMinimoMassimoSeg = new double[2][2];
		   ArrayList<Double> distanze=new ArrayList<Double>();
		   ArrayList<double[]> puntiContorno = new ArrayList<double[]>();
		   ArrayList<Double> distanzeContornoMinimo = new ArrayList<Double>();
           ArrayList<Double> distanzeContornoMassimo = new ArrayList<Double>();
	       try {
	    	   Class.forName("org.postgresql.Driver");
			   conn1=DriverManager.getConnection("jdbc:postgresql:progetto_Basi","postgres","Basi");
	    	   stmt1=conn1.prepareStatement("SELECT PuntoSegmento.Longitudine,PuntoSegmento.Latitudine "+
	                    "FROM PuntoSegmento join Segmento on PuntoSegmento.Segmento = Segmento.ID "+
			            "WHERE Segmento.ID = ? AND NumeroProgressivo <= all"+
	                    "(SELECT NumeroProgressivo "+
			            "FROM PuntoSegmento join Segmento on PuntoSegmento.Segmento = Segmento.ID "+
	                    "WHERE Segmento.ID = ? ) ") ;
	    	   
	    	   stmt1.setInt(1, seg.getIdSegmento());
	    	   stmt1.setInt(2, seg.getIdSegmento());
	    	   
	    	   ResultSet rs1 = stmt1.executeQuery();
	    	   
	    	   while(rs1.next()) {
	    		   
	    	   coordinateMinimoMassimoSeg[0][0]=rs1.getDouble("Longitudine");
	    	   coordinateMinimoMassimoSeg[0][1]=rs1.getDouble("Latitudine");
	    	   }
	    	   rs1.close();
	    	   stmt1.close();
	    	   
	    	   
	    	  
	    	   stmt2=conn1.prepareStatement("SELECT Longitudine,Latitudine "+
	                    "FROM PuntoSegmento join Segmento on PuntoSegmento.Segmento = Segmento.ID "+
			            "WHERE Segmento.ID = ? AND NumeroProgressivo >= all "+
			            "(SELECT NumeroProgressivo "+
			            "FROM PuntoSegmento join Segmento on PuntoSegmento.Segmento = Segmento.ID "+
	                    "WHERE Segmento.ID = ? ) ") ;
	    	   
	    	   stmt2.setInt(1, seg.getIdSegmento());
	    	   stmt2.setInt(2, seg.getIdSegmento());
	    	   ResultSet rs2 = stmt2.executeQuery();
	    	   
	    	   while(rs2.next()) {
	    		   coordinateMinimoMassimoSeg[1][0]=rs2.getDouble("Longitudine");
		    	   coordinateMinimoMassimoSeg[1][1]=rs2.getDouble("Latitudine");
	    	   }
	    	   
	    	   rs2.close();
	    	   stmt2.close();

	    	   stmt3=conn1.prepareStatement("SELECT Longitudine,Latitudine "+
                       "FROM ComposizioneContorno  "+
                       "WHERE ComposizioneContorno.Filamento = ? ") ;
	    	   
              /*Selezioniamo tutti i punti del contorno relativi a quel filamento*/
	    	   stmt3.setInt(1, fil.getId());
	    	   
              ResultSet rs3=stmt3.executeQuery();
              while(rs3.next()){
              double[] punto = new double[2];
              double LongitudinePunto = rs3.getDouble("Longitudine");
              double LatitudinePunto = rs3.getDouble("Latitudine");
              punto[0]=LongitudinePunto;
              punto[1]=LatitudinePunto;
              puntiContorno.add(punto);
              }
              rs3.close();
              stmt3.close();
              
              
              
              for(int i=0;i<puntiContorno.size();i++) {
            	  double distanzaMinimo=(double) Math.sqrt(Math.pow(((double)puntiContorno.get(i)[0]-(double)coordinateMinimoMassimoSeg[0][0]),2)+Math.pow((double)puntiContorno.get(i)[1]-(double)coordinateMinimoMassimoSeg[0][1],2));
            	  distanzeContornoMinimo.add(distanzaMinimo);
              }
              
              for(int i=0;i<puntiContorno.size();i++) {
            	  double distanzaMassimo=(double) Math.sqrt(Math.pow(((double)puntiContorno.get(i)[0]-(double)coordinateMinimoMassimoSeg[1][0]),2)+Math.pow((double)puntiContorno.get(i)[1]-(double)coordinateMinimoMassimoSeg[1][1],2));
            	  distanzeContornoMassimo.add(distanzaMassimo);
              }
              
              double min=distanzeContornoMinimo.get(0);
              for(int k=1;k<distanzeContornoMinimo.size();k++) {
            	  if(distanzeContornoMinimo.get(k)<min)
            		  min=distanzeContornoMinimo.get(k);
       
              }
             
              
              double min1=distanzeContornoMassimo.get(0);
              for(int z=1;z<distanzeContornoMassimo.size();z++) {
            	  if(distanzeContornoMassimo.get(z)<min1)
            		  min1=distanzeContornoMassimo.get(z);
              }
              
              distanze.add(min);
              distanze.add(min1);
              
	    	  conn1.close();
	    	 
	       }catch (SQLException se) {
	   		se.printStackTrace();
			
	       }
	       
	       return distanze;
	   }
}
