package data;


/**
 * Questo tipo di eccezione gestisce il caso di acquisizione errata del Training
 * set, nei casi
 * 1) la connessione al database fallisce
 * 2) la tabella non esiste
 * 3) la tabella ha meno di due colonne
 * 4) la tabella ha zero tuple
 * 5) l’attributo corrispondente all’ultima colonna della tabella non è numerico<br>
 
 * Estende la classe Exception per poter gestire le eccezioni 
 */

public class TrainingDataException extends Exception {

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	
	 /**
	  * Costruttore di default dell'eccezione TrainingDataException
	  */
	 TrainingDataException() {}
	
	/**
	 * Costruttore con parametro di tipo Throwable per specificare il tipo di eccezione lanciata.
	 * @param e eccezione da incapsulare
	 */
	 TrainingDataException(Throwable e) {
			super(e);	
	}
	
    /**Costruttore con un messaggio che va stampato a video 
     * quando viene sollevata un'eccezione di questo tipo
     * @param msg messaggio da stampare
     */
	 TrainingDataException(String msg) {
		super(msg);
		
	}
}
