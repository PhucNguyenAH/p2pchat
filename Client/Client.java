package Client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Client {
	private static InetAddress serverIP = null;
	private static int serverPort = 3000;

	public Client(InetAddress serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;

		Socket client;
		try {
			client = new Socket(serverIP, serverPort);
			System.out.println(client + " connect to server");
			ClientWriteToServer writeServer = new ClientWriteToServer(client);
			writeServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	class ClientWriteToServer extends Thread {
		private Socket client;

		public ClientWriteToServer(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			DataOutputStream Dout = null;
			try {
				Dout = new DataOutputStream(client.getOutputStream());
				System.out.println("Write");
				while (true) {
					Dout.writeUTF("UPDATE");
					Dout.flush();
				}
			} catch (UnknownHostException e) {
				System.err.println("Can't find localhost");
			} catch (SocketException e) {
				System.err.println("Could not connected to localhost ");
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				if (client != null)
					try {
						Dout.close();
						client.close();
					} catch (IOException e) {
						System.out.println("Client disconnected");
					}
			}
		}
	}
}
