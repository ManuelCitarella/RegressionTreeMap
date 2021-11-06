package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Questa classe modella l’insieme di transazioni collezionate in una tabella.
 * Ogni singola transazione(esempio) viene modellata nella classe Example
 *
 */
public class TableData {

	/**
	 * Oggetto per l'accesso al database
	 */
	private DbAccess db;

	/**
	 * Costruttore di classe, in modo da inizializzare il database
	 * @param db è il database che useremo 
	 */
	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 *  Ricava lo schema della tabella con nome table. Esegue una interrogazione
	 *  per estrarre le tuple distinte da tale tabella.<br>
	 *  Per ogni tupla del resultset si crea un oggetto istanza della classe Example,
	 *  il cui riferimento va incluso nella lista da restituire.<br>
	 *  In particolare, per la tupla corrente nel resultset, si estraggono i valori 
	 *  dei singoli campi, e li si aggiungono all’oggetto istanza 
	 *  della classe Example che si sta costruendo.<br>
	 *  Il metodo può propagare un' eccezione di tipo SQLException (in presenza
	 *  di errori nell'esecuzione della query) o EmptySetException
	 *  (se il resultset è vuoto).
	 * @param table nome della tabella nel database
	 * @return lista di transazioni memorizzate nella tabella
	 * @throws SQLException lanciata nel momento in cui si ha un errore in fase di connessione al database
	 * @throws EmptySetException lanciata se si cerca di manipolare una tabella vuota
	 */
	public List<Example> getTransazioni(String table) throws SQLException, EmptySetException{
		/*Example corrisponde a una tupla che viene riempita ogni cella con un elemento
		 * di ciascuna colonna della tabella
		 * TransSet è una lista di tuple(example)*/
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		String query="select ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty=true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException();
		
		return transSet;

	}
	
	/* 
	 * prendere la colonna column della tabella table e vedere quali sono i valori distinti presenti nella tabella
	 * di quella specifica colonna e metterli nel Set
	 */
	/**
	 * 
	 * Metodo che dopo aver formulato una query interroga il database in modo da estrarre
	 * valori distinti ordinati dell'attributo nella colonna identificata da column e popola un insieme
	 * da restituire. 
	 * @param table, nome della tabella
	 * @param column nome della colonna specifica della tabella
	 * @return collezione di valori distinti in ordine crescente
	 * @throws SQLException lanciata nel momento in cui la connessione al database 
	 */
	public Set<Object> getDistinctColumnValues (String table, Column column) throws SQLException{
		
		Set<Object> distinctSet= new TreeSet<Object>();
		
		Statement statement;
		
		String query="select distinct "+ column.getColumnName()+ " FROM "+ table;
		query += (" order by "+ column.getColumnName()+ " asc");
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while (rs.next()) {
			if (column.isNumber()) 
				distinctSet.add(rs.getDouble(1));
			else
				distinctSet.add(rs.getString(1));		
		}
		rs.close();
		statement.close();
	
		return distinctSet;
	}
	
	
	
	public enum QUERY_TYPE {
		MIN, MAX
	}


}
