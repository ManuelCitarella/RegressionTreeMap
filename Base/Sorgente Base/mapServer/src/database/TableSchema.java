package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che modella lo schema di una tabella presente nel database relazionale.
 * Implementa l'interfaccia Iterable.
 */

public class TableSchema implements Iterable<Column>{
	
	/**
	 * 
	 * Rappresenta la lista delle colonne della tabella presente nel database
	 */
	private List<Column> tableSchema=new ArrayList<Column>();
	
	/**
	 * Costruttore di classe, inizializza le colonne della tabella
	 * @param db database della tabella su cui stiamo lavorando
	 * @param tableName nome della tabella 
	 * @throws SQLException eccezione lanciata quando si ha un errore in fase di connessione al database o se il nome della tabella è
	 * scorretto
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		 Connection con=db.getConnection();
		 DatabaseMetaData meta = con.getMetaData();
	
		 ResultSet res = meta.getColumns(null, null, tableName, null);//potrebbe richiamare SQLExc
		 
		 if(res.next()==false)
			throw new SQLException();
		 else 
			 res.previous();
		 
	     while (res.next()){
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(res.getString("COLUMN_NAME"),mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
	      	}
	      res.close();
	    }
	  
	/**
	 * @return numero di attributi della tabella
	 */
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
		
		/**
		 * @param index indice della colonna da ottenere
		 * @return colonna in posizione index della tabella
		 */
		public Column getColumn(int index){
			return tableSchema.get(index);
		}

		/**
		 * @return iteratore per la lista di colonne
		 */
		@Override
		public Iterator<Column> iterator() {
			// TODO Auto-generated method stub
			return tableSchema.iterator();
		}

		
	}

		     


