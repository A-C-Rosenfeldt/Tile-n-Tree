package com.arnecrosenfeldt.tileandtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IFrameResponse extends HttpServlet {

	public static String FIELD_USER = "username";
	public static String FIELD_PASSWORD = "password";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// This seems hard to be found using GIS
		String username = request.getParameter(FIELD_USER);
		String password = request.getParameter(FIELD_PASSWORD);

		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		//ToDo
		//request.setAttribute("result",str);
		//request.getRequestDispatcher("/index.jsp").forward(request,response);
//		<% 
//		out.print(request.getAttribute("servletName").toString());
//		%>
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Your login may have been successful</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Hello World from Servlet <br>");
		out.println("FIELD_USER : " + username + "<br>");
		out.println("FIELD_PASSWORD : " + password + "<br>");
		out.println("Nice to here from you again <br>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
