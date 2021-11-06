package tree;

import java.io.Serializable;

import data.Data;

/**
 * Questa classe estende la superClasse LeafNode per modellare l'entità nodo foglia dell'albero
 * Attravero l'implementazione dell'interfaccia Serializable
 * gli oggetti di questa classe potranno essere serializzati
 */

public class LeafNode extends Node implements Serializable{

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	/**
	 * Valore dell'attributo di classe espresso nella foglia corrente
	 */
	private Double predictedClassValue ;

	/**
	 * Costruttore di classe che istanzia un oggetto invocando il costruttore
	 * della superclasse Node e avvalora l'attributo predictedClassValue
	 * (come media dei valori dell’attributo di classe che ricadono nella partizione,
	 * ossia la porzione di trainingSet nell'intervallo compreso tra gli indici beginExampleIndex 
	 * e endExampleIndex)
	 * 
	 * @param trainingSet training set complessivo
	 * @param beginExampleIndex indice superiore del sotto-insieme di training
	 * @param endExampleIndex indice inferiore del sotto-insieme di training
	 */
	 LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex) {
		super(trainingSet, beginExampleIndex, endExampleIndex);
		
		Double avg = 0.0;
		int cont = 0;
		for (int i = getBeginExampleIndex(); i <= getEndExampleIndex(); i++) {
			avg += trainingSet.getClassValue(i);
			cont++;
		}
		predictedClassValue = avg/cont;
		
	}

		/**
		 * @return Numero di split originati dal nodo foglia, ovvero 0
		 */
	 public int getNumberOfChildren() {
		return 0;
	}
	/**
	 * 
	 * @return Valore dell'attributo di classe espresso nella foglia corrente, ovvero il valore di predictClassValue
	 */
	public Double getPredictedClassValue() {
		return predictedClassValue;
	}
	
	/**
	 * @return descrizione testuale dell'oggetto
	 */
	public String toString() {
		String leaf = "LEAF: Class="+ predictedClassValue +" "+ super.toString() ;
		return leaf;
	}
}
