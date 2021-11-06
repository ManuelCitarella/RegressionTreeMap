package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * DiscreteAttribute è una sottoclasse concreta di Attribute e ne eredita ed implementa 
 * attributi e metodi. Modella gli attributi discreti.
 */

public class DiscreteAttribute extends Attribute implements Iterable<String>{

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista dei valori che l'attributo discreto può assumere.
	 * Ordinato in ordine crescente
	 */
	private Set<String> values=new TreeSet<String>(); // order by asc array che contiene il dominio dell'attributo

	/**
	 * Costruttore di classe, che inizializza gli attributi name e index richiamando
	 * il costruttore della superclasse Attribute, e popola la lista values 
	 * della sottoclasse DiscreteAttribute con i valori discreti in input
	 * @param name nome dell'attributo
	 * @param index indice dell'attributo
	 * @param values elenco dei possibili valori che l'attributo può assumere
	 */

	 DiscreteAttribute(String name, int index, Set<String> values) { // costruttore
		super(name, index);
		this.values = values;
	}

	/**
	 * 
	 * @return dimensione dell'insieme dei possibili valori che l'attributo può assumere
	 */
     int getNumberOfDistinctValues() { // restituisce la lunghezza del dominio
		return values.size();
	}
 /**
  * 
  * 
  * @param i indice di un solo valore discreto dall'elenco dei valori possibili che l'attributo può assumere
  * @return valore dell'attributo in posizione i della lista
  */
     String getValue(int i) { // restituisce il valore del dato in posizione i del dominio
		int cont=0;
		for(String s : values) {
			if(cont==i) 
				return s;
			
			cont++;
		}
		return null;
	}

   /**
    * @return descrizione testuale dell'oggetto 
    */
	public String toString() {
		 return getName();
	}


	/**
	 * Restituisce un iteratore per l'insieme dei possibili valori.
	 */
	public Iterator<String> iterator() {
		return values.iterator();
	}
	
}
