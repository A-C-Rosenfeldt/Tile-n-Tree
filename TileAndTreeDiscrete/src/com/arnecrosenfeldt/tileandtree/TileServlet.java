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

@SuppressWarnings("serial")
public class TileServlet extends HttpServlet {

	private static java.util.List<com.arnecrosenfeldt.tileandtree.User> logins = new java.util.ArrayList<com.arnecrosenfeldt.tileandtree.User>(); //[3]; // in contrast with google user

	// com.arnecrosenfeldt.tileandtree.User

	public TileServlet() {
		// ToDo: Take cara that these static code does not end up in production.
		logins.add(new com.arnecrosenfeldt.tileandtree.User("Hans", 5, "WurstZabc", 0));
		logins.add(new com.arnecrosenfeldt.tileandtree.User("Joe Doe", 1, "1234", 1));
		logins.add(new com.arnecrosenfeldt.tileandtree.User("James Bond", 7, "SPECTRE", 2));
	}

	// http://stackoverflow.com/questions/15056686/sending-a-blob-to-a-servlet-through-ajax
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/*
		 * 
		 * 
		 * http://stackoverflow.com/questions/15611653/implementing-http-basic-authentication-in-a-servlet
		 
		 public Credentials credentialsWithBasicAuthentication(HttpServletRequest req) {
    String authHeader = req.getHeader("Authorization");
    if (authHeader != null) {
        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
            String basic = st.nextToken();

            if (basic.equalsIgnoreCase("Basic")) {
                try {
                    String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
                    LOG.debug("Credentials: " + credentials);
                    int p = credentials.indexOf(":");
                    if (p != -1) {
                        String login = credentials.substring(0, p).trim();
                        String password = credentials.substring(p + 1).trim();

                        return new Credentials(login, password);
                    } else {
                        LOG.error("Invalid authentication token");
                    }
                } catch (UnsupportedEncodingException e) {
                    LOG.warn("Couldn't retrieve authentication", e);
                }
            }
        }
    }

    return null;
}
		 
		 * 
		 */
		
		
		
		/*
		 http://www.coderanch.com/t/352345/Servlets/java/HTTP-basic-authentication-Web-Applications
		 
		 import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class BasicAuthentication extends HttpServlet { 
    Hashtable validUsers = new Hashtable();
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // ie this user has no password
        validUsers.put("james:","authorized");
          
        validUsers.put("jswan:mypassword","authorized");
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
         
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        // Get Authorization header
        String auth = req.getHeader("Authorization");
        // Do we allow that user?
        if (!allowUser(auth)) {
            // Not allowed, so report he's unauthorized
            res.setHeader("WWW-Authenticate", "BASIC realm=\"jswan test\"");
            res.sendError(res.SC_UNAUTHORIZED);
            // Could offer to add him to the allowed user list
        } else {
            // Allowed, so show him the secret stuff
            out.println("Top-secret stuff");
        }
    }
    // This method checks the user information sent in the Authorization
    // header against the database of users maintained in the users Hashtable.
    protected boolean allowUser(String auth) throws IOException {
         
        if (auth == null) {
            return false;  // no auth
        }
        if (!auth.toUpperCase().startsWith("BASIC ")) { 
            return false;  // we only do BASIC
        }
        // Get encoded user and password, comes after "BASIC "
        String userpassEncoded = auth.substring(6);
        // Decode it, using any base 64 decoder
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));
     
        // Check our user list to see if that user and password are "allowed"
        if ("authorized".equals(validUsers.get(userpassDecoded))) {
            return true;
        } else {
            return false;
        }
    }
}
		
		
		*/
		response.setContentType("application/octet-stream"); // resp.setContentType("text/plain");  
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		// This seems hard to be found using GIS
		String username = request.getParameter(FIELD_USER);
		String password = request.getParameter(FIELD_PASSWORD);

		
		if (username==null || password==null) {
			// http://leonjza.github.io/blog/2013/06/25/dtob-dot-py-digest-to-basic-authentication-a-simple-example-of-a-authentication-downgrade-attack/
			// Don’t stress too much about the realm part. In short, this is usually used to give the user a short message like “Restricted Area” etc.
			String messageToUser="Please enter ASCII user name and password";
			response.addHeader("WWW-Authenticate","Basic realm=\""+messageToUser+"\"");
			
			response.sendError(response.SC_UNAUTHORIZED); //, "Unauthorized, please supply login using your browser.");
		
			
			
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/html");
	        out.println("");
	        out.println("");
	        out.println("");
	        out.println("	This is a response to an HTTP " + request.getMethod() + " request.	");
	        
	        out.println("");
	        out.println("");
			//header('WWW-Authenticate: Basic realm="My Realm"');
			return;
		}
		
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

