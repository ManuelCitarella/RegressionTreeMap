package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Classe di supporta alle classi controller. Serve a creare una nuova pagina 
 * dell'interfaccia.
 *
 */
public class CreatePage {

	/**
	 * Metodo che permette di creare una nuova pagina dell'interfaccia tramite 
	 * le informazioni fornite tra i parametri. 
	 * @param width,larghezza della pagina
	 * @param height, altezza della pagina
	 * @param title, titolo della pagina
	 * @param source, sorgente del file fxml da cui prendere le indicazioni per la grafica della pagina
	 * @param css, percorso del file css da cui prendere le indicazioni per lo stile della pagina
	 */
	 void createNewPage(int width, int height, String title, URL source, String css){
		Stage primaryStage = new Stage();
		Parent root =null;
		try {
			root = FXMLLoader.load(source);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root,width,height);
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			ExitWindow c= new ExitWindow();
			c.close();
			
		});

		primaryStage.show();
	}
	
}
