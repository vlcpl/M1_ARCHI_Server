import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import Utilitaires.Transport;

public class FileServer {
	// The default port number that this server will listen on
	private final static int DEFAULT_PORT_NUMBER = 1234;

	// The maximum connections the operating system should
	// accept when the server is not waiting to accept one.
	private final static int MAX_BACKLOG = 20;

	// Timeout in milliseconds for accepting connections.
	// It may go this long before noticing a request to shut down.
	private final static int TIMEOUT = 500;

	// The port number to listen for connections on
	private int portNumber;

	// Sets to true when server should shut down.
	private boolean shutDownFlag = false;

	private int activeConnectionCount = 0;

	/**
	 * Constructor for server that listens on default port.
	 */
	public FileServer() {
		this(DEFAULT_PORT_NUMBER);
	} // constructor()

	/**
	 * Two instances of the server will not be able to successfully listen for
	 * connections on the same port.
	 * 
	 * @param port The port number to listen on.
	 */
	public FileServer(int port) {
		portNumber = port;
	} // constructor(int)

	/**
	 * Return the number of active connections
	 */
	public int getActiveConnectionCount() {
		return activeConnectionCount;
	} // getActiveConnectionCount()

	/**
	 * This is the top level method for the file server. It does not return until
	 * the server shuts down.
	 */
	public void runServer() {
		ServerSocket s;

		try {
			// Create the ServerSocket.
			System.out.println("Lancement du serveur");
			s = new ServerSocket(portNumber, MAX_BACKLOG);

			// Set timeout for accepting connections so server won't
			// wait forever until noticing a request to shut down.
			s.setSoTimeout(TIMEOUT);
		} catch (IOException e) {
			System.err.println("Unable to create socket");
			e.printStackTrace();
			return;
		} // try

		// loop to keep accepting new connections and talking to clients
		try {
			Socket socket;
			serverLoop: while (true) { // Keep accepting connections.
				try {
					socket = s.accept(); // Accept a connection.
					
				} catch (java.io.InterruptedIOException e) {
					socket = null;
					if (!shutDownFlag)
						continue serverLoop;
				} // try
				if (shutDownFlag) {
					if (socket != null)
						socket.close();
					s.close();
					return;
				} // if
					// Create worker object to process connection.
				System.out.println("Acceptation d'un client ");
				new FileServerWorker(socket);
			} // while
		} catch (IOException e) {
			// if there is an I/O error just return
		} // try
	} // start()

	/**
	 * This is called to request the server to shut down.
	 */
	public void stop() {
		shutDownFlag = true;
	} // shutDown()

	private class FileServerWorker implements Runnable {
		private Socket s;

		FileServerWorker(Socket s) {
			this.s = s;
			new Thread(this).start();
		} // constructor(Socket)

		public void run() {
			Transport ts = null;
			try {
				ts = new Transport(this.s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InputStream in;
			String fileName = "";
			PrintStream out = null;
			FileInputStream f;

			activeConnectionCount++;
			System.out.println("Lancement du thread pour gérer" + "le protocole avec un client");
			// read the file name sent by the client and open the file.
			try {
                fileName = (String) ts.recevoir();
				f = new FileInputStream(fileName);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				activeConnectionCount--;

				try {
					ts.envoyer("Bad:" + fileName + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ts.fermer();
				} catch (IOException ie) {
				}
				return;
			} // try

			// send contents of file to client.
			try {
				ts.envoyer("Good:\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				int len;
				StringBuilder sb = new StringBuilder();
				while (!shutDownFlag && (len = f.read()) > 0) {
					sb.append((char) len);
				} // while
				ts.envoyer(sb);
			} catch (IOException e) {
			} finally {
				try {
					activeConnectionCount--;
					ts.fermer();
				} catch (IOException e) {
				} // try
			} // try
		} // run()
	} // class FileServerWorker
} // class FileServer