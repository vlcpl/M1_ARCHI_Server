package Utilitaires;

import java.io.IOException;
import java.net.Socket;

public class Protocol {
	private Transport ts;
	
	public Protocol(Socket s) {
		// On essaye d'accéder au serveur
		try {
			this.ts = new Transport(s);
		} catch (Exception e) {
			System.err.println("Unable to reach the server");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Receives a request from a client
	 * @return
	 */
	public String receiveClientRequest(){
		String clientRequest = null;
		try {
			clientRequest = (String) this.ts.recevoir();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clientRequest;
	}
	
	/**
	 * If the file exists, sends a "good" response to the client with the file's content
	 * @param fileContent
	 */
	public void sendGoodResponse(String fileContent) {
		try {
			this.ts.envoyer("Good");
			this.ts.envoyer(fileContent);
			this.ts.fermer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * If the file does not exist, sends a "bad" response to the client
	 */
	public void sendBadResponse() {
		try {
			this.ts.envoyer("Bad");
			this.ts.fermer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
