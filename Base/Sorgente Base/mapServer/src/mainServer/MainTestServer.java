package mainServer;
import java.io.IOException;

import server.MultiServer;

/**
 * 
 * Classe che rappresenta il main del server, il quale costruisce oggetti Multiserver
 * per gestire le richieste da parte del client
 *
 */
public class MainTestServer {

	public static void main(String[] args) {

		try {
			 new MultiServer(8080);
			
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		} 
	}

}
