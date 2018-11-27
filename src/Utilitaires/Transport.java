package Utilitaires;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class Transport implements InterfaceTransport {

	private Socket s = null;
	private ObjectOutputStream out;
	private ObjectInputStream din;

	//Pour le client
	public Transport(String adresse, int port) throws Exception {
		s = new Socket(adresse, port);
		out = new ObjectOutputStream(s.getOutputStream());
		din = new ObjectInputStream(s.getInputStream());
	}

	//Pour le serveur
	public Transport(Socket socket) throws IOException {
		this.s = socket;
		din = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}

	public void envoyer(Object object) throws IOException {
		out.writeObject(object);
	}

	public Object recevoir() throws NullPointerException, ClassNotFoundException, IOException {
		Object o = din.readObject();
		if(Objects.isNull(o)) {
			throw new NullPointerException();
		}
		return o;
	}

	public void fermer() throws IOException {
		if (out != null) {
			out.close();
		}
		if (din != null) {
			din.close();
		}
		if (s != null) {
			s.close();
		}
	}
}
