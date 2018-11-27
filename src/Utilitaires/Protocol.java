package Utilitaires;

import java.io.IOException;

public class Protocol implements InterfaceProtocol {
	private InterfaceTransport ts;
	
	public Protocol(InterfaceTransport t) {
		// On essaye d'accéder au serveur
			this.ts = t;
	}
	
	/* (non-Javadoc)
	 * @see Utilitaires.InterfaceProtocol#receiveClientRequest()
	 */
	@Override
	public String receiveClientRequest() throws ClassNotFoundException, IOException {
		return (String) this.ts.recevoir();
	}
	
	/* (non-Javadoc)
	 * @see Utilitaires.InterfaceProtocol#sendGoodResponse(java.lang.String)
	 */
	@Override
	public void sendGoodResponse(String fileContent) throws IOException {
			this.ts.envoyer("Good");
			this.ts.envoyer(fileContent);
	}
	
	
	@Override
	public void fermer() throws IOException{
			this.ts.fermer();
	}
}
