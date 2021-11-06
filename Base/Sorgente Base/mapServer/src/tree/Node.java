package tree;

import java.io.Serializable;

import data.Data;

/**
 * Classe che modella l'astrazione dell'entità nodo (fogliare o intermedio)
 * dell'albero di decisione. 
 * Attravero l'implementazione dell'interfaccia Serializable
 *  gli oggetti di questa classe potranno essere serializzati
 */
public abstract class Node implements Serializable{

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	/**
	 * Contatore dei nodi generati nell'albero
	 */
	private static int idNodeCount = 0;
	/**
	 * Identificativo numerico del nodo
	 */
	private int idNode;
	/**
	 * Indice nell'array del training set del primo esempio coperto dal nodo corrente
	 */
	private int beginExampleIndex;
	/**
	 * Indice nell'array del training set dell'ultimo esempio coperto dal nodo corrente
	 */
	private int endExampleIndex;
	/**
	 * Valore della varianza calcolata rispetto all'attributo di classe nel sottoinsieme di training del nodo
	 */
	private double variance;

	/**
	 * Costruttore di classe che avvalora gli attributi primitivi di classe,
	 * inclusa la varianza che viene calcolata rispetto all'attributo 
	 * da predire nel sotto-insieme di training 
	 * @param trainingSet training set complessivo
	 * @param beginExampleIndex indice del primo esempio da considerare
	 * @param endExampleIndex indice dell'ultimo esempio da considerare
	 */
	Node(Data trainingSet, int beginExampleIndex, int endExampleIndex) {

		this.beginExampleIndex = beginExampleIndex;
		this.endExampleIndex = endExampleIndex;
		this.idNode = idNodeCount++;

		double sum = 0;
		double sum2 = 0;

		for (int i = beginExampleIndex; i <= endExampleIndex; i++) {// somma tutto per poi fare al quadrato tutto
																	// insieme
			sum += trainingSet.getClassValue(i);
			sum2 += Math.pow(trainingSet.getClassValue(i), 2);
		}

		variance=(Math.pow(sum, 2) / ((endExampleIndex - beginExampleIndex) + 1));
		variance = sum2 -variance;
		
	}

    /**
     * Restituisce il valore del membro idNode
     * @return identificativo numerico del nodo
     */
	int getIdNode() {
		return idNode;
	}

	/**
	 * Restituisce il valore del membro beginExampleIndex
	 * @return indice del primo esempio del sotto-insieme rispetto al training set complessivo
	 */
	int getBeginExampleIndex() {
		return beginExampleIndex;
	}
    /**
     * Restituisce il valore del membro endExampleIndex
     * @return indice dell' ultimo esempio del sotto-insieme rispetto al training set complessivo
     */
	int getEndExampleIndex() {
		return endExampleIndex;
	}
   
	/**
	 * Restituisce il valore del membro variance
	 * @return valore della varianza dell’attributo da predire rispetto al nodo corrente 
	 */
	double getVariance() {
		return variance;
	}

    /**
     * E' un metodo astratto la cui implementazione riguarda i nodi di tipo test 
     * dai quali si possono generare figli, uno per ogni split prodotto. 
     * Restituisce il numero di tali nodi figli.
     * @return valore del numero di nodi sottostanti
     */
	public abstract int getNumberOfChildren();

	/**
	 * @return descrizione testuale dell'oggetto
	 */

	public String toString() {
		return "NODE:  [Examples: "+ beginExampleIndex +" - "+ endExampleIndex + " ]" + "  variance=" + variance;
	}

}
