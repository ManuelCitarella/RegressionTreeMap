package database;

/**
 * Classe che estende Exception per 
 * modellare la restituzione di un resultset vuoto.
 *
 *
 */
public class EmptySetException extends Exception {

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	
	/**
	  * Costruttore di default dell'eccezione EmptySetException
	  */
	public EmptySetException() {}

	/**Costruttore con un messaggio che va stampato a video 
     * quando viene sollevata un'eccezione di questo tipo
     * @param msg messaggio da stampare
     */
	public EmptySetException(String msg) {
		super(msg);
		System.out.println(msg);	
	}
}
