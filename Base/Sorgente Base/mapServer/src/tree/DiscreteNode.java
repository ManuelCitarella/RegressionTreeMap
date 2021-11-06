package tree;

import java.io.Serializable;
import java.util.ArrayList;

import data.*;

/**
 * Classe che estende SplitNode per modellare l'entità nodo corrispondente ad un attributo indipendente discreto.
 * Attravero l'implementazione dell'interfaccia Serializable
 * gli oggetti di questa classe potranno essere serializzati
 */

 class DiscreteNode extends SplitNode implements Serializable{

	 
		/**
		 * ID necessario per serializzare gli oggetti di questa classe.
		 */	
		private static final long serialVersionUID = 1L;
		
		/**
		 * Costruttore di classe, istanzia un oggetto invocando il costruttore della superclasse SplitNode
		 * @param trainingSet training set 
		 * @param beginExampleIndex indice superiore del sotto-insieme di training
		 * @param endExampleIndex indice inferiore del sotto-insieme di training
		 * @param discreteAttribute attributo indipendente sul quale si definisce lo split 
		 */
    DiscreteNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, DiscreteAttribute discreteAttribute) {
		super(trainingSet, beginExampleIndex, endExampleIndex, discreteAttribute);
	}

	/**
	 * Istanzia oggetti SplitInfo con ciascuno dei valori discreti dell’attributo relativamente 
	 * al sotto-insieme di training corrente compreso tra beginExampleIndex ed endExampleIndex, quindi 
	 * popola mapSplit con tali oggetti.  
	 * @param trainingSet training set complessivo
	 * @param beginExampleIndex indice superiore del sotto-insieme di training
	 * @param endExampleIndex indice inferiore del sotto-insieme di training
	 * @param attribute attributo indipendente sul quale si definisce lo split 
	 */
	void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute){
		
		mapSplit= new ArrayList<SplitInfo>();
		int j=0;
		int begin=beginExampleIndex;//indica inizio dello split
		int end=0;//indica fine dello split
		Object current=trainingSet.getExplanatoryValue(beginExampleIndex, attribute.getIndex());
		int i;
		for(i = beginExampleIndex; i <=endExampleIndex ; i++) {
			if(trainingSet.getExplanatoryValue(i, attribute.getIndex()).equals(current)==false){
				end=i-1;
				
				mapSplit.add(new SplitInfo(current,begin,end,j));
				begin=i;
				current=trainingSet.getExplanatoryValue(i, attribute.getIndex());
				j++;
			}
		}
		end=endExampleIndex;
		mapSplit.add(new SplitInfo(current,begin,end,j));
		
	}

	/**
	 * Metodo che effettua il confronto del valore in input rispetto al valore contenuto 
	 * nell’attributo splitValue di ciascuno degli oggetti SplitInfo collezionati 
	 * nell'array mapSplit e restituisce l'indice dello split,
	 * ovvero l'indice della posizione nell’array mapSplit con cui il test è positivo
	 * @param value  discreto dell'attributo che si vuole testare rispetto a tutti gli split
	 */
	int testCondition(Object value) {
      
		int i;
		for (i = 0; i < getNumberOfChildren() ; i++) {
			if ( value.equals(mapSplit.get(i).getSplitValue())) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * @return descrizione testuale dell'oggetto
	 */
	public String toString() {
		String discrete= "DISCRETE ";
		discrete = discrete + super.toString();
		return discrete;
	}

}
