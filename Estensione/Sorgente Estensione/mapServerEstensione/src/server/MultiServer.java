package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce le multiple richieste di connessione al server.
 * Per ogni richiesta di connessione viene creato un oggetto ServerOneClient 
 * che la gestirà.
 *
 */
public class MultiServer {

	/**
	 * Numero di porta del server in ascolto
	 */
	private int PORT= 8080;
	
	/**
	 * Costruttore di classe, inizializza il numero di porta (default 8080) e invoca il metodo run();
	 * @param port, numero di porta
	 * @throws IOException lanciata se occorre un errore nella comunicazione col client
	 */
	public MultiServer(int port) throws IOException { 
		this.PORT=port;
		run();
	}
	
	/**
	 * Metodo che istanzia un oggetto della classe ServerSocket, che pone in attesa
	 * di richiesta di connessione da parte del client.
	 * Per ogni richiesta di connessione crea un oggetto ServerOneClient 
	 * @throws IOException lanciata se occorre un errore nella comunicazione col client
	 */
	private void run() throws IOException{
		
		ServerSocket serversocket=new ServerSocket(PORT);
		System.out.println("Server started!");
		try {
			while(true) {
				Socket socket=serversocket.accept();
				System.out.println("Connected to client! ");
			try {
					new ServerOneClient(socket);
					
			}catch(IOException e) {
				socket.close();
			}
			
			}
		
		}finally {
			serversocket.close();
		}
		
	}
}
