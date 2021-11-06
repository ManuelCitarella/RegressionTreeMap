package data;

import java.io.Serializable;

/**
 * Attribute è una classe astratta che modella un generico attributo, il quale può essere continuo o discreto.
 * Per questo motivo la classe Attribute verrà estesa dalle classi concrete ContinuousAttribute e DiscreteAttribute.
 */

public abstract class Attribute implements Serializable{ 

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *   name è il nome simbolico dell'attributo
	 */
	private String name; 
	
	/**
	 *  index è l'identificativo numerico dell'attributo
	 */
	
	private int index; 
	
	/**
	 * E' il costruttore di classe. Vengono inizializzati i valori dei membri name e index. 
	 * @param name nome dell'attributo
	 * @param index indentificativo numerico dell'attributo
	 */
	
	 Attribute(String name, int index) { // constructor
		this.name = name;
		this.index = index;
	}

	
	/**
	 * @return il nome dell'attributo
	 * 
	 */
	
	public String getName() { // metodo da usare per leggere il nome
		return this.name;
	}

	/**
	 * @return l'indice dell'attributo
	 * 
	 */
	public  int getIndex() { // metodo da usare per leggere l'index
		return index;
	}
	
	/**
	 * @return descrizione testuale dell'oggetto Attribute
	 */

	public String toString() {
		return "Attribute [name=" + name + ", index=" + index + "]";
	}

}
