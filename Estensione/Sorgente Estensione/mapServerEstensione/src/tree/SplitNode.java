package tree;

import java.io.Serializable;
import java.util.List;

import data.*;

/**
 * Classe astratta che estende la superclasse Node per modellare l'astrazione dell'entità 
 * nodo di split (continuo o discreto).
 * Attravero l'implementazione dell'interfaccia Serializable
 * gli oggetti di questa classe potranno essere serializzati
 * Implementa l'interfaccia Comparable per usufruire del metodo compareTo
 */

public abstract class SplitNode extends Node implements Comparable<SplitNode>, Serializable {
	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;

	/**
	 * Inner class di SplitNode che colleziona informazioni descrittive del nodo di split
	 * Implementa l'interfaccia Serializable
	 */
	 class SplitInfo implements Serializable{
		
		/**
		 * ID necessario per serializzare gli oggetti di questa classe.
		 */	
		private static final long serialVersionUID = 1L;
		/**
		 * Attributo indipendente che definisce uno split
		 */	
		private Object splitValue;
		
		/**
		 * Indice del primo esempio del training set da considerare
		 */
		private int beginIndex;
		/**
		 * Indice dell'ultimo esempio del training set da considerare
		 */
		private int endIndex;
		/**
		 * numero di split (nodi figli) generati dal nodo corrente
		 */
		 private int numberChild;
		/**
		 * tipo di comparatore da utilizzare, per i valori discreti è "="
		 */
	     private String comparator = "=";
		
		/**
		 * Costruttore di classe che avvalora gli attributi di classe per split a valori discreti
		 * @param splitValue valore di split
		 * @param beginIndex indice iniziale
		 * @param endIndex indice finale
		 * @param numberChild numero di nodi figli
		 */

		SplitInfo(Object splitValue, int beginIndex, int endIndex, int numberChild) {
			this.splitValue = splitValue;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
			this.numberChild = numberChild;
		}

		/**
		 * Costruttore di classe che avvalora gli attributi di classe per split a valori continui
		 * @param splitValue valore di split
		 * @param beginIndex indice iniziale
		 * @param endIndex indice finale
		 * @param numberChild numero di nodi figli
		 * @param comparator tipo di comparatore utilizzato
		 */

		SplitInfo(Object splitValue, int beginIndex, int endIndex, int numberChild, String comparator) {
			this.splitValue = splitValue;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
			this.numberChild = numberChild;
			this.comparator = comparator;
		}

		/**
		 * 
		 * @return indice del primo esempio del training set da considerare
		 */
		int getBeginindex() {
			return beginIndex;
		}
		/**
		 * 
		 * @return indice dell'ultimo esempio del training set da considerare
		 */
		int getEndIndex() {
			return endIndex;
		}
    
		/**
		 * 
		 * @return attributo indipendente che definisce uno split
		 */
		Object getSplitValue() {
			return splitValue;
		}

		/** 
		 * @return Descrizione testuale dell'oggetto
		 */
		public String toString() {
			return "child " + numberChild + " split value" + comparator + splitValue + "[Examples:" + beginIndex + "-"
					+ endIndex + "]";
		}

		/**
		 * 
		 * @return Il tipo di comparatore da utilizzare tra due nodi
		 */
		 String getComparator() {
			return comparator;
		}

	}
	
	/**
	 * Attributo indipendente sul quale lo split è generato	
	 */
	private Attribute attribute; 
	
	/**
	 * Lista in cui vengono memorizzati gli split candidati
	 */
	 List<SplitInfo> mapSplit; 

	/**
	 * Varianza ottenuta dopo partizionamento indotto dallo split corrente
	 */
	private double splitVariance;

	/**
	 * Metodo astratto che genera le informazioni necessarie per 
	 * ciascuno degli split candidati presenti nell'array mapSplit 
	 * @param trainingSet training set complessivo
	 * @param beginExampelIndex indice del primo esempio da considerare
	 * @param endExampleIndex indice dell'ultimo esempio da considerare
	 * @param attribute attributo indipendente su cui lavorare
	 */
	abstract void setSplitInfo(Data trainingSet, int beginExampelIndex, int endExampleIndex, Attribute attribute);

	/**
	 * metodo abstract per modellare la condizione di test 
	 * (ad ogni valore di test c'è un ramo dallo split)
	 * @param value valore dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return l'indice della posizione nell’array mapSplit con cui il test è positivo
	 */
	abstract int testCondition(Object value);

	/**
	 * Invoca il costruttore della superclasse, ordina i valori dell'attributo
	 * di input per gli esempi compresi tra beginExampleIndex ed endExampleIndex e sfrutta
	 * questo ordinamento per determinare i possibili split e popolare 
	 * il training set. Infine computa la varianza per l'attributo 
	 * usato nello split sulla base del partizionamento indotto dallo split
	 * @param trainingSet training set da utilizzare
	 * @param beginExampleIndex indice del primo esempio da considerare
	 * @param endExampleIndex indice dell'ultimo esempio da considerare
	 * @param attribute attributo su cui lavorare
	 */
	SplitNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
		super(trainingSet, beginExampleIndex, endExampleIndex);
		this.attribute = attribute;

		trainingSet.sort(attribute, beginExampleIndex, endExampleIndex); // order by attribute

		this.setSplitInfo(trainingSet, beginExampleIndex, endExampleIndex, attribute);

		// compute variance
		splitVariance = 0;
		for (int i = 0; i <mapSplit.size(); i++) {
			double localVariance = new LeafNode(trainingSet, mapSplit.get(i).getBeginindex(), mapSplit.get(i).getEndIndex())
					.getVariance();
			splitVariance += (localVariance);
		}
	}
	
	/**
	 * @return attributo indipendente sul quale lo split è generato	
	 */

	Attribute getAttribute() {
		return attribute;
	}
	
	/**
	 * @return restituisce l'information gain per lo split corrente
	 */

	double getVariance() {
		return splitVariance;
	}

	/**
	 * @return numero dei rami originanti nel nodo corrente
	 */
	public int getNumberOfChildren() {
		return mapSplit.size();
	}

	/**
	 * 
	 * @param child indice del nodo figlio
	 * @return informazioni sul nodo figlio con indice child
	 */
	SplitInfo getSplitInfo(int child) {
		return mapSplit.get(child);
	}

	/**
	 * 
	 * @return stringa che concatena le informazioni di ciascun test, 
	 * necessaria per predire nuovi esempi
	 */
	public String formulateQuery() {
		String query = "";
		for (int i = 0; i < mapSplit.size(); i++)
			query += (i + ":" + attribute + mapSplit.get(i).getComparator() + mapSplit.get(i).getSplitValue()) + "\n";
		return query;
	}

	/**
	 * @return descrizione testuale dell'oggetto
	 */
	public String toString() {
		String v = "SPLIT : attribute=" + attribute + " " + super.toString() + "\n Split Variance: " + getVariance()
				+ "\n";

		for (int i = 0; i < mapSplit.size(); i++) {
			v += "\t" + mapSplit.get(i) + "\n";
		}

		return v;
	}
	
	/**
	 * Confronta i valori di splitVariance dei due nodi 
	 * @return esito del confronto 
	 */
	public int compareTo(SplitNode s) {
		if(this.splitVariance==s.getVariance()) {
			return 0;
			}else if(this.splitVariance<s.getVariance()) {
				return -1;
			}else {
				return 1;	
			}
	}
}