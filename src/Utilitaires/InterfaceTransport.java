package Utilitaires;

import java.io.IOException;

public interface InterfaceTransport {	
	public void envoyer(Object obj) throws IOException;
	public Object recevoir() throws IOException, ClassNotFoundException ;
	public void fermer() throws IOException;
	
}
