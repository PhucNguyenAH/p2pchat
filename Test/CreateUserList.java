package Test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CreateUserList {
    public static void main(String[] args) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // add elements to Document
            Element rootElement = doc.createElement("UserList");
            // append root element to document
            doc.appendChild(rootElement);

            // append first child element to root element
            rootElement.appendChild(createUserElement(doc, "Ramesh", "12345"));

            // append second child
            rootElement.appendChild(createUserElement(doc, "John","abc321"));

            // append third child
            rootElement.appendChild(createUserElement(doc, "Tom","54321"));
            
         // append second child
            rootElement.appendChild(createUserElement(doc, "HoangPhuc","123"));

            // append third child
            rootElement.appendChild(createUserElement(doc, "Dang","121221"));

            // for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // for pretty print
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            // write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("UserList.xml"));

            // write data
            transformer.transform(source, console);
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node createUserElement(Document doc, String username, String password) {
        Element user = doc.createElement("User");

        // set username attribute
        user.appendChild(createUserElements(doc, user, "username", username));

        // create password element
        user.appendChild(createUserElements(doc, user, "password", password));

//        // create ipaddress element
//        user.appendChild(createUserElements(doc, user, "IPAddress", IPAddress));

        return user;
    }

    // utility method to create text node
    private static Node createUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
