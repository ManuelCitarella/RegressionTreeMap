package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Questa classe modella una singola transazione, o esempio, letta dal database.
 * 
 */
public class Example implements Comparable<Example>, Iterable<Object>{
	/**
	 * identifica un singolo esempio, visto come una collezione di oggetti. Una lista Aggiungere Comparable??
	 */
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Metodo che aggiunge un oggetto O alla lista example
	 * @param o oggetto aggiunto alla lista
	 */
	public void add(Object o){
		example.add(o);
	}
	/**
	 * Metodo che restituisce l'oggetto in posizione i della lista example
	 * @param i indice della posizione
	 * @return oggetto della lista example in posizione i
	 */
	public Object get(int i){
		return example.get(i);
	}
	
	/**
	 * Metodo dell'interfaccia Comparable. Permette di confrontare due esempi
	 * @param ex esempio da confrontare
	 * @return risultato del confronto tra due esempi
	 */
	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable<Example>)(Example)o).compareTo((Example)example.get(i));
			i++;
		}
		return 0;
	}
	
	/**
	 * @return descrizione testuale dell'oggetto
	 */
	
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}

	/**
	 * iteratore da usare nella lista
	 */
	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}