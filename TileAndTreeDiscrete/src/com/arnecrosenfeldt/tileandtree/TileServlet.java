package com.arnecrosenfeldt.tileandtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

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
		response.setContentType("application/octet-stream"); // resp.setContentType("text/plain");  
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		/*
		byte[] buffer = new byte[16 * 1024];

		InputStream input = request.getInputStream();
		PrintWriter out = response.getWriter();

		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			System.out.println(bytesRead);
			out.println(bytesRead);
		}
		*/
		
		
		ServletOutputStream out=response.getOutputStream();
		//out.write(buf); stringlängen wie in Pascal / Java intern
		
		
		byte[] a=new byte[4]; // I need the number of bytes for each string.

		// All this ugly manual memory allocation  is here to avoid that even more ugly   magic string of formData
		
		String NAME="Joe Doe";		
		a[0]=(byte) NAME.length();
		a[1]=7; // Id
		String PASSWORD="1234";
		a[2]=(byte) PASSWORD.length();		
		a[3]=1; // FUNCTION_value  0=none (ident) per best practice for enums
		out.write(a);

		a=NAME.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
		out.write(a);

		a=PASSWORD.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
		out.write(a);
		
		out.close();
		
		// input.close();
	}

	// Format: count, int, int, int.  Java is big endian. JavaScript is Typed Arrays are little-endian !!??
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream input = request.getInputStream(); // ECMA script is not able to build XML. It can produce FormData where I have to provide magic markers (aaarg).
		
		byte[] a=new byte[4]; // I need the number of bytes for each string.
		int count_read_elements=input.read(a);
				
		PrintWriter out = response.getWriter();

		out.println("count_read_elements: "+count_read_elements);
		
		// All this ugly manual memory allocation  is here to avoid that even more ugly   magic string of formData
		int NAME_lenght=a[0];
		out.println("NAme.length: "+NAME_lenght);
		int ID=a[1];
		out.println("ID: "+ID);
		int PASSWORD_length=a[2];
		out.println("PASSWORD_length: "+PASSWORD_length);
		int FUNCTION_value=a[3];
		out.println("FUNCTION_value: "+FUNCTION_value);
		
		 a=new byte[NAME_lenght]; // I need the number of bytes for each string.
		 count_read_elements=input.read(a);
			
		String NAME=new String(a,"UTF-8");
		
		out.println("NAme: "+NAME);

		 a=new byte[PASSWORD_length]; // I need the number of bytes for each string.
		 count_read_elements=input.read(a);
			
		String PASSWORD=new String(a,"UTF-8");

		out.println("PASSWORD: "+PASSWORD);
		
		

		
		//while ((bytesRead = input.read(buffer)) != -1) {
		
		out.close();
		input.close();
	}
}
