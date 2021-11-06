package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 
 * La classe DbAccess ha il compito di instaurare la connessione 
 * con il database. Realizza l'accesso alla base di dati
 *
 */

public class DbAccess {

	/**
	 * nome della classe per la gestione dei driver
	 */
	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * DBMS utilizzato
	 */
	private final String DBMS = "jdbc:mysql";
	/**
	 * identificativo del server su cui è presente la base di dati
	 */
	private final String SERVER = "localhost";
	/**
	 * nome del database
	 */
	private final String DATABASE = "MapDB";
	/**
	 * numero di porta su cui il DBMS MySQL accetta le connessioni
	 */
	private final int PORT = 3306;
	/**
	 * nome dell’utente per l’accesso al database
	 */
	private final String USER_ID = "MapUser";
	/**
	 * password di autenticazione per l'utente identificato da USER_ID nel database
	 */
	private final String PASSWORD = "map";
	
	/**
	 * Oggetto di tipo connection che permette la connessione al database  
	 */
	private Connection conn;
	
	
	/**
	 * Il metodo initConnection impartisce al class loader l’ordine di caricare il driver mysql e
	 * inizializza la connessione riferita da conn.
	 * Il metodo potrebbe sollevare un'eccezione di tipo DatabaseConnectionException 
	 * in caso di fallimento nella connessione al database. 
	 * @throws DatabaseConnectionException se la connessione al database usato fallisce
	 */
	
	public void initConnection() throws DatabaseConnectionException		
	{
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch(ClassNotFoundException e) {
			System.out.println("[!] Driver not found: " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(InstantiationException e){
			System.out.println("[!] Error during the instantiation : " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(IllegalAccessException e){
			System.out.println("[!] Cannot access the driver : " + e.getMessage());
			throw new DatabaseConnectionException();
		}
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
					+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
			
		
		try {			
			conn = DriverManager.getConnection(connectionString);
		} catch(SQLException e) {
			System.out.println("[!] SQLException: " + e.getMessage());
			System.out.println("[!] SQLState: " + e.getSQLState());
			System.out.println("[!] VendorError: " + e.getErrorCode());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * @return oggetto di tipo Connection che permette la connessione al database
	 */
	public Connection getConnection() {
		return conn;
	}
	/**
	 * Chiude la connesione al database
	 * @throws SQLException lanciata nel caso di errore nella connessione col  database
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}
}
