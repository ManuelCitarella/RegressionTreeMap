package data;

import java.io.Serializable;

/**
 * 
 * Sottoclasse concreta della classe astratta Attribute che ne estende ed eredita 
 * metodi ed attributi. Rappresenta gli attributi continui.
 * 
 */
public class ContinuousAttribute extends Attribute implements Serializable {

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Costruttore di classe della classe ContinuosAttribute. Invoca il costruttore della classe 
	 * astratta Attribute e ne inizializza i valori, ovvero gli attributi name e index
	 * @param name nome dell'attributo
	 * @param index indice dell'attributo
	 */
	ContinuousAttribute(String name, int index) { // costruttore
		super(name, index);
	}

	/**
	 * @return il nome simbolico dell'attributo
	 */
	public String toString() {
		return getName();
	}

}
