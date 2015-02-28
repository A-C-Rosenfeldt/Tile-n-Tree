package com.arnecrosenfeldt.tileandtree;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

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

public class BidirectionalXml extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml;charset=UTF-8"); // resp.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		Document document;

	    
	    /////////
	    
	    document = documentBuilder.newDocument();
	    Element answer=document.createElement("answere");
	    document.appendChild(answer);
	    Element el = document.createElement("USER");
		el.setAttribute("NAME", "SomeStrange UnicodeGuy sv");
		el.setAttribute("ID_", "legacy BAA id");
		el.setAttribute("HASH", "JFUHFKRIWIFHJFKSJS");
		el.setAttribute("FUNCTION", "md5");
		answer.appendChild(el);

		el = document.createElement("USER");
		el.setAttribute("NAME", "utf-8 for the win sv");
		el.setAttribute("ID_", "1234");
		el.setAttribute("HASH", "156735464631");
		el.setAttribute("FUNCTION", "5*bcrypt");

		answer.appendChild(el);	    
	    PrintWriter out=response.getWriter();
	    
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(out);

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		// TODO Auto-generated method stub
		Document document;
	    try {
			document = documentBuilder.parse(request.getInputStream());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} 
	    
	    Node a=document.getFirstChild().getFirstChild();	
		PrintWriter out = response.getWriter();
		//ToDo
		//request.setAttribute("result",str);
		//request.getRequestDispatcher("/index.jsp").forward(request,response);
//		<% 
//		out.print(request.getAttribute("servletName").toString());
//		%>
//		out.println("<!DOCTYPE html>");
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<meta charset=\"UTF-8\">");
//		out.println("<title>Your login may have been successful</title>");
//		out.println("</head>");
//		out.println("<body>");
		out.println("Hello World from Servlet <br>");
		out.println("FIELD_USER : " + a.getTextContent() + "<br>");
	//	out.println("FIELD_PASSWORD : " + a.getChildNodes().item(1).getNodeValue() + "<br>");
		out.println("Nice to here from you again <br>");
//		out.println("</body>");
//		out.println("</html>");
		out.close();
	}
	
	
		
	
	
}
