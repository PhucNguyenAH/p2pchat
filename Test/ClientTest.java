package Test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class ClientTest {
	private static String SERVER_IP = "127.0.0.1";
	private static int SERVER_PORT = 3000;
	private static ArrayList<Friends> FriendList = new ArrayList<Friends>();
	private static boolean Status = false;

	public void execute() throws IOException {

		Scanner sc = new Scanner(System.in);
		String username = null;
		String password = null;
		String server_ip = null;
		String server_port = null;
		Socket client = new Socket(SERVER_IP, SERVER_PORT);
		;
		boolean log = false;

//		DataInputStream din = new DataInputStream(client.getInputStream());
		ObjectOutputStream dout = new ObjectOutputStream(client.getOutputStream());
		do {
			System.out.println("Username: ");
			username = sc.next();
			System.out.println("Password: ");
			password = sc.next();
			String account = "REG " + username + " " + password + " 123";
			dout.writeObject(account);
			dout.flush();
//			while (true) {
//				String sms = din.readUTF();
//				if (sms.equals("OK")) {
//					Status = true;
//					break;
//				}
//				if (sms.equals("NO")) {
//					Status = false;
//					break;
//				}
//			}
//			System.out.println(Status);
		} while (true);
//		} while (!Status);
//		din.close();
//		dout.close();
//		System.out.println("Success");
//		System.out.println(username + " is connected");
//
//		ClientReadFromServer readServer = new ClientReadFromServer(client);
//		readServer.start();
//		ClientWriteToServer writeServer = new ClientWriteToServer(client, FriendList);
//		writeServer.start();
//	}

	}

	class ClientReadFromServer extends Thread {
		private Socket client;
//		private static ArrayList<Friends> FriendList = null;
//		private static ArrayList<Friends> UserList = null;

		public ClientReadFromServer(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			ObjectInputStream Oin = null;
			try {
				Oin = new ObjectInputStream(client.getInputStream());
				while (true) {
//				Oin = new ObjectInputStream(client.getInputStream());
					FriendList = (ArrayList<Friends>) Oin.readObject();
//					UserList = (ArrayList<Friends>) Oin.readObject();
					System.out.println("Friend: ");
					for (Friends emp : FriendList) {
						System.out.println(emp.toString());
					}
					System.out.println("Non friend:");
//					for (Friends emp : UserList) {
//						System.out.println(emp.toString());
//					}

				}
			} catch (UnknownHostException e) {
				System.err.println("Can't find localhost");
			} catch (SocketException e) {
				System.err.println("Could not connected to localhost ");
			} catch (IOException e) {
				System.err.println(e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (client != null)
					try {
						Oin.close();
						client.close();
					} catch (IOException e) {
						System.out.println(client + "disconnected");
					}
			}
		}
	}

	class ClientWriteToServer extends Thread {
//		private static ArrayList<Friends> FriendList = new ArrayList<Friends>();
		private Socket client;

		public ClientWriteToServer(Socket client, ArrayList<Friends> FriendList) {
			this.client = client;
//			this.FriendList = FriendList;
		}

		@Override
		public void run() {
			DataOutputStream Dout = null;
			ObjectOutputStream Oout = null;

			String filePath = "FriendList.xml";
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				NodeList nodeList = doc.getElementsByTagName("Friend");

//			for (int i = 0; i < nodeList.getLength(); i++) {
//				FriendList.add(getUser(nodeList.item(i)));
//			}
				// lets print Friends list information
				for (Friends emp : FriendList) {
					System.out.println(emp.toString());
				}

				Dout = new DataOutputStream(client.getOutputStream());
				Oout = new ObjectOutputStream(client.getOutputStream());
				while (true) {
//				Dout = new DataOutputStream(client.getOutputStream());
//				Oout = new ObjectOutputStream(client.getOutputStream());
					Dout.writeUTF("GET");
					Dout.flush();
					Oout.writeObject(FriendList);
					Oout.flush();
				}
			} catch (SAXException | ParserConfigurationException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				System.err.println("Can't find localhost");
			} catch (SocketException e) {
				System.err.println("Could not connected to localhost ");
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				if (client != null)
					try {
						Oout.close();
						client.close();
					} catch (IOException e) {
						System.out.println("Client disconnected");
					}
			}
		}

//	private static Friends getUser(Node node) {
//		// XMLReaderDOM domReader = new XMLReaderDOM();
//		Friends friend = new Friends();
//		if (node.getNodeType() == Node.ELEMENT_NODE) {
//			Element element = (Element) node;
//			friend.setUsername(getTagValue("username", element));
//		}
//		return friend;
//	}
//
//	private static String getTagValue(String tag, Element element) {
//		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//		Node node = (Node) nodeList.item(0);
//		return node.getNodeValue();
//	}
	}
}
