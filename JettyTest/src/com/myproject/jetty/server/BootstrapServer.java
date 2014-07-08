package com.myproject.jetty.server;

public class BootstrapServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JettyTestServer server = new JettyTestServer("./jetty/etc/jetty.xml", "/test");
		server.startServer();
	}

}
