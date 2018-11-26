package Utilitaires;

import java.io.IOException;
import java.net.Socket;

public class Protocol implements InterfaceProtocol {
	private InterfaceTransport ts;
	
	public Protocol(Transport t) {
		// On essaye d'accéder au serveur
		try {
			this.ts = t;
		} catch (Exception e) {
			System.err.println("Unable to reach the server");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/* (non-Javadoc)
	 * @see Utilitaires.InterfaceProtocol#receiveClientRequest()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see Utilitaires.InterfaceProtocol#sendGoodResponse(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see Utilitaires.InterfaceProtocol#sendBadResponse()
	 */
	@Override
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
