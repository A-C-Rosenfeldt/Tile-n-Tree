package com.arnecrosenfeldt.tileandtree;

import java.io.IOException;
import java.io.InputStream;
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


@SuppressWarnings("serial")
public class TileServlet extends HttpServlet {
	
	// http://stackoverflow.com/questions/15056686/sending-a-blob-to-a-servlet-through-ajax
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml;charset=UTF-8"); // resp.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
	
	
	byte[] buffer = new byte[16 * 1024];

	InputStream input = request.getInputStream();       
	PrintWriter out = response.getWriter();
	
	int bytesRead;
	while ((bytesRead = input.read(buffer)) != -1){
	    System.out.println(bytesRead);
	    out.println(bytesRead);
	}
	out.close();
	input.close();
}
}
