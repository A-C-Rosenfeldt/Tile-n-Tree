/*
 * ToDo: Change to xhr+array once that works with table of booleans
 * Okay that will not work: http://stackoverflow.com/questions/6965107/converting-between-strings-and-arraybuffers
 */
package com.arnecrosenfeldt.tileandtree;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
/*
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
*/

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {
	
	static int persistentData; // ToDo: use dataStore
	
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml;charset=UTF-8"); // resp.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		PrintWriter out = response.getWriter();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("PERSON");
			doc.appendChild(rootElement);

			Element el = doc.createElement("USER");
			el.setAttribute("NAME", "SomeStrange UnicodeGuy sv");
			el.setAttribute("ID_", "legacy BAA id");
			el.setAttribute("HASH", "JFUHFKRIWIFHJFKSJS");
			el.setAttribute("FUNCTION", "md5");
			rootElement.appendChild(el);

			el = doc.createElement("USER");
			el.setAttribute("NAME", "utf-8 for the win sv");
			el.setAttribute("ID_", "1234");
			el.setAttribute("HASH", "156735464631");
			el.setAttribute("FUNCTION", "5*bcrypt");

			rootElement.appendChild(el);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(out);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) { // | TransformerConfigurationException
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		//out.print("<PERSON>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//User user = userService.getCurrentUser();
		response.setContentType("text/plain");
	    PrintWriter out = response.getWriter();
	    
	    /*
	    // This only works on google. Offline sign in?
	    UserService userService = UserServiceFactory.getUserService();
	    // The user needs to be logged in. How to force?
		if (!userService.isUserAdmin()) {
			out.print("Only allowed for the one devoping admin");
			out.close(); // why do I have to write this?
			response.sendError(403); // access denied
			return;
		}
		*/
	    
	//    out.println("Name: "+request.getReader().readLine());
	    // does not work with post  and there is no alternative for post : out.println("QueryString: "+request.getQueryString());
	    
		//out.println("1.Name: "+request.getParameter("first_name"));
		
		// not used right now: request.getInputStream(); // Here is the data
		// Servlet 3.0 request.getParts();

	    
	    
		out.println("Name: "+request.getParameter("NAME"));
		out.println("ID_: "+request.getParameter("ID_"));
		out.println("PASSWORD: "+request.getParameter("PASSWORD"));
		out.println("FUNCTION: "+request.getParameter("FUNCTION"));

		out.close();
	}
}
