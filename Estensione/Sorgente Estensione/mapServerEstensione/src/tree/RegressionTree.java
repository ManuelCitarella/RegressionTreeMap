package tree;

import java.util.TreeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import data.*;

/**
 * Questa classe modella un intero albero di decisione, come una radice e 
 * un insieme di sottoalberi.
 * Attravero l'implementazione dell'interfaccia Serializable
 * gli oggetti di questa classe potranno essere serializzati
 *  
 */

public class RegressionTree  implements Serializable {

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */	
	private static final long serialVersionUID = 1L;
	/**
	 * Radice del sottoalbero corrente
	 */
	private Node root;
	/**
	 * Array di sottoalberi originati nel nodo root. Per ogni figlio del nodo vi è un elemento dell'array
	 */
	private RegressionTree childTree[];

	/**
	 * Costruttore che istanzia un sotto-albero vuoto
	 */
	private RegressionTree() {}
	
	/**
	 * Costruttore che istanzia un sottoalbero basandosi sull'intero trainingSet in input.
	 * Inoltre avvia l'induzione dell'albero dagli esempi del trainingSet dato in input.
	 * @param trainingSet training set di base da cui istanziare il sottoalbero
	 */
	public RegressionTree(Data trainingSet) {

		learnTree(trainingSet, 0, trainingSet.getNumberOfExamples() - 1, trainingSet.getNumberOfExamples() * 10 / 100);
	}

	/**
	 * @return radice del regression tree corrente
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * @param pos indice della posizione del sottoalbero che si vuole ottenere
	 * @return sottoalbero del regression tree nella posizione pos specificata
	 */
	public RegressionTree getChildTree(int pos) {
		return childTree[pos];
	}
	
	/**
	 * Metodo che verifica se il sotto-insieme corrente può essere coperto da un 
	 * nodo foglia controllando che il numero di esempi del training set 
	 * compresi tra begin ed end sia minore uguale di numberOfExamplesPerLeaf
	 * @param trainingSet training set complessivo
	 * @param begin indice superiore del sottoinsieme di training
	 * @param end indice inferiore del sotto-insieme di training
	 * @param numberOfExamplesPerLeaf numero minimo di esempi che una foglia deve contenere
	 * @return esito sulle condizioni per i nodi fogliari 
	 */
	private boolean isLeaf(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
		int diff = end - begin + 1;
		if (diff <= numberOfExamplesPerLeaf) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * Questo metodo per ciascun attributo indipendente istanzia il DiscreteNode
	 * associato e seleziona il nodo di split con minore varianza tra i
	 * DiscreteNode istanziati. 
	 * Inoltre ordina la porzione di trainingSet corrente
	 * definita tra gli indici begin ed end
	 * rispetto all’attributo indipendente del nodo di split
	 * selezionato.
	 * @param trainingSet training set complessivo 
	 * @param begin indice superiore del sotto-insieme di training
	 * @param end indice inferiore del sotto-insieme di training
	 * @return nodo di split migliore per il sottoinsieme di training 
	 */

	private SplitNode determineBestSplitNode(Data trainingSet, int begin, int end) {
		
		TreeSet<SplitNode> ts = new TreeSet<SplitNode>();
		
		
		for (int i = 0; i < trainingSet.getNumberOfExplanatoryAttributes(); i++) {
			Attribute a = trainingSet.getExplanatoryAttribute(i);
		   try {	
			if(a instanceof DiscreteAttribute){
				ts.add(new DiscreteNode(trainingSet,begin,end,(DiscreteAttribute)a));
				}else {
					ts.add(new ContinuousNode(trainingSet,begin,end,(ContinuousAttribute)a));
				}
			
		
		}catch(NullPointerException e) {}
		}
			//intercettare eccezione dello splitnode non costruito
		trainingSet.sort(ts.first().getAttribute(), begin, end);

		return ts.first();
	}
	
	
	/**
	 * Metodo che genera un sottoalbero con il sottoinsieme di input istanziando un
	 * nodo fogliare dato da isLeaf() o un nodo di split. 
	 * In tal caso determina il miglior nodo rispetto al sottoinsieme 
	 * di input (determineBestSplitNode()), ed a tale nodo associa 
	 * un sottoalbero avente radice il nodo medesimo definito da root e avente un
	 * numero di rami pari al numero dei figli determinati dallo split.
	 * Ricorsivamente per ogni oggetto DecisionTree nell'array childTree[] sarà re-invocato il metodo
	 * learnTree() per l'apprendimento su un insieme ridotto del sotto-insieme attuale compreso nell'intervallo tra gli indici begin ed end.
	 * Nella condizione in cui il nodo di split non origina figli, il nodo diventa fogliare.
	 * @param trainingSet training set complessivo 
	 * @param begin indice superiore del sotto-insieme di training
	 * @param end indice inferiore del sotto-insieme di training
	 * @param numberOfExamplesPerLeaf numero di esempi massimo che una foglia deve contenere
	 */
	
	private void learnTree(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
		if (isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)) {
			// determina la classe che compare più frequentemente nella partizione corrente
			root = new LeafNode(trainingSet, begin, end);
		} else // split node
		{
			root = determineBestSplitNode(trainingSet, begin, end);

			if (root.getNumberOfChildren() > 1) {
				childTree = new RegressionTree[root.getNumberOfChildren()];
				for (int i = 0; i < root.getNumberOfChildren(); i++) {
					childTree[i] = new RegressionTree();
					childTree[i].learnTree(trainingSet, ((SplitNode) root).getSplitInfo(i).getBeginindex(),
							((SplitNode) root).getSplitInfo(i).getEndIndex(), numberOfExamplesPerLeaf);
				}
			} else
				root = new LeafNode(trainingSet, begin, end);

		}
	}
	
