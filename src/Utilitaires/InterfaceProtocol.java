package Utilitaires;

import java.io.IOException;

public interface InterfaceProtocol {

	/**
	 * Receives a request from a client
	 * @return
	 */
	String receiveClientRequest() throws NullPointerException, ClassNotFoundException, IOException ;

	/**
	 * If the file exists, sends a "good" response to the client with the file's content
	 * @param fileContent
	 * @throws IOException 
	 */
	void sendGoodResponse(String fileContent) throws IOException;

	
	void fermer() throws IOException;

}