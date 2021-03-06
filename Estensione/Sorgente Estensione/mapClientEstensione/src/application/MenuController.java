package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import mainClient.MainClientExtension;


/**
 * Classe controller che si occupa di gestire le funzionalitą 
 * del menu dell'applicazione.
 *
 */

public class MenuController {
		
		/**
		 * Oggetto utilizzato per creare le pagine dell'interfaccia 
		 */
		private CreatePage page = new CreatePage();
	
		
		/**
		 * Elemento dell'interfaccia. Se l'utente lo utilizza  apre la pagina 
		 * dove vengono mostrate le regole dell'albero di regressione derivato. 
		 */
		@FXML
		private Button rulesBtn;
		
		/**
		 * Elemento dell'interfaccia. Se l'utente lo utilizza apre la pagina 
		 * dove viene mostrato l'albero di regressione derivato. 
		 */
		@FXML
		private Button regBtn;
		
		/**
		 * Elemento dell'interfaccia. Se l'utente lo utilizza apre la pagina 
		 *per la fare di predizione dall'albero.
		 */
		@FXML
		private Button predBtn;
		
		
		/**
		 * Metodo che si occupa di aprire la pagina che mostra all'utente le regole dell'albero di regressione.
		 * @param event, evento che consiste nel compiere un'azione sull'interfaccia da parte dell'utente.
		 * In questo caso si tratta del cliccare il Button rulesBtn.

		 */
		@FXML
	public void rulesButton(ActionEvent event) {
			MainClientExtension.writeToServer(5);
		((Node)event.getSource()).getScene().getWindow().hide();
		page.createNewPage(599, 433, "Rules", getClass().getResource("/fxml_files/Rules.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());
	
		}
	
		/**
		 * Metodo che si occupa di aprire la pagina che mostra all'utente l'albero di regressione.
		 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
		 * In questo caso si tratta del cliccare il Button rulesBtn.
		 */
		
		@FXML
	public void regButton(ActionEvent event){
			MainClientExtension.writeToServer(4);
		((Node)event.getSource()).getScene().getWindow().hide();
		
		page.createNewPage(599, 433, "Regression Tree", getClass().getResource("/fxml_files/RegressionTree.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());
		
		}
	
		
		/**
		 * Metodo che si occupa  di aprire la pagina che permette all'utente di effettuare delle
		 * predizioni sull'albero di regressione.
		 * @param event, evento che consiste nel compiere un'azione sulle interfaccia da parte dell'utente.
		 * In questo caso si tratta del cliccare il Button predBtn.
		 */
		
		@FXML
	public void predButton(ActionEvent event) {
			MainClientExtension.writeToServer(6);
		((Node)event.getSource()).getScene().getWindow().hide();
		page.createNewPage(599, 433, "Prediction Phase", getClass().getResource("/fxml_files/Prediction.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());
		
	}
}