	/**
	 * @return descrizione testuale dell'oggetto
	 */
	
	public String toString() {
		String tree = root.toString() + "\n";

		if (root instanceof LeafNode) {

		} else // split node
		{
			for (int i = 0; i < childTree.length; i++)
				tree += childTree[i];
		}
		return tree;
	}
	
	/**
	 * Metodo che restituisce la stringa che contiene il regression tree corrente
	 * @return stringa 'tree' concatenata che rappresenta l'albero di regressione
	 */
	public String printTree() {
		String tree="";
		tree= tree + toString();
		return tree;
	}

	/** 
	 * Metodo che scandisce ciascun ramo dell'albero completo dalla radice alla foglia concatenando le informazioni 
	 * dei nodi di split fino al nodo foglia. In particolare per ogni sotto-albero (oggetto DecisionTree) nell'array childTree 
	 * concatena le informazioni del nodo root: se è di split discende ricorsivamente l'albero per ottenere le informazioni del nodo sottostante
	 * (necessario per ricostruire le condizioni in AND) di ogni ramo-regola, 
	 * se è di foglia (leaf) termina l'attraversamento inviando al server la regola. 
	 * @param out, stream di output usato dal server per comunicare col client
	 */
	public void printRules(ObjectOutputStream out) {
	      try {
			printRules(" ",out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    }
	    
	    
	   
	/**
	 * Questo metodo supporta il metodo public void printRules(), e concatena alle
	 * informazioni del precedente nodo quelle del nodo root del corrente 
	 * sottoalbero: se il nodo corrente è di split il metodo
	 * viene invocato ricorsivamente con current e le informazioni del nodo corrente,
	 * se è fogliare (leaf) invia al client tutte le informazioni concatenate. 
	 * Come parametro abbiamo le informazioni del nodo di split ovvero:
	 * @param current nodo corrente
	 * @param out, stream di output usato dal server per comunicare col client
	 * @throws IOException , lanciata dallo stream di output in caso di errore
	 */
	
	 private  void printRules(String current, ObjectOutputStream out) throws IOException { 
	      String str ="";
	      SplitNode node=(SplitNode)root;
	      for(int i=0;i<childTree.length;i++) {
	       str=current+node.getAttribute().getName()+node.getSplitInfo(i).getComparator()+node.getSplitInfo(i).getSplitValue();
	        if(childTree[i].root instanceof SplitNode) {
	          str = str + " AND "; 
	         
	          childTree[i].printRules(str,out);
	        }else if( childTree[i].root instanceof LeafNode) {
	          str=str+ " ==> Class " +((LeafNode)childTree[i].root).getPredictedClassValue()+" \n";
	          out.writeObject(str);
	        }
	       
	      }
	      
	     
	  }
	    
	  /**
		 * Questo metodo permette di serializzare l'albero in un file
		 * @param nomeFile nome del file in cui salvare l'albero 
		 * @throws IOException lanciata nel momento in cui vi e' un errore di comunicazione col client
		 * (possibili errori mentre si scrive sul file)
		 */
	  public void salva(String nomeFile) throws  IOException {
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeFile));
			out.writeObject(this);//writeObject viene invocato per serializzare l'oggetto e si occuperà di salvare attributo per attributo(metodo della classe ObjectOutputStream)
			out.writeObject(root);
			out.writeObject(childTree);
			out.close();
		}
	  
		/**
		 * Carica un albero di regressione salvato in un file
		 * @param nomeFile nome del file in cui è salvato l'albero da caricare
		 * @return l'albero di regressione contenuto nel file
		 * @throws FileNotFoundException se il file da cui si vuole caricare l'albero di regressione e' inesistente
		 * @throws IOException lanciata nel momento in cui vi e' un errore di comunicazione col client (lettura del file)
		 * @throws ClassNotFoundException se non viene trovata la classe desiderata
		 */
		public static RegressionTree carica(String nomeFile) throws  FileNotFoundException,IOException, ClassNotFoundException {//IO perhcè potrebbero verificarsi problemi nella lettura del file 
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
			RegressionTree n=(RegressionTree)in.readObject(); //readObject viene invocato per deserializzare l'oggetto(metodo della classe ObjectInputStream)
			in.close();
			return n;
		}
}
