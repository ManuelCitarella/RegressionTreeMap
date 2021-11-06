package database;

public class Column{
	
	/**
	 * nome della colonna
	 */
	private String name;
	
	/**
	 * tipo della colonna, discreto o continuo
	 * 
	 */
	private String type;

	/**
	 * Costruttore di classe, istanzia gli attributi name e type
	 * @param name nome della colonna
	 * @param type tipo della colonna, continuo o discreto
	 */
	Column(String name,String type){
		this.name=name;
		this.type=type;
	}
	
	/**
	 * 
	 * @return nome della colonna
	 */
	public String getColumnName(){
		return name;
	}
	
	/**
	 * 
	 * @return true se la colonna contiene valori numerici, false altrimenti
	 */
	
	public boolean isNumber(){
		return type.equals("number");
	}
	
	/**
	 *  @return descrizione testuale della colonna
	 */
	public String toString(){
		return name+":"+type;
	}
}