		ServletOutputStream out = response.getOutputStream();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("USER"); //.addSort("ID", Query.SortDirection.ASCENDING);
		List<Entity> users = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));

		for (Entity user : users) {
			byte[] a = new byte[4]; // I need the number of bytes for each string.

			String NAME = user.getKey().getName(); //"Joe Doe";
			a[0] = (byte) NAME.length();
			a[1] = ((Long) user.getProperty("smallID")).byteValue();//7; // Id
			String PASSWORD = (String) user.getProperty("PASSWORD"); //login.getPassword();//"1234";
			a[2] = (byte) PASSWORD.length();
			a[3] = ((Long) user.getProperty("FUNCTION")).byteValue();//1; // FUNCTION_value  0=none (ident) per best practice for enums
			out.write(a);

			a = NAME.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

			a = PASSWORD.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

		}
		/*
		for (com.arnecrosenfeldt.tileandtree.User login : logins) {
			byte[] a = new byte[4]; // I need the number of bytes for each string.

			String NAME = login.getName(); //"Joe Doe";
			a[0] = (byte) NAME.length();
			a[1] = (byte) login.getId();//7; // Id
			String PASSWORD = login.getPassword();//"1234";
			a[2] = (byte) PASSWORD.length();
			a[3] = (byte) login.getFunction();//1; // FUNCTION_value  0=none (ident) per best practice for enums
			out.write(a);

			a = NAME.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

			a = PASSWORD.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

		}
		*/

		//out.write(buf); stringlängen wie in Pascal / Java intern

		// All this ugly manual memory allocation  is here to avoid that even more ugly   magic string of formData

		//for (int i=0;i<3;i++) {

		/*
		for (com.arnecrosenfeldt.tileandtree.User login : logins) {
			byte[] a = new byte[4]; // I need the number of bytes for each string.

			String NAME = login.getName(); //"Joe Doe";
			a[0] = (byte) NAME.length();
			a[1] = (byte) login.getId();//7; // Id
			String PASSWORD = login.getPassword();//"1234";
			a[2] = (byte) PASSWORD.length();
			a[3] = (byte) login.getFunction();//1; // FUNCTION_value  0=none (ident) per best practice for enums
			out.write(a);

			a = NAME.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

			a = PASSWORD.getBytes("UTF-8"); // should be optimized away as there is nothing to convert
			out.write(a);

		}
		
		*/

		out.close();

		// input.close();
	}

	public static String FIELD_USER = "username";

	public static String FIELD_PASSWORD = "password";

	// Format: count, int, int, int.  Java is big endian. JavaScript is Typed Arrays are little-endian !!??
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	    //Authenticate user
		//http://www.javaxt.com/Tutorials/Javascript/Form_Based_HTTP_Authentication
		/*
	      String username = null;
	      java.util.HashMap<String, String> credentials = getCredentials(request);
	      if (credentials==null){
	          response.setStatus(401, "Access Denied");
	          response.setHeader("WWW-Authenticate", "Basic realm=\"My Site\"");
	          response.write("Unauthorized");
	          return;
	      }
	      */
		
		// This seems hard to be found using GIS
		String username = request.getParameter(FIELD_USER);
		String password = request.getParameter(FIELD_PASSWORD);

		if (username==null || password==null) {
			response.sendError(response.SC_UNAUTHORIZED); //, "Unauthorized, please supply login using your browser.");
			response.addHeader("WWW-Authenticate","Basic realm=\"bus driver\"");
			//header('WWW-Authenticate: Basic realm="My Realm"');
			return;
		}
		
		//////////////////////////

		InputStream input = request.getInputStream(); // ECMA script is not able to build XML. It can produce FormData where I have to provide magic markers (aaarg).

		byte[] a = new byte[4]; // I need the number of bytes for each string.
		int count_read_elements = input.read(a);

		PrintWriter out = response.getWriter();

		out.println("count_read_elements: " + count_read_elements);

		// All this ugly manual memory allocation  is here to avoid that even more ugly   magic string of formData
		byte NAME_lenght = a[0];
		out.println("NAme.length: " + NAME_lenght);
		byte ID = a[1];
		out.println("ID: " + ID);
		byte PASSWORD_length = a[2];
		out.println("PASSWORD_length: " + PASSWORD_length);
		byte FUNCTION_value = a[3];
		out.println("FUNCTION_value: " + FUNCTION_value);

		a = new byte[NAME_lenght]; // I need the number of bytes for each string.
		count_read_elements = input.read(a);

		String NAME = new String(a, "UTF-8");

		out.println("NAme: " + NAME);

		a = new byte[PASSWORD_length]; // I need the number of bytes for each string.
		count_read_elements = input.read(a);

		String PASSWORD = new String(a, "UTF-8");

		out.println("PASSWORD: " + PASSWORD);

		//while ((bytesRead = input.read(buffer)) != -1) {

		out.close();
		input.close();

		//logins.add(new com.arnecrosenfeldt.tileandtree.User(NAME, ID, PASSWORD, FUNCTION_value));

		Entity user = new Entity("USER", NAME);
		user.setProperty("smallID", ID);
		user.setProperty("FUNCTION", FUNCTION_value);
		user.setProperty("PASSWORD", PASSWORD);

		user.setProperty("FV", new Blob(new byte[] { 0, 2, 3 })); // TODO move to other kind of entry

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(user);
	}

	private byte[] hash(int function, byte[] password) {
		MessageDigest digest = null;
		try {
			switch (function) { // ToDo: Use Enum, or inheritance. Then this is splattered across ECMAScript and Java and the compiler cannot help me.
			case 5:

				digest = MessageDigest.getInstance("MD5");
				break;
			case 384:

				digest = MessageDigest.getInstance("SHA-384");
				break;
			case 0:
			default:
				return password;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			// is this runtime because the code is in the jre, and may as well not be there 
			e.printStackTrace();
		}

		// salt. Maybe cached
		digest.update("com.arnecrosenfeldt.tileandtree".getBytes());
		// digest.update(UserService.);  If multiple instances of this app are running, we need more salt. 

		digest.update(password, 0, password.length);

		return digest.digest();
	}
}
