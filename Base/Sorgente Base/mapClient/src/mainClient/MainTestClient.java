package mainClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


import utility.Keyboard;

 class MainTestClient {

	 /**
	  * Questa classe rappresenta il main del client, mediante il quale avviene la
	  * comunicazione con l'utente: vengono raccolte le richieste dell'utente,
	  * inviate al server il quale le elabora e restituisce i risultati al client che le mostra
	  * a video all'utente.<br>
	  * Le possibili scelte dell'utente sono:<br>
	  * 1) Creare un albero di regressione a partire da una tabella presente in un database<br>
	  * 2) Caricare un albero di regressione già presente nell'archivio<br>
	  * Una volta ottenuto l'albero di regressione,
	  * l'utente potrà usare l'applicazione per predire i risultati di ulteriori esempi.
	  * @param args , array di stringhe di argomenti della riga di comando
	  */
	public static void main(String[] args) {
		
		InetAddress addr=null;
		try {
			addr = InetAddress.getByName(null); //localhost
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			return;
		}
		
		Socket socket=null;
		ObjectOutputStream out=null;
		ObjectInputStream in=null;
		try {
			socket = new Socket(addr, 8080);	
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());	; //stream con richieste del client
			
		}  catch (IOException e) {
			System.out.println(e.toString());
			return;
		}

		
		String answer="";
		int decision=0;
		do{
		
			System.out.println("Learn Regression Tree from data [1]");
			System.out.println("Load Regression Tree from archive [2]");
			decision=Keyboard.readInt();
		}while(!(decision==1) && !(decision ==2));
		
		String tableName="";
		if(decision==1) {
			System.out.println("Table name: ");
		}else {
		   System.out.println("File name: ");
		}
		tableName=Keyboard.readString();
		try{
		
			if(decision==1)
			{
				System.out.println("Starting data acquisition phase!...");
				
				
				//richieste del client
				// dice al server  di creare l'oggetto data dal database
				out.writeObject(0);//quando al server arriva 0 il server sa che vuole acquisire i dati da database
				out.writeObject(tableName);
				answer=in.readObject().toString();// risposta del server
				if(!answer.equals("OK")){
					System.out.println(answer);
					return;
				}
					
				
				
			
				System.out.println("Starting learning phase!...");
				// dice al server di creare il regression tree
				out.writeObject(1);
				
			
			}
			else
			{
				System.out.println("Starting learning phase!...");
				//vai a caricare il regr tree dal file serializzato
				out.writeObject(2);
				out.writeObject(tableName);
				
			}
			
			answer=in.readObject().toString();
			if(!answer.equals("OK")){
				System.out.println(answer);
				return;
			}
				
					
			char risp='y';
			//cicla su quante richieste fa, ossia quante predizioni diverse vuole fare
			do{
				out.writeObject(3);
				
				System.out.println("Starting prediction phase!...");
				answer=in.readObject().toString();
			
				
				while(answer.equals("QUERY")){
					// Formualting query, reading answer
					answer=in.readObject().toString();
					System.out.println(answer);
					System.out.print("Choice: ");
					int path=Keyboard.readInt();
					out.writeObject(path);
					answer=in.readObject().toString();
				}
			
				if(answer.equals("OK"))
				{ // Reading prediction
					answer=in.readObject().toString();
					System.out.println("Predicted class:"+answer);
					
				}
				else //Printing error message
					System.out.println(answer);
				
			
				System.out.println("Would you repeat ? (y/n)");
				risp=Keyboard.readChar();
					
			}while (Character.toUpperCase(risp)=='Y');
			   
		}
		catch(IOException | ClassNotFoundException e){
			System.out.println(e.toString());
			
		}
		
	}		
		
}
