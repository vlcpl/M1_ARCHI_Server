package Utilitaires;

public interface InterfaceProtocol {

	/**
	 * Receives a request from a client
	 * @return
	 */
	String receiveClientRequest();

	/**
	 * If the file exists, sends a "good" response to the client with the file's content
	 * @param fileContent
	 */
	void sendGoodResponse(String fileContent);

	/**
	 * If the file does not exist, sends a "bad" response to the client
	 */
	void sendBadResponse();

}