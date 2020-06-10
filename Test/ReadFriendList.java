package Test;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Java DOM Parser to Read XML File in Java
 * 
 * @author Ramesh Fadatare
 *
 */

public class ReadFriendList {
	public static void main(String[] args) {
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
			// now XML is loaded as Document in memory, lets convert it to Object List
			List<Friends> friendList = new ArrayList<Friends>();

			for (int i = 0; i < nodeList.getLength(); i++) {
				friendList.add(getUser(nodeList.item(i)));
			}
			// lets print User list information
			for (Friends emp : friendList) {
				System.out.println(emp.toString());
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}

	}

	private static Friends getUser(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		Friends friend = new Friends();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			friend.setUsername(getTagValue("username", element));
		}
		return friend;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

}