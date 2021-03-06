package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.text.Text;
import mainClient.MainClientExtension;

public class PredictedClassController  implements Initializable {
	
	/**
	 * Oggetto utilizzato per creare le pagine dell'interfaccia 
	 */
	private CreatePage page = new CreatePage();
	
	/**
	 * Elemento dell'interfaccia.Se utilizzato consente all'utente di tornare 
	 * al menu e ripetere la predizione.
	 */
	@FXML
	private Button continueBtn;
	
	/**
	 * Elemento dell'interfaccia. Se utilizzato consente all'utente di chiudere 
	 * l'applicazione.
	 */
	@FXML
	private Button exitBtn;
	
	/**
	 * Elemento dell'interfaccia. Mostra all'utente il risultato della predizione.
	 */
	@FXML
	private Text pred;
	

	/**
	 * Metodo che permette di tornare alla pagina di menu.
	 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
	 * In questo caso si tratta del cliccare il Button continueBtn.
	 */
	@FXML 
	public void continueButton(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide();
		page.createNewPage(599, 433, "Menu", getClass().getResource("/fxml_files/Menu.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());
		
		
	}
	
	/**
	 * Metodo che permette di chiudere l'applicazione. 
	 * @param event,evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
	 * In questo caso si tratta del cliccare il Button exitBtn.
	 */
	@FXML 
	public void exitButton(ActionEvent event){
		System.exit(0);
	}

	/**
	 * Metodo chiamato per inizializzare la classe controller dopo che 
	 * i suoi elementi sono stati processati completamente.Inoltre legge  da server il risultato della predizione e 
	 * lo mostra all'utente. 
	 * @param location,usata per identificare il percorso relativo alla root dell'oggetto, null se non si conosce 
	 * @param resources, usato per localizzare la root delll'oggetto, null se non ? localizzata
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			pred.setText(MainClientExtension.receiveFromServer());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}
