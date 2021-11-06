package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import mainClient.MainClientExtension;
/**
 * Classe controller, gestisce la fase di predizione dall'albero di 
 * regressione.
 * Implementa l'interfaccia Initializable.
 * 
 *
 */
public class PredictionController  implements Initializable {
	
	/**
	 * Oggetto utilizzato per creare le pagine dell'interfaccia 
	 */
	private CreatePage page = new CreatePage();
	
/**
 * Attributo utile a richiamare il metodo initialize. 
 */
	private URL arg0;
	
	/**
	 * Attributo utile a richiamare il metodo initialize. 
	 */
	private ResourceBundle arg1;
	
	/**
	 * Lista di possibili rami che verrà mostrata nella ComboBox.
	 */
	private ObservableList<String> list = FXCollections.observableArrayList();
	
	
	/**
	 * Elemento dell'interfaccia. Mostra all'utente le predizioni fatte in precedenza.
	 */
	@FXML
	private Text resume;
	
	/**
	 * Elemento dell'interfaccia. Mostra all'utente una lista di possibilità che può selezionare. 
	 * In questo caso mostra i rami che si possono selezionare in una determinata
	 * fase della predizione. 
	 */
	@FXML
	private ComboBox<String> combo =new ComboBox<>();
	
	
	/**
	 * Elemento dell'interfaccia. Se viene utilizzato dall'utente permette di utilizzare
	 * per la predizione il ramo selezionato nella ComboBox. Se non viene selezionato nulla
	 * nella ComboBox, non si verifica nessuna azione. 
	 */
	@FXML
	private Button predictBtn;
	
	

	/**
	 * Metodo chiamato per inizializzare la classe controller dopo che 
	 * i suoi elementi sono stati processati completamente. Questo metodo comunica al server
	 * i rami selezionati dall'utente e se non ci si trova ad un nodo foglia, continua la predizione, altrimenti
	 * apre la pagina relativa alla comunicazione del valore predetto. 
	 * @param arg0 ,usata per identificare il percorso relativo alla root dell'oggetto, null se non si conosce.
	 * @param arg1 , usato per localizzare la root delll'oggetto, null se non è localizzata.
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String serveranw="";
		try {
			
			 serveranw=MainClientExtension.receiveFromServer();
		
			if(serveranw.equals("QUERY")){	
				// Formualting query, reading answer
				serveranw=MainClientExtension.receiveFromServer();
				String[] x = serveranw.split("\n");
				
				for(String s : x){
					list.add(s);
				}
				combo.setItems(list);
			
			}
			else if(serveranw.equals("OK"))
			{ 
				combo.getScene().getWindow().hide();
				page.createNewPage(320, 200, "Predicted Class", getClass().getResource("/fxml_files/Predicted.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());
	
			}
			
			} catch (IOException | ClassNotFoundException  e) {
				e.printStackTrace();
			}
		
			
		
	}
	
	
	/**
	 * Metodo che prende il ramo selezionato dall'utente nella Combobox, lo 
	 * mostra nella sezione di riepilogo delle scelte fatte precedentemente e invia
	 * al server l'informazione sul ramo scelto. 
	 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente,
		 * in questo caso si tratta di cliccare il Button predictBtn.
	 */
	@FXML 
	public void predictButton(ActionEvent event){
		String scelta="";
		int risposta;
		scelta= combo.getValue();
		if(scelta != null) {
			resume.setText(resume.getText()+  " \n" +combo.getValue());
			risposta = ((int)scelta.charAt(0))-48;
			MainClientExtension.writeToServer(risposta);
	    	list.clear();
	    	initialize(arg0,arg1);
		}
    	
	}
	
	
}
