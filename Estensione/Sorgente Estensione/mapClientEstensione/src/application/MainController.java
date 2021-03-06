package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import mainClient.MainClientExtension;

/**
 * Classe controller, che si occupa di gestire le funzionalit? 
 * della home page dell'applicazione.
 *
 */

public class MainController {
	
	/**
	 * Oggetto utilizzato per creare le pagine dell'interfaccia 
	 */
	private CreatePage page = new CreatePage();
	
	
	/**
	 * Elemento dell'interfaccia. Serve a comunicare 
	 * il messaggio di errore nella home page.
	 */
	@FXML
	private Label labelError;
	
	/**
	 * Elemento dell'interfaccia. Serve  a mostrare l'estensione
	 * del file da cui si vuole realizzare l'albero di regressione. 
	 */
	@FXML
	private Label estensione;
	
	/**
	 * Elemento dell'interfaccia. Componente di immissione del testo che consente all'
	 *  utente di inserire una singola riga di testo (nome del file/tabella).
	 */
	@FXML
	private TextField txtFile;

	/**
	 * Elemento dell'interfaccia. Se l'utente lo utilizza avvia la fase di 
	 * costruzione dell'albero a partire dal file/tabella indicata nella casella di testo e apre la pagina 
	 * di menu. 
	 * Se il file/tabella specificato non ? corretto viene mostrato un messaggio di errore
	 * nella home page. 
	 */
	@FXML
	private Button learnBtn;
	
	
	/**
	 * Elemento dell'interfaccia. Se selezionato indica la scelta dell'utente
	 * di voler derivare l'albero da una tabella. E' selezionato di default. 
	 */
	@FXML
	private RadioButton radioData;
	
	/**
	 * Elemento dell'interfaccia.  Se selezionato indica la scelta dell'utente
	 * di voler caricare l'albero da file. 
	 */
	@FXML
	private RadioButton radioArchive;
	
	
	/**
	 * Elemento dell'interfaccia. Serve a comunicare all'utente
	 * se deve inserire nella casella di testo il nome di una tabella o di un file. 
	 *  
	 */
	@FXML
	private Label filelbl;

	/**
	 * Metodo che modifica le label della home page in base a quale RadioButton
	 * viene selezionato.
	 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
	 * In questo caso si tratta del selezionare un RadioButton tra radioData e radioArchive.
	 */
	
	@FXML
	public void radioSelect(ActionEvent event){
		
		if(radioData.isSelected()) {		
			filelbl.setText("Insert the table name:");
			estensione.setText("");
			
		}
		
		if(radioArchive.isSelected()){
			filelbl.setText("Insert the file name:");
			estensione.setText(".dmp");
		}	
	}
	
	
	/**
	 * Metodo che si occupa di comunicare al server la fonte di dati scelta dall'utente e di aprire
	 * la pagina di Menu. Se le informazioni fornite dall'utente non sono corrette verr? comunicato l'errore
	 * all'utente tramite  la label relativa ai messaggi di errore 
	 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
	 * In questo caso si tratta del cliccare il Button learnBtn.
	 * @throws Exception , lanciata dal metodo recieveFromServer per leggere da stream 
	 */
	@FXML	
	public void learnButton(ActionEvent event) throws Exception{
	
		String serveranw="";
		if(radioData.isSelected()) {
			MainClientExtension.writeToServer(0);
			MainClientExtension.writeToServer(txtFile.getText());
			serveranw = MainClientExtension.receiveFromServer();
			if (!serveranw.equals("OK")) {
				labelError.setText(serveranw);
			}else {
				MainClientExtension.writeToServer(1);
			}
		}else if (radioArchive.isSelected()) {
			MainClientExtension.writeToServer(2);
			MainClientExtension.writeToServer(txtFile.getText());
			serveranw = MainClientExtension.receiveFromServer();
			if (!serveranw.equals("OK")) {
				labelError.setText(serveranw);
			}
		}
		if (serveranw.equals("OK")) {
		((Node)event.getSource()).getScene().getWindow().hide();
		page.createNewPage(599, 433, "Menu", getClass().getResource("/fxml_files/Menu.fxml"),getClass().getResource("/css_files/style.css").toExternalForm());

		}
	}
	
	
}




