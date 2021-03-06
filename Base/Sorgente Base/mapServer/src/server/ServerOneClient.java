package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Data;
import data.TrainingDataException;
import tree.LeafNode;
import tree.RegressionTree;
import tree.SplitNode;


/**
 * Classe che gestisce le singole connessioni con un client, il quale invia le
 * richieste specifiche dell'utente e riceve le risposte alla richiesta elaborate 
 * dal server.
 *  Estende la classe Thread in modo da poter creare ed eseguire thread
 */

 class ServerOneClient extends Thread{ 

	 /**
	  * Attributo che identifica la socket per la connesione col client
	  */
	private Socket socket;
	/**
	 * In stream di input usato dal server per comunicare col client
	 */
	private ObjectInputStream in;
	/**
	 * Out stream di output usato dal server per comunicare col client
	 */
	private ObjectOutputStream out;
	
/**
 * Costruttore di classe che inizializza gli attributi in,out e socket.
 * Avvia il thread attraverso il metodo start
 * @param s, socket che identifica il nuovo processo server
 * @throws IOException lanciata se occorre un errore nella comunicazione col client
 */
public ServerOneClient(Socket s) throws IOException{

		this.socket=s;
		in=new ObjectInputStream (socket.getInputStream());
		out= new ObjectOutputStream(socket.getOutputStream());
		start();
}
	
/** Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client 
 *  ed ottenere in questo modo i risultati richiesti.
 */
public void run() {
	try {
		RegressionTree tree=null;
		Data trainingSet=null;
		String tableName="";
		
			Object anw= in.readObject();
			if(anw.equals(0)) {
				try{
					tableName=(String) in.readObject();
					trainingSet= new Data(tableName);	
					out.writeObject("OK");	
					if(in.readObject().equals(1)) {
						tree=new RegressionTree(trainingSet);
						try {
							tree.salva(tableName+".dmp");
							tree.printRules();
							tree.printTree();
							out.writeObject("OK");	
						} catch (IOException e) {
							System.out.println(e.toString());
						}
			
					}
				}
				catch(TrainingDataException e){
					System.out.println(e);
					out.writeObject(e.getMessage());	

					}
			
			
			}else if (anw.equals(2)){
				
				tableName= (String) in.readObject();
				try {
					tree=RegressionTree.carica(tableName+".dmp");
					
					tree.printRules();
					tree.printTree();
					out.writeObject("OK");	
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e);
					out.writeObject(e.getMessage());	
					
				}	
			}
			
			while (in.readObject().equals(3)){
			
						try {
							Double pred = predictClass(tree,in, out);
							out.writeObject("OK");
							out.writeObject(pred);
						}catch(UnknownValueException e) {
							out.writeObject(e.getMessage());	
						}
					
			
		}
		
			}catch( IOException e) {
			try {
				socket.close();
			}catch(IOException e1) {
				System.out.println(e1);
			}
		}catch( ClassNotFoundException  e1){
			System.out.println(e1);
		}
		finally {
			try {
				socket.close();
			}catch(IOException e) {
				System.out.println("Socket not closed!");
			}
		}
	
	} 
	
	
	/**
	 * Mostra all'utente le informazioni di ciascuno split dell'albero
	 * e per il corrispondente attributo acquisisce il valore dell'esempio da predire
	 * da tastiera.<br>
	 * Se il nodo root corrente ? leaf termina l'acquisizione e mostra all'utente la predizione
	 * per l?attributo classe, altrimenti invoca ricorsivamente sul figlio di root 
	 * nell'array childTree individuato dal valore acquisito da tastiera. <br>
	 * Il metodo solleva l'eccezione UnknownValueException quando la risposta 
	 * dell?utente non permette di selezionare un ramo valido del nodo di split, e 
	 * l'eccezione sar? gestita nel metodo che invoca predictClass().
	 * 
	 * @param tree albero su cui si effettua la predizione
	 * @param out canale di output per la comunicazione col il client
	 * @param in canale di input per la comunicazione col il client
	 * @throws UnknownValueException lanciata se l'utente sceglie l'indice 
	 * di un ramo inesistente
	 * @throws IOException lanciata nel momento in cui vi e' un errore di comunicazione col client
	 * @throws UnknownValueException , lanciata se il valore inserito dall'utente non ? compreso nel range possibile
	 * @throws ClassNotFoundException , lanciata se la classe di cui si sta verificando l'istanza non esiste
	 * @return valore risultato della predizione
	 */
	private Double predictClass(RegressionTree tree,ObjectInputStream in, ObjectOutputStream out ) throws UnknownValueException, IOException, ClassNotFoundException{ //uso di RTTI
	
				
		if(tree.getRoot() instanceof LeafNode) {
			
			return ((LeafNode) tree.getRoot()).getPredictedClassValue();
		}else
		  {
			out.writeObject("QUERY");
			int risp;
			
			out.writeObject(((SplitNode)tree.getRoot()).formulateQuery());
			risp=(int)in.readObject();
			if(risp<0 || risp>=tree.getRoot().getNumberOfChildren()) {
				throw new UnknownValueException("The answer should be an integer between 0 and " +(tree.getRoot().getNumberOfChildren()-1)+"!");
			}else {
				
				return predictClass(tree.getChildTree(risp),in,out);
			}
	      }
		
	}
	
}
	
