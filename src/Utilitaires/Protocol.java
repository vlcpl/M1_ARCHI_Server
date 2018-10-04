package Utilitaires;

import java.io.IOException;
import java.net.Socket;

public class Protocol {
	private Transport ts;
	
	public Protocol(Socket s) {
		// On essaye d'accéder au serveur
		try {
			ts = new Transport(s);
		} catch (Exception e) {
			System.err.println("Unable to reach the server");
			e.printStackTrace();
			System.exit(1);
		}
	}
	public String receiveClientRequest(){
		String clientRequest = null;
		try {
			clientRequest = (String) ts.recevoir();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clientRequest;
	}
	
	public void sendGoodResponse(String fileContent) {
		try {
			ts.envoyer("Good");
			ts.envoyer(fileContent);
			ts.fermer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendBadResponse() {
		try {
			ts.envoyer("Bad");
			ts.fermer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
