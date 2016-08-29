package server;


import introsde.assignment.soap.PeopleWSImplementation;

import java.net.InetAddress;
import java.net.URI;

import javax.xml.ws.Endpoint;


public class Server {
	private static int port = 7000;
	private static boolean asLocalhost = false;

	public static void main(String[] args) throws Exception {

		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (asLocalhost)
			hostname = "localhost";

		if (args.length >= 1)
			port  = Integer.parseInt(args[0]);
		else
			System.out.println("No port specified in arguments. Using default port: " + port);
		
		String url_str = String.format("http://%s:%d/ws", hostname, port);
		URI baseUrl = new URI(url_str);

		Endpoint.publish(url_str, new PeopleWSImplementation());
		
		System.out.println("Server started: " + baseUrl);
	}
}