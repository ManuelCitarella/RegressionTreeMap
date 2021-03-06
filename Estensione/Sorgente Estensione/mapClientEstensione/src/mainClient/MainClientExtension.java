package mainClient;
 import java.io.IOException;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;

import application.ExitWindow;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application; 

/**
 * Questa classe rappresenta il main del client, mediante il quale avviene la
	  * comunicazione con l'utente: vengono raccolte le richieste dell'utente e
	  * inviate al server il quale le elabora e restituisce i risultati al client che le mostra
	  * a video all'utente.<br> 
	  * Estende la classe Application per realizzare l'interfaccia utente con JavaFx.
 *
 */
public class MainClientExtension extends Application {
	/**
	 *  stream di output usato dal client per comunicare con il server
	 */
	static ObjectOutputStream out = null ;
	/**
	 * stream di input  usato dal client per ricevere le risposte dal server. 
	 */
	static  ObjectInputStream in = null;
	

	/**
	 * Metodo che permette di avviare l'applicazione
	 * e mostrare l'interfaccia all'utente. 
	 * @param args   array di stringhe di argomenti della riga di comando
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
/**
 * Il punto di inizio dell'applicazione. Il metodo di avvio viene chiamato dopo che
 *  il metodo di inizializzazione ? stato restituito e dopo che il sistema ? pronto per l'avvio dell'applicazione.
 *  Mostra la home page.
 *  @param primaryStage, the top level JavaFX container
 */

	@Override
	public void start(Stage primaryStage) {
		try {
			InetAddress addr=null;
			try {
				addr = InetAddress.getByName(null); //localhost
			} catch (UnknownHostException e) {
				System.out.println(e.toString());
				return;
			}
			
			Socket socket=null;
			
			try {
				socket = new Socket(addr,8080);	
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());	 //stream con richieste del client
			}  catch (IOException e) {
				System.out.println(e.toString());
				
			}

			
			Parent root= FXMLLoader.load(getClass().getResource("/fxml_files/Main.fxml"));
			Scene scene = new Scene(root,599,433);
			scene.getStylesheets().add(getClass().getResource("/css_files/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Home Page");
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				ExitWindow c= new ExitWindow();
				c.close();
			
				
			});

				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.show();
		
	}
	
	/**
	 * Metodo che gestisce l'invio dei messaggi al server
	 * @param mess, messaggio da inviare al server 
	 */
	
	public static void writeToServer(Object mess) {
		try {
			out.writeObject(mess);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo che gestisce la ricezione dei messaggi inviati dal server
	 * @return messaggio letto dal server
	 * @throws ClassNotFoundException , se la classe object serializzata non viene trovata
	 * @throws IOException , in caso di problemi di lettura da stream 
	 */
	public static String receiveFromServer() throws ClassNotFoundException, IOException {
		return in.readObject().toString();
	}
	

}

