package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import mainClient.MainClientExtension;


/**
 * Classe controller che si occupa di gestile le funzionalit? 
 * della pagina che mostra all'utente le regole dell'albero di regressione.
 * Implementa l'interfaccia Initializable. 
 *
 */
public class RulesController implements Initializable {
	
	/**
	 * Oggetto utilizzato per creare le pagine dell'interfaccia 
	 */
	private CreatePage page = new CreatePage();
	
	
	/**
	 * Elemento dell'interfaccia. Componente di immissione del testo che consente di inserire
	 * pi? righe di testo. In questa applicazione ? non modificabile e mostra le regole dell'albero.
	 */
	@FXML
	private TextArea rulestxt;
	
	/**
	 * Elemento dell'interfaccia. Se l'utente lo utilizza permette 
	 * di tornare indetro alla pagina di Menu.
	 */
	@FXML
	private Button backBtn;
	
	
	

	/**
	 * Metodo chiamato per inizializzare la classe controller dopo che 
	 * i suoi elementi sono stati processati completamente.Inoltre legge da server le regole
	 * e le mostra all'utente. 
	 * @param arg0,usata per identificare il percorso relativo alla root dell'oggetto, null se non si conosce.
	 * @param arg1, usato per localizzare la root delll'oggetto, null se non ? localizzata.
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String serveranw="";
		String regole="";
		try {
			serveranw =MainClientExtension.receiveFromServer();
			while (!serveranw.equals("END")) {
				regole += serveranw + "\n";
				serveranw = MainClientExtension.receiveFromServer();
			}
			
			rulestxt.setText(regole);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
  /**
   * Metodo che si occupa di chiudere la pagina corrente e riaprire la 
   * pagina relativa al Menu.
   * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente,
   *In questo caso si tratta di cliccare il Button backBtn.
  
   */
	@FXML
	public void backButton(ActionEvent event){
		
		((Node)event.getSource()).getScene().getWindow().hide();
		page.createNewPage(599, 433, "Menu", getClass().getResource("/fxml_files/Menu.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());

	}


	
}
