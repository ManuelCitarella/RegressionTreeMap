package server;

/**
 * Questa classe estende Exception per gestire il caso di acquisizione di valore
 * mancante o fuori range di un attributo di un nuovo esempio da classificare.
 *
 */
public class UnknownValueException extends Exception{
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	
	private static final long serialVersionUID = 6588931838733052750L;
	
	/**
	 * Costruttore con un messaggio che va stampato a video 
     * quando viene sollevata un'eccezione di questo tipo
     * @param msg messaggio da stampare
	 */
	UnknownValueException(String msg){
		super(msg);
	}
	
}
