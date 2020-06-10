package Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServerTest {
	private int SERVER_PORT = 4499;
	public static ArrayList<Socket> listSocket;

	private void execute() throws IOException {
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

		System.out.println("Server is listening...");
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Connect to " + socket);
			ServerTest.listSocket.add(socket);
			ServerResponse serverResponse = new ServerResponse(socket);
			serverResponse.start();
		}
	}

	public static void main(String[] args) throws IOException {
		ServerTest.listSocket = new ArrayList<>();
		ServerTest server = new ServerTest();
		server.execute();
	}
}

class ServerResponse extends Thread {
	private Socket server;
	private static ArrayList<Friends> FriendList = null;
	private static ArrayList<UsersTest> UserList = null;
	private static ArrayList<Friends> nonFriendList = null;
	private static String Username = null;
	private static String Password = null;

	public ServerResponse(Socket server) {
		this.server = server;
	}

	@Override
	public void run() {
		DataInputStream din = null;
		DataOutputStream dout = null;
		ObjectInputStream Oin = null;
		ObjectOutputStream Oout = null;
		String filePath = "UserList.xml";
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		boolean flag = true;

		try {
			
			din = new DataInputStream(server.getInputStream());
			// Thread for listening to Client
			while (true) {
				
				String sms = din.readUTF();
				System.out.println(sms);
				// Load User list in UserList.xml
				try {
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(xmlFile);
					doc.getDocumentElement().normalize();
					System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					NodeList nodeList = doc.getElementsByTagName("User");
					UserList = new ArrayList<UsersTest>();
					for (int i = 0; i < nodeList.getLength(); i++) {
						UserList.add(getUser(nodeList.item(i)));
					}
				} catch (SAXException | ParserConfigurationException | IOException e1) {
					e1.printStackTrace();
				}

				if (sms.contains("LOG")) {
					dout = new DataOutputStream(server.getOutputStream());
					String[] str = sms.split(" ", 0);
					Username = str[1];
					Password = str[2];
					flag = true;
					// Check client exist or not
					for (UsersTest emp : UserList) {
						if (emp.getUsername().equals(Username) && emp.getPassword().equals(Password)) {
							emp.setIPaddress(server.getInetAddress().getHostName());
							flag = false;
						}
					}
					if (flag) {
						dout.writeUTF("NO");
						dout.flush();
					}
					if (!flag) {
						dout.writeUTF("OK");
						dout.flush();
					}
				}

				if (sms.contains("GET")) {
					Oin = new ObjectInputStream(server.getInputStream());
					Oout = new ObjectOutputStream(server.getOutputStream());
					// Server receive fried list of Client
					FriendList = new ArrayList<Friends>();
					FriendList = (ArrayList<Friends>) Oin.readObject();
				
					nonFriendList = new ArrayList<Friends>();
					for (UsersTest emp : UserList) {
						flag = true;
						for (Friends emp2 : FriendList) {
							if (emp.getUsername().equals(emp2.getUsername())) {
								flag = false;
								emp2.setIPaddress(emp.getIPaddress());
							}
						}
						if (flag) {
							Friends nonFriend = new Friends();
							nonFriend.setUsername(emp.getUsername());
							nonFriend.setIPaddress(emp.getIPaddress());
							nonFriendList.add(nonFriend);
						}
					}
					
					for (Friends emp : FriendList) {
						System.out.println(emp.toString());
					}
					for (Friends emp : nonFriendList) {
						System.out.println(emp.toString());
					}
					
					Oout.writeObject(FriendList);
					Oout.flush();
					Oout.writeObject(nonFriendList);
					Oout.flush();
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Can't find localhost");
		} catch (SocketException e) {
			System.err.println("Could not connected to localhost ");
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					din.close();
					dout.close();
					Oin.close();
					Oout.close();
					server.close();
				} catch (IOException e) {
					System.out.println("Client disconnected");
				}
		}
	}

	private static UsersTest getUser(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		UsersTest user = new UsersTest();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			user.setUsername(getTagValue("username", element));
			user.setPassword(getTagValue("password", element));
			user.setIPaddress(getTagValue("IPAddress", element));
		}
		return user;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}
}
