package application;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ExitWindow {
	
	/**
	 * Elemento dell'interfaccia. Se utilizzato, l'applicazione viene chiusa. 
	 */
	@FXML
	private Button yesBtn;
	
	/**
	 * Elemento dell'interfaccia. Se utilizzato permette di continuare ad utilizzare
	 * l'applicaizone. 
	 */
	@FXML
	private Button noBtn;
	
	/**
	 * Metodo che avvia la pagina dell'interfaccia relativa al warning di chiusura
	 * dell'applicazione. 
	 */
	 public void close() {
		 Stage primaryStage = new Stage();
			Parent root=null;
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml_files/Close.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			Scene scene = new Scene(root,264,139);
			scene.getStylesheets().add(getClass().getResource("/css_files/exit.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Exit");
			primaryStage.setResizable(false);
			
			primaryStage.show();
	}

	 /**
		 * Metodo che permette di chiudere l'applicazione. 
		 * @param event,evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
		 * In questo caso si tratta del cliccare il Button yesBtn.
		 */
	 @FXML 
		public void yesButton(ActionEvent event) {
			
			System.exit(0);
		}
		
	 
	 /**
		 * Metodo che permette di continuare ad utilizzare l'applicazione. 
		 * @param event, evento che consiste nel compiere un'azione sull' interfaccia da parte dell'utente.
		 * In questo caso si tratta del cliccare il Button noBtn.
		 */
		@FXML 
		public void noButton(ActionEvent event) {
			((Node)event.getSource()).getScene().getWindow().hide();
		}

}
