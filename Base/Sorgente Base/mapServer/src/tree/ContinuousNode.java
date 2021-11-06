package tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.Attribute;
//import data.ContinuousAttribute;
import data.Data;

/**
 * 
 * Classe che estende la classe astratta SplitNode, ereditando ed implementando metodi ed attributi.
 * Modella i nodi corrispondenti agli attributi di tipo continuo
 * Attravero l'implementazione dell'interfaccia Serializable
 * gli oggetti di questa classe potranno essere serializzati
 *
 */

 class ContinuousNode extends SplitNode implements Serializable{

	
	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore di classe che richiama il costruttore della superclasse Node
	 * @param trainingSet training set da utilizzare
	 * @param beginExampleIndex indice del primo esempio 
	 * @param endExampleIndex indice dell'ultimo esempio 
	 * @param attribute attributo su cui lavorare
	 */
	 ContinuousNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
		super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
		
	}
		/**
		 * Metodo che istanzia oggetti di classe SplitInfo con 
		 * ciascuno dei valori dell’attributo relativamente al sotto-insieme di training
		 * corrente (ossia la porzione di trainingSet compresa tra beginExampleIndex e 
		 * endExampleIndex), quindi popola il training set con tali oggetti. 
	     * @param trainingSet training set da utilizzare
		 * @param beginExampleIndex indice del primo esempio 
		 * @param endExampleIndex indice dell'ultimo esempio 
		 * @param attribute attributo su cui lavorare
		 */
	void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute){
			//Update mapSplit defined in SplitNode -- contiene gli indici del partizionamento
			Double currentSplitValue= (Double)trainingSet.getExplanatoryValue(beginExampleIndex,attribute.getIndex());
			double bestInfoVariance=0;
			List <SplitInfo> bestMapSplit=null;
			
			for(int i=beginExampleIndex+1;i<=endExampleIndex;i++){
				Double value=(Double)trainingSet.getExplanatoryValue(i,attribute.getIndex());
				if(value.doubleValue()!=currentSplitValue.doubleValue()){
				//	System.out.print(currentSplitValue +" var ");
					double localVariance=new LeafNode(trainingSet, beginExampleIndex,i-1).getVariance();
					double candidateSplitVariance=localVariance;
					localVariance=new LeafNode(trainingSet, i,endExampleIndex).getVariance();
					candidateSplitVariance+=localVariance;
					//System.out.println(candidateSplitVariance);
					if(bestMapSplit==null){
						bestMapSplit=new ArrayList<SplitInfo>();
						bestMapSplit.add(new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
						bestMapSplit.add(new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
						bestInfoVariance=candidateSplitVariance;
					}
					else{		
												
						if(candidateSplitVariance<bestInfoVariance){
							bestInfoVariance=candidateSplitVariance;
							bestMapSplit.set(0, new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
							bestMapSplit.set(1, new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
						}
					}
					currentSplitValue=value;
				}
			}
			mapSplit=bestMapSplit;
			//rimuovo split inutili (che includono tutti gli esempi nella stessa partizione)
			
			if((mapSplit.get(1).getBeginindex()==mapSplit.get(1).getEndIndex())){
				mapSplit.remove(1);
				
			}
			
	 }

 /**
	 * Metodo per modellare la condizione di test 
	 * (ad ogni valore di test c'è un ramo dallo split)
	 * @param value valore dell'attributo che si vuole testare rispetto a tutti gli split
	 */

	
	int testCondition(Object value) {
		//confrontare i vari figli per capire se andare sul nodo sx o dx confrontato tramite<= (quelli maggiori vanno a destra
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
		String cont= "CONTINUOUS ";
		cont = cont + super.toString();
		return cont;
	}





}
