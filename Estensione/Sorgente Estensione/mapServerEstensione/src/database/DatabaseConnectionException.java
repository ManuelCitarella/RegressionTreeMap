package database;

/**
 * Questa classe estende la classe Exception 
 * per gestire l'eventuale fallimento della connessione al database.
 */
public class DatabaseConnectionException extends Exception{
	
	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	/**
	  * Costruttore di default dell'eccezione DatabaseConnectionException
	  */
	public DatabaseConnectionException() {}

	/**Costruttore con un messaggio che va stampato a video 
     * quando viene sollevata un'eccezione di questo tipo
     * @param msg messaggio da stampare
     */
	public DatabaseConnectionException(String msg) {
		super(msg);
		System.out.println(msg);	
	}
}
