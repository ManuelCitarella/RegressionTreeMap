package data;
import java.util.*;


import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.TableData;
import database.TableSchema;


import java.sql.SQLException;


/**
 * La classe Data ha l'obiettivo di modellare 
 * l'insieme di esempi del training set
 */
public class Data {
    
	/**
	 * Training set contenente gli esempi 
	 */
	private List<Example> data=new ArrayList<Example>(); //lista che contiene il training set
	
	/**
	 * Numero di esempi del training set
	 */
	private int numberOfExamples; // numero di esempi nella matrice
	
	/**
	 * Lista degli attributi indipendenti del training set.
	 * Gli oggetti di questa lista sono di tipo Attribute perchè possono essere sia discreti che continui
	 */
	private List<Attribute> explanatorySet; // colonne del training set, ossia gli attributi indipendenti
	
	/**
	 * Attributo che indica il dato da prevedere, che per definizione è per forza continuo (numerico)
	 */
	private ContinuousAttribute classAttribute; // dato da prevedere
	
	/**
	 * Costruttore del Training Set a partire da una tabella di un database specifico.
	 * @param tableName nome della tabella da analizzare
	 * @throws TrainingDataException eccezione che gestisce tutte le possibili cause
	 * di errore nella fase di apprendimento
	 */

	public Data(String tableName) throws TrainingDataException {

			DbAccess db= new DbAccess();
			try {
				db.initConnection();
			} catch (DatabaseConnectionException e) {
				throw new TrainingDataException("Database connection failed!");
			}
			
			TableSchema ts;
			try {
				ts= new TableSchema(db,tableName);
				
			}catch(SQLException e){
				throw new TrainingDataException("The table doesn't exists. Try again!");
			}
		
			if(ts.getNumberOfAttributes()<2)
				throw new TrainingDataException("The table has less than 2 columns.Try again!");
			
			
			TableData td = new TableData(db);
			try {
				data= td.getTransazioni(tableName);
			} catch (EmptySetException | SQLException e) {
				throw new TrainingDataException("The table has 0 rows. Try again!");
			}
			
			numberOfExamples=data.size();	
			explanatorySet = new LinkedList<Attribute>();
			
			int i=0;
			for( i=0; i<ts.getNumberOfAttributes()-1; i++) {
				
				if(ts.getColumn(i).isNumber()) {
					explanatorySet.add(new ContinuousAttribute(ts.getColumn(i).getColumnName(), i));
				}else {
					Set<String> discreteValues= new TreeSet<String>();
					try {
						for(Object o : td.getDistinctColumnValues(tableName, ts.getColumn(i))) 
							 discreteValues.add((String)o);
						  
					} catch (SQLException e) {
						throw new TrainingDataException("Error in the reception of distinct values. Try again!");
					}catch (NullPointerException e) {
						throw new TrainingDataException("Found null value in a row. Try again!");
					}
					
					explanatorySet.add(new DiscreteAttribute(ts.getColumn(i).getColumnName(), i,discreteValues));
				}
					
			}
			
			if(ts.getColumn(ts.getNumberOfAttributes()-1).isNumber()) {
				classAttribute = new ContinuousAttribute(ts.getColumn(i).getColumnName(), i);
			}else {
				throw new TrainingDataException("The last column is not a continuous value. Try again!");
			}
			
			try {
				db.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}

	/**
	 * @return cardinalità del training set, ovvero il numero di esempi
	 */
	public int getNumberOfExamples() { // numero di esempi
		return numberOfExamples;
	}

	/** 
	 * @return cardinalità dell'insieme degli attributi indipendenti
	 */
	
	public int getNumberOfExplanatoryAttributes() { // numero di attributi
		return explanatorySet.size();
	}

	/**
	 * @param exampleIndex indice dell'esempio di cui si vuole predire il valore
	 * @return valore dell'attributo di classe per l'esempio indicizzato da exampleIndex
	 */
	
	public Double getClassValue(int exampleIndex) { // dato l'esempio, restituire il valore da predire
		return (Double) data.get(exampleIndex).get(getNumberOfExplanatoryAttributes());
		
	}

	/**
	 * @param exampleIndex indice dell'esempio
	 * @param attributeIndex indice dell'attributo
	 * @return oggetto relativo all'attributo indipendente
	 * dell'insieme di attributi indipendenti indicizzato da attributeIndex per l'esempio indicizzato da exampleIndex
	 */
	
	public Object getExplanatoryValue(int exampleIndex, int attributeIndex) {// restituire l'attributo in colonna attributeIndex per la riga exampleIndex																
		return data.get(exampleIndex).get(attributeIndex);
	}

	
	/**
	 * @param index indice dell'attributo indipedente desiderato
	 * @return oggetto di tipo Attribute indicizzato da index 
	 */
	
	public Attribute getExplanatoryAttribute(int index) {// attributo in posizione index
		return explanatorySet.get(index);
	}

	/**Restituisce l'oggetto corrispondente all'attributo di classe
	 * @return oggetto di tipo ContinuousAttribute associato al membro classAttribute
	 */
	
     ContinuousAttribute getClassAttribute() {
		return classAttribute;
	}
     
     
   /**
    * @return descrizione testuale dell'oggetto
    */
	public String toString() {
		String value = "";
		for (int i = 0; i < numberOfExamples; i++) {
			for (int j = 0; j < explanatorySet.size(); j++)
				value += data.get(i).get(j) + ",";

			value += data.get(i).get(getNumberOfExplanatoryAttributes()) + "\n";
		}
		return value;

	}


	/**
	 * Ordina il training set di esempi compresi nell'intervallo [inf,sup] in base all'attributo attribute,
	 *  usando l'algoritmo di quicksort basato sullla relazione d'ordine totale 'minore o uguale'.
	 * @param attribute attributo secondo cui fare l'ordinamento
	 * @param beginExampleIndex limite inferiore per l'insieme di esempi (inf)
	 * @param endExampleIndex limite superiore per l'insieme di esempi (sup)
	 */
	public void sort(Attribute attribute, int beginExampleIndex, int endExampleIndex) {

		quicksort(attribute, beginExampleIndex, endExampleIndex);
	}

	
	/**
	 * Scambia l'esempio in riga i con l'esempio in riga j
	 * @param i indice di riga
	 * @param j indice di colonna
	 */
	private void swap(int i, int j) {
		Object temp;
		temp = data.get(j);
		data.set(j, data.get(i));
		data.set(i,(Example) temp);
		
	}

	/**
	 * Il metodo partition partiziona il vettore rispetto all'attributo discreto attribute
	 * e restituisce il punto di separazione.
	 * @param attribute attributo discreto secondo cui ordinare gli esempi
	 * @param inf limite inferiore per l'insieme di esempi 
	 * @param sup limite superiore per l'insieme di esempi
	 * @return indice del punto di separazione della partizione
	 */
	private int partition(DiscreteAttribute attribute, int inf, int sup) {
		int i, j;

		i = inf;
		j = sup;
		int med = (inf + sup) / 2;
		String x = (String) getExplanatoryValue(med, attribute.getIndex());
		swap(inf, med);

		while (true) {

			while (i <= sup && ((String) getExplanatoryValue(i, attribute.getIndex())).compareTo(x) <= 0) {
				i++;

			}

			while (((String) getExplanatoryValue(j, attribute.getIndex())).compareTo(x) > 0) {
				j--;

			}

			if (i < j) {
				swap(i, j);
			} else
				break;
		}
		swap(inf, j);
		return j;

	}

	
	/*
	 * Partiziona il vettore rispetto all'elemento x e restiutisce il punto di separazione
	 */
	
	/**
	 * Questo metodo partiziona il vettore rispetto all'attributo continuo attribute
	 * e restituisce il punto di separazione.
	 * @param attribute attributo continuo secondo cui ordinare gli esempi
	 * @param inf limite inferiore per l'insieme di esempi da considerare per l'ordinamento
	 * @param sup limite superiore per l'insieme di esempi da considerare per l'ordinamento
	 * @return indice del punto di separazione della partizione
	 */
	private  int partition(ContinuousAttribute attribute, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;
		Double x=(Double)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);
	
		while (true) 
		{
			
			while(i<=sup && ((Double)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){ 
				i++; 
				
			}
		
			while(((Double)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;
			
			}
			
			if(i<j) { 
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	
	
	/*
	 * Algoritmo quicksort per l'ordinamento di un array di interi A usando come
	 * relazione d'ordine totale "<="
	 * 
	 * @param A
	 */
	
	/**
	 * Ordina il training set seguendo l'algoritmo del quicksort, ovvero "minore uguale"
	 * @param attribute attributo secondo cui ordinare gli esempi
	 * @param inf limite inferiore per l'insieme di esempi 
	 * @param sup limite superiore per l'insieme di esempi 
	 */
	private void quicksort(Attribute attribute, int inf, int sup) {

		if(sup>=inf){
					
					int pos;
					if(attribute instanceof DiscreteAttribute)
						pos=partition((DiscreteAttribute)attribute, inf, sup);
					else
						pos=partition((ContinuousAttribute)attribute, inf, sup);
							
					if ((pos-inf) < (sup-pos+1)) {
						quicksort(attribute, inf, pos-1); 
						quicksort(attribute, pos+1,sup);
					}
					else
					{
						quicksort(attribute, pos+1, sup); 
						quicksort(attribute, inf, pos-1);
					}	
					
				}
	}

	
}